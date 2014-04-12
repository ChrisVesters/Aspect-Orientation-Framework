package aof;

import java.util.ArrayList;

// TODO: add comments
public class PointcutSet {

	// What should we put here?
	private final String name;
	private final ArrayList<Pointcut> rules;
	private final ArrayList<Argument> args;

	public PointcutSet(String name, ArrayList<Argument> args,
			ArrayList<Pointcut> rules) {
		assert ((name != null) && (name.trim() != ""));
		assert (args != null);
		assert (rules != null);

		this.name = name;
		this.args = args;
		this.rules = rules;
	}

	public String getName() {
		return this.name;
	}

	public Argument[] getArgs() {
		return this.args.toArray(new Argument[0]);
	}

	public Pointcut[] getRules() {
		return this.rules.toArray(new Pointcut[0]);
	}
}
