package aofDot;

import java.util.ArrayList;
import aof.Argument;
import aof.Match;
import aof.Pointcut;

public class EdgePointcut extends Pointcut {

	public final NodePointcut source;
	public final NodePointcut target;
	public final boolean directed;

	public EdgePointcut(NodePointcut source, NodePointcut target,
			boolean dir, ArrayList<Argument> args) {
		super(args, null);

		this.source = source;
		this.target = target;
		this.directed = dir;
	}

	@Override
	public Match encloses(Pointcut rule) {
		assert (rule != null);

		Match match = new Match();
		
		if (!(rule instanceof EdgePointcut)) {
			return null;
		}

		EdgePointcut epc = (EdgePointcut) rule;

		// Check the source and target node.
		Match sourceMatch = this.source.encloses(epc.source);
		Match targetMatch = this.target.encloses(epc.target);
		if (sourceMatch == null) {
			return null;
		} else if (targetMatch == null) {
			return null;
		}
		
		
		match.putAll(sourceMatch);
		match.putAll(targetMatch);

		// Check directionality
		if (this.directed && !epc.directed) {
			return null;
		} else if (!this.directed && epc.directed) {
			return null;
		}

		// Check the arguments.
		// Note that due to wildcards we have two cases:
		// - Wildcard just for value.
		// - Wildcard as entire attribute.
		
		for (int i = 0; i < this.args.size(); i++) {
			Argument thisArg = this.args.get(i);
			
			if (thisArg.type instanceof AnyType) {
				// The '..' attribute always occurs at the end.
				// Since we matched up to now, we match for the rest as well!
				return match;
			} else {
				// Look for the same attribute.
				boolean found = false;
				for (int j = 0; j < epc.args.size(); j++) {
					Argument thatArg = epc.args.get(j);

					if (!thisArg.name.equals(thatArg.name)) {
						continue;
					}
					
					if (thisArg.name.equals("_this")) {
						found = true;
						String thisValue = ((Value) thisArg.type).value;
						match.put(thisValue, thatArg);
						break;
					}

					assert (thisArg.type instanceof Value);
					assert (thatArg.type instanceof Value);

					String thisValue = ((Value) thisArg.type).value;
					String thatValue = ((Value) thatArg.type).value;

					if (thisValue.equals("..") || thisValue.equals(thatValue)) {
						found = true;
						match.put(thisValue, thatArg);
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
		// To check the other way around we simply compare the size of the
		// argument!

		if (this.args.size() != epc.args.size()) {
			return null;
		}
		
		return match;
	}

}