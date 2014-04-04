/**
 * 
 * @file BlockNode.java
 * @description This file contains the block node of the AST.
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
 * This class represents a block node in an AST.
 * 
 **/
public class BlockNode extends ASTNode {

	private static final long serialVersionUID = -4055735565642366156L;
	private final int id;
	
	public BlockNode(int id){
		this.id = id;
	}

	@Override
	public void toCode() {
		// Just pass
		boolean definitions = true;
		if(this.getChildren().length > 0){
			CodeGenerator.insertCode("ujp l_" + this.id);
		}
		for(Node child : this.getChildren()){
			ASTNode astchild = (ASTNode) child;
			if((definitions) && (!(astchild instanceof FunctionDeclNode))){
				// The first of the 'real' code, so insert a label here
				CodeGenerator.insertLabel("l_" + this.id);
				definitions = false;
			}
			if(!(astchild instanceof VarNode)){
				// A simple call to the VarNode from in a block is useless and will clotter the stack
				// so we don't generate it
				astchild.toCode();
			}
		}
		if(this.getChildren().length > 0 && definitions){
			CodeGenerator.insertLabel("l_" + this.id);
		}
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
		return "BlockNode " + this.id;
	}
	
	@Override
	public int getEP() {
		if(this.getChildren().length == 0){
			return 0;
		}
		List<Integer> eps = new ArrayList<Integer>();
		for(Node child : this.getChildren()){
			eps.add(((ASTNode) child).getEP());
		}
		return Collections.max(eps);
	}
	
	@Override
	public String toString() {
		String str = new String();
		str += "{\n";
		
		for (Node child: this.getChildren()) {
			String childString = child.toString();
			
			if (childString.contains("\n")) {
				// Mutli-sequences: a block itself!
				childString = "\t" + childString;
				childString = childString.replaceAll("\n", "\n\t");
				str += childString + "\n";
			} else {
				str += "\t" + childString + ";\n";
			}

			
		}
		str += "}\n";
		
		return str;
	}
}
