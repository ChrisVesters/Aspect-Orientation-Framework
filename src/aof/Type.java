/**
 * 
 * @file Type.java
 * @description This file contains the Type interface.
 * @author Chris Vesters
 * @date 21/12/13
 * 
 **/
package aof;

/**
 * 
 * This class represents the interface type.
 * This will be used to be able to do the required things with the type.
 * 
 **/
public interface Type {

	/**
	 * 
	 * Can we assign another type to this one?
	 * 
	 * @param t
	 * @return
	 *
	 **/
	public boolean isAssignable(Type t);
	
	// TODO: do we need this?
	public String toString();
}
