/**
 * 
 * @file FunctionDeclNode.java
 * @description This file contains the function declaration node of the AST.
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
import aofC.Cparser.symbolTable.FunctionType;
import aofC.Cparser.symbolTable.TableEntry;
import aofC.Cparser.symbolTable.CType;




/**
 * 
 * This class represents a function declaration node in an AST.
 * 
 * This node has the following children: function name, parameters, body.
 * 
 **/

public class FunctionDeclNode extends ASTNode {

	private static final long serialVersionUID = -8523477199220624416L;

	public void toCode() {
		/*
		 * Code should be of the form: l_name: ssp n_a''; code_p specs ro' st;
		 * code_p vdecls ro'' st; sep k; ujp l; proc_code; l_name_body: code
		 * body ro''' st; retf/retp;
		 */
		// my_id should be the ID of the function as mentioned in the
		// symbol table. Not the name of the function, since there
		// could be possible overloading
		CodeGenerator.insertLabel("l_func_" + this.id);
		TableEntry me = CodeGenerator.symbolTable.getByID(this.id);
		int n_a = this.deepsize;
		CodeGenerator.insertCode("ssp " + n_a);
		// Code to generate the descriptors for dynamic arrays will
		// be generated in the AST itself (ArrayDescriptorNode)
		// Code to generate the descriptors of a parameter is not needed
		// as we will simply use the descriptor that we got.
		CType[] args = ((FunctionType) me.getNewType()).argsType;

		// Set to 5 as this is the location of the first parameter
		int location = 5;
		// Use a counter to identify between multiple arrays being passed (and
		// specifically their labels)
		int count = 0;
		for (CType type : args) {
			if (type instanceof ArrayType) {
				ArrayType artype = (ArrayType) type;
				// Should we ever have the possibility to determine this
				// statically, please do so...
				if (true) {
					// First, we have to check whether or not it was passed by
					// reference or not
					// This is just checking if subtract == 0
					CodeGenerator.insertCode("lod i 0 " + (location + 2));
					CodeGenerator.insertCode("ldc i 0");
					CodeGenerator.insertCode("equ i");
					// The top of stack will now be true if it is a copy by
					// value
					// The top of stack will now be false if it is a copy by
					// reference
					// So we jump if it is a copy by reference
					CodeGenerator.insertCode("fjp l_" + this.id + "_" + count
							+ "_arrcheck");
					// It was passed by value, copy everything too!
					CodeGenerator.insertCode("movd " + location);
					CodeGenerator.insertCode("ujp l_" + this.id + "_" + count
							+ "_arrcheck_end");
					CodeGenerator.insertLabel("l_" + this.id + "_" + count
							+ "_arrcheck");
					// It was passed by reference
					// Don't forget to put the subtract to 0
					CodeGenerator.insertCode("ldc i 0");
					CodeGenerator.insertCode("str i 0 " + (location + 2));
					// Now this is done too
					CodeGenerator.insertLabel("l_" + this.id + "_" + count
							+ "_arrcheck_end");
					// The address in the descriptor will be updated
					// automatically by the movd
					count += 1;
				}
				// Skip place for the array descriptor
				location += artype.dimensions + 3;
			} else if (type instanceof FunctionType) {
				// Do nothing, this is actually very strange should this
				// happen...
			} else {
				location += 1;
			}
		}

		int k = this.getEP();
		CodeGenerator.insertCode("sep " + k);
		((ASTNode) this.getChildren()[0]).toCode();
		CodeGenerator.insertCode("retp");
	}

	/**
	 * 
	 * The id of the node.
	 * 
	 **/
	private final int id;

	/**
	 * The number of stack locations to reserve
	 */
	private final int deepsize;

	/**
	 * 
	 * Constructor
	 * 
	 * @param id
	 *            The id of the node.
	 * 
	 **/
	public FunctionDeclNode(int id, int deepsize) {
		this.id = id;
		this.deepsize = deepsize;
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
		return "FUN DECL " + this.getID();
	}

	@Override
	public int getEP() {
		List<Integer> eps = new ArrayList<Integer>();
		for (Node child : this.getChildren()) {
			eps.add(((ASTNode) child).getEP());
		}
		return Collections.max(eps);
	}
	
	@Override
	public String toString() {
		String str = new String();
		int id = this.id;
		TableEntry function = CodeGenerator.symbolTable.getByID(id);
		
		if (function.getName().endsWith("_call_")) {
			return str;
		} else if (function.getName().endsWith("_exec_")) {
			return str;
		} else if (function.getName().endsWith("_get_")) {
			return str;
		} else if (function.getName().endsWith("_set_")) {
			return str;
		}
		
		FunctionType func = (FunctionType) function.getNewType();
		str += func.returnType.toString() + " ";
		str += function.getName();
		str += "(";

		for (int i = 0; i < func.argsType.length; i++) {
			CType type = func.argsType[i];
			String name = func.argsName[i];
			
			if (type instanceof ArrayType) {
				str += ((ArrayType) type).getType().toString();
				str += " " + name;

				for (int j = 0; j < ((ArrayType) type).dimensions; j++) {
					str += "[]";
				}
			} else {
				str += type.toString() + " " + name;
			}
			
			str += ", ";
		}

		// Remove the last ", "
		if (func.argsType.length > 0) {
			str = str.substring(0, str.length() - 2);
		}

		str += ") ";
		
		str += this.getChildren()[0].toString();
		str += "\n";
		
		return str;
	}
}
