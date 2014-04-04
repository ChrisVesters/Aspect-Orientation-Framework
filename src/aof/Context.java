package aof;

public interface Context {

	/**
	 * 
	 * Does the context enclose another one? We need to this know whether a
	 * pointcut matches a join point.
	 * 
	 * @param c
	 *            The other context to which we compare this one.
	 * 
	 * @return true if this context encloses the context c.
	 * 
	 **/
	public boolean isAssignable(Context c);
}
