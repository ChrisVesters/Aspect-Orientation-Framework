/**
 * 
 * @file FunctionType.java
 * @description This file contains the Function Type.
 * @author Chris Vesters
 * @date 28/3/13
 * 
 **/
package aofC.Cparser.symbolTable;

/**
 * 
 * This class represents a function type.
 * 
 **/
public class FunctionType extends CType {

	/**
	 * 
	 * The return type of the function.
	 * 
	 **/
	public final CType returnType;

	/**
	 * 
	 * The types of the arguments of the function.
	 * 
	 **/
	public final CType[] argsType;
	public final String[] argsName;

	/**
	 * 
	 * Constructor
	 * 
	 * @param returnType
	 *            The return type of the function.
	 * @param argsType
	 *            The types of the arguments.
	 * 
	 **/
	public FunctionType(CType returnType, CType[] argsType, String[] argsName) {
		assert (returnType != null);
		assert (argsType != null);

		this.returnType = returnType;
		this.argsType = argsType;
		this.argsName = argsName;
	}

	@Override
	public CType clone() {
		CType[] clonedArray = new CType[this.argsType.length];
		for (int i = 0; i < this.argsType.length; i++) {
			clonedArray[i] = this.argsType[i].clone();
		}
		return new FunctionType(this.returnType.clone(), clonedArray, this.argsName);
	}

	@Override
	public PrimaryType getPrimary() {
		// Should never happen
		return null;
	}

	@Override
	public boolean same(CType other) {
		if (other instanceof FunctionType) {
			FunctionType otherType = (FunctionType) other;
			if (!this.returnType.same(otherType.returnType)) {
				return false;
			} else if (this.argsType.length != otherType.argsType.length) {
				return false;
			}

			// We know the amount of arguments is the same, now compare them!
			for (int i = 0; i < this.argsType.length; i++) {
				if (!this.argsType[i].same(otherType.argsType[i])) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String print(){
		return "Function call";
	}

	@Override
	public char getChar() {
		return this.returnType.getChar();
	}
}
