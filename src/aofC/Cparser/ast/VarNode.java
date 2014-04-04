/**
 * 
 * @file VarNode.java
 * @description This file contains the variable node of the AST.
 * @author Chris Vesters
 * @date 21/2/13
 * 
 **/

package aofC.Cparser.ast;

import aofC.Cparser.compiler.CodeGenerator;
import aofC.Cparser.symbolTable.ArrayType;
import aofC.Cparser.symbolTable.TableEntry;
import aofC.Cparser.symbolTable.CType;


/**
 * 
 * This class represents a variable node in an AST.
 * 
 **/
public class VarNode extends TypedNode {

	private static final long serialVersionUID = -5554838498986816798L;

	/**
	 * 
	 * The id of the node.
	 * 
	 **/
	private int id;

	/**
	 * The nesting depth of this variable, used to determine the number of
	 * static links to follow when resolving variables.
	 */
	private int depth;
	
	/**
	 * 
	 * A boolean to indicate whether this is a declatarion or just access to a variable.
	 * 
	 **/
	private final boolean declaration;

	/**
	 * 
	 * Constructor
	 * 
	 * @param id
	 *            The id of the node.
	 * 
	 **/
//	public VarNode(int id, int nestingDepth) {
//		this.id = id;
//		this.depth = nestingDepth;
//		this.declaration = false;
//	}
	
	/**
	 * 
	 * Constructor
	 * 
	 * @param id
	 *            The id of the node.
	 * 
	 **/
	public VarNode(int id, int nestingDepth, boolean decl) {
		this.id = id;
		this.depth = nestingDepth;
		this.declaration = decl;
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
		return "VAR " + this.getID() + " @ " + this.depth;
	}

	/**
	 * Generate code for this node
	 */
	@Override
	public void toCode() {
		TableEntry me = CodeGenerator.symbolTable.getByID(this.id);
		int d = this.depth - me.getDepth();
		int ra = CodeGenerator.symbolTable.getLocation(me);
		this.type = me.getNewType();
		CodeGenerator.insertCode("lda " + d + " " + ra);
	}

	@Override
	public void lCode() {
		if (CodeGenerator.symbolTable.getByID(this.id).getNewType() instanceof ArrayType) {
			// Some special code...
			// We need the lCode of an array descriptor
			// This would normally end badly, though it should be meant to mean
			// 'pass by reference'
			// Since our called function doesn't know whether or not it is by
			// value or by reference,
			// we need to do some magic here to handle this
			// The address of the descriptor should already be on the stack by
			// now, so we fill in
			// all remaining variables with a pattern to allow the called
			// function to identify this
			// situation (pass by reference)
			// If the call was 'by value', the complete descriptor will have
			// been copied and all place
			// is used. If it is 'by reference', only the address of the
			// descriptor itself will be
			// put on the stack, which means that we still have a gap to fill.
			// This gap can now be used
			// to our advantage to put in the 'strange situation' to help the
			// called function identify
			// the call by reference.
			// For a call by reference, we will put in the following signature
			// (subtract = -1):
			// 0 -- start address
			// 1 -- size
			// 2 -- -1
			// 3 -- dimension 1
			// 4 -- dimension 2
			// This means that the caller MUST check if the subtract is -1 and,
			// if so, fix the address
			// ourselves and DO NOT call the movd instruction
			// We have to write 'something' in the rest of the array descriptor,
			// as otherwise we will not
			// advance the SP to make all remaining arguments correct.
			// Therefore, we will copy the
			// array descriptor already
			TableEntry me = CodeGenerator.symbolTable.getByID(this.id);
			int location = CodeGenerator.symbolTable.getLocation(me);
			int dims = ((ArrayType) me.getNewType()).dimensions;
			// The start address
			CodeGenerator.insertCode("lod a 0 " + location);
			// The size
			CodeGenerator.insertCode("lod i 0 " + (location + 1));
			// The 'subtract', set to -1
			CodeGenerator.insertCode("ldc i -1");
			// The dimensions, just do a movs for this
			// But for a movs, we must put the stack location to begin copying
			// on top of the stack which is the first dimension
			CodeGenerator.insertCode("lda 0 " + (location + 3));
			// NOT + something, as we are only copying the dimension
			CodeGenerator.insertCode("movs " + dims);
		} else {
			this.toCode();
		}
	}

	@Override
	public void rCode() {
		if (CodeGenerator.symbolTable.getByID(this.id).getNewType() instanceof ArrayType) {
			// Copy by value!
			this.toCode();
			int dims = ((ArrayType) CodeGenerator.symbolTable.getByID(this.id)
					.getNewType()).dimensions;
			CodeGenerator.insertCode("movs " + (dims + 3));
			// Now the descriptor is copied, but we also need to copy the data
			// on the heap, this happens at the receiver
		} else {
			this.lCode();
			CodeGenerator.insertCode("ind " + this.type.getChar());
		}
	}

	@Override
	public int getEP() {
		// Just 1, as this is the address of the variable
		// Problem if array descriptors used...
		return 1;
	}
	
	@Override
	public String toString() {
		String str = new String();
		// is it a declaration?
		
		TableEntry entry = CodeGenerator.symbolTable.getByID(this.id);
		if (this.declaration) {
			CType type = entry.getNewType();
			if (type instanceof ArrayType) {
				str += ((ArrayType) type).getType().toString();
				str += " ";
				str += entry.getName();
				for (int i = 0; i < ((ArrayType) type).dimensions; i++) {
					str += "[]";
				}
			} else {
				str += type.toString();
				str += " ";
				str += entry.getName();
			}
		} else {
			str += entry.getName();
		}
		
		return str;
	}
}
