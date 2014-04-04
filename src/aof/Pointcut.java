package aof;

import java.util.ArrayList;

// TODO: add comments
public class Pointcut {

	// What should we put here?
	private final String name;
	private final ArrayList<PointcutRule> rules;
	private final ArrayList<Argument> args;

	public Pointcut(String name, ArrayList<Argument> args,
			ArrayList<PointcutRule> rules) {
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

	public PointcutRule[] getRules() {
		return this.rules.toArray(new PointcutRule[0]);
	}
}
