/**
 * 
 * @file PrintfNode.java
 * @description This file contains the printf node of the AST.
 * @author Chris Vesters
 * @date 7/4/13
 * 
 **/

package aofC.Cparser.ast;

import aofC.Cparser.ast.OperatorNode.Operator;
import aofC.Cparser.compiler.CodeGenerator;
import aofC.Cparser.symbolTable.ArrayType;
import aofC.Cparser.symbolTable.PrimaryType;
import aofC.Cparser.symbolTable.TableEntry;
import aofC.Cparser.symbolTable.CType;


/**
 * 
 * This class represents a printf node in an AST.
 * 
 **/
public class PrintfNode extends TypedNode {

	private static final long serialVersionUID = -8724983562098315869L;
	private int depth;

	public PrintfNode(int depth) {
		this.depth = depth;
		this.type = new PrimaryType(PrimaryType.PType.INT);
	}

	public int getSize() {
		int size = 0;
		for (Node child : this.getChildren()) {
			ASTNode astchild = (ASTNode) child;
			if (astchild instanceof VarNode) {
				// A variable, but which kind?
				VarNode varchild = (VarNode) astchild;
				int id = varchild.getID();
				TableEntry entry = CodeGenerator.symbolTable.getByID(id);
				if (entry.getNewType() instanceof ArrayType) {
					// It is an array we are passing...
					// Add 3 as this is a descriptor
					size += ((ArrayType) entry.getNewType()).dimensions + 3;
				}

				else {
					// Just an average type
					size += 1;
				}
			} else if (astchild instanceof StringNode) {
				// Array descriptor for a one dimensional array
				size += 4;
			} else if (astchild instanceof OperatorNode) {
				OperatorNode opNode = (OperatorNode) astchild;

				// We are only interested in the & operator.
				if (opNode.getOperator() != Operator.ADDR) {
					size += 1;
					continue;
				}

				if (!(opNode.getChildren()[0] instanceof VarNode)) {
					size += 1;
					continue;
				}

				VarNode childNode = (VarNode) opNode.getChildren()[0];
				CType childType = CodeGenerator.symbolTable.getByID(
						childNode.getID()).getNewType();

				if (childType instanceof ArrayType) {
					// An array by reference that we are passing...
					size += ((ArrayType) childType).dimensions + 3;
				} else {
					size += 1;
				}
			} else {
				// Just a constant
				size += 1;
			}
		}
		return size;
	}

	@Override
	public void toCode() {
		CodeGenerator.insertCode("mst " + this.depth);
		for (Node node : this.getChildren()) {
			ASTNode astnode = (ASTNode) node;
			// rCode, so arrays will have an array descriptor
			astnode.rCode();
		}
		CodeGenerator.insertCode("cup " + this.getSize() + " l_print");
		/*
		 * STDIO will ALWAYS be generated at level 0
		 */
	}

	@Override
	public int getEP() {
		return 0;
	}
	
	@Override
	public String toString() {
		String str = new String();
		Node[] children = this.getChildren();
		
		str += "printf(";
		str += children[0].toString();
		
		for (int i = 1; i < children.length; i++) {
			str += ", " + children[i].toString();
		}
		
		str += ")";
		
		return str;
	}
}
