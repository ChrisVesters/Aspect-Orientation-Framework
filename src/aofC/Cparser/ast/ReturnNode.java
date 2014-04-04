/**
 * 
 * @file ReturnNode.java
 * @description This file contains the return node of the AST.
 * @author Chris Vesters
 * @date 23/2/13
 * 
 **/

package aofC.Cparser.ast;

import aofC.Cparser.compiler.CodeGenerator;


/**
 * 
 * This class represents a return node in an AST.
 * 
 **/
public class ReturnNode extends ASTNode {

	private static final long serialVersionUID = -3552753417522036583L;
	private char rettype;
	
	public ReturnNode(char type){
		this.rettype = type;
	}
	
	public ReturnNode(){}

	@Override
	public void toCode() {
		if(this.getChildren().length > 0){
			((ASTNode) this.getChildren()[0]).rCode();
			CodeGenerator.insertCode("str " + rettype + " 0 0");
			CodeGenerator.insertCode("retf");
		}
		else{
			CodeGenerator.insertCode("retp");
		}
	}
	
	@Override
	public int getEP() {
		if(this.getChildren().length == 0){
			return 0;
		}
		else{
			return ((ASTNode) this.getChildren()[0]).getEP();
		}
	}
	
	@Override
	public String toString() {
		if(this.getChildren().length == 0){
			return "return";
		}
		else{
			return "return " + this.getChildren()[0].toString();
		}
	}
}
