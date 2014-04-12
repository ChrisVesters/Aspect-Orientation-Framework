package aofDot;

import java.util.ArrayList;

import aof.Argument;
import aof.Match;
import aof.Pointcut;

public class GraphPointcut extends Pointcut {

	private String name;
	private ArrayList<Pointcut> body;
	
	public GraphPointcut(String name, ArrayList<Pointcut> rules, ArrayList<Argument> args) {
		super(args, null);
		
		this.name = name;
		this.body = rules;
	}

	@Override
	public Match encloses(Pointcut rule) {
		assert (rule != null);

		Match match = new Match();
		
		if (!(rule instanceof GraphPointcut)) {
			return null;
		}

		GraphPointcut gpc = (GraphPointcut) rule;
		
		// Check each rule of the body!
		// Note that we reverse it, we will check for each rule in gpc every rule in this.
		// This makes it possible that a rule in this covers multiple rules in gpc!
		for (Pointcut thatRule: gpc.body) {
			boolean found = false;
			
			for (Pointcut thisRule: this.body) {
				Match map = thisRule.encloses(thatRule);
				
				if (map == null) {
					continue;
				}
				
				found = true;
				match.putAll(map);
			}
			
			if (!found) {
				return null;
			}
		}

		return match;
	}

}
