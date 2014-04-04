/**
 * 
 */
package aofDot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.TreeMap;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;

import aof.Advice;
import aof.Argument;
import aof.Match;
import aof.PointcutRule;
import aof.Weaver;
import aofDot.AspectParser.AODotLexer;
import aofDot.AspectParser.AODotParser;
import aofDot.DotParser.Parser;
import aofDot.DotParser.objects.Edge;
import aofDot.DotParser.objects.Graph;
import aofDot.DotParser.objects.Id;
import aofDot.DotParser.objects.Node;
import aofDot.DotParser.objects.PortNode;

/**
 * @author Chris
 *
 */
// TODO: comments
public class DotWeaver extends Weaver {
	
	/**
	 * 
	 * This method creates a joinpoint (pointcutrule) based on a node.
	 * 
	 * @param node
	 * @return
	 */
	private NodePointcutRule createPointcutRule(Node node) {
		assert (node != null);
		
		ArrayList<Argument> arguments = new ArrayList<Argument>();
		Hashtable<String, String> attrs = node.getAttributes();
		for (String key: attrs.keySet()) {
			arguments.add(new Argument(key, new Value(attrs.get(key))));
		}
		arguments.add(0, new Argument("_this", new Value(node.getId().getId())));
		return new NodePointcutRule(node.getId().getId(), arguments);
	}
	
	/**
	 * 
	 * This method creates a joinpoint (pointcutrule) based on an edge.
	 * 
	 * @param node
	 * @return
	 */
	private EdgePointcutRule createPointcutRule(Edge edge) {
		assert (edge != null);
		
		ArrayList<Argument> arguments = new ArrayList<Argument>();
		Hashtable<String, String> attrs = edge.getAttributes();
		for (String key: attrs.keySet()) {
			arguments.add(new Argument(key, new Value(attrs.get(key))));
		}
		arguments.add(0, new Argument("_this", new Value(edge.toString())));
		
		NodePointcutRule source = createPointcutRule(edge.getSource().getNode());
		NodePointcutRule target = createPointcutRule(edge.getTarget().getNode());
		
		boolean dir = (edge.getType() == 2);
		return new EdgePointcutRule(source, target, dir, arguments);
	}
	
	
	public GraphPointcutRule createPointcutRule(Graph graph) {
		assert (graph != null);
		
		ArrayList<PointcutRule> body = new ArrayList<PointcutRule>();
		ArrayList<Argument> gArgs = new ArrayList<Argument>();
		
		for (Node node: graph.getNodes(false)) {
			body.add(createPointcutRule(node));
		}
		
		for (Edge edge: graph.getEdges()) {
			body.add(createPointcutRule(edge));
		}
		
		return new GraphPointcutRule(graph.getId().getId(), body ,gArgs);
	}
	
	
	/**
	 * 
	 * This method will execute all advices.
	 * 
	 * @param g
	 * @param advices
	 *
	 **/
	private void executeAdvices(Graph g, TreeMap<Advice, ArrayList<Match>> advices) {
		assert (g != null);
		assert (advices != null);
		
		ArrayList<Node> nodes = g.getNodes(true);
		ArrayList<Edge> edges = g.getEdges();
		
		for (Advice advice: advices.keySet()) {
			assert (advice instanceof DotAdvice);
			
			DotAdvice dAdvice = (DotAdvice) advice;
			
			switch (dAdvice.action) {
			case INSERT:
				for (Match match: advices.get(advice)) {
					for (PointcutRule rule: dAdvice.body) {
						if (rule instanceof NodePointcutRule) {
							NodePointcutRule npc = (NodePointcutRule) rule;
							
							if (match.containsKey(npc.name)) {
								// It is an argument!
								// Note that this does not have to be about a node!
								// It could be the entire edge as well!
								
								Value value = (Value) match.get(npc.name).type;
								
								for (Node node: nodes) {
									if (node.getId().getId().equals(value.value)) {
										// Add the attributes to the node.
										for (Argument arg: rule.getArgs()) {
											node.setAttribute(arg.name, ((Value) arg.type).value);
										}
									}
								}
								
								for (Edge edge: edges) {
									if (edge.toString().equals(value.value)) {
										// Add the attributes to the edge.
										for (Argument arg: rule.getArgs()) {
											edge.setAttribute(arg.name, ((Value) arg.type).value);
										}
									}
								}
							} else {
								// Not an argument!
								boolean found = false;
								
								for (Node node: nodes) {
									// TODO: wildcards!
									if (node.getId().getId().equals(npc.name)) {
										// Add the attributes to the node.
										found = true;
										for (Argument arg: rule.getArgs()) {
											node.setAttribute(arg.name, ((Value) arg.type).value);
										}
										
										break;
									}
								}
								
								if (!found) {
									// Create a new node!
									Node node = new Node();
									Id id = new Id();
									id.setId(npc.name);
									node.setId(id);

									for (Argument arg: rule.getArgs()) {
										node.setAttribute(arg.name, ((Value) arg.type).value);
									}
									g.addNode(node);
								}
							}
						} else if (rule instanceof EdgePointcutRule) {							
							EdgePointcutRule epc = (EdgePointcutRule) rule;
							
							NodePointcutRule source = epc.source;
							NodePointcutRule target = epc.target;

							// Note: an insert on an edge means that we have to insert these attributes!
							// Therefore we don't have to look for these!
							
							if (match.containsKey(source.name)) {
								String name = ((Value) match.get(source.name).type).value;
								ArrayList<Argument> nodeArgs = new ArrayList<Argument>();
								for (Argument arg: source.getArgs()) {
									nodeArgs.add(arg);
								}
								source = new NodePointcutRule(name, nodeArgs);
							}
							
							if (match.containsKey(target.name)) {
								String name = ((Value) match.get(target.name).type).value;
								ArrayList<Argument> nodeArgs = new ArrayList<Argument>();
								for (Argument arg: target.getArgs()) {
									nodeArgs.add(arg);
								}
								target = new NodePointcutRule(name, nodeArgs);
							}
							
							// Create the edge!
							// TODO: because of wildcards, it is possible we need to create multiple edges!

							Id sId = new Id();
							sId.setId(source.name);

							Node srcNode = g.findNode(sId);
							if (srcNode == null) {
								srcNode = new Node();
								srcNode.setId(sId);
									
								for (Argument arg: source.getArgs()) {
									srcNode.setAttribute(arg.name, ((Value) arg.type).value);
								}
									
								g.addNode(srcNode);
							} else {
								boolean check = true;
								for (Argument arg: source.getArgs()) {
									if (arg.type instanceof AnyType) {
										break;
									}
										
									if (srcNode.getAttribute(arg.name) == null) {
										check = false;
									} else if (!srcNode.getAttribute(arg.name).equals(((Value) arg.type).value)) {
										check = false;
									}
								}
									
								if (!check) {
									continue;
								}
							}
								
							Id tId = new Id();
							tId.setId(target.name);
							Node tgtNode = g.findNode(tId);
							if (tgtNode == null) {
								tgtNode = new Node();
								tgtNode.setId(tId);
									
								for (Argument arg: target.getArgs()) {
									tgtNode.setAttribute(arg.name, ((Value) arg.type).value);
								}
									
								g.addNode(tgtNode);
							} else {
								boolean check = true;
								for (Argument arg: target.getArgs()) {
									if (arg.type instanceof AnyType) {
										break;
									}
										
									if (tgtNode.getAttribute(arg.name) == null) {
										check = false;
									} else if (!tgtNode.getAttribute(arg.name).equals(((Value) arg.type).value)) {
										check = false;
									}
								}
									
								if (!check) {
									continue;
								}
							}
							
							// Check whether there exists an edge between these two nodes.
							boolean found = false;
							for (Edge edge: edges) {
								
								if (!edge.getSource().getNode().equals(srcNode)) {
									continue;
								} else if (!edge.getTarget().getNode().equals(tgtNode)) {
									continue;
								} else if (edge.getType() != (epc.directed ? Graph.DIRECTED : Graph.UNDIRECTED)) {
									continue;
								}
								
								// We found an exiting edge that matches the one we need to add!
								// Just change it's attributes.
								for (Argument arg: rule.getArgs()) {
									edge.setAttribute(arg.name, ((Value) arg.type).value);
								}
								
								found = true;
							}
							
							if (!found) {
								int type = (epc.directed ? Graph.DIRECTED : Graph.UNDIRECTED);
								Edge edge = new Edge(new PortNode(srcNode), new PortNode(tgtNode), type);

								for (Argument arg: rule.getArgs()) {
									edge.setAttribute(arg.name, ((Value) arg.type).value);
								}
								g.addEdge(edge);
							}
						}
					}
				}
				break;
			case DELETE:
				for (Match match: advices.get(advice)) {
					for (PointcutRule rule: dAdvice.body) {
						if (rule instanceof NodePointcutRule) {
							NodePointcutRule npc = (NodePointcutRule) rule;
							
							if (match.containsKey(npc.name)) {
								// It is an argument!
								// Note that this does not have to be about a node!
								// It could be the entire edge as well!
								
								Value value = (Value) match.get(npc.name).type;
								
								for (Node node: nodes) {
									if (!node.getId().getId().equals(value.value)) {
										continue;
									}
									
									// Remove the attributes from the node.
									for (Argument arg: npc.getArgs()) {
										node.getAttributes().remove(arg.name);
									}
									
									break;
								}
								
								for (Edge edge: edges) {
									if (!edge.toString().equals(value.value)) {
										continue;
									}
									
									// Remove the attributes from the edge.
									for (Argument arg: npc.getArgs()) {
										edge.getAttributes().remove(arg.name);
									}
									
									break;
								}
							} else {
								// Not an argument!
							
								for (Node node: nodes) {
									if (!node.getId().getId().equals(npc.name)) {
										continue;
									}
										
									if (npc.getArgs().length == 0) {
										// We have to remove the entire node!
										nodes.remove(node);
									} else {
										// Remove the attributes from the node.
										for (Argument arg: npc.getArgs()) {
											node.getAttributes().remove(arg.name);
										}
									}
									break;
								}
							}
						} else if (rule instanceof EdgePointcutRule) {
							// Note: an edge can not be an argument!
							// If we use an edge as argument, it becomes a NodePointcutRule!
							// It can however contain arguments for it's source and target!
							
							EdgePointcutRule epc = (EdgePointcutRule) rule;
							NodePointcutRule source = epc.source;
							NodePointcutRule target = epc.target;
							ArrayList<Argument> args = new ArrayList<Argument>();
							args.add(new Argument("_this", new Value("this")));
							for (Argument arg: epc.getArgs()) {
								args.add(arg);
							}
							
							if (match.containsKey(source.name)) {
								String name = ((Value) match.get(source.name).type).value;
								ArrayList<Argument> nodeArgs = new ArrayList<Argument>();
								nodeArgs.add(new Argument("_this", new Value("this")));
								for (Argument arg: source.getArgs()) {
									nodeArgs.add(arg);
								}
								source = new NodePointcutRule(name, nodeArgs);
							}
							
							if (match.containsKey(target.name)) {
								String name = ((Value) match.get(target.name).type).value;
								ArrayList<Argument> nodeArgs = new ArrayList<Argument>();
								nodeArgs.add(new Argument("_this", new Value("this")));
								for (Argument arg: target.getArgs()) {
									nodeArgs.add(arg);
								}
								target = new NodePointcutRule(name, nodeArgs);
							}
							
							if (epc.getArgs().length == 0) {
								args.add(new Argument(null, new AnyType()));
							}
							
							EdgePointcutRule compare = new EdgePointcutRule(source, target, epc.directed, args);
							
							for (Edge edge: edges) {
								if (compare.encloses(createPointcutRule(edge)) == null) {
									continue;
								}

								if (epc.getArgs().length == 0) {
									// We have to remove the entire edge!
									edges.remove(edge);
								} else {
									// Remove the attributes from the edge.
									for (Argument arg: epc.getArgs()) {
										if (arg.type instanceof AnyType) {
											edge.getAttributes().clear();
											break;
										}
										
										edge.getAttributes().remove(arg.name);
									}
								}
								break;
							}
						}
					}
				}
				break;
			}
		}
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Weaver.main(args);

		DotWeaver weaver = new DotWeaver();
		
		// Parse the aspect files.
		for (String aspectFile : Weaver.aspectFiles) {
			FileInputStream in = new FileInputStream(
					System.getProperty("user.dir") + File.separatorChar
							+ aspectFile);
			ANTLRInputStream input = new ANTLRInputStream(in);
			AODotLexer aspectLexer = new AODotLexer(input);
			CommonTokenStream aspectTokens = new CommonTokenStream(aspectLexer);
			AODotParser aspectParser = new AODotParser(aspectTokens);
			aspectParser.file();
		}
		
		if (Weaver.sourceFiles.size() == 0) {
			return;
		}
		
		for (String sourceFile : Weaver.sourceFiles) {
			File f = new File(System.getProperty("user.dir") + File.separatorChar
					+ sourceFile);
			FileReader in = new FileReader(f);
			Parser p = new Parser(in);
			p.parse(in);
			
			// Search for all joinpoints in the source file.
			
			for (Graph graph: p.getGraphs()) {
				TreeMap<Advice, ArrayList<Match>> advices = new TreeMap<Advice, ArrayList<Match>>(weaver.new AdviceComparator());
				
				// Create a GraphPointcutRule.
				{	
					GraphPointcutRule jp = weaver.createPointcutRule(graph);
					TreeMap<Advice, Match> execAdvices = weaver.executingAdvices(jp);
					for (Advice advice: execAdvices.keySet()) {
						ArrayList<Match> matches = new ArrayList <Match>();
						matches.add(execAdvices.get(advice));
						
						if (advices.containsKey(advice)) {
							matches.addAll(advices.get(advice));
						}
							
						advices.put(advice, matches);
					}
				}
				
				// Create a NodePointcutRule for each node.
				for (Node node: graph.getNodes(false)) {
					NodePointcutRule jp = weaver.createPointcutRule(node);
					
					TreeMap<Advice, Match> execAdvices = weaver.executingAdvices(jp);
					for (Advice advice: execAdvices.keySet()) {
						ArrayList<Match> matches = new ArrayList <Match>();
						matches.add(execAdvices.get(advice));
						
						if (advices.containsKey(advice)) {
							matches.addAll(advices.get(advice));
						}
						
						advices.put(advice, matches);
					}
				}
				
				// Create an EdgePointcutRule for each edge.
				for (Edge edge: graph.getEdges()) {
					EdgePointcutRule jp = weaver.createPointcutRule(edge);

					TreeMap<Advice, Match> execAdvices = weaver.executingAdvices(jp);
					for (Advice advice: execAdvices.keySet()) {
						ArrayList<Match> matches = new ArrayList <Match>();
						matches.add(execAdvices.get(advice));
						
						if (advices.containsKey(advice)) {
							matches.addAll(advices.get(advice));
						}
						
						advices.put(advice, matches);
					}
				}
				
				weaver.executeAdvices(graph, advices);
			}
			
			// Write it to the new file.
			String[] parts = sourceFile.split(String.valueOf(File.separatorChar));
			FileWriter writer = new FileWriter(System.getProperty("user.dir")
					+ File.separatorChar + Weaver.outputDir + File.separatorChar
					+ parts[parts.length-1]);
            for(Graph graph: p.getGraphs()) {
            	writer.append(graph.toString());
            }
            
    		writer.flush();
    		writer.close();
		}
	}
}