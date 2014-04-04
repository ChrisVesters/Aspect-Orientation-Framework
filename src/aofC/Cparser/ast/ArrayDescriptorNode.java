/**
 * 
 * @file ArgsNode.java
 * @description This file contains the arguments node of the AST.
 * @author Chris Vesters
 * @date 23/2/13
 * 
 **/

package aofC.Cparser.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import aofC.Cparser.compiler.CodeGenerator;




/**
 * 
 * This class represents the creation of an array descriptor in an AST.
 * 
 **/
public class ArrayDescriptorNode extends ASTNode {

	private static final long serialVersionUID = 1964439789426871397L;
	private final int id;

	public ArrayDescriptorNode(int id) {
		this.id = id;
	}

	public int getID() {
		return this.id;
	}

	@Override
	public void toCode() {
		assert (this.getChildren().length > 0);
		// Follow the same idea as in session 4, exercise 2b
		// However, we only have a single bound to worry about (C doesn't
		// support lower bounds)
		int startloc = CodeGenerator.symbolTable
				.getLocation(CodeGenerator.symbolTable.getByID(this.id));
		for (int i = 0; i < this.getChildren().length; i++) {
			((ASTNode) this.getChildren()[i]).rCode();
			CodeGenerator.insertCode("str i 0 " + (startloc + i + 3));
		}
		// Now all sizes are evaluated and saved in the descriptor
		// Calculate the size now
		CodeGenerator.insertCode("ldc i 1");
		for (int i = 0; i < this.getChildren().length; i++) {
			CodeGenerator.insertCode("lod i 0 " + (startloc + i + 3));
			CodeGenerator.insertCode("mul i");
		}
		// Now we have the size of the complete array
		// Save it in the designated place in the descriptor
		CodeGenerator.insertCode("str i 0 " + (startloc + 1));

		// The subtract, always 0 but is accessed in the 'movd' instruction, so
		// it is needed
		CodeGenerator.insertCode("ldc i 0");
		CodeGenerator.insertCode("str i 0 " + (startloc + 2));

		// Now prepare to do the 'new' instruction
		CodeGenerator.insertCode("lda 0 " + startloc);
		CodeGenerator.insertCode("lod i 0 " + (startloc + 1));
		CodeGenerator.insertCode("new");
	}

	@Override
	public int getEP() {
		List<Integer> eps = new ArrayList<Integer>();

		// The list for the evaluation
		for (Node child : this.getChildren()) {
			ASTNode astchild = (ASTNode) child;
			eps.add(astchild.getEP());
		}

		// A generic to perform computations
		eps.add(2);
		return Collections.max(eps);
	}
}
