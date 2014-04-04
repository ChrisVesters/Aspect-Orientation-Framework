package aofC;

import java.util.ArrayList;
import java.util.HashMap;

import aof.Argument;
import aof.Match;
import aof.PointcutRule;

// TODO: add comments.
public class MemberPointcutRule extends PointcutRule {
	
	// TODO: rename JoinPoint
	public enum JoinPoint {
		SET, GET
	}
	
	// The type and name information are stored in the PointcutRule itself as argument!
	JoinPoint joinPoint;
	
	public MemberPointcutRule(JoinPoint jp, Argument arg) {
		super(new ArrayList<Argument>(), null);
		
		assert (jp != null);
		
		this.joinPoint = jp;
		this.args.add(arg);
	}
	
	@Override
	public Match encloses(PointcutRule rule) {
		assert (rule != null);
		
		if (!(rule instanceof MemberPointcutRule)) {
			return null;
		}

		MemberPointcutRule methodRule = (MemberPointcutRule) rule;

		// Check the joinpoint.
		if (!(this.joinPoint.equals(methodRule.joinPoint))) {
			return null;
		}

		// Compare the names.
		Argument thisArg = this.args.get(0);
		Argument targetArg = methodRule.args.get(0);
				
		String regex = thisArg.name.replace("..", ".*");
		if (!targetArg.name.matches(regex)) {
			return null;
		}

		// Compare types.
		if (!thisArg.isAssignable(targetArg)) {
			return null;
		}
		
		HashMap <String, Argument> map = new HashMap<String, Argument>();
		if (!thisArg.name.contains("..")) {
			map.put(thisArg.name, targetArg);
		}

		return new Match(map);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MemberPointcutRule)) {
			return false;
		}
		
		MemberPointcutRule mpcr = (MemberPointcutRule) obj;
		
		// Compare names.
		Argument thisArg = this.args.get(0);
		Argument targetArg = mpcr.args.get(0);
		
		if (!thisArg.name.equals(targetArg.name)) {
			return false;
		}

		// Compare types.
		if (!thisArg.isAssignable(targetArg)) {
			return false;
		}
		
		// Compare the moment
		if (this.joinPoint != mpcr.joinPoint) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		int value = 0;
		
		Argument arg = this.args.get(0);
		value += arg.name.hashCode();
		value += arg.type.hashCode();
		value += this.joinPoint.hashCode();
		
		return value;
	}
}