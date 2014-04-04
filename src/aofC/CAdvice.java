package aofC;

import java.util.ArrayList;

import aof.Advice;
import aof.Argument;

// TODO: add comments
public class CAdvice extends Advice {
	
	public final Moment moment; 
	private final String code;
	
	public CAdvice(String name, ArrayList<Argument> args, Moment moment, String code) {
		super (name, args);
		
		assert (moment != null);
		assert (code != null);
		
		this.moment = moment;
		this.code = code;
	}
	
	public String toString() {
		return code;
	}
}