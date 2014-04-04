/**
 * 
 * @file StringNode.java
 * @description This file contains the string node of the AST.
 * @author Chris Vesters
 * @date 8/3/13
 * 
 **/

package aofC.Cparser.ast;

import aofC.Cparser.compiler.CodeGenerator;
import aofC.Cparser.symbolTable.TableEntry;


/**
 * 
 * This class represents a string node in an AST.
 * 
 **/
public class StringNode extends ASTNode {

	private static final long serialVersionUID = -8736873267567434754L;

	/**
	 * 
	 * The value of the node.
	 * 
	 **/
	private String value;

	/**
	 * 
	 * Constructor
	 * 
	 * @param i
	 *            The value of the node.
	 * 
	 **/
	public StringNode(String i) {
		String nlreplace = i.replace("\\n", "\n");
		String real = nlreplace.substring(1, nlreplace.length() - 1);
		// Append the \0 byte to make sure that it is always zero-terminated
		this.value = real + '\0';
	}

	/**
	 * 
	 * This method will return the value of the node.
	 * 
	 * @return The value of the node.
	 * 
	 **/
	public String getValue() {
		return value;
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
		String name = this.value.replaceAll("\n", "\\n");
		return "String \"" + name + "\"";
	}

	@Override
	public void toCode() {
		// Construct the array first
		((ArrayDescriptorNode) this.getChildren()[0]).toCode();
		// Load the start address on TOS, depth is always zero as it is just
		// constructed
		TableEntry me = CodeGenerator.symbolTable
				.getByID(((ArrayDescriptorNode) this.getChildren()[0]).getID());
		CodeGenerator.insertCode("lod a 0 "
				+ CodeGenerator.symbolTable.getLocation(me));
		// Now initialize it
		char[] chars = this.value.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char character = chars[i];
			CodeGenerator.insertCode("dpl a");
			if (character == '\n') {
				CodeGenerator.insertCode("ldc c '\\n'");
			} else {
				CodeGenerator.insertCode("ldc c '" + character + "'");
			}
			CodeGenerator.insertCode("sto c");
			CodeGenerator.insertCode("inc a 1");
		}
		// We need to flush the address on TOS, this is relatively dirty...
		// Save it to 0 0 since this holds our return value and is not used,
		// as soon as it gets used, it will be overwritten
		CodeGenerator.insertCode("str a 0 0");
		// Finally, return as if it was a variable
		((ASTNode) this.getChildren()[1]).rCode();
	}

	@Override
	public int getEP() {
		return 1;
	}
	
	@Override
	public String toString() {
		String value = this.value.replace("\0", "");
		value = value.replace("\n", "\\n");
		return "\"" + value + "\"";
	}
}
