package aofC;

import java.util.ArrayList;
import java.util.HashMap;

import aof.Argument;
import aof.Match;
import aof.PointcutRule;
import aofC.Cparser.symbolTable.CType;
import aofC.Cparser.symbolTable.AnyType;

// TODO: add comments.
public class MethodPointcutRule extends PointcutRule {
	
	// TODO: rename JoinPoint
	public enum JoinPoint {
		CALL, EXECUTE
	}
	
	// The methodSignature does not have arguments, because these are present in the PointcutRule itself!
	JoinPoint joinPoint;
	CType returnType;
	String name;
	
	public MethodPointcutRule(JoinPoint jp, CType returnType, String name, ArrayList<Argument> args) {
		super (args, null);
		
		assert (jp != null);
		assert (returnType != null);
		assert ((name != null) && (name.trim() != ""));
		
		this.joinPoint = jp;
		this.returnType = returnType;
		this.name = name;
	}
	
	//@Override
	public Match encloses(PointcutRule rule) {
		assert (rule != null);
		
		if (!(rule instanceof MethodPointcutRule)) {
			return null;
		}
		
		MethodPointcutRule methodRule = (MethodPointcutRule) rule;
		
		// Check the joinpoint.
		if (!(this.joinPoint.equals(methodRule.joinPoint))) {
			return null;
		}
		
		// Now on type.
		// Note that due to wildcards we have two cases:
		// - Wildcard just for type.
		// - Wildcard as entire parameter (without name).
		HashMap<String, Argument> map = new HashMap<String, Argument>();
		int j = 0;
		for (int i = 0; i < this.args.size(); i++) {
			Argument thisArg = this.args.get(i);
			
			if ((thisArg.type instanceof AnyType) && (thisArg.name.isEmpty())) {
				// If there is a next argument, go look for that one.
				// If not, we match always!
				if (i + 1 < this.args.size()) {
					Argument targArg = methodRule.args.get(j);
					
					while (!this.args.get(i+1).isAssignable(targArg)) {
						j++;
						targArg = methodRule.args.get(j);
					}
				} else {
					j = methodRule.args.size();
					break;
				}
			} else {
				// There aren't any arguments in the other rule left!
				if (j >= methodRule.args.size()) {
					return null;
				}
				
				Argument targArg = methodRule.args.get(j);
				
				if (!thisArg.isAssignable(targArg)) {
					return null;
				}
				
				map.put(thisArg.name, targArg);
			}
			j++;
		}
		
		// Check sizes of the arguments.
		if (j < methodRule.args.size()) {
			return null;
		}
		
		// Note: since we are working with Small C,
		// it is allowed to require that the types are equal!
				
		// Compare the return types.
		if (!this.returnType.same(methodRule.returnType)) {
			return null;
		}
				
		// Compare the names.
		String regex = this.name.replace("..", ".*");
		if (!methodRule.name.matches(regex)) {
			return null;
		}
		
		return new Match(map);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MethodPointcutRule)) {
			return false;
		}
		
		MethodPointcutRule mpcr = (MethodPointcutRule) obj;
		
		// Compare names.
		if (!this.name.equals(mpcr.name)) {
			return false;
		}
		
		// Compare the moment
		if (this.joinPoint != mpcr.joinPoint) {
			return false;
		}
		
		// Compare size of arguments.
		if (this.args.size() != mpcr.args.size()) {
			return false;
		}
		
		// Compare types of arguments.
		for (int i = 0; i < this.args.size(); i++) {
			CType thisType = (CType) this.args.get(i).type;
			CType mpcrType = (CType) mpcr.args.get(i).type;
			if (!thisType.same(mpcrType)) {
				return false;
			}
		}
		
		// Note that we don't compare return types.
		// This is in most programming languages not used to distinct functions!
		
		return true;
	}
	
	@Override
	public int hashCode() {
		int value = 0;
		
		value += this.name.hashCode();
		value += this.joinPoint.hashCode();
		
		// Note that we don't use the haschode of the args.
		// Instead we use the hashcode of the types!
		for (Argument arg: this.args) {
			value += arg.type.hashCode();
		}
		
		return value;
	}
}