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
 * This class represents a pointer type.
 * 
 **/
public class PointerType extends CType {

	/**
	 * 
	 * The type of the variable it is pointing to.
	 * 
	 **/
	public CType refType;

	/**
	 * 
	 * Constructor
	 * 
	 * @param refType
	 *            The type of the variable it is pointing to.
	 * 
	 **/
	public PointerType(CType refType) {
		this.refType = refType;
	}

	@Override
	public CType clone() {
		if (this.refType != null) {
			return new PointerType(this.refType.clone());
		} else {
			return new PointerType(null);
		}
	}

	@Override
	public PrimaryType getPrimary() {
		if (this.refType == null) {
			return null;
		} else {
			return refType.getPrimary();
		}
	}

	@Override
	public void setBottom(CType bottom) {
		if (this.refType == null) {
			this.refType = bottom;
		} else {
			this.refType.setBottom(bottom);
		}
	}

	public boolean same(CType other) {
		if (other instanceof PointerType) {
			if (this.refType != null) {
				return this.refType.same(((PointerType) other).refType);
			} else {
				return (((PointerType) other).refType == null);
			}
		}
		return false;
	}
	
	@Override
	public String print(){
		return "Pointer to " + this.refType.print();
	}

	@Override
	public char getChar() {
		return 'a';
	}
}