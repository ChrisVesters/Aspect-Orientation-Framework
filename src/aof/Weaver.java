/**
 * 
 * @Filename Weaver.java
 * @Description This file contains the abstract Weaver.
 * 
 * @Author Chris Vesters
 * @Date 12/12/2013
 *
 **/
package aof;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

/**
 * 
 * This is the Weaver class.
 * This class contains all general aspects of weaving.
 *
 **/
public abstract class Weaver {

	/** The pointcuts specified in the aspect files. **/
	protected static HashMap<String, Pointcut> pointcuts = new HashMap<String, Pointcut>();
	/** The advices specified in the aspect files. **/
	protected static HashMap<String, Advice> advices = new HashMap<String, Advice>();
	/** A link between the pointcuts and the advices. **/
	protected static HashMap<String, ArrayList<String>> link = new HashMap<String, ArrayList<String>>();
	/** An order between the advices. We map an advice to it's predecessors! **/
	protected static HashMap<String, ArrayList<String>> order = new HashMap<String, ArrayList<String>>();
	/** The joinpoints encountered in the source files. **/
	protected static HashSet<PointcutRule> joinpoints = new HashSet<PointcutRule>();

	/**
	 * 
	 * This class will be used to determine the order between advices.
	 *
	 **/
	public class AdviceComparator implements Comparator<Advice> {

		/**
		 * 
		 * This method compares two advices.
		 * 
		 * @param adv0 The first advice.
		 * @param adv1 The second advice.
		 * 
		 * @return an integer representing order:
		 * 		0 if the advices are equal.
		 * 		1 if adv1 should occur before adv0.
		 * 		-1 if adv0 should occur before adv1;
		 * 
		 **/
		@Override
		public int compare(Advice adv0, Advice adv1) {
			assert ((adv0 != null) && Weaver.advices.containsKey(adv0.name));
			assert ((adv1 != null) && Weaver.advices.containsKey(adv1.name));

			// Same advice can never have an order!
			if (adv0.equals(adv1)) {
				return 0;
			}
			
			String adv0Name = adv0.name;
			String adv1Name = adv1.name;
			
			// Does adv0 has predecessors?
			if (Weaver.order.containsKey(adv0Name)) {
				// Is adv1 a predecessor of adv0?
				ArrayList<String> pred = Weaver.order.get(adv0Name);
				while (!pred.isEmpty()) {
					if (pred.contains(adv1Name)) {
						return 1;
					}
					
					ArrayList<String> anc = new ArrayList<>();
					for (String name: pred) {
						if (Weaver.order.containsKey(name)) {
							anc.addAll(Weaver.order.get(name));
						}
					}
					pred = anc;
				}
			}

			// Does adv1 has predecessors?
			if (Weaver.order.containsKey(adv1Name)) {
				// Is adv0 a predecessor of adv1?
				ArrayList<String> pred = Weaver.order.get(adv1Name);
				while (!pred.isEmpty()) {
					if (pred.contains(adv0Name)) {
						return -1;
					}
					
					ArrayList<String> anc = new ArrayList<>();
					for (String name: pred) {
						if (Weaver.order.containsKey(name)) {
							anc.addAll(Weaver.order.get(name));
						}
					}
					pred = anc;
				}
			}

			// There is no order specified between these two advices!
			// Still we have to specify some order, so use lexical order!
			return adv0Name.compareTo(adv1Name);
		}
	}

	/** All aspect files we have to handle. **/
	protected static ArrayList<String> aspectFiles = new ArrayList<String>();
	/** All source files we have to handle. **/
	protected static ArrayList<String> sourceFiles = new ArrayList<String>();
	/** The output directory in which to store the result. **/
	protected static String outputDir = null;

	/**
	 * 
	 * This method will add a pointcut to the weaver.
	 * 
	 * @param pc The pointcut we want to add.
	 * @return true if adding the pointcut was successful, false otherwise.
	 *
	 **/
	public static boolean addPointcut(Pointcut pc) {
		assert (pc != null);

		String name = pc.getName();

		if (Weaver.pointcuts.containsKey(name)) {
			return false;
		}

		Weaver.pointcuts.put(name, pc);

		return true;
	}

	/**
	 * 
	 * This method will add an advice to the weaver.
	 * 
	 * @param advice The advice we want to add.
	 * @param pc The pointcut on which the advice works.
	 * @return true if adding the advice was successful, false otherwise.
	 *
	 **/
	public static boolean addAdvice(Advice advice, String pc) {
		assert (advice != null);
		assert (pc != null);

		// Currently we assume that advices are added after the pointcut.
		String name = advice.name;

		// Is the name taken?
		if (Weaver.advices.containsKey(name)) {
			return false;
		}

		// Does the pointcut exist?
		if (!Weaver.pointcuts.containsKey(pc)) {
			return false;
		}

		// Are the types of advice and poincut compatible?
		Argument[] pArgs = Weaver.pointcuts.get(pc).getArgs();
		Argument[] aArgs = advice.getParams();
		if (pArgs.length != aArgs.length) {
			return false;
		}

		for (int i = 0; i < pArgs.length; i++) {
			if (!pArgs[i].isAssignable(aArgs[i])) {
				return false;
			}
		}

		Weaver.advices.put(name, advice);
		ArrayList<String> linked = Weaver.link.get(pc);

		if (linked == null) {
			linked = new ArrayList<String>();
		}

		linked.add(name);
		Weaver.link.put(pc, linked);
		
		return true;
	}

	/**
	 * 
	 * This method will add an order between advices.
	 * 
	 * @param pre The advice that should occur before the other one.
	 * @param post The advice that should occur after the other one.
	 * 
	 **/
	public static void addOrder(String pre, String post) {
		assert ((pre != null) && !pre.trim().equals(""));
		assert ((post != null) && !post.trim().equals(""));

		// The predecessors!
		ArrayList<String> pred = new ArrayList<String>();

		if (Weaver.order.containsKey(post)) {
			pred = Weaver.order.get(post);
		}
		pred.add(pre);

		Weaver.order.put(post, pred);
	}

	/**
	 * 
	 * This method will add a joinpoint to the weaver.
	 * 
	 * @param jp The joinpoint we want to add.
	 *
	 **/
	public static void addJoinpoint(PointcutRule jp) {
		assert (jp != null);
		
		if (!Weaver.joinpoints.contains(jp)) {
			Weaver.joinpoints.add(jp);
		}
	}

	/**
	 * 
	 * This method will initialize the weaver.
	 * 
	 * @param args The arguments specified.
	 * @throws Exception If there is an invalid argument.
	 * 
	 **/
	public static void main(String[] args) throws Exception {
		// First we need to clear possible remaining information!
		Weaver.pointcuts = new HashMap<String, Pointcut>();
		Weaver.advices = new HashMap<String, Advice>();
		Weaver.link = new HashMap<String, ArrayList<String>>();
		Weaver.order = new HashMap<String, ArrayList<String>>();
		Weaver.joinpoints = new HashSet<PointcutRule>();
		Weaver.aspectFiles = new ArrayList<String>();
		Weaver.sourceFiles = new ArrayList<String>();
		Weaver.outputDir = null;
		
		// Parse arguments:
		// -a AspectFiles
		// -s SourceFiles
		// -oDir OutputDir
		int state = 0;
		
		for (String arg : args) {
			if (arg.equals("-a")) {
				state = 1;
			} else if (arg.equals("-s")) {
				state = 2;
			} else if (arg.equals("-oDir")) {
				state = 3;
			} else if (arg.startsWith("-")) {
				// Invalid Syntax!
				throw new Exception();
			} else if (state == 1) {
				// Aspect file.
				aspectFiles.add(arg);
			} else if (state == 2) {
				// Source file.
				sourceFiles.add(arg);
			} else if ((state == 3) && (outputDir == null)) {
				// Output directory.
				outputDir = arg;
			} else {
				// Invalid syntax!
				throw new Exception();
			}
		}

		if (outputDir == null) {
			outputDir = "output";
		}

		// Create the output directory if it doesn't exist!
		File path = new File(System.getProperty("user.dir")
				+ File.separatorChar + Weaver.outputDir);
		if (!path.exists()) {
			path.mkdirs();
		}

		// Parsing should be done by the subclass Weavers.
	}

	
	protected HashMap<Pointcut, Match> enclosingPointcuts(PointcutRule jp) {
		assert (jp != null);

		HashMap<Pointcut, Match> pointcuts = new HashMap<Pointcut, Match>();
		for (Pointcut pointcut: Weaver.pointcuts.values()) {
			// Get the matching pointcutrule.
			for (PointcutRule rule : pointcut.getRules()) {
				Match match = rule.encloses(jp);
				if (match == null) {
					continue;
				} else if (match.size() < pointcut.getArgs().length) {
					continue;
				}

				pointcuts.put(pointcut, match);
				break;
			}
		}
		
		return pointcuts;
	}
	
	/**
	 * 
	 * This method gets all the advices that execute given a set of pointcuts.
	 * 
	 * @param pointcuts
	 * @return
	 */
	protected TreeMap<Advice, Match> executingAdvices(HashMap<Pointcut, Match> pointcuts) {
		TreeMap<Advice, Match> advices = new TreeMap<Advice, Match>(new AdviceComparator());
		
		for (Pointcut pointcut: pointcuts.keySet()) {
			String name = pointcut.getName();
			for (String advice: Weaver.link.get(name)) {
				Advice ad = Weaver.advices.get(advice);
				
				Match match = pointcuts.get(pointcut);
				Argument[] pArgs = pointcut.getArgs();
				Argument[] aArgs = ad.getParams();
				
				HashMap<String, Argument> map = new HashMap<String, Argument>();
				for (int i = 0; i < pArgs.length; i++) {
					Argument pArg = pArgs[i];
					Argument aArg = aArgs[i];
					
					map.put(aArg.name, match.get(pArg.name));
				}
				
				advices.put(ad, new Match(map));
			}
		}
		
		return advices;
	}
	
	/**
	 * 
	 * This method gets all the advices that execute given a joinpoint.
	 * 
	 * @param pointcuts
	 * @return
	 */
	protected TreeMap<Advice, Match> executingAdvices(PointcutRule jp) {
		assert (jp != null);
		
		HashMap<Pointcut, Match> pointcuts = enclosingPointcuts(jp);
		TreeMap<Advice, Match> advices = new TreeMap<Advice, Match>(new AdviceComparator());
		
		for (Pointcut pointcut: pointcuts.keySet()) {
			String name = pointcut.getName();

			if (!Weaver.link.containsKey(name)) {
				continue;
			}
			
			for (String advice: Weaver.link.get(name)) {
				Advice ad = Weaver.advices.get(advice);
				
				Match match = pointcuts.get(pointcut);
				Argument[] pArgs = pointcut.getArgs();
				Argument[] aArgs = ad.getParams();
				
				HashMap<String, Argument> map = new HashMap<String, Argument>();
				for (int i = 0; i < pArgs.length; i++) {
					Argument pArg = pArgs[i];
					Argument aArg = aArgs[i];
					
					map.put(aArg.name, match.get(pArg.name));
				}
				
				advices.put(ad, new Match(map));
			}
		}
		
		return advices;
	}
}