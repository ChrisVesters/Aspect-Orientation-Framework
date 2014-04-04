/**
 * 
 * @file TableEntry.java
 * @description This file contains the entry for the symbol table.
 * @author Chris Vesters
 * @date 24/2/13
 * 
 **/
package aofC.Cparser.symbolTable;

/**
 * 
 * This class represents the entries for the symbol table.
 * 
 **/
public class TableEntry {

	/**
	 * 
	 * Unique ID 'generator'.
	 * 
	 **/
	private static int UNIQUE_ID = 0;

	/**
	 * 
	 * The unique id of the declaration.
	 * 
	 **/
	final int id;

	/**
	 * 
	 * The name of the declared variable/function.
	 * 
	 **/
	final String name;

	/**
	 * 
	 * The type of the variable/function.
	 * 
	 **/
	CType type;

	/**
	 * 
	 * The ID of the scope were the variable/function is declared.
	 * 
	 **/
	final long scope;

	/**
	 * 
	 * The depth of the declaration.
	 * 
	 **/
	final int depth;

	/**
	 * 
	 * A reference to the previous (older scope) table entry with the same name.
	 * This is required to go to 'older' versions of the variable.
	 * 
	 **/
	TableEntry varEntry = null;

	/**
	 * 
	 * A reference to the previous table entry with the same scope. This may be
	 * useful to determine the size required for a method and such.
	 * 
	 **/
	TableEntry scopeEntry = null;

	/**
	 * 
	 * Constructor
	 * 
	 * @param name
	 *            The name of the variable/function.
	 * @param type
	 *            The type of the declaration.
	 * @param scope
	 *            The id of the scope in which it is declared.
	 * @param depth
	 *            The (function) depth of the declaration.
	 * 
	 **/
	public TableEntry(String name, CType type, long scope, int depth) {
		assert (name != null && name.trim() != "");
		assert (type != null);
		assert (scope >= 0);
		assert (depth >= 0);

		this.name = name;
		this.type = type;
		this.scope = scope;
		this.depth = depth;
		this.id = TableEntry.UNIQUE_ID;
		TableEntry.UNIQUE_ID++;
	}

	/**
	 * 
	 * Constructor that takes a certain id.
	 * 
	 * @param id
	 *            The id of the entry.
	 * @param name
	 *            The name of the variable/function.
	 * @param type
	 *            The type of the declaration.
	 * @param scope
	 *            The id of the scope in which it is declared.
	 * @param depth
	 *            The (function) depth of the declaration.
	 * 
	 **/
	TableEntry(int id, String name, CType type, long scope, int depth) {
		assert (id >= 0);
		assert ((name != null) && (name.trim() != ""));
		assert (type != null);
		assert (scope >= 0);

		this.id = id;
		this.name = name;
		this.type = type;
		this.scope = scope;
		this.depth = depth;
	}

	/**
	 * 
	 * This method returns the id of the table entry.
	 * 
	 * @return The id of the entry.
	 * 
	 **/
	public int getID() {
		return this.id;
	}

	/**
	 * 
	 * The name of the declared variable or function.
	 * 
	 * @return The name of the entry.
	 * 
	 **/
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * This method returns the type of the table entry.
	 * 
	 * @return The type of the entry
	 * 
	 **/
	public CType getNewType() {
		return this.type;
	}

	/**
	 * 
	 * This method returns the depth of the declaration.
	 * 
	 * @return The depth of the declaration.
	 * 
	 **/
	public int getDepth() {
		return this.depth;
	}

	/**
	 * 
	 * This method returns the size of the variable.
	 * 
	 * @return The size of the variable.
	 * 
	 **/
	public int getSize() {
		CType type = this.getNewType();

		// Determine the size based on the type.
		if ((type instanceof PrimaryType) || (type instanceof PointerType)) {
			return 1;
		} else if (type instanceof ArrayType) {
			return 3 + ((ArrayType) type).dimensions;
		} else {
			return 0;
		}
	}

	/**
	 * 
	 * This method will reset the id counter.
	 * 
	 **/
	public static void reset() {
		TableEntry.UNIQUE_ID = 0;
	}

	public void redefine(CType type) {
		this.type = type;
	}
}
