/**
 * 
 * @file AssignNode.java
 * @description This file contains the assignment node of the AST.
 * @author Chris Vesters
 * @date 24/2/13
 * 
 **/

package aofC.Cparser.ast;

import aofC.Cparser.compiler.CodeGenerator;

/**
 * 
 * This class represents an assignment node in an AST.
 * 
 **/
public class AssignNode extends TypedNode {

	private static final long serialVersionUID = -7105289291676429287L;

	@Override
	public void toCode() {
		assert (this.getChildren().length == 2);

		((ASTNode) this.getChildren()[0]).lCode();
		((ASTNode) this.getChildren()[1]).rCode();
		this.type = ((TypedNode) this.getChildren()[1]).type;
		CodeGenerator.insertCode("sto " + this.type.getChar());
	}

	@Override
	public void rCode() {
		assert (this.getChildren().length == 2);

		this.toCode();
		// Reevaluate this part, as we need the result later on
		((ASTNode) this.getChildren()[0]).rCode();
	}

	@Override
	public int getEP() {
		assert (this.getChildren().length == 2);

		ASTNode var = (ASTNode) this.getChildren()[0];
		ASTNode assign = (ASTNode) this.getChildren()[1];
		// Either we need most stack space for the location,
		// or we need most space for the value to store, though
		// this has to be done with the location on the stack
		return Math.max(var.getEP(), assign.getEP() + 1);
	}
	
	@Override
	public String toString() {
		assert (this.getChildren().length == 2);
		return this.getChildren()[0].toString() + " = " + this.getChildren()[1].toString();
	}
}
