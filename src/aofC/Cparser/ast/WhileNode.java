/**
 * 
 * @file WhileNode.java
 * @description This file contains the while node of the AST.
 * @author Chris Vesters
 * @date 22/2/13
 * 
 **/

package aofC.Cparser.ast;

import aofC.Cparser.compiler.CodeGenerator;


/**
 * 
 * This class represents a while node in an AST.
 * 
 * This node should have 2 children: A condition and a body.
 * 
 **/
public class WhileNode extends ASTNode {

	private static final long serialVersionUID = -6344884163524473467L;
	private final int loopID;
	
	public WhileNode(int loopID){
		this.loopID = loopID;
	}
	
	public int getLoopID(){
		return this.loopID;
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
		return "WhileNode " + this.getLoopID();
	}

	@Override
	public void toCode() {
		CodeGenerator.insertLabel("l_" + this.loopID);
		
		// The loop condition
		((ASTNode) this.getChildren()[0]).rCode();
		switch(((TypedNode) this.getChildren()[0]).getType()){
		case 'b':
			break;
		case 'i':
			CodeGenerator.insertCode("conv i b");
			break;
		case 'r':
			CodeGenerator.insertCode("conv r b");
			break;
		case 'c':
			CodeGenerator.insertCode("conv c b");
			break;
		default:
			break;
		}
		CodeGenerator.insertCode("fjp l_" + this.loopID + "_end");

		// The body of the loop
		((ASTNode) this.getChildren()[1]).toCode();
		CodeGenerator.insertCode("ujp l_" + this.loopID);
		CodeGenerator.insertLabel("l_" + this.loopID + "_end");
	}
	
	@Override
	public int getEP() {
		ASTNode condition = (ASTNode) this.getChildren()[0];
		ASTNode body = (ASTNode) this.getChildren()[1];
		return Math.max(condition.getEP(), body.getEP());
	}
	
	@Override
	public String toString() {
		String str = new String();
		str += "while(";
		str += this.getChildren()[0].toString();
		str += ") ";
		str += this.getChildren()[1].toString();
		
		return str;
	}
}
