/**
 * 
 * @file IfNode.java
 * @description This file contains the ff node of the AST.
 * @author Chris Vesters
 * @date 22/2/13
 * 
 **/

package aofC.Cparser.ast;

import aofC.Cparser.compiler.CodeGenerator;


/**
 * 
 * This class represents an if node in an AST.
 * 
 * This node should have 2 or 3 children: first the condition, then-clause and
 * an optional else-clause.
 * 
 **/
public class IfNode extends ASTNode {

	private static final long serialVersionUID = -5635167230897134485L;
	private final int id;
	
	public IfNode(int id){
		this.id = id;
	}
	
	public int getID(){
		return this.id;
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
		return "IfNode " + this.getID();
	}
	
	@Override
	public void toCode() {
		// The condition
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

		if(this.getChildren().length == 2){
			// Only an 'if' part
			CodeGenerator.insertCode("fjp l_" + this.id);
			
			// The 'if' part
			((ASTNode) this.getChildren()[1]).toCode();
			CodeGenerator.insertLabel("l_" + this.id);
		}
		else{
			// Both an 'if' and an 'else'
			CodeGenerator.insertCode("fjp l_" + this.id);
			
			// The 'if' part
			((ASTNode) this.getChildren()[1]).toCode();
			CodeGenerator.insertCode("ujp l_" + this.id + "_end");
			CodeGenerator.insertLabel("l_" + this.id);
			
			// The 'else' part (optional)
			((ASTNode) this.getChildren()[2]).toCode();
			CodeGenerator.insertLabel("l_" + this.id + "_end");
		}

	}
	
	@Override
	public int getEP() {
		ASTNode condition = (ASTNode) this.getChildren()[0];
		ASTNode if_block = (ASTNode) this.getChildren()[1];
		if(this.getChildren().length > 2){
			ASTNode else_block = (ASTNode) this.getChildren()[2];
			return Math.max(condition.getEP(), Math.max(if_block.getEP(), else_block.getEP()));
		}
		else{
			return Math.max(condition.getEP(), if_block.getEP());
		}
	}
	
	@Override
	public String toString() {
		String str = new String();
		str += "if (";
		str += this.getChildren()[0].toString();
		str += ") ";
		str += this.getChildren()[1].toString();
		
		if (this.getChildren().length == 3) {
			str += "else ";
			str += this.getChildren()[2].toString();
		}
		
		return str;
	}
}
