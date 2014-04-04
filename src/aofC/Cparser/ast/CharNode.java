/**
 * 
 * @file CharNode.java
 * @description This file contains the char node of the AST.
 * @author Chris Vesters
 * @date 21/2/13
 * 
 **/

package aofC.Cparser.ast;

import aofC.Cparser.compiler.CodeGenerator;
import aofC.Cparser.symbolTable.PrimaryType;

/**
 * 
 * This class represents an char node in an AST.
 * 
 **/
public class CharNode extends TypedNode {

	private static final long serialVersionUID = -1878038389841788828L;

	/**
	 * 
	 * The value of the node.
	 * 
	 **/
	private char value;

	/**
	 * 
	 * Constructor
	 * 
	 * @param i
	 *            The value of the node.
	 * 
	 **/
	public CharNode(char i) {
		this.value = i;
		this.type = new PrimaryType(PrimaryType.PType.CHAR);
	}

	/**
	 * 
	 * This method will return the value of the node.
	 * 
	 * @return The value of the node.
	 * 
	 **/
	public char getValue() {
		return value;
	}

	/**
	 * 
	 * This method returns the name of the node. This will be used for pretty
	 * printing!
	 * 
	 * @return The name of the node.
	 * 
	 **/
	@Override
	protected String getName() {
		switch (this.value) {
		case '\n':
			return "Char \\n";
		case '\0':
			return "Char \\0";
		default:
			return "Char " + this.value;
		}
	}

	@Override
	public void toCode() {
		// Do nothing
	}

	@Override
	public void lCode() {
		
	}

	@Override
	public void rCode() {
		switch (this.value) {
		case '\n':
			CodeGenerator.insertCode("ldc c '\\n'");
			break;
		default:
			CodeGenerator.insertCode("ldc c '" + this.value + "'");
			break;
		}
	}

	@Override
	public int getEP() {
		return 1;
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.value);
	}
}
