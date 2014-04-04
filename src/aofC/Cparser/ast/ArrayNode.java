/**
 * 
 * @file ArrayNode.java
 * @description This file contains the array node of the AST.
 * @author Chris Vesters
 * @date 22/2/13
 * 
 **/

package aofC.Cparser.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import aofC.Cparser.compiler.CodeGenerator;
import aofC.Cparser.symbolTable.ArrayType;
import aofC.Cparser.symbolTable.CType;




/**
 * 
 * This class represents an array node in an AST.
 * 
 * This node should have at least 2 children: one for the variable and one for
 * the offset of each dimension.
 * 
 **/
public class ArrayNode extends TypedNode {

	private static final long serialVersionUID = -6202793488000679563L;

	@Override
	public void toCode() {
		assert (this.getChildren().length > 0);
		assert (this.getChildren()[0] instanceof TypedNode);

		// Assume it is always a dynamic array! This isn't too bad though, since
		// we will always have to create the descriptor, so all this data will
		// always be present. Load the address of the array on the stack

		// DO NOT CALL THE LCODE METHOD, AS THIS WILL PREPARE FOR A CALL BY
		// REFERENCE
		((ASTNode) this.getChildren()[0]).toCode();
		CType type = ((TypedNode) this.getChildren()[0]).type;
		ArrayType arraytype = ((ArrayType) type);
		this.type = arraytype.getType();
		CodeGenerator.insertCode("dpl a");
		CodeGenerator.insertCode("ind a");
		CodeGenerator.insertCode("ldc i 0");
		for (int i = 1; i < this.getChildren().length - 1; i++) {
			((ASTNode) this.getChildren()[i]).rCode();
			CodeGenerator.insertCode("add i");
			// Originally, the command is 'ldd 2*k+3' with the 3 increasing each
			// iteration
			// this is the same as 2*(k+1)+i.
			// We use another array descriptor though, since C doesn't support
			// lower bounds
			// This reduces our descriptor to:
			/*
			 * 0 -- start address 1 -- size 2 -- zero (for the 'movd'
			 * instruction) 3 -- dimension 1 4 -- dimension 2
			 */
			CodeGenerator.insertCode("ldd " + (i + 3));
			CodeGenerator.insertCode("mul i");
		}
		((ASTNode) this.getChildren()[this.getChildren().length - 1]).rCode();
		CodeGenerator.insertCode("add i");
		// Make the assumption that g is always 1, this should not be too
		// bad, since we only support primary types (and not structured types)
		// This ixa could be changed to an add though...
		CodeGenerator.insertCode("ixa 1");
		CodeGenerator.insertCode("sli a");
	}

	@Override
	public void rCode() {
		this.toCode();
		CodeGenerator.insertCode("ind " + this.type.getChar());
	}

	@Override
	public int getEP() {
		List<Integer> eps = new ArrayList<Integer>();
		for (Node child : this.getChildren()) {
			ASTNode astchild = (ASTNode) child;
			eps.add(astchild.getEP() + 2);
		}
		return Collections.max(eps);
	}

	public int getDimensions() {
		// 1 is for the variable that gets dereferenced
		return this.getChildren().length - 1;
	}
	
	@Override
	public String toString() {
		assert (this.getChildren().length > 0);
		assert (this.getChildren()[0] instanceof TypedNode);
		
		Node[] children = this.getChildren();
		String str = children[0].toString();
		for (int i = 1; i < children.length; i++) {
			str += "[" + children[i] + "]";
		}
		
		return str;
	}
}
