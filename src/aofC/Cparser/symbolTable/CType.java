/**
 * 
 * @file Type.java
 * @description This file contains the abstract Type.
 * @author Chris Vesters
 * @date 28/3/13
 * 
 **/
package aofC.Cparser.symbolTable;

import aof.Type;

/**
 * 
 * This class represents the abstract type for all variables and functions.
 * 
 **/
public abstract class CType implements Type {
	public abstract CType clone();

	public abstract PrimaryType getPrimary();

	public void setBottom(CType bottom) {
		// Standard way of dealing with this is doing nothing.
	};

	public abstract boolean same(CType other);
	
	public boolean equals(Type other) {
		assert (other instanceof CType);
		
		CType ctype = (CType) other;
		return same(ctype);
	}
	
	public boolean isAssignable(Type other) {
		assert (other instanceof CType);
		
		CType ctype = (CType) other;
		return same(ctype);
	}
	
	public abstract String print();
	public abstract char getChar();
}
