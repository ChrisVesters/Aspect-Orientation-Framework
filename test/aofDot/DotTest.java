package aofDot;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Test;

import aofDot.DotParser.Parser;
import aofDot.DotParser.objects.Edge;
import aofDot.DotParser.objects.Graph;
import aofDot.DotParser.objects.Node;

public class DotTest {

	private String output = "test" + File.separatorChar + "output" + File.separatorChar + "Dot" + File.separatorChar;
	private String source = "test" + File.separatorChar + "source" + File.separatorChar + "Dot" + File.separatorChar;

	@After
	public void tearDown() throws Exception {
		File outFile = new File(output + "source.viz");
		outFile.delete();
	}
	
	private boolean compare(String file1, String file2) throws IOException {
		assert (file1 != null);
		assert (file2 != null);		

		ArrayList <Graph> graphs1;
		ArrayList <Graph> graphs2;
		try {
			File f = new File(file1);
			FileReader in = new FileReader(f);
			Parser p = new Parser(in);
			p.parse(in);
			graphs1 = p.getGraphs();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		try {
			File f = new File(file2);
			FileReader in = new FileReader(f);
			Parser p = new Parser(in);
			p.parse(in);
			graphs2 = p.getGraphs();
		} catch (Exception e) {
			return false;
		}
		
		assert (graphs1.size() == graphs2.size());
		assert (graphs1.size() == 1);
		
		Graph g = graphs1.get(0);
		Graph h = graphs2.get(0);
		
		ArrayList<Node> nodes = h.getNodes(true);
		for (Node node: g.getNodes(true)) {
			for (Node n: nodes) {
				if (n.toString().equals(node.toString())) {
					nodes.remove(n);
					break;
				}
			}
		}
		
		ArrayList<Edge> edges = h.getEdges();
		for (Edge edge: g.getEdges()) {
			for (Edge e: edges) {
				if (e.toString().equals(edge.toString())) {
					edges.remove(e);
					break;
				}
			}
		}

		if ((nodes.size() == 0) && (edges.size() == 0)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Test
	public void NoAspectFile() {
		String[] args = {"-s", source + "source.viz", "-oDir", output};
		try {
			DotWeaver.main(args);
			String oFile = output + "source.viz";
			String sFile = output + "NoAspectFile.viz";
			
			assertTrue(compare(oFile, sFile));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void NoSourceFile() {
		String[] args = {"-a", source + "InsertAdvice.aof", "-oDir", output};
		File oFile = null;
		try {
			DotWeaver.main(args);
			
			oFile = new File(output + "source.viz");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		assertFalse(oFile.exists());
	}
	
	@Test
	public void NoMatchingAspects() {
		String[] args = {"-a", source + "NoMatchingAspects.aof", "-s", source + "source.viz", "-oDir", output};
		try {
			DotWeaver.main(args);
			String oFile = output + "source.viz";
			String sFile = output + "NoMatchingAspects.viz";
			
			assertTrue(compare(oFile, sFile));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void InsertAdvice() {
		String[] args = {"-a", source + "InsertAdvice.aof", "-s", source + "source.viz", "-oDir", output};
		try {
			DotWeaver.main(args);
			String oFile = output + "source.viz";
			String sFile = output + "InsertAdvice.viz";
			
			assertTrue(compare(oFile, sFile));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void DeleteAdvice() {
		String[] args = {"-a", source + "DeleteAdvice.aof", "-s", source + "source.viz", "-oDir", output};
		try {
			DotWeaver.main(args);
			String oFile = output + "source.viz";
			String sFile = output + "DeleteAdvice.viz";
			
			assertTrue(compare(oFile, sFile));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void EdgePointcut() {
		String[] args = {"-a", source + "EdgePointcut.aof", "-s", source + "source.viz", "-oDir", output};
		try {
			DotWeaver.main(args);
			String oFile = output + "source.viz";
			String sFile = output + "EdgePointcut.viz";
			
			assertTrue(compare(oFile, sFile));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void EdgeAdvice() {
		String[] args = {"-a", source + "EdgeAdvice.aof", "-s", source + "source.viz", "-oDir", output};
		try {
			DotWeaver.main(args);
			String oFile = output + "source.viz";
			String sFile = output + "EdgeAdvice.viz";
			
			assertTrue(compare(oFile, sFile));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void OrderAdvicesOption1() {
		String[] args = {"-a", source + "OrderAdvicesOption1.aof", "-s", source + "source.viz", "-oDir", output};
		try {
			DotWeaver.main(args);
			String oFile = output + "source.viz";
			String sFile = output + "OrderAdvicesOption1.viz";
			
			assertTrue(compare(oFile, sFile));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void OrderAdvicesOption2() {
		String[] args = {"-a", source + "OrderAdvicesOption2.aof", "-s", source + "source.viz", "-oDir", output};
		try {
			DotWeaver.main(args);
			String oFile = output + "source.viz";
			String sFile = output + "OrderAdvicesOption2.viz";
			
			assertTrue(compare(oFile, sFile));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void ArgumentNode() {
		String[] args = {"-a", source + "ArgumentNode.aof", "-s", source + "source.viz", "-oDir", output};
		try {
			DotWeaver.main(args);
			String oFile = output + "source.viz";
			String sFile = output + "ArgumentNode.viz";
			
			assertTrue(compare(oFile, sFile));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void ArgumentEdge() {
		String[] args = {"-a", source + "ArgumentEdge.aof", "-s", source + "source.viz", "-oDir", output};
		try {
			DotWeaver.main(args);
			String oFile = output + "source.viz";
			String sFile = output + "ArgumentEdge.viz";
			
			assertTrue(compare(oFile, sFile));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void WildcardNode() {
		String[] args = {"-a", source + "WildcardNode.aof", "-s", source + "source.viz", "-oDir", output};
		try {
			DotWeaver.main(args);
			String oFile = output + "source.viz";
			String sFile = output + "WildcardNode.viz";
			
			assertTrue(compare(oFile, sFile));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void WildcardEdge() {
		String[] args = {"-a", source + "WildcardEdge.aof", "-s", source + "source.viz", "-oDir", output};
		try {
			DotWeaver.main(args);
			String oFile = output + "source.viz";
			String sFile = output + "WildcardEdge.viz";
			
			assertTrue(compare(oFile, sFile));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void WildcardGraph() {
		String[] args = {"-a", source + "WildcardGraph.aof", "-s", source + "source.viz", "-oDir", output};
		try {
			DotWeaver.main(args);
			String oFile = output + "source.viz";
			String sFile = output + "WildcardGraph.viz";
			
			assertTrue(compare(oFile, sFile));
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}