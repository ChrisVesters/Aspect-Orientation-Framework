package aofDot;

import aof.Type;

public class Value implements Type {

	public final String value;
	
	public Value(String value) {
		this.value = value;
	}

	@Override
	public boolean isAssignable(Type t) {
		assert (t != null);
		
		if (t instanceof Value) {
			return this.value.equals(((Value) t).value);
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return this.value;
	}

}
