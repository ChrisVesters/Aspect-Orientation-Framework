/**
 * 
 * @file RootNode.java
 * @description This file contains the root node of the AST, containing all definitions.
 * @author Yentl Van Tendeloo
 * @date 23/2/13
 * 
 **/

package aofC.Cparser.ast;

import aofC.Cparser.compiler.CodeGenerator;


/**
 * 
 * This class represents the root of the AST.
 * 
 * This node has the following children: declarations
 * 
 **/
public class RootNode extends ASTNode {

	private static final long serialVersionUID = 5677987451682972452L;

	private int mainID;

	public void setID(int id) {
		this.mainID = id;
	}

	/**
	 * Generate code for this node (and all its children)
	 */
	@Override
	public void toCode() {
		/*
		 * Code is of the form mst 0; cup 0 l_main; l_main: ssp n_a; code_p
		 * vdecls ro 1; sep k; ujp l; proc_code; l: code stats ro' st; stp;
		 * However, since the C code is structured slightly different, we will
		 * try to restructure the code generation too. Something like: mst 0;
		 * cup 0 l_main; code for all function definitions; stp;
		 */

		// Reserve space on the stack for all variables, _maybe_ this could be
		// done a little more efficient, as the order in which we encounter them
		// is the order of their entry in the symbol table...
		int n_a = CodeGenerator.symbolTable.getDeepSize();
		CodeGenerator.insertCode("ssp " + n_a);

		// Initialize all global variables!
		for (Node node : this.getChildren()) {
			if (node instanceof AssignNode) {
				// Do the actual assignment
				((AssignNode) node).toCode();
			} 
			else if(node instanceof ArrayDescriptorNode){
				((ArrayDescriptorNode) node).toCode();
			}
			else {
				// Don't do anything, a function is defined after the hlt
				// instruction
			}
		}

		// The ID of the main function, not just 'main' as this might be
		// overridden elsewhere (e.g. in a scope)
		CodeGenerator.insertCode("mst 0");
		CodeGenerator.insertCode("cup 0 l_func_" + this.mainID);
		CodeGenerator.insertCode("hlt");
		for (Node node : this.getChildren()) {
			if (node instanceof FunctionDeclNode || node instanceof STDIONode) {
				((ASTNode) node).toCode();
			} else {
				// This should not happen, since only FunctionDecls are allowed
				// at the highest level
			}
		}
		// Should be stp according to the compendium, but the P machine
		// only takes hlt

	}

	@Override
	public int getEP() {
		// Will never be used
		return 0;
	}
	
	@Override
	public String toString() {
		String str = new String();
		for (Node child: this.getChildren()) {
			str += child.toString();
			
			if (!(child instanceof FunctionDeclNode) && !(child instanceof STDIONode)) {
				str += ";\n";
			}
		}
			
		return str;
	}
}
