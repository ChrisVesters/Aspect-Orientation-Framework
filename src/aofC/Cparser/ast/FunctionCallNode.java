/**
 * 
 * @file FunctionCallNode.java
 * @description This file contains the function call node of the AST.
 * @author Chris Vesters
 * @date 22/2/13
 * 
 **/

package aofC.Cparser.ast;

import aofC.Cparser.compiler.CodeGenerator;
import aofC.Cparser.symbolTable.FunctionType;
import aofC.Cparser.symbolTable.TableEntry;


/**
 * 
 * This class represents a function call node in an AST.
 * 
 * This node has the following children: function name (signature?), parameters.
 * 
 **/
public class FunctionCallNode extends TypedNode {

	private static final long serialVersionUID = -7061458635694973343L;

	/**
	 * 
	 * The id of the node.
	 * 
	 **/
	private int id;

	/**
	 * The nesting depth of the call
	 */
	private int depth;

	/**
	 * 
	 * Constructor
	 * 
	 * @param id
	 *            The id of the node.
	 * @param depth
	 *            The nesting depth of the call.
	 * 
	 **/
	public FunctionCallNode(int id, int depth) {
		this.id = id;
		this.depth = depth;

	}

	/**
	 * 
	 * This method will return the id of the node.
	 * 
	 * @return The id of the node.
	 * 
	 **/
	public int getID() {
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
		return "FUN CALL " + this.getID() + " @ " + this.depth;
	}

	@Override
	public void toCode() {
		FunctionType type = (FunctionType) CodeGenerator.symbolTable.getByID(
				this.id).getNewType();
		this.type = type.returnType;
		int calldepth = this.depth;
		int defdepth = CodeGenerator.symbolTable.getByID(this.id).getDepth();
		int stdiff = calldepth - defdepth;
		CodeGenerator.insertCode("mst " + stdiff);
		int s = 0;
		if (this.getChildren().length > 0) {
			((ASTNode) this.getChildren()[0]).toCode();
			s = ((ArgsNode) this.getChildren()[0]).getSize();
		}
		CodeGenerator.insertCode("cup " + s + " l_func_" + this.id);
	}

	@Override
	public int getEP() {
		// Reserve space for the return value
		// Should we also reserve place for the arguments?
		if (this.getChildren().length > 0) {
			return ((ArgsNode) this.getChildren()[0]).getSize();
		} else {
			// Put 1 here, as we need to reserve space for the return value
			// But what about procedures?
			return 1;
		}
	}
	
	@Override
	public String toString() {
		TableEntry function = CodeGenerator.symbolTable.getByID(this.id);
		return function.getName() + this.getChildren()[0].toString();
	}
}
