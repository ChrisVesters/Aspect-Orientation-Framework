package aof;

// TODO: add comment!
// TODO: is this required by the Weaver?
public class Argument {

	public final String name;
	public final Type type;
	
	public Argument(String name, Type type) {
		assert (type != null);
		
		this.name = name;
		this.type = type;
	}
	
	// TODO: add a compare method.
	public boolean isAssignable(Argument arg) {
		if (this.type.isAssignable(arg.type)) {
			return true;
		} else {
			return false;
		}
	}
	
	// Is this used?
	@Deprecated
	public boolean equals(Argument arg) {
		assert (arg != null);
		
		if (!this.type.equals(arg.type)) {
			return false;
		} else if (!this.name.equals(arg.name)) {
			return false;
		}
		
		return true;
	}
	
	public String toString() {
		return this.type + " " + this.name;
	}
}
