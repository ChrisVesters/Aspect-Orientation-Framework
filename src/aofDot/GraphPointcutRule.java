package aofDot;

import java.util.ArrayList;

import aof.Argument;
import aof.Match;
import aof.PointcutRule;

public class GraphPointcutRule extends PointcutRule {

	private String name;
	private ArrayList<PointcutRule> body;
	
	public GraphPointcutRule(String name, ArrayList<PointcutRule> rules, ArrayList<Argument> args) {
		super(args, null);
		
		this.name = name;
		this.body = rules;
	}

	@Override
	public Match encloses(PointcutRule rule) {
		assert (rule != null);

		Match match = new Match();
		
		if (!(rule instanceof GraphPointcutRule)) {
			return null;
		}

		GraphPointcutRule gpc = (GraphPointcutRule) rule;
		
		// Check each rule of the body!
		// Note that we reverse it, we will check for each rule in gpc every rule in this.
		// This makes it possible that a rule in this covers multiple rules in gpc!
		for (PointcutRule thatRule: gpc.body) {
			boolean found = false;
			
			for (PointcutRule thisRule: this.body) {
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
