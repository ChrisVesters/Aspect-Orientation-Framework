/**
 * 
 * @file Continue.java
 * @description This file contains the continue node of the AST.
 * @author Chris Vesters
 * @date 23/2/13
 * 
 **/

package aofC.Cparser.ast;

import aofC.Cparser.compiler.CodeGenerator;

/**
 * 
 * This class represents a continue node in an AST.
 * 
 **/
public class ContinueNode extends ASTNode {

	private static final long serialVersionUID = -4851255745318775548L;
	private final int loopID;

	public ContinueNode(int loopID) {
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
		return "ContinueNode " + this.getLoopID();
	}

	@Override
	public void toCode() {
		CodeGenerator.insertCode("ujp l_" + this.loopID);
	}

	@Override
	public int getEP() {
		return 0;
	}
	
	@Override
	public String toString() {
		return "continue";
	}
}
