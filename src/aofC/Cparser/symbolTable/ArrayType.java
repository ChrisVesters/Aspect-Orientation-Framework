/**
 * 
 * @file ArrayType.java
 * @description This file contains the ArrayType Type.
 * @author Chris Vesters
 * @date 28/3/13
 * 
 **/
package aofC.Cparser.symbolTable;

/**
 * 
 * This class represents an array type.
 * 
 **/
public class ArrayType extends CType {

	/**
	 * 
	 * The type of the elements of the array.
	 * 
	 **/
	private CType elemType;

	/**
	 * 
	 * The amount of dimensions.
	 * 
	 **/
	public final int dimensions;

	/**
	 * 
	 * Constructor
	 * 
	 * @param elemType
	 *            The type of the elements of the array.
	 * @param size
	 *            The amount of dimension.
	 * 
	 **/
	public ArrayType(CType elemType, int size) {
		assert (size > 0);

		this.elemType = elemType;
		this.dimensions = size;
	}

	/**
	 * 
	 * This method returns the type of the array.
	 * 
	 * @return The type of the array
	 * 
	 **/
	public CType getType() {
		return this.elemType;
	}

	/**
	 * 
	 * This method will set the type of the array.
	 * 
	 * @param type
	 *            The type of the array.
	 * 
	 **/
	public void setType(CType type) {
		assert (type != null);
		assert (type != this);
		assert (this.elemType == null);

		this.elemType = type;
	}

	/**
	 * Make a deep copy of this Type
	 */
	@Override
	public CType clone() {
		if (this.elemType != null) {
			return new ArrayType(this.elemType, this.dimensions);
		} else {
			return new ArrayType(null, this.dimensions);
		}
	}

	@Override
	public PrimaryType getPrimary() {
		if (elemType == null) {
			return null;
		}

		return elemType.getPrimary();
	}

	@Override
	public boolean same(CType other) {
		if (other instanceof ArrayType) {
			ArrayType otherType = (ArrayType) other;
			if (this.elemType == null) {
				return ((otherType.elemType == null) && (this.dimensions == otherType.dimensions));
			}

			return (this.elemType.same(otherType.elemType) && (this.dimensions == otherType.dimensions));
		}
		return false;
	}

	@Override
	public String print() {
		return "Array of " + this.elemType.print();
	}
	
	public char getChar(){
		return this.elemType.getChar();
	}
	
	@Override
	public String toString() {
		String type = this.elemType.toString();
		for (int i = 0; i < this.dimensions; i++) {
			type += "[]";
		}
		
		return type;
	}
}