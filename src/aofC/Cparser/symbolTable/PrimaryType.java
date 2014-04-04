/**
 * 
 * @file PrimaryType.java
 * @description This file contains the Primary Types.
 * @author Chris Vesters
 * @date 28/3/13
 * 
 **/
package aofC.Cparser.symbolTable;

/**
 * 
 * This class represents a primary type (int, float, void, char).
 * 
 **/
public class PrimaryType extends CType {

	/**
	 * 
	 * An enumeration of all primary types.
	 * 
	 **/
	public static enum PType {
		INT, FLOAT, CHAR, VOID, BOOL
	};

	/**
	 * 
	 * The type of the instance.
	 * 
	 **/
	public final PType type;

	/**
	 * Is this a constant value
	 */
	public final boolean constant;

	/**
	 * 
	 * Constructor.
	 * 
	 * @param type
	 *            The type.
	 * 
	 **/
	public PrimaryType(PType type) {
		this.type = type;
		this.constant = false;
	}

	/**
	 * 
	 * Constructor.
	 * 
	 * @param type
	 *            The type.
	 * @param constant
	 *            Whether or not the type is constant.
	 * 
	 **/
	public PrimaryType(PType type, boolean constant) {
		this.type = type;
		this.constant = constant;
	}

	@Override
	public CType clone() {
		return new PrimaryType(this.type, this.constant);
	}

	@Override
	public PrimaryType getPrimary() {
		return this;
	}

	@Override
	public boolean same(CType other) {
		if (other instanceof PrimaryType) {
			return this.type == ((PrimaryType) other).type;
		}
		return false;
	}
	
	@Override
	public String print(){
		return this.type.toString();
	}

	@Override
	public char getChar() {
		switch(type){
		case INT:
			return 'i';
		case FLOAT:
			return 'r';
		case CHAR:
			return 'c';
		case BOOL:
			return 'b';
		case VOID:
		default:
			return ' ';
		}
	}
	
	@Override
	public String toString() {
		switch(type){
		case INT:
			return "int";
		case FLOAT:
			return "float";
		case CHAR:
			return "char";
		case BOOL:
			return "bool";
		case VOID:
			return "void";
		default:
			return null;
		}
	}
}
