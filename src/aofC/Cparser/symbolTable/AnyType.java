/**
 * 
 * @file PointerType.java
 * @description This file contains the Pointer Type.
 * @author Chris Vesters
 * @date 28/3/13
 * 
 **/
package aofC.Cparser.symbolTable;

/**
 * 
 * This class represents the wildcard '..', which can be anything!
 * 
 **/
// TODO: move this up! since it will be common to have wildcards!
public class AnyType extends CType {

	@Override
	public CType clone() {
		return new AnyType();
	}

	@Override
	public PrimaryType getPrimary() {
		return null;
	}

	public boolean same(CType other) {
		return true;
	}
	
	@Override
	public String print(){
		return "..";
	}

	@Override
	public char getChar() {
		return 0;
	}
}