/**
 * 
 * @file ForNode.java
 * @description This file contains the for node of the AST.
 * @author Chris Vesters
 * @date 28/2/13
 * 
 **/

package aofC.Cparser.ast;

import aofC.Cparser.compiler.CodeGenerator;

/**
 * 
 * This class represents a for node in an AST.
 * 
 **/
public class ForNode extends ASTNode {

	private static final long serialVersionUID = 7983963039166829241L;
	private final int loopID;
	
	public ForNode(int loopID){
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
		return "ForNode " + this.getLoopID();
	}

	@Override
	public void toCode() {
		ASTNode init = (ASTNode) this.getChildren()[0];
		ASTNode condition = (ASTNode) this.getChildren()[1];
		ASTNode action = (ASTNode) this.getChildren()[2];
		ASTNode body = (ASTNode) this.getChildren()[3];

		init.toCode();
		CodeGenerator.insertLabel("l_" + this.loopID);
		condition.rCode();
		switch(((TypedNode) condition).getType()){
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
		body.toCode();
		action.toCode();
		CodeGenerator.insertCode("ujp l_" + this.loopID);
		CodeGenerator.insertLabel("l_" + this.loopID + "_end");
	}

	@Override
	public int getEP() {
		ASTNode init = (ASTNode) this.getChildren()[0];
		ASTNode condition = (ASTNode) this.getChildren()[1];
		ASTNode action = (ASTNode) this.getChildren()[2];
		ASTNode body = (ASTNode) this.getChildren()[3];
		// So every part is independent of the other
		// We don't need to take into account the init variable,
		// as it will be defined in another scope due to the limitation
		// provided by C.
		return Math.max(Math.max(init.getEP(), condition.getEP()),
				Math.max(action.getEP(), body.getEP()));
	}
	
	@Override
	public String toString() {
		String str = new String();
		str += "for (";
		str += this.getChildren()[0].toString();
		str += "; ";
		str += this.getChildren()[1].toString();
		str += "; ";
		str += this.getChildren()[2].toString();
		str += ") ";
		str += this.getChildren()[3].toString();
		return str;
	}
}
