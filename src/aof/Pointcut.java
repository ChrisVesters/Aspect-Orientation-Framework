package aof;

import java.util.ArrayList;

// TODO: add comments
public abstract class Pointcut {

	// A Pointcut contains arguments.
	// It should even contain all arguments present in the PointcutSet.
	protected final ArrayList<Argument> args;
	protected final Context context;

	public Pointcut(ArrayList<Argument> args, Context context) {
		assert (args != null);

		this.args = args;
		this.context = context;
	}

	public Argument[] getArgs() {
		return this.args.toArray(new Argument[0]);
	}

	/**
	 * 
	 * This method will generate a match between this pointcutrule and the
	 * specified one.
	 * 
	 * @param rule
	 *            The rule we want to create a match for.
	 * @return A match if this rule encloses the specified one, null otherwise.
	 * 
	 **/
	public abstract Match encloses(Pointcut rule);
}