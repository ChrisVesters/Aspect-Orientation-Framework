/**
 * 
 * @file PointerType.java
 * @description This file contains the Pointer Type.
 * @author Chris Vesters
 * @date 28/3/13
 * 
 **/
package aofDot;

import aof.Type;

/**
 * 
 * This class represents the wildcard '..', which can be anything!
 * 
 **/
// TODO: move this up! since it will be common to have wildcards!
public class AnyType implements Type {

	@Override
	public boolean isAssignable(Type t) {
		return true;
	}
}