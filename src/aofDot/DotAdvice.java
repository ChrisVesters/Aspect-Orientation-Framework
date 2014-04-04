package aofDot;

import java.util.ArrayList;

import aof.Advice;
import aof.Argument;
import aof.PointcutRule;

public class DotAdvice extends Advice {

	final Action action;
	final ArrayList<PointcutRule> body;
	
	public DotAdvice(String name, ArrayList<Argument> args, Action action, ArrayList<PointcutRule> stmts) {
		super(name, args);
		
		this.action = action;
		this.body = stmts;
	}

	@Override
	public String toString() {
		return action + " " + body;
		// TODO Auto-generated method stub
		//return null;
	}

}
