/**
 * 
 * @file ASTNode.java
 * @description This file contains the base node of the AST.
 * @author Chris Vesters
 * @date 28/3/13
 * 
 **/
package aofC.Cparser.ast;

import aofC.Cparser.symbolTable.CType;

/**
 * 
 * The base node of the AST. All specific nodes must be derived from this one!
 * 
 **/
public abstract class TypedNode extends ASTNode {

	private static final long serialVersionUID = 8734389908347897597L;

	protected CType type;

	public char getType() {
		return this.type.getChar();
	}
	
	@Override
	public String toString() {
		return this.type.toString();
	}
}
