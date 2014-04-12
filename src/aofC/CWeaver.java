/**
 * 
 * @Filename CWeaver.java
 * @Description This file contains the Weaver for Small C.
 * 
 * @Author Chris Vesters
 * @Date 12/12/2013
 *
 **/

package aofC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import aof.Advice;
import aof.Argument;
import aof.Match;
import aof.Pointcut;
import aof.Weaver;
import aofC.MethodPointcut.JoinPoint;
import aofC.AspectParser.AOCLexer;
import aofC.AspectParser.AOCParser;
import aofC.Cparser.ast.RootNode;
import aofC.Cparser.compiler.CodeGenerator;
import aofC.Cparser.compiler.ExprLexer;
import aofC.Cparser.compiler.SmallCParserPass1;
import aofC.Cparser.compiler.SmallCParserPass2;
import aofC.Cparser.symbolTable.PrimaryType;
import aofC.Cparser.symbolTable.PrimaryType.PType;

/**
 * 
 * This is the CWeaver class. This is the beginning of generating a weaved Small
 * C file.
 * 
 * Weaving happens in 3 steps: 1: Get all the joinpoints present in the source
 * file. 2: Rename all the calls to the wrappers. 3: Add the generated code to
 * the source code. 4: Compile this code with a normal compiler
 * 
 * Note that step 4 isn't done by the weaver.
 * 
 **/
public class CWeaver extends Weaver {

	/**
	 * 
	 * This method generates empty wrappers. This is required for step 2, to
	 * prevent the compiler from complaining.
	 * 
	 * @return A string with the Small C code of the empty wrappers.
	 * 
	 **/
	private String generateForwardDecls() {
		String result = new String();

		for (Pointcut pcr : Weaver.joinpoints) {
			if (pcr instanceof MethodPointcut) {
				MethodPointcut mpcr = (MethodPointcut) pcr;

				String wrap = mpcr.returnType + " " + mpcr.name;
				if (mpcr.joinPoint == MethodPointcut.JoinPoint.CALL) {
					wrap += "_call_(";
				} else if (mpcr.joinPoint == MethodPointcut.JoinPoint.EXECUTE) {
					wrap += "_exec_(";
				}

				Argument[] args = mpcr.getArgs();
				for (int i = 0; i < args.length; i++) {
					if (i != 0) {
						wrap += ", ";
					}
					wrap += args[i].type.toString() + " " + args[i].name;
				}
				wrap += ") {\n";
				if (!mpcr.returnType.same(new PrimaryType(
						PrimaryType.PType.VOID))) {
					wrap += "\t" + mpcr.returnType + " _result_;\n";
					wrap += "\treturn _result_;\n";
				}
				wrap += "}\n\n";

				result += wrap;
			} else if (pcr instanceof MemberPointcut) {
				MemberPointcut mpcr = (MemberPointcut) pcr;

				Argument arg = mpcr.getArgs()[0];
				String wrap = new String();
				if (mpcr.joinPoint == MemberPointcut.JoinPoint.SET) {
					wrap += "void " + arg.name + "_set_(" + arg.type
							+ " _new_)";
					wrap += "{\n}\n\n";
				} else if (mpcr.joinPoint == MemberPointcut.JoinPoint.GET) {
					wrap += arg.type + " " + arg.name + "_get_()";
					wrap += "{\n";
					wrap += "\t" + arg.type + " _result_;\n";
					wrap += "\treturn _result_;\n";
					wrap += "}\n\n";
				}

				result += wrap;
			} else {
				// This shouldn't be happening!
			}
		}

		result += "\n\n";

		return result;
	}

	// TODO: re-engineer
	private String generateMemberAround(String name, MemberPointcut jp,
			NavigableMap<Advice, Match> advices) {
		// private String generateMemberAround(String name, MemberPointcutRule
		// jp, NavigableMap<Advice, ArrayList<Argument>> advices) {
		String result = new String();

		// The first element in advice is the around!
		assert (advices.firstKey().getName().equals(name));
		Argument[] adviceArgs = advices.firstKey().getParams();
		advices = advices.tailMap(advices.firstKey(), false);

		CAdvice thisAdvice = (CAdvice) Weaver.advices.get(name);
		String code = thisAdvice.toString();

		String[] parts = code.split("proceed\\(.*\\)");
		assert (parts.length <= 2);

		// Determine the wrapper name.
		Argument arg = jp.getArgs()[0];
		String returnType = new String();
		name += "_" + arg.name + "_(";
		if (jp.joinPoint == MemberPointcut.JoinPoint.GET) {
			// name += "_" + arg.name + "_get_(";
			returnType = arg.type.toString();
		} else if (jp.joinPoint == MemberPointcut.JoinPoint.SET) {
			// name += "_" + arg.name + "_set_(";
			returnType = "void";
		}

		// Merge the arguments of the advice and the joinpoint.
		Argument[] args = new Argument[jp.getArgs().length];
		for (int i = 0; i < jp.getArgs().length; i++) {
			if (i < adviceArgs.length) {
				args[i] = adviceArgs[i];
			} else {
				args[i] = jp.getArgs()[i];
			}
		}

		// If the proceed defines it's own arguments, use those!
		String[] proceedArgs = new String[0];
		String proceed = new String();
		if (parts.length == 2) {
			proceed = code.replace(parts[0], "").replace(parts[1], "");
			String argString = proceed.substring(proceed.indexOf('(') + 1,
					proceed.indexOf(')'));
			if (!argString.trim().equals("")) {
				proceedArgs = argString.split(",");
			}
		}

		{
			// Since the around will call the real function, it needs
			// arguments. Even if there are no specified in the advice itself!
			for (int i = 0; i < args.length; i++) {
				if (i > 0) {
					name += ", ";
				}

				name += args[i].type + " " + args[i].name;
			}
		}

		name += ")";

		// Write the function signature of the wrapper.
		result += returnType + " " + name + " {";

		int prevLine = parts[0].lastIndexOf(';') + 1;
		String before = parts[0].substring(0, prevLine);
		result += before + "\n";

		if (parts.length == 2) {
			// There is a proceed(), start adding
			int nextLine = parts[1].indexOf(';') + 1;

			result += "\t{\n";

			boolean toCall = true;
			// Start adding calls to the body -> This are all the before and
			// arround
			// advices.
			for (Advice advice : advices.keySet()) {
				CAdvice cadvice = (CAdvice) advice;
				if (cadvice.moment == Moment.AFTER) {
					continue;
				} else if (cadvice.moment == Moment.BEFORE) {
					String callArgs = "(";

					for (int i = 0; i < advices.get(advice).size(); i++) {
						if (i > 0) {
							callArgs += ", ";
						}

						if (i < proceedArgs.length) {
							callArgs += proceedArgs[i];
						} else {
							callArgs += args[i].name;
						}
					}
					callArgs += ");\n";

					result += "\t\t" + cadvice.getName() + callArgs;
				} else if (cadvice.moment == Moment.AROUND) {
					String callArgs = "(";

					// Since the arround will call the real function, it needs
					// arguments. Even if there are no specified in the advice
					// itself!
					// Argument[] args = jp.getArgs();
					for (int i = 0; i < jp.getArgs().length; i++) {
						if (i > 0) {
							callArgs += ", ";
						}

						if (i < proceedArgs.length) {
							callArgs += proceedArgs[i];
						} else {
							callArgs += args[i].name;
						}
					}
					callArgs += ")";

					NavigableMap<Advice, Match> subMap = advices.tailMap(
							advice, true);
					String fDecl = generateMemberAround(cadvice.getName(), jp,
							subMap);
					result = fDecl + result;

					advices = (NavigableMap<Advice, Match>) advices
							.headMap(advice);

					result += "\t\t" + parts[0].substring(prevLine).trim()
							+ " ";
					result += cadvice.getName() + "_" + arg.name + "_"
							+ callArgs;
					result += parts[1].substring(0, nextLine) + "\n";
					toCall = false;
					break;
				}
			}

			if (toCall) {
				// Add the body. If a proceed argument is specified, we need to
				// use that one!

				result += "\t\t" + parts[0].substring(prevLine).trim() + " ";

				if (jp.joinPoint == MemberPointcut.JoinPoint.SET) {
					if (proceedArgs.length > 0) {
						result += arg.name + " = " + proceedArgs[0];
					} else {
						result += arg.name + " = " + args[0].name;
					}
				} else if (jp.joinPoint == MemberPointcut.JoinPoint.GET) {
					if (proceedArgs.length > 0) {
						result += proceedArgs[0];
					} else {
						result += args[0].name;
					}
				}
				result += parts[1].substring(0, nextLine) + "\n";
			}

			// Start adding the remaining calls -> This are the after advices.
			for (Advice advice : advices.descendingKeySet()) {
				CAdvice cadvice = (CAdvice) advice;

				if (cadvice.moment != Moment.AFTER) {
					continue;
				}

				String call = cadvice.getName() + "(";

				// ArrayList<Argument> args = advices.get(advice);
				for (int i = 0; i < advices.get(advice).size(); i++) {
					if (i > 0) {
						call += ", ";
					}

					call += args[i].name;
				}
				call += ");\n";

				result += "\t\t" + call;
			}

			result += "\t}\n";

			// Put the part of the advice body that occurs after proceed.
			result += parts[1].substring(nextLine);
		}

		result += "}\n\n";

		return result;
	}

	// TODO: re-engineer
	private String generateFunctionArround(String funcName,
			MethodPointcut jp, NavigableMap<Advice, Match> advices) {
		// private String generateFunctionArround(String funcName,
		// MethodPointcutRule jp,
		// NavigableMap<Advice, ArrayList<Argument>> advices) {
		String result = new String();

		// The first element in advice is the around!
		assert (advices.firstKey().getName().equals(funcName));
		Argument[] adviceArgs = advices.firstKey().getParams();
		advices = advices.tailMap(advices.firstKey(), false);

		CAdvice thisAdvice = (CAdvice) Weaver.advices.get(funcName);
		String code = thisAdvice.toString();

		String[] parts = code.split("proceed\\(.*\\)");
		assert (parts.length <= 2);

		// Determine the wrapper name.
		String callName = new String();
		if (jp.joinPoint == JoinPoint.CALL) {
			callName = jp.name + "_exec_(";
		} else if (jp.joinPoint == JoinPoint.EXECUTE) {
			callName = jp.name + "(";
		}
		funcName += "_" + jp.name + "_(";

		// Merge the arguments of the advice and the joinpoint.
		Argument[] args = new Argument[jp.getArgs().length];
		for (int i = 0; i < jp.getArgs().length; i++) {
			if (i < adviceArgs.length) {
				args[i] = adviceArgs[i];
			} else {
				args[i] = jp.getArgs()[i];
			}
		}

		// If the proceed defines it's own arguments, use those!
		String[] proceedArgs = new String[0];
		String proceed = new String();
		if (parts.length == 2) {
			proceed = code.replace(parts[0], "").replace(parts[1], "");
			String argString = proceed.substring(proceed.indexOf('(') + 1,
					proceed.indexOf(')'));
			if (!argString.trim().equals("")) {
				proceedArgs = argString.split(",");
			}
		}

		{
			// Since the arround will call the real function, it needs
			// arguments. Even if there are no specified in the advice itself!

			for (int i = 0; i < args.length; i++) {
				if (i > 0) {
					funcName += ", ";
					callName += ", ";
				}

				funcName += args[i].type + " " + args[i].name;

				if (i < proceedArgs.length) {
					callName += proceedArgs[i];
				} else {
					callName += args[i].name;
				}
			}
		}

		funcName += ")";
		callName += ")";

		// Write the function signature of the wrapper.
		result += jp.returnType + " " + funcName + " {";

		int prevLine = parts[0].lastIndexOf(';') + 1;
		String before = parts[0].substring(0, prevLine);
		result += before + "\n";

		if (parts.length == 2) {
			// There is a proceed(), start adding
			int nextLine = parts[1].indexOf(';') + 1;

			result += "\t{\n";

			boolean toCall = true;
			// Start adding calls to the body -> This are all the before and
			// arround
			// advices.
			for (Advice advice : advices.keySet()) {
				CAdvice cadvice = (CAdvice) advice;
				if (cadvice.moment == Moment.AFTER) {
					continue;
				} else if (cadvice.moment == Moment.BEFORE) {
					String callArgs = "(";

					// Since the arround will call the real function, it needs
					// arguments. Even if there are no specified in the advice
					// itself!
					// Argument[] args = jp.getArgs();
					for (int i = 0; i < advices.get(advice).size(); i++) {
						if (i > 0) {
							callArgs += ", ";
						}

						if (i < proceedArgs.length) {
							callArgs += proceedArgs[i];
						} else {
							callArgs += args[i].name;
						}
					}
					callArgs += ");\n";

					result += "\t\t" + cadvice.getName() + callArgs;
				} else if (cadvice.moment == Moment.AROUND) {
					String callArgs = "(";

					// Since the arround will call the real function, it needs
					// arguments. Even if there are no specified in the advice
					// itself!
					// Argument[] args = jp.getArgs();
					for (int i = 0; i < jp.getArgs().length; i++) {
						if (i > 0) {
							callArgs += ", ";
						}

						if (i < proceedArgs.length) {
							callArgs += proceedArgs[i];
						} else {
							callArgs += args[i].name;
						}
					}
					callArgs += ")";

					NavigableMap<Advice, Match> subMap = advices.tailMap(
							advice, true);
					String fDecl = generateFunctionArround(cadvice.getName(),
							jp, subMap);
					result = fDecl + result;

					advices = (NavigableMap<Advice, Match>) advices
							.headMap(advice);

					result += "\t\t" + parts[0].substring(prevLine).trim()
							+ " ";
					result += cadvice.getName() + "_" + jp.name + "_"
							+ callArgs;
					result += parts[1].substring(0, nextLine) + "\n";
					toCall = false;
					break;
				}
			}

			// Call to the next function. Unless it was an arround!
			// In that case it's allready done!
			if (toCall) {
				result += "\t\t" + parts[0].substring(prevLine).trim() + " ";
				result += callName;
				result += parts[1].substring(0, nextLine) + "\n";
			}

			// Start adding the remaining calls -> This are the after advices.
			for (Advice advice : advices.descendingKeySet()) {
				CAdvice cadvice = (CAdvice) advice;

				if (cadvice.moment != Moment.AFTER) {
					continue;
				}

				String call = cadvice.getName() + "(";

				// ArrayList<Argument> args = advices.get(advice);
				for (int i = 0; i < advices.get(advice).size(); i++) {
					if (i > 0) {
						call += ", ";
					}

					call += args[i].name;
				}
				call += ");\n";

				result += "\t\t" + call;
			}

			result += "\t}\n";

			// Put the part of the advice body that occurs after proceed.
			result += parts[1].substring(nextLine);
		}

		result += "}\n\n";

		return result;
	}

	// TODO: re-engineer?
	private String generateMemberWrapper(String varName, MemberPointcut jp,
			NavigableMap<Advice, Match> advices) {
		// private String generateMemberWrapper(String varName,
		// MemberPointcutRule jp,
		// NavigableMap<Advice, ArrayList<Argument>> advices) {
		String result = new String();

		// Write the function signature of the wrapper.
		Argument arg = jp.getArgs()[0];
		if (jp.joinPoint == MemberPointcut.JoinPoint.SET) {
			result += "void " + varName + "(" + arg.type + " _new_) {\n";
		} else if (jp.joinPoint == MemberPointcut.JoinPoint.GET) {
			result += arg.type + " " + varName + "() {\n";
			// Before calling the advices, prepare a variable.
			result += "\t" + arg.type + " _result_;\n";
		}

		// Start adding calls to the body -> This are all the before and arround
		// advices.
		boolean toCall = true;
		for (Advice advice : advices.keySet()) {
			CAdvice cadvice = (CAdvice) advice;

			if (cadvice.moment == Moment.AFTER) {
				continue;
			} else if (cadvice.moment == Moment.BEFORE) {
				String callArgs = "(";

				Match args = advices.get(advice);
				// ArrayList<Argument> args = advices.get(advice);
				assert (args.size() <= 1);

				// If get, use the original value!
				// If set, use the setted value!
				if (args.size() == 1) {
					if (jp.joinPoint == MemberPointcut.JoinPoint.SET) {
						callArgs += "_new_";
					} else {
						Argument adArg = cadvice.getParams()[0];
						callArgs += args.get(adArg.name).name;
						// callArgs += args.get(0).name;
					}
				}

				callArgs += ");\n";
				result += "\t" + cadvice.getName() + callArgs;
			} else if (cadvice.moment == Moment.AROUND) {
				String callArgs = "(";

				// In case of get, use the modified name, in case of set use the
				// original name.
				// For set, the original name will always be updated, in the
				// case of get, it won't!
				Argument[] args = jp.getArgs();
				if (args.length == 1) {
					if (jp.joinPoint == MemberPointcut.JoinPoint.SET) {
						callArgs += "_new_";
					} else {
						callArgs += args[0].name;
					}
				}
				callArgs += ");\n";

				NavigableMap<Advice, Match> subMap = advices.tailMap(advice,
						true);
				String fDecl = generateMemberAround(cadvice.getName(), jp,
						subMap);
				result = fDecl + result;

				advices = (NavigableMap<Advice, Match>) advices.headMap(advice);

				result += "\t";
				if (jp.joinPoint == MemberPointcut.JoinPoint.GET) {
					result += "_result_ = ";
				}
				result += cadvice.getName() + "_" + arg.name + "_" + callArgs;

				toCall = false;
				break;
			}
		}

		if (toCall) {
			// Add the body.
			if (jp.joinPoint == MemberPointcut.JoinPoint.SET) {
				result += "\t" + arg.name + " = _new_;\n";
			} else if (jp.joinPoint == MemberPointcut.JoinPoint.GET) {
				result += "\t_result_ = " + arg.name + ";\n";
			}
		}

		// Start adding the remaining calls -? This are the after advices.
		for (Advice advice : advices.descendingKeySet()) {
			CAdvice cadvice = (CAdvice) advice;

			if (cadvice.moment != Moment.AFTER) {
				continue;
			}

			String call = cadvice.getName() + "(";

			Match args = advices.get(advice);
			// ArrayList<Argument> args = advices.get(advice);
			assert (args.size() <= 1);

			// In case of get, use the modified name, in case of set use the
			// original name.
			// For set, the original name will always be updated, in the case of
			// get, it won't!
			if (args.size() == 1) {
				if (jp.joinPoint == MemberPointcut.JoinPoint.GET) {
					call += "_result_";
				} else {
					Argument adArg = cadvice.getParams()[0];
					call += args.get(adArg.name).name;
					// call += args.get(0).name;
				}
			}

			call += ");\n";

			result += "\t" + call;
		}

		// Return the stored value.
		if (jp.joinPoint == MemberPointcut.JoinPoint.GET) {
			result += "\treturn _result_;\n";
		}

		result += "}\n\n";

		return result;
	}

	// TODO: re-engineer
	private String generateFunctionWrapper(String funcName,
			MethodPointcut jp, NavigableMap<Advice, Match> advices) {
		// private String generateFunctionWrapper(String funcName,
		// MethodPointcutRule jp,
		// NavigableMap<Advice, ArrayList<Argument>> advices) {
		String result = new String();

		// Determine the wrapper name.
		String callName = new String();
		if (jp.joinPoint == JoinPoint.CALL) {
			callName = jp.name + "_exec_(";
		} else if (jp.joinPoint == JoinPoint.EXECUTE) {
			callName = jp.name + "(";
		}
		funcName += "(";

		{
			Argument[] args = jp.getArgs();
			for (int i = 0; i < args.length; i++) {
				if (i > 0) {
					funcName += ", ";
					callName += ", ";
				}

				funcName += args[i].type + " " + args[i].name;
				callName += args[i].name;
			}
		}

		funcName += ")";
		callName += ")";

		// Write the function signature of the wrapper.
		result += jp.returnType + " " + funcName + " {\n";

		// Before calling the advices, prepare a variable if there is a
		// returntype!
		if (!jp.returnType.same(new PrimaryType(PType.VOID))) {
			result += "\t" + jp.returnType + " _result_;\n";
		}

		boolean toCall = true;
		// Start adding calls to the body -> This are all the before and arround
		// advices.
		for (Advice advice : advices.keySet()) {
			CAdvice cadvice = (CAdvice) advice;

			if (cadvice.moment == Moment.AFTER) {
				continue;
			} else if (cadvice.moment == Moment.BEFORE) {
				String callArgs = "(";

				Match args = advices.get(advice);
				for (int i = 0; i < args.size(); i++) {
					if (i > 0) {
						callArgs += ", ";
					}

					System.out.println(args.size());
					System.out.println(cadvice.getParams()[i]);
					System.out.println(args.keySet());
					System.out.println(args.get(cadvice.getParams()[i].name));
					callArgs += args.get(cadvice.getParams()[i].name).name;
				}

				// ArrayList<Argument> args = advices.get(advice);
				// for (int i = 0; i < args.size(); i++) {
				// if (i > 0) {
				// callArgs += ", ";
				// }

				// callArgs += args.get(i).name;
				// }
				callArgs += ");\n";
				result += "\t" + cadvice.getName() + callArgs;
			} else if (cadvice.moment == Moment.AROUND) {
				String callArgs = "(";

				// Since the around will call the real function, it needs
				// arguments.
				// Even if there are no specified in the advice itself!
				Argument[] args = jp.getArgs();
				for (int i = 0; i < args.length; i++) {
					if (i > 0) {
						callArgs += ", ";
					}

					callArgs += args[i].name;
				}
				callArgs += ");\n";

				NavigableMap<Advice, Match> subMap = advices.tailMap(advice,
						true);
				String fDecl = generateFunctionArround(cadvice.getName(), jp,
						subMap);
				result = fDecl + result;

				advices = (NavigableMap<Advice, Match>) advices.headMap(advice);

				result += "\t";
				if (!jp.returnType.same(new PrimaryType(PType.VOID))) {
					result += "_result_ = ";
				}
				result += cadvice.getName() + "_" + jp.name + "_" + callArgs;
				toCall = false;
				break;
			}
		}

		// Call to the next function. Unless it was an arround!
		// In that case it's allready done!
		if (toCall) {
			result += "\t";
			if (!jp.returnType.same(new PrimaryType(PType.VOID))) {
				result += "_result_ = ";
			}
			result += callName + ";\n";
		}

		// Start adding the remaining calls -? This are the after advices.
		for (Advice advice : advices.descendingKeySet()) {
			CAdvice cadvice = (CAdvice) advice;

			if (cadvice.moment != Moment.AFTER) {
				continue;
			}

			String call = cadvice.getName() + "(";

			Match args = advices.get(advice);
			for (int i = 0; i < args.size(); i++) {
				if (i > 0) {
					call += ", ";
				}

				call += args.get(cadvice.getParams()[i].name).name;
			}

			// ArrayList<Argument> args = advices.get(advice);
			// for (int i = 0; i < args.size(); i++) {
			// if (i > 0) {
			// call += ", ";
			// }

			// call += args.get(i).name;
			// }
			call += ");\n";

			result += "\t" + call;
		}

		// Return the stored value.
		if (!jp.returnType.same(new PrimaryType(PrimaryType.PType.VOID))) {
			result += "\treturn _result_;\n";
		}

		result += "}\n\n";

		return result;
	}

	/**
	 * 
	 * This method generates the Small C code of the advices.
	 * 
	 * @return A string with the small C code of the advices.
	 * 
	 **/
	private String generateAdvices() {
		String result = new String();

		// Write all the before and after advices.
		for (String adviceName : Weaver.advices.keySet()) {
			CAdvice advice = (CAdvice) Weaver.advices.get(adviceName);

			if (advice.moment != Moment.AROUND) {
				result += "void " + advice.getName() + "(";

				Argument[] args = advice.getParams();
				for (int i = 0; i < args.length; i++) {
					if (i != 0) {
						result += ", ";
					}
					result += args[i].toString();
				}

				result += ") {" + advice.toString() + "}\n\n";
			}
		}

		// Generate all wrappers
		for (Pointcut pcr : Weaver.joinpoints) {
			if (pcr instanceof MethodPointcut) {
				MethodPointcut mpcr = (MethodPointcut) pcr;

				// Determine the wrapper name.
				String wrapperName = mpcr.name;
				if (mpcr.joinPoint == MethodPointcut.JoinPoint.CALL) {
					wrapperName += "_call_";
				} else if (mpcr.joinPoint == MethodPointcut.JoinPoint.EXECUTE) {
					wrapperName += "_exec_";
				}

				// Now start adding the body of the wrapper.
				TreeMap<Advice, Match> advices = executingAdvices(pcr);
				result += generateFunctionWrapper(wrapperName, mpcr, advices);
			} else if (pcr instanceof MemberPointcut) {
				MemberPointcut mpcr = (MemberPointcut) pcr;

				// Determine the wrapper name.
				Argument arg = mpcr.getArgs()[0];
				String wrapperName = new String();
				if (mpcr.joinPoint == MemberPointcut.JoinPoint.SET) {
					wrapperName += arg.name + "_set_";
				} else if (mpcr.joinPoint == MemberPointcut.JoinPoint.GET) {
					wrapperName += arg.name + "_get_";
				}

				// Now start adding the body of the wrapper.
				TreeMap<Advice, Match> advices = executingAdvices(pcr);
				result += generateMemberWrapper(wrapperName, mpcr, advices);
			}
		}

		return result;
	}

	// TODO: re-engineer
	public static void main(String[] args) throws Exception {
		Weaver.main(args);

		CWeaver weaver = new CWeaver();
		CodeGenerator.clear();

		// Parse the aspect files.
		// In contrary to the source files we can handle multiple aspect
		// files.
		for (String aspectFile : CWeaver.aspectFiles) {
			FileInputStream in = new FileInputStream(
					System.getProperty("user.dir") + File.separatorChar
							+ aspectFile);
			ANTLRInputStream input = new ANTLRInputStream(in);
			AOCLexer aspectLexer = new AOCLexer(input);
			CommonTokenStream aspectTokens = new CommonTokenStream(aspectLexer);
			AOCParser aspectParser = new AOCParser(aspectTokens);
			aspectParser.file();
		}

		if (CWeaver.sourceFiles.size() == 0) {
			return;
		} else if (CWeaver.sourceFiles.size() > 1) {
			throw new Exception();
		}

		String sourceFile = CWeaver.sourceFiles.get(0);

		// Pass 1 of the parsing: this will give us all the joinpoints in the
		// source code.
		{
			FileInputStream is = new FileInputStream(
					System.getProperty("user.dir") + File.separatorChar
							+ sourceFile);
			String code = new String();
			// Small C can not handle imports, so only 1 sourcefile allowed!

			String line = new String();
			BufferedReader input = new BufferedReader(new InputStreamReader(is));
			while ((line = input.readLine()) != null) {
				code += line + "\n";
			}

			ANTLRStringStream antlrinputv2 = new ANTLRStringStream(code);
			ExprLexer lexerv2 = new ExprLexer(antlrinputv2);
			CommonTokenStream tokensv2 = new CommonTokenStream(lexerv2);
			SmallCParserPass1 pass1 = new SmallCParserPass1(tokensv2);
			pass1.smallc_program();
		}

		CodeGenerator.clear();

		// Pass 2 of the parsing: this will rename all the calls.
		String result = new String();
		{
			FileInputStream is = new FileInputStream(
					System.getProperty("user.dir") + File.separatorChar
							+ sourceFile);
			String code = weaver.generateForwardDecls();

			String line = new String();
			BufferedReader input = new BufferedReader(new InputStreamReader(is));
			while ((line = input.readLine()) != null) {
				code += line + "\n";
			}

			ANTLRStringStream antlrinput = new ANTLRStringStream(code);
			ExprLexer lexer = new ExprLexer(antlrinput);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			SmallCParserPass2 pass2 = new SmallCParserPass2(tokens);
			RootNode rootnode = pass2.smallc_program();
			result = rootnode.toString();
		}

		// Pass 3 of the parsing: this is a regular parsing step!
		// Note that this is skipped because the SmallC parser can only
		// translate into P-Code

		// Write it to file to test it.
		FileWriter writer = new FileWriter(System.getProperty("user.dir")
				+ File.separatorChar + Weaver.outputDir + File.separatorChar
				+ "aspects.c");
		writer.write("#include <stdio.h>\n\n");
		writer.write(result);
		writer.write(weaver.generateAdvices());
		writer.flush();
		writer.close();
	}
}