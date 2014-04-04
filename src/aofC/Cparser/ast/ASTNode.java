/**
 * 
 * @file ASTNode.java
 * @description This file contains the base node of the AST.
 * @author Chris Vesters
 * @date 28/3/13
 * 
 **/
package aofC.Cparser.ast;


/**
 * 
 * The base node of the AST. All specific nodes must be derived from this one!
 * 
 **/
public abstract class ASTNode extends Node {

	private static final long serialVersionUID = 294607372206621923L;

	/**
	 * 
	 * The abstract toCode method. This will generate code for this node.
	 * 
	 **/
	public abstract void toCode();
	
	/**
	 * Perform the code_l function, falls back to the default toCode if it is not overridden
	 */
	public void lCode(){
		this.toCode();
	}
	
	/**
	 * Perform the code_r function, falls back to the default toCode if it is not overridden
	 */
	public void rCode(){
		this.toCode();
	}
	
	/**
	 * Fetches the number of stack locations needed to process this instruction
	 * @return max number of stack locations
	 */
	public abstract int getEP();
	
	@Override
	public String toString() {
		String str = new String();
		for (Node child: this.getChildren()) {
			str += child.toString();
		}
			
		return str;
	}

}
