/**
 * 
 * @file ArgsNode.java
 * @description This file contains the arguments node of the AST.
 * @author Chris Vesters
 * @date 23/2/13
 * 
 **/

package aofC.Cparser.ast;

import aofC.Cparser.ast.OperatorNode.Operator;
import aofC.Cparser.compiler.CodeGenerator;
import aofC.Cparser.symbolTable.ArrayType;
import aofC.Cparser.symbolTable.TableEntry;
import aofC.Cparser.symbolTable.CType;


/**
 * 
 * This class represents a arguments node in an AST.
 * 
 **/
public class ArgsNode extends ASTNode {

	private static final long serialVersionUID = 7761098245651381664L;

	@Override
	public void toCode() {
		for (Node child : this.getChildren()) {
			ASTNode astchild = (ASTNode) child;
			// Array support is provided in the rCode function itself
			astchild.rCode();
		}
	}

	@Override
	public int getEP() {
		return this.getSize();
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

				// We are only interested as an & as operator.
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
	public String toString() {
		String str = "(";
		for (Node child: this.getChildren()) {
			str += child.toString();
			str += ", ";
		}
		
		// Remove the last ", "
		if (this.getChildren().length > 0) {
			str = str.substring(0, str.length() - 2);
		}
		str += ")";
		return str;
	}
}
