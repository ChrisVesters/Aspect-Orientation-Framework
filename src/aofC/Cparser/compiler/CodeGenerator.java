/**
 * 
 * @file CodeGenerator.java
 * @description This file contains the CodeGenerator.
 * @author Chris Vesters
 * @date 27/3/13
 * 
 **/
package aofC.Cparser.compiler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import aofC.Cparser.symbolTable.SymbolTable;
import aofC.Cparser.symbolTable.SymbolTableBuilder;



/**
 * 
 * This class is a central storage for all code. When finished it will write all
 * the code to an outputstream after resolving all labels.
 * 
 **/
public class CodeGenerator {

	/**
	 * 
	 * The symbolTableExplorer.
	 * 
	 **/
	public static SymbolTableBuilder symbolTable = new SymbolTableBuilder();

	/**
	 * 
	 * The current code line.
	 * 
	 **/
	private static int loc = 0;

	/**
	 * 
	 * The hashmap containing all labels and their location.
	 * 
	 **/
	private static HashMap<String, Integer> labels = new HashMap<String, Integer>();

	/**
	 * 
	 * All code that has been generated (this may still contain references to
	 * labels!)
	 * 
	 **/
	private static ArrayList<String> code = new ArrayList<String>();

	/**
	 * 
	 * This method will insert a label at the current location.
	 * 
	 **/
	public static void insertLabel(String label) {
		assert (label != null && label.trim() != "");
		assert (CodeGenerator.labels.get(label) == null);

		CodeGenerator.labels.put(label, CodeGenerator.loc);
	}

	/**
	 * 
	 * This method will add a line of code to the existing code.
	 * 
	 * @param code
	 *            The line of code that will be added. This may only be 1 line!
	 * 
	 **/
	public static void insertCode(String code) {
		assert (code != null && code.trim() != "");
		assert (code.replaceAll("\\n", "").contains("\n") == false);
		assert (code.replaceAll("\\r", "").contains("\r") == false);

		CodeGenerator.code.add(code);
		CodeGenerator.loc++;
	}

	/**
	 * 
	 * This method will optimize a part of 3 lines.
	 * 
	 * @param start
	 *            The start position of the optimization. This means that start
	 *            and the next 2 lines will be optimized.
	 * 
	 * @return The amount of lines that have been removed.
	 * 
	 **/
	private static int optimize3(int start) {
		assert (start >= 0);

		String[] parts1 = CodeGenerator.code.get(start).split(" ");
		String[] parts2 = CodeGenerator.code.get(start + 1).split(" ");
		String[] parts3 = CodeGenerator.code.get(start + 2).split(" ");

		// Though constant additions etc are already optimized during the
		// parsing phase, there are still cases in which this is not optimized.

		if (parts1[0].equals("ldc") && parts1[1].equals("i")
				&& parts1[2].equals("0")) {
			// 0 + x = x
			if (parts3[0].equals("add") && parts3[1].equals("i")) {
				CodeGenerator.code.remove(start + 2);
				CodeGenerator.code.remove(start);
				return 2;
			}

			// 0 - x = -x
			if (parts3[0].equals("sub") && parts3[1].equals("i")) {
				CodeGenerator.code.remove(start + 2);
				CodeGenerator.code.remove(start + 1);
				CodeGenerator.code.remove(start);
				CodeGenerator.code.add(start, parts2[0] + " " + parts2[1]
						+ " -" + parts2[2]);
				return 2;
			}

			// 0 * x = 0
			if (parts3[0].equals("mul") && parts3[1].equals("i")) {
				CodeGenerator.code.remove(start + 2);
				CodeGenerator.code.remove(start);
				return 2;
			}

			// 0 / x = 0
			if (parts3[0].equals("div") && parts3[1].equals("i")) {
				CodeGenerator.code.remove(start + 2);
				CodeGenerator.code.remove(start + 1);
				return 2;
			}
		}

		if (parts2[0].equals("ldc") && parts2[1].equals("i")
				&& parts2[2].equals("0")) {
			// x + 0 = x
			if (parts3[0].equals("add") && parts3[1].equals("i")) {
				CodeGenerator.code.remove(start + 2);
				CodeGenerator.code.remove(start + 1);
				return 2;
			}

			// x - 0 = x
			if (parts3[0].equals("sub") && parts3[1].equals("i")) {
				CodeGenerator.code.remove(start + 2);
				CodeGenerator.code.remove(start + 1);
				return 2;
			}

			// x * 0 = 0
			if (parts3[0].equals("mul") && parts3[1].equals("i")) {
				CodeGenerator.code.remove(start + 2);
				CodeGenerator.code.remove(start);
				return 2;
			}
		}

		if (parts1[0].equals("ldc") && parts1[1].equals("i")
				&& parts1[2].equals("1")) {
			// 1 * x = x
			if (parts3[0].equals("mul") && parts3[1].equals("i")) {
				CodeGenerator.code.remove(start + 2);
				CodeGenerator.code.remove(start);
				return 2;
			}
		}

		if (parts2[0].equals("ldc") && parts2[1].equals("i")
				&& parts2[2].equals("1")) {
			// x * 1 = x
			if (parts3[0].equals("mul") && parts3[1].equals("i")) {
				CodeGenerator.code.remove(start + 2);
				CodeGenerator.code.remove(start + 1);
				return 2;
			}

			// x / 1 = x
			if (parts3[0].equals("div") && parts3[1].equals("i")) {
				CodeGenerator.code.remove(start + 2);
				CodeGenerator.code.remove(start + 1);
				return 2;
			}
		}

		if (parts1[0].equals("ldc") && parts2[0].equals("ldc")) {
			if (parts1[1].equals("i") && parts2[1].equals("i")) {
				int i = Integer.parseInt(parts1[2]);
				int j = Integer.parseInt(parts2[2]);

				// a + b
				if (parts3[0].equals("add") && parts3[1].equals("i")) {
					CodeGenerator.code.remove(start + 2);
					CodeGenerator.code.remove(start + 1);
					CodeGenerator.code.remove(start);
					CodeGenerator.code.add(start, "ldc i " + (i + j));
					return 2;
				}

				// a - b
				if (parts3[0].equals("sub") && parts3[1].equals("i")) {
					CodeGenerator.code.remove(start + 2);
					CodeGenerator.code.remove(start + 1);
					CodeGenerator.code.remove(start);
					CodeGenerator.code.add(start, "ldc i " + (i - j));
					return 2;
				}

				// a * b
				if (parts3[0].equals("mul") && parts3[1].equals("i")) {
					CodeGenerator.code.remove(start + 2);
					CodeGenerator.code.remove(start + 1);
					CodeGenerator.code.remove(start);
					CodeGenerator.code.add(start, "ldc i " + (i * j));
					return 2;
				}

				// a / b
				if (parts3[0].equals("div") && parts3[1].equals("i")) {
					CodeGenerator.code.remove(start + 2);
					CodeGenerator.code.remove(start + 1);
					CodeGenerator.code.remove(start);
					CodeGenerator.code.add(start, "ldc i " + (i / j));
					return 2;
				}
			}
		}

		if (parts1[0].equals("lda") && parts3[0].equals("sto")) {
			if (parts2[0].equals("ldc") || parts2[0].equals("lod")) {
				assert (parts2[1].equals(parts3[1]));

				CodeGenerator.code.remove(start + 2);
				CodeGenerator.code.add(start + 2, "str " + parts3[1] + " "
						+ parts1[1] + " " + parts1[2]);
				CodeGenerator.code.remove(start);
				return 1;
			}
		}

		return optimize2(start + 1);
	}

	/**
	 * 
	 * This method will optimize a part of 2 lines.
	 * 
	 * @param start
	 *            The start position of the optimization. This means that the
	 *            line at address start and the next will be considered for
	 *            optimization.
	 * 
	 * @return The amount of lines that have been removed.
	 * 
	 **/
	private static int optimize2(int start) {
		assert (start >= 0);

		String[] parts1 = CodeGenerator.code.get(start).split(" ");
		String[] parts2 = CodeGenerator.code.get(start + 1).split(" ");

		if (parts1[0].equals("lda") && parts2[0].equals("ind")) {
			CodeGenerator.code.remove(start + 1);
			CodeGenerator.code.remove(start);
			CodeGenerator.code.add(start, "lod " + parts2[1] + " " + parts1[1]
					+ " " + parts1[2]);
			return 1;
		}

		if (parts1[0].equals("ldc") && parts1[1].equals("i")
				&& parts2[0].equals("add")) {
			assert (parts1[1].equals(parts2[1]));

			CodeGenerator.code.remove(start + 1);
			CodeGenerator.code.remove(start);
			CodeGenerator.code.add(start, "inc " + parts1[1] + " " + parts1[2]);
			return 1;
		}

		if (parts1[0].equals("ldc") && parts1[1].equals("i")
				&& parts2[0].equals("sub")) {
			assert (parts1[1].equals(parts2[1]));

			CodeGenerator.code.remove(start + 1);
			CodeGenerator.code.remove(start);
			CodeGenerator.code.add(start, "dec " + parts1[1] + " " + parts1[2]);
			return 1;
		}

		return optimize1(start + 1);
		// return 0;
	}

	/**
	 * 
	 * This method will optimize a part of 1 line.
	 * 
	 * @param start
	 *            The start position of the optimization. This means that only
	 *            this line will be optimized.
	 * 
	 * @return The amount of lines that have been removed.
	 * 
	 **/
	private static int optimize1(int start) {
		assert (start >= 0);

		String[] parts = CodeGenerator.code.get(start).split(" ");
		if (parts[0].equals("ujp")) {
			assert (CodeGenerator.labels.get(parts[1]) != null);
			if (CodeGenerator.labels.get(parts[1]) == (start + 1)) {
				CodeGenerator.code.remove(start);
				return 1;
			}
		}

		if (parts[0].equals("inc") && parts[2].equals("0")) {
			CodeGenerator.code.remove(start);
			return 1;
		}

		if (parts[0].equals("dec") && parts[2].equals("0")) {
			CodeGenerator.code.remove(start);
			return 1;
		}

		return 0;
	}

	/**
	 * 
	 * This method will optimize the code.
	 * 
	 **/
	public static void optimize() {

		// The following technique can be considered as a sliding window.
		// Note that i will indicate the end location, and not the start!
		for (int i = 0; i < CodeGenerator.code.size(); i++) {
			int removed;
			if (CodeGenerator.labels.values().contains(i)) {
				// Because the label is at the current end location, and all
				// above lines have been optimized as much as possible (1, 2 or
				// 3 without crossing the label), we only have to optimize the
				// current line.
				removed = optimize1(i);
			} else if (CodeGenerator.labels.values().contains(i - 1)) {
				// Because the label is one above the current end location, and
				// all above lines have been optimized as much as possible (1, 2
				// or 3 without crossing the label), we only have to optimize
				// the 2 lines 'below' the label.
				removed = optimize2(i - 1);
			} else {
				// No label found!
				if (i >= 2) {
					removed = optimize3(i - 2);
				} else if (i == 1) {
					removed = optimize2(0);
				} else {
					removed = optimize1(0);
				}
			}

			if (removed > 0) {
				for (String label : CodeGenerator.labels.keySet()) {
					int value = CodeGenerator.labels.get(label);
					if (value > i) {
						CodeGenerator.labels.put(label, value - removed);
					}
				}

				i -= removed + 1;
				CodeGenerator.loc -= removed;
			}
		}
	}

	/**
	 * 
	 * This method will store the code to the specified outputstream without
	 * resolving the labels.
	 * 
	 * @param os
	 *            The outputstream to which we will write the code.
	 * 
	 * @throws IOException
	 *             If writing to the outputstream resulits in an exception.
	 * 
	 **/
	public static void saveRaw(OutputStream os) throws IOException {
		assert (os != null);

		// Reverse the labels. from label -> location to location -> label.
		HashMap<Integer, ArrayList<String>> rLabels = new HashMap<Integer, ArrayList<String>>();
		for (String key : CodeGenerator.labels.keySet()) {
			int value = CodeGenerator.labels.get(key);
			ArrayList<String> existing = rLabels.get(value);
			if (existing == null) {
				existing = new ArrayList<String>();
			}
			existing.add(key);
			rLabels.put(value, existing);
		}

		int loc = 0;
		for (String line : CodeGenerator.code) {
			if (rLabels.containsKey(loc)) {
				for (String label : rLabels.get(loc)) {
					os.write(label.getBytes());
					os.write(":\n".getBytes());
				}
			}

			os.write(line.trim().getBytes());
			os.write("\n".getBytes());

			loc++;
		}
	}

	/**
	 * 
	 * This method will store the code to the specified outputstream.
	 * 
	 * @param os
	 *            The outputstream to which we will write the code.
	 * 
	 * @throws IOException
	 *             If writing to the outputstream resulits in an exception.
	 * 
	 **/
	public static void save(OutputStream os) throws IOException {
		assert (os != null);

		for (String line : CodeGenerator.code) {
			// Search for lables and resolve them if any!
			String[] parts = line.split(" ");
			// Currently still naive and thus we must be carefull with the names
			// of the labels!
			StringBuilder newLine = new StringBuilder();
			for (String part : parts) {
				if (CodeGenerator.labels.containsKey(part)) {
					// Replace the part!
					newLine.append(CodeGenerator.labels.get(part).toString());
				} else {
					newLine.append(part);
				}
				newLine.append(" ");
			}

			os.write(newLine.toString().trim().getBytes());
			os.write("\n".getBytes());
		}
	}

	/**
	 * 
	 * This method will clear the code generator.
	 * 
	 **/
	public static void clear() {
		CodeGenerator.code = new ArrayList<String>();
		CodeGenerator.labels = new HashMap<String, Integer>();
		CodeGenerator.loc = 0;
		SymbolTable.clear();
		CodeGenerator.symbolTable = new SymbolTableBuilder();
	}
}
