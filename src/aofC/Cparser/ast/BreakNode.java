/**
 * 
 * @file BreakNode.java
 * @description This file contains the break node of the AST.
 * @author Chris Vesters
 * @date 23/2/13
 * 
 **/

package aofC.Cparser.ast;

import aofC.Cparser.compiler.CodeGenerator;

/**
 * 
 * This class represents a break node in an AST.
 * 
 **/
public class BreakNode extends ASTNode {

	private static final long serialVersionUID = -505500025014330222L;
	private final int loopID;

	public BreakNode(int loopID) {
		this.loopID = loopID;
	}

	public int getLoopID() {
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
		return "BreakNode " + this.getLoopID();
	}

	@Override
	public void toCode() {
		CodeGenerator.insertCode("ujp l_" + this.loopID + "_end");
	}

	@Override
	public int getEP() {
		return 0;
	}
	
	@Override
	public String toString() {
		return "break";
	}
}
