/**
 * 
 * @file SymbolTableBuilder.java
 * @description This file contains the symbol table builder.
 * @author Chris Vesters
 * @date 26/2/13
 * 
 **/
package aofC.Cparser.symbolTable;

import aofC.Cparser.ast.Node;

/**
 * 
 * This class represents the symbol table builder.
 * 
 **/
public class SymbolTableBuilder {

	/**
	 * 
	 * The current scope.
	 * 
	 **/
	private ScopeNode currentScope;

	/**
	 * 
	 * Standard constructor.
	 * 
	 **/
	public SymbolTableBuilder() {
		this.currentScope = SymbolTable.scopeRoot;
	}
	
	/**
	 * 
	 * This method will forward declare a function in the current scope. It is
	 * allowed to forward declare an existing function (either already forward
	 * declared or already declared) with the same name.
	 * 
	 * @param name
	 *            The name of the function.
	 * @param type
	 *            The type of the function.
	 * @param depth
	 *            The depth of the function.
	 * @param depth
	 *            The depth of the declaration.
	 * 
	 **/
	public void forwardDeclare(String name, FunctionType type, int depth) {
		assert (name != null && name != "");
		assert ((!existsInScope(name)) || (type instanceof FunctionType));
		assert (this.currentScope != null);
		assert (type != null);

		// Check whether such a function already exists.
		// Either an earlier forward declaration or a definition in the global
		// scope!
		TableEntry function = getByName(name);
		while ((function != null) || (SymbolTable.fctDefs.contains(function))) {
			if (function.scope != SymbolTable.scopeRoot.getID()) {
				function = function.varEntry;
			} else {
				break;
			}
		}

		long scope = this.currentScope.getID();
		TableEntry entry;

		if (function == null) {
			entry = new TableEntry(name, type, scope, depth);
		} else {
			// Copy the existing function.
			entry = new TableEntry(function.id, name, type, scope, depth);
		}

		entry.varEntry = SymbolTable.hashTable.put(name, entry);
		entry.scopeEntry = this.currentScope.scopeHead;
		this.currentScope.scopeHead = entry;
		SymbolTable.list.add(entry);
	}

	/**
	 * 
	 * This method will declare a new variable or function in the current scope.
	 * It is not allowed to declare a new variable with a used name in the
	 * current scope!
	 * 
	 * @param name
	 *            The name of the variable/function.
	 * @param type
	 *            The type or the variable/function.
	 * @param depth
	 *            The depth of the declaration.
	 * 
	 **/
	public void declare(String name, CType type, int depth) {
		assert (name != null && name != "");
		assert ((!existsInScope(name)) || (type instanceof FunctionType));
		assert (this.currentScope != null);
		assert (type != null);

		TableEntry entry;
		if (type instanceof FunctionType) {
			long scope = this.currentScope.getID();
			for (TableEntry fctDef : SymbolTable.fctDefs) {
				if ((fctDef.name == name) && (fctDef.scope == scope)) {
					// Re-definition: illegal!
					assert (true);
				}
			}

			// We only look for forward declarations if we are in the global
			// scope!
			if ((this.currentScope.getParent() == null) && (exists(name))) {
				TableEntry function = this.getByName(name);

				// Copy the information!
				entry = new TableEntry(function.id, name, type, scope, depth);
			} else {
				// Create a new one.
				entry = new TableEntry(name, type, scope, depth);
			}
			SymbolTable.fctDefs.add(entry);
		} else {
			long scope = this.currentScope.getID();
			entry = new TableEntry(name, type, scope, depth);
		}

		entry.varEntry = SymbolTable.hashTable.put(name, entry);
		entry.scopeEntry = this.currentScope.scopeHead;
		this.currentScope.scopeHead = entry;
		SymbolTable.list.add(entry);
	}

	/**
	 * 
	 * This method will begin a new scope.
	 * 
	 **/
	public void newScope() {
		assert (this.currentScope != null);

		ScopeNode newScope = new ScopeNode();
		this.currentScope.appendChild(newScope);
		this.currentScope = newScope;

		assert (this.currentScope != null);
	}

	/**
	 * 
	 * This method will exit the current scope.
	 * 
	 **/
	public void exitScope() {
		assert (this.currentScope != null);

		if (this.currentScope.getParent() != null) {
			this.currentScope = (ScopeNode) this.currentScope.getParent();
		}

		assert (this.currentScope != null);
	}

	/**
	 * 
	 * This method will go to the next declaration.
	 * 
	 **/
	public void nextScope() {
		assert (this.currentScope != null);

		long id = this.currentScope.getID();
		ScopeNode trav = this.currentScope;

		// We will now search for the scope with id + 1
		do {
			Node[] children = trav.getChildren();
			boolean checkChildren;
			if (children.length == 0) {
				checkChildren = false;
			} else {
				ScopeNode last = ((ScopeNode) children[children.length - 1]);
				checkChildren = (last.getID() >= id + 1);
			}

			if (checkChildren) {
				// We need to go to one of it's children.
				for (Node child : children) {
					if (((ScopeNode) child).getID() == id + 1) {
						this.currentScope = (ScopeNode) child;
						return;
					}
				}
			} else {
				// We need to back up!
				trav = (ScopeNode) trav.getParent();
			}
		} while (trav != null);

		assert (this.currentScope != null);
	}

	/**
	 * 
	 * This method will determine whether there exists a variable or function
	 * with the specified name in the same scope.
	 * 
	 * @param name
	 *            The name of the variable or function.
	 * 
	 * @return True if there exists such a variable or function, false
	 *         otherwise.
	 * 
	 **/
	public boolean existsInScope(String name) {
		assert (name != null && name != "");
		assert (this.currentScope != null);

		TableEntry entry = SymbolTable.hashTable.get(name);
		long scope = this.currentScope.getID();

		while (entry != null) {
			if (entry.scope > scope) {
				// Go to older version.
				entry = entry.varEntry;
			} else if (entry.scope == scope) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 
	 * This method will determine whether there exists a variable or function
	 * with the specified name that can be accessed from the current scope.
	 * 
	 * @param name
	 *            The name of the variable or function.
	 * 
	 * @return True if there exists such a variable or function, false
	 *         otherwise.
	 * 
	 **/
	public boolean exists(String name) {
		assert (this.currentScope != null);
		assert ((name != null) && (!name.trim().equals("")));

		return (this.getByName(name) != null);
	}

	/**
	 * 
	 * This method returns the 'value' of the name at the current scope.
	 * 
	 * @param name
	 *            The name of the variable or function.
	 * 
	 * @return The TableEntry used for the specified name. Null if there doesn't
	 *         exist one.
	 * 
	 **/
	public TableEntry getByName(String name) {
		assert (this.currentScope != null);
		assert ((name != null) && (!name.trim().equals("")));

		TableEntry entry = SymbolTable.hashTable.get(name);
		long scope = this.currentScope.getID();
		ScopeNode trav = this.currentScope;

		while (entry != null) {
			if (entry.scope > scope) {
				// Go to older version.
				entry = entry.varEntry;
			} else if (entry.scope < scope) {
				// Go to parent of current scope.
				trav = (ScopeNode) trav.getParent();
				scope = trav.getID();
			} else {
				return entry;
			}
		}
		return null;
	}

	/**
	 * 
	 * This method returns the 'value' of the name at the current scope.
	 * 
	 * @param id
	 *            The id of the variable or function.
	 * 
	 * @return The TableEntry used for the specified name. Null if there doesn't
	 *         exist one.
	 * 
	 **/
	public TableEntry getByID(int id) {
		assert (SymbolTable.list.size() > 0);
		assert (id >= SymbolTable.list.get(0).id);
		assert (id <= SymbolTable.list.get(SymbolTable.list.size() - 1).id);

		return SymbolTable.list.get(id - SymbolTable.list.get(0).id);
	}

	/**
	 * 
	 * This method will return the location of the variable.
	 * 
	 * @param entry
	 *            The entry for which we will calculate the location.
	 * 
	 * @return The location of the variable.
	 * 
	 **/
	public int getLocation(TableEntry entry) {
		assert (entry != null);

		int location = 0;
		TableEntry current = entry.scopeEntry;
		TableEntry previous = entry;
		do {
			if (current != null) {
				location += current.getSize();
				previous = current;
				current = current.scopeEntry;
			}

			// Note that this can become true due to the previous if!
			if (current == null) {
				// We have added all of the current scope. We need all those of
				// the same depth and scope!
				ScopeNode trav = SymbolTable.scopeRoot;
				while (trav.getID() < previous.scope) {
					Node[] children = trav.getChildren();
					for (Node child : children) {
						if (((ScopeNode) child).getID() <= previous.scope) {
							trav = (ScopeNode) child;
						}
					}
				}
				assert (trav.getID() == previous.scope);

				if (trav.getParent() == null) {
					current = null;
				} else {
					do {
						current = ((ScopeNode) trav.getParent()).scopeHead;
						trav = (ScopeNode) trav.getParent();
					} while ((current == null) && (trav != null));
				}
			}
		} while ((current != null) && (current.depth == entry.depth));

		return location + 5;
	}

	/**
	 * 
	 * This method calculates the size required for a function. Note that it is
	 * assumed that the current scope is the start (main scope) of the function.
	 * 
	 * @return The size required for the function.
	 * 
	 **/
	public int getDeepSize() {
		return this.currentScope.getDeepSize() + 5;
	}
}
