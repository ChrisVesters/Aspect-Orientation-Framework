/**
 * 
 */
package aof;

import java.util.HashMap;
import java.util.Set;

/**
 *
 */
// TODO: comments
public class Match {

	private HashMap<String, Argument> args;
	
 	public Match() {
 		this(new HashMap<String, Argument>());
 	}
	
	public Match(HashMap<String, Argument> args) {
		assert (args != null);
		
		this.args = args;
	}
	
	public Argument get(String key) {
		return this.args.get(key);
	}
	
	public Argument put(String key, Argument arg) {
		return this.args.put(key, arg);
	}
	
	public void putAll(Match match) {
		this.args.putAll(match.args);
	}
	
	public int size() {
		return this.args.size();
	}
	
	public Set<String> keySet() {
		return this.args.keySet();
	}
	
	public boolean containsKey(String key) {
		return this.args.containsKey(key);
	}
	
	@Override
	public String toString() {
		String str = new String();
		
		str += "{";
		for (String key: args.keySet()) {
			str += key + "=" + args.get(key) + ", ";
		}
		
		if (args.size() > 0) {
			str = str.substring(0, str.length()-2);
		}
		
		str += "}";
		
		return str;
	}
	
}
