/**
 * 
 * @file ScopeNode.java
 * @description This file contains the scope node (which will make up the scope tree).
 * @author Chris Vesters
 * @date 24/2/13
 * 
 **/
package aofC.Cparser.symbolTable;

import aofC.Cparser.ast.Node;

/**
 * 
 * This class represents the scope node. It holds differs in that from the book
 * because it doesn't hold a reference to the first element of each scope.
 * 
 **/
public class ScopeNode extends Node {

	/**
	 * 
	 * ID used for serialization.
	 * 
	 **/
	private static final long serialVersionUID = -2226332732594108103L;

	/**
	 * 
	 * Unique ID 'generator'.
	 * 
	 **/
	private static long UNIQUE_ID = 0;

	/**
	 * 
	 * The unique id of the scope.
	 * 
	 **/
	private long id;

	/**
	 * 
	 * The (current) head of this scope.
	 * 
	 **/
	TableEntry scopeHead = null;

	/**
	 * 
	 * Empty constructor
	 * 
	 **/
	public ScopeNode() {
		this.id = UNIQUE_ID;
		UNIQUE_ID++;
	}

	/**
	 * 
	 * This method returns the ID of the scope.
	 * 
	 * @return The ID of the scope.
	 * 
	 **/
	public long getID() {
		return this.id;
	}

	/**
	 * 
	 * This method returns the size of the scope. This is the size required for
	 * all declared variables.
	 * 
	 * @return The size of the scope.
	 * 
	 **/
	public int getSize() {
		TableEntry current = this.scopeHead;
		int size = 0;

		while (current != null) {
			size += current.getSize();
			current = current.scopeEntry;
		}
		return size;
	}

	/**
	 * 
	 * This method return the size required for the current scope and all the
	 * children (when children are serially allocated).
	 * 
	 * @return The size required for the current scope and its children.
	 * 
	 **/
	public int getDeepSize() {
		// The size required for the function consists of the size for it's own
		// variables + the maximum size of scopes 1 level deeper.
		int max = 0;
		for (Node child : this.getChildren()) {
			int size = ((ScopeNode) child).getDeepSize();
			if (size > max) {
				max = size;
			}
		}

		return this.getSize() + max;
	}
}
