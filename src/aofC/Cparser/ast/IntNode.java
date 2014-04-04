/**
 * 
 * @file IntNode.java
 * @description This file contains the integer node of the AST.
 * @author Chris Vesters
 * @date 21/2/13
 * 
 **/

package aofC.Cparser.ast;

import aofC.Cparser.compiler.CodeGenerator;
import aofC.Cparser.symbolTable.PrimaryType;

/**
 * 
 * This class represents an integer node in an AST.
 * 
 **/
public class IntNode extends TypedNode {

	private static final long serialVersionUID = -3167631543065877950L;
	
	/**
	 * 
	 * The value of the node.
	 * 
	 **/
	private int value;

	/**
	 * 
	 * Constructor
	 * 
	 * @param i
	 *            The value of the node.
	 * 
	 **/
	public IntNode(int i) {
		this.value = i;
		this.type = new PrimaryType(PrimaryType.PType.INT);
	}

	/**
	 * 
	 * This method will return the value of the node.
	 * 
	 * @return The value of the node.
	 * 
	 **/
	public int getValue() {
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
		return "Int " + this.getValue();
	}

	/**
	 * Generate code for this node
	 */
	@Override
	public void toCode() {
		// Do nothing
	}
	
	public void lCode() {
		
	}
	
	@Override
	public void rCode() {
		CodeGenerator.insertCode("ldc i " + this.value);
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
