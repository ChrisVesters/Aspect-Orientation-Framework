/**
 * 
 * @file FloatNode.java
 * @description This file contains the float node of the AST.
 * @author Chris Vesters
 * @date 21/2/13
 * 
 **/

package aofC.Cparser.ast;

import aofC.Cparser.compiler.CodeGenerator;
import aofC.Cparser.symbolTable.PrimaryType;


/**
 * 
 * This class represents a float node in an AST.
 * 
 **/
public class FloatNode extends TypedNode {

	private static final long serialVersionUID = 4921223427706380079L;
	
	/**
	 * 
	 * The value of the node.
	 * 
	 **/
	private float value;

	/**
	 * 
	 * Constructor
	 * 
	 * @param i
	 *            The value of the node.
	 * 
	 **/
	public FloatNode(float i) {
		this.value = i;
		this.type = new PrimaryType(PrimaryType.PType.FLOAT);
	}

	/**
	 * 
	 * This method will return the value of the node.
	 * 
	 * @return The value of the node.
	 * 
	 **/
	public float getValue() {
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
		return "Float " + this.getValue();
	}

	@Override
	public void toCode() {
		// do nothing
	}
	
	@Override
	public void lCode(){
		
	}
	
	@Override
	public void rCode(){
		CodeGenerator.insertCode("ldc r " + this.value);
	}
	
	@Override
	public int getEP() {
		return 1;
	}
	
	@Override
	public String toString() {
		return String.valueOf(this.value);
	}
}
