package aofDot;

import java.util.ArrayList;
import java.util.HashMap;

import aof.Argument;
import aof.Match;
import aof.PointcutRule;

public class NodePointcutRule extends PointcutRule {

	public final String name;
	
	public NodePointcutRule(String name, ArrayList<Argument> args) {
		super(args, null);
		
		this.name = name;
	}

	@Override
	public Match encloses(PointcutRule rule) {
		assert (rule != null);
		
		if (!(rule instanceof NodePointcutRule)) {
			return null;
		}
		
		NodePointcutRule npc = (NodePointcutRule) rule;
		
		// Compare the names.
		String regex = this.name.replace("..", ".*");
		if (!npc.name.matches(regex)) {
			return null;
		}
		
		// Check the arguments.
		// Note that due to wildcards we have two cases:
		// - Wildcard just for value.
		// - Wildcard as entire attribute.
		HashMap<String, Argument> map = new HashMap<String, Argument>();
		for (int i = 0; i < this.args.size(); i++) {
			Argument thisArg = this.args.get(i);
			
			if (thisArg.type instanceof AnyType) {
				// The '..' attribute always occurs at the end.
				// Since we matched up to now, we match for the rest as well!
				return new Match(map);
			} else {
				// Look for the same attribute.
				boolean found = false;
				for (int j = 0; j < npc.args.size(); j++) {
					Argument thatArg = npc.args.get(j);
					
					if (!thisArg.name.equals(thatArg.name)) {
						continue;
					}
					
					if (thisArg.name.equals("_this")) {
						found = true;
						String thisValue = ((Value) thisArg.type).value;
						map.put(thisValue, thatArg);
						break;
					}
					
					assert (thisArg.type instanceof Value);
					assert (thatArg.type instanceof Value);
					
					String thisValue = ((Value) thisArg.type).value;
					String thatValue = ((Value) thatArg.type).value;
					
					if (thisValue.equals("..") || thisValue.equals(thatValue)) {
						found = true;
						map.put(thisValue, thatArg);
						break;
					}
				}
				
				if (!found) {
					return null;
				}
			}
		}

		// Are all the attributes of the target enclosed?
		// The fact that we got here, means that this does not contain '..'
		// And that all attributes of this are in target.
		// To check the other way around we simply compare the size of the argument!
		
		if (this.args.size() != npc.args.size()) {
			return null;
		}
		
		return new Match(map);
	}

}