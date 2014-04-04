/**
 * 
 * @file SymbolTable.java
 * @description This file contains the symbol table.
 * @author Chris Vesters
 * @date 24/2/13
 * 
 **/
package aofC.Cparser.symbolTable;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * 
 * This class represents the symbol table.
 * 
 **/
public class SymbolTable {

	/**
	 * 
	 * The root of the scope tree. The scopetree differs from that one in the
	 * book since it does not hold any reference to table entries.
	 * 
	 **/
	static ScopeNode scopeRoot = new ScopeNode();

	/**
	 * 
	 * The hashtable with the table entries.
	 * 
	 **/
	static Hashtable<String, TableEntry> hashTable = new Hashtable<String, TableEntry>();

	/**
	 * 
	 * An ordered list of the entries based on id.
	 * 
	 **/
	static ArrayList<TableEntry> list = new ArrayList<TableEntry>();

	/**
	 * 
	 * A list with all declarations of functions.
	 * 
	 * 
	 **/
	static ArrayList<TableEntry> fctDefs = new ArrayList<TableEntry>();

	/**
	 * 
	 * This method will clear the SymbolTable.
	 * 
	 */
	public static void clear() {
		SymbolTable.scopeRoot = new ScopeNode();
		SymbolTable.hashTable = new Hashtable<String, TableEntry>();
		SymbolTable.list = new ArrayList<TableEntry>();
		SymbolTable.fctDefs = new ArrayList<TableEntry>();
	}
}