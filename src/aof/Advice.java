package aof;

import java.util.ArrayList;

// TODO: comments
/**
 * 
 * This class represents the advice. An advice contains what should happen. It
 * may also contain further information on when it should happen, but only
 * compared to the pointcut, thus: before or after the pointcut.
 * 
 * Note that it does not contain the pointcut, the Weaver holds this
 * information.
 * 
 **/
public abstract class Advice {
	
	/**
	 * 
	 * The name of the advice.
	 * Though it is not really required, it may come in handy!
	 * 
	 **/
	protected final String name;
	
	/**
	 * 
	 * The arguments of the advice.
	 * Note that these may differ in name from those of the pointcut.
	 * 
	 **/
	protected final ArrayList <Argument> param;
	
	/**
	 * 
	 * Standard constructor of the advice.
	 * 
	 * @param name The name of the advice.
	 * @param args The arguments of the advice.
	 *
	 **/
	public Advice(String name, ArrayList<Argument> args) {
		assert ((name != null) && (name.trim() != ""));
		assert (args != null);
		
		this.name = name;
		this.param = args;
	}
	
	/**
	 * 
	 * This method returns the parameters of the advice.
	 * 
	 * @return The parameters of the advice.
	 *
	 **/
	public Argument[] getParams() {
		return this.param.toArray(new Argument[0]);
	}
	
	/**
	 * 
	 * This method returns the name of the advice.
	 * 
	 * @return The name of the advice.
	 *
	 **/
	public String getName() {
		return this.name;
	}
	
	/**
	 * 
	 * This method returns a printable version of the advice.
	 * This method is used to write the advice to file.
	 * 
	 **/
	// TODO: This shouldn't be used in such a way!
	public abstract String toString();
}
