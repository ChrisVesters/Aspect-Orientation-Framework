// $ANTLR 3.4 src/main/antlr3/compiler/Expr.g 2013-11-09 16:35:05

package aofC.Cparser.compiler;

import org.antlr.runtime.*;

import aof.Advice;
import aof.Argument;
import aof.Pointcut;
import aof.PointcutRule;
import aof.Weaver;
import aofC.CAdvice;
import aofC.CWeaver;
import aofC.MemberPointcutRule;
import aofC.MethodPointcutRule;
import aofC.Moment;
import aofC.Cparser.ast.*;
import aofC.Cparser.ast.OperatorNode.Operator;
import aofC.Cparser.symbolTable.*;
import aofC.Cparser.symbolTable.PrimaryType.PType;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * 
 * @file Expr.g
 * @description The ANTLR grammar file
 * @author Yentl Van Tendeloo
 * @date 6/3/13
 * 
 **/
@SuppressWarnings({ "all", "warnings", "unchecked" })
public class SmallCParserPass1 extends Parser {
	public static final String[] tokenNames = new String[] { "<invalid>",
			"<EOR>", "<DOWN>", "<UP>", "BREAK", "CHAR", "CHARCONST", "COMMENT",
			"CONST", "CONTINUE", "DEREFERENCE", "ELSE", "FLOAT", "FLOATCONST",
			"FOR", "ID", "IF", "INT", "INTCONST", "ML_COMMENT", "POINTER",
			"READ", "RETURN", "STDIOIMPORT", "STRING", "VOID", "WHILE",
			"WRITE", "WS", "'!'", "'!='", "'&&'", "'('", "')'", "'+'", "','",
			"'-'", "'/'", "':'", "';'", "'<'", "'<='", "'='", "'=='", "'>'",
			"'>='", "'?'", "'['", "']'", "'{'", "'||'", "'}'" };

	public static final int EOF = -1;
	public static final int T__29 = 29;
	public static final int T__30 = 30;
	public static final int T__31 = 31;
	public static final int T__32 = 32;
	public static final int T__33 = 33;
	public static final int T__34 = 34;
	public static final int T__35 = 35;
	public static final int T__36 = 36;
	public static final int T__37 = 37;
	public static final int T__38 = 38;
	public static final int T__39 = 39;
	public static final int T__40 = 40;
	public static final int T__41 = 41;
	public static final int T__42 = 42;
	public static final int T__43 = 43;
	public static final int T__44 = 44;
	public static final int T__45 = 45;
	public static final int T__46 = 46;
	public static final int T__47 = 47;
	public static final int T__48 = 48;
	public static final int T__49 = 49;
	public static final int T__50 = 50;
	public static final int T__51 = 51;
	public static final int BREAK = 4;
	public static final int CHAR = 5;
	public static final int CHARCONST = 6;
	public static final int COMMENT = 7;
	public static final int CONST = 8;
	public static final int CONTINUE = 9;
	public static final int DEREFERENCE = 10;
	public static final int ELSE = 11;
	public static final int FLOAT = 12;
	public static final int FLOATCONST = 13;
	public static final int FOR = 14;
	public static final int ID = 15;
	public static final int IF = 16;
	public static final int INT = 17;
	public static final int INTCONST = 18;
	public static final int ML_COMMENT = 19;
	public static final int POINTER = 20;
	public static final int READ = 21;
	public static final int RETURN = 22;
	public static final int STDIOIMPORT = 23;
	public static final int STRING = 24;
	public static final int VOID = 25;
	public static final int WHILE = 26;
	public static final int WRITE = 27;
	public static final int WS = 28;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators

	public SmallCParserPass1(TokenStream input) {
		this(input, new RecognizerSharedState());
	}

	public SmallCParserPass1(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	public String[] getTokenNames() {
		return SmallCParserPass1.tokenNames;
	}

	public String getGrammarFileName() {
		return "src/main/antlr3/compiler/Expr.g";
	}

	/** Number of exceptions */
	int exceptions = 0;

	// Start at line 1
	int linenumber = 1;

	/** Checks whether or not errors happened while parsing/lexing */
	public boolean hasError() {
		return exceptions > 0;
	}

	/**
	 * Override the default error handler in ANTLR, both for returning the
	 * number of exceptions that happened, but also to prevent ANTLR errors to
	 * appear while tests are ran
	 */
	@Override
	public void reportError(RecognitionException e) {
		// Don't pass failed predicates, since these are ours
		if (!this.testcase && !(e instanceof FailedPredicateException)) {
			super.reportError(e);
		}
		exceptions += 1;
	}

	/**
	 * List of all currently nested loops, a continue/break will do this on the
	 * last one
	 */
	List<Integer> loopList = new ArrayList<Integer>();

	/** Current unique ID for a while/for/if */
	int labelID = 0;

	/** Is this a test run? */
	boolean testcase = false;

	/** Have we already found the required 'void main()' function? */
	boolean foundmain = false;

	/**
	 * The depth of the while/for loop, needed to known whether or not a
	 * break/continue is actually logical/possible
	 */
	int whiledepth = 0;

	/** Not used yet, use for import checking */
	boolean stdio = false;

	/** The weaver used. **/
	CWeaver weaver = new CWeaver();

	/** Kind of stack for the expected return type */
	List<CType> returnTypes = new ArrayList<CType>();

	/**
	 * For temporary operations, done on a global scope to prevent
	 * goingout-of-scope
	 */
	Node tmpnode;

	/** The symbol table */
	SymbolTableBuilder stb = new SymbolTableBuilder();

	/** List of all defined variables, used for checking later on */
	HashMap<Integer, Boolean> defined = new HashMap<Integer, Boolean>();

	/**
	 * List of all variables that are used but not yet defined. All these
	 * variables MUST be checked at the end of the AST construction to make sure
	 * they are actually defined. This is to comply with C, since it is possible
	 * to forward declare a function but never define it (as long as it is not
	 * used)
	 */
	List<Integer> mustExist = new ArrayList<Integer>();

	/** Return the symbol table */
	public SymbolTableBuilder GetSymbolTable() {
		return stb;
	}

	/**
	 * Used internally to do 'intelligent' error handling and printing semantic
	 * errors only when not running a test (as we don't need all the output
	 * generated during tests)
	 */
	private void throwError(String error, int linenumber)
			throws FailedPredicateException {
		if (!this.testcase) {
			System.err.println("Line " + linenumber + ": " + error);
		}
		// No matter what, throw the exception
		throw new FailedPredicateException();
	}

	/**
	 * Check if type1 and type2 are compatible types. Currently, they must be
	 * EXACTLY equal types, this should be changed later on
	 */
	private CType compatibleTypes(CType type1, CType type2, int linenumber)
			throws FailedPredicateException {
		if (type1 == null) {
			// This means that type1 is unnecessary and that it is always
			// possible
			return type2;
		} else if (type2 == null) {
			// See above
			return type1;
		} else if (type1.same(type2)) {
			// Equal types, so everything is OK
			return type1;
		} else {
			// Types are not equal, this is a type error
			throwError("Type error: incompatible types " + type1.print()
					+ " and " + type2.print(), linenumber);
		}
		// This is here to make Java happy...
		return null;
	}

	/**
	 * Declare a variable, this does the most important checking, note that it
	 * does NOT yet allow for all strange forward declaration magic allowed in C
	 */
	private void declare(String name, CType type, int linenumber)
			throws FailedPredicateException {
		if (stb.existsInScope(name)) {
			// Variable already exists in this scope do some checks
			if (stb.getByName(name).getNewType() instanceof FunctionType) {
				// It is already defined as a function
				if (stb.getByName(name).getNewType().same(type)) {
					// HOWEVER if the type is the same, we should allow it
					// in the assumption that a reDEFINITION is caught elsewhere
					// AND this means that we should NOT reassign a value to the
					// function, as this would cause calling bugs
					return;
				}
				throwError("Function was previously declared as "
						+ stb.getByName(name).getNewType()
						+ "\nNew type specified as " + type, linenumber);
			}
			// Already exists in this scope, this is an error
			throwError("Variables cannot be redefined", linenumber);
		}
		// No problems, variable doesn't exist in current scope yet
		stb.declare(name, type, this.returnTypes.size());
	}

	private void forwardDeclare(String name, CType type, int linenumber)
			throws FailedPredicateException {
		if (type instanceof FunctionType) {
			stb.forwardDeclare(name, (FunctionType) type,
					this.returnTypes.size());
		} else {
			throwError("Internal error: forward declaration of a primitive",
					linenumber);
		}
	}

	/**
	 * Checks if the evalution of the node is certain to be true in every case
	 * (so statically)
	 */
	private boolean certainTrue(Node node) {
		if (((node instanceof IntNode) && (((IntNode) node).getValue() != 0))
				|| ((node instanceof FloatNode) && (((FloatNode) node)
						.getValue() != 0))
				|| ((node instanceof CharNode) && (((CharNode) node).getValue() != 0))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if the evalution of the node is certain to be false in every case
	 * (so statically)
	 */
	private boolean certainFalse(Node node) {
		if (((node instanceof IntNode) && (((IntNode) node).getValue() == 0))
				|| ((node instanceof FloatNode) && (((FloatNode) node)
						.getValue() == 0))
				|| ((node instanceof CharNode) && (((CharNode) node).getValue() == 0))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Fetch the value of the node if it can be statically determined, null
	 * otherwise
	 */
	private Comparable getValue(Node node) {
		if (node instanceof IntNode) {
			return ((IntNode) node).getValue();
		} else if (node instanceof FloatNode) {
			return ((FloatNode) node).getValue();
		} else if (node instanceof CharNode) {
			return ((CharNode) node).getValue();
		} else {
			return null;
		}
	}

	/** Evaluate the expression statically */
	private Node staticEvaluation(Comparable ca, Comparable cb,
			OperatorNode.Operator op, int linenumber)
			throws FailedPredicateException {
		if ((ca instanceof Integer) && (cb instanceof Integer)) {
			int a = (Integer) ca;
			int b = (Integer) cb;
			switch (op) {
			case ADD:
				return new IntNode(a + b);
			case SUB:
				return new IntNode(a - b);
			case MULT:
				return new IntNode(a * b);
			case DIV:
				if (b == 0) {
					throwError("Division by zero", linenumber);
				} else {
					return new IntNode(a / b);
				}
			case MOD:
				if (b == 0) {
					throwError("Modulo zero is undefined", linenumber);
				} else if (a < 0 || b < 0) {
					throwError(
							"Modulo of negative number, results are language dependent...",
							linenumber);
				} else {
					// The grammar contains a backslash before the percentage
					// sign as this is an ANTLR keyword...
					return new IntNode(a % b);
				}
			default:
				break;
			}
		} else if ((ca instanceof Float) && (cb instanceof Float)) {
			float a = (Float) ca;
			float b = (Float) cb;
			switch (op) {
			case ADD:
				return new FloatNode(a + b);
			case SUB:
				return new FloatNode(a - b);
			case MULT:
				return new FloatNode(a * b);
			case DIV:
				if (b == 0) {
					throwError("Division by zero", linenumber);
				} else {
					return new FloatNode(a / b);
				}
			case MOD:
				if (b == 0) {
					throwError("Modulo zero is undefined", linenumber);
				} else if (a < 0 || b < 0) {
					throwError(
							"Modulo of negative number, results are language dependent...",
							linenumber);
				} else {
					// The grammar contains a backslash before the percentage
					// sign as this is an ANTLR keyword...
					return new FloatNode(a % b);
				}
			default:
				break;
			}
		}
		return null;
	}

	/** Optimize the node if it is an operator */
	private Node optimizeOperator(Node node, int linenumber)
			throws FailedPredicateException {
		Node child1 = node.getChildren()[0];
		Node child2 = node.getChildren()[1];
		Comparable val1 = getValue(child1);
		Comparable val2 = getValue(child2);
		if (val1 != null && val2 != null && !(child1 instanceof CharNode)
				&& !(child2 instanceof CharNode)) {
			// Will always be possible, so do it right now
			int compval = val1.compareTo(val2);
			// -1: val1 < val2
			// 0: val1 == val2
			// 1: val1 > val2
			switch (((OperatorNode) node).getOperator()) {
			case EQ:
				node = new IntNode((compval == 0) ? 1 : 0);
				break;
			case NEQ:
				node = new IntNode((compval != 0) ? 1 : 0);
				break;
			case LT:
				node = new IntNode((compval < 0) ? 1 : 0);
				break;
			case LEQ:
				node = new IntNode((compval <= 0) ? 1 : 0);
				break;
			case GT:
				node = new IntNode((compval > 0) ? 1 : 0);
				break;
			case GEQ:
				node = new IntNode((compval >= 0) ? 1 : 0);
				break;
			case ADD:
			case SUB:
			case MULT:
			case DIV:
			case MOD:
				node = staticEvaluation(val1, val2,
						((OperatorNode) node).getOperator(), linenumber);
				break;
			default:
				throwError("Internal error: static equality evaluation",
						linenumber);
				break;
			}
		}
		return node;
	}

	// $ANTLR start "smallc_program"
	// src/main/antlr3/compiler/Expr.g:326:1: smallc_program returns [RootNode
	// node] : (e= declaration )+ EOF ;
	public final RootNode smallc_program() throws RecognitionException {
		RootNode node = null;

		ArrayList<Node> e = null;

		// Make the rootnode that will be the actual result. Done here to
		// prevent out-of-scope.
		node = new RootNode();
		// Reset everything
		this.labelID = 0;
		this.whiledepth = 0;

		try {
			// src/main/antlr3/compiler/Expr.g:334:9: ( (e= declaration )+ EOF )
			// src/main/antlr3/compiler/Expr.g:334:11: (e= declaration )+ EOF
			{
				// src/main/antlr3/compiler/Expr.g:334:11: (e= declaration )+
				int cnt1 = 0;
				loop1: do {
					int alt1 = 2;
					int LA1_0 = input.LA(1);

					if ((LA1_0 == CHAR || LA1_0 == CONST || LA1_0 == FLOAT
							|| LA1_0 == INT || LA1_0 == STDIOIMPORT || LA1_0 == VOID)) {
						alt1 = 1;
					}

					switch (alt1) {
					case 1:
					// src/main/antlr3/compiler/Expr.g:334:12: e= declaration
					{
						pushFollow(FOLLOW_declaration_in_smallc_program92);
						e = declaration();

						state._fsp--;
						if (state.failed)
							return node;

						if (state.backtracking == 0) {
							if (e != null) {
								for (int i = 0; i < e.size(); i++) {
									node.appendChild(e.get(i));
								}
							}
						}

					}
						break;

					default:
						if (cnt1 >= 1)
							break loop1;
						if (state.backtracking > 0) {
							state.failed = true;
							return node;
						}
						EarlyExitException eee = new EarlyExitException(1,
								input);
						throw eee;
					}
					cnt1++;
				} while (true);

				match(input, EOF, FOLLOW_EOF_in_smallc_program124);
				if (state.failed)
					return node;

				if (state.backtracking == 0) {
					// Check whether or not a main function is defined
					if (!foundmain) {
						throwError("No void main function defined!", 0);
					} else {
						// Fill in the ID of the main function for later use
						node.setID(stb.getByName("main").getID());
					}

					// Now verify that all used forward declarations are
					// actually defined somewhere
					for (int i = 0; i < mustExist.size(); i++) {
						int id = mustExist.get(i);
						if (!defined.containsKey(id)) {
							throwError("Variable " + stb.getByID(id).getName()
									+ " is declared and used, but not defined",
									0);
						}
					}

					if (this.stdio) {
						node.appendChild(new STDIONode());
					}
				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return node;
	}

	// $ANTLR end "smallc_program"

	// $ANTLR start "declaration"
	// src/main/antlr3/compiler/Expr.g:372:1: declaration returns
	// [ArrayList<Node> nodes] : ( type_specifier (
	// var_decl_list[$type_specifier.type, $type_specifier.pointer] ';' |
	// id_check[true] '(' ( param_decl_list ) ')' ( ( ';' )=>|) (
	// compound_stmt[false] | ';' ) ) | STDIOIMPORT );
	public final ArrayList<Node> declaration() throws RecognitionException {
		ArrayList<Node> nodes = null;

		SmallCParserPass1.type_specifier_return type_specifier1 = null;

		ArrayList<Node> var_decl_list2 = null;

		SmallCParserPass1.param_decl_list_return param_decl_list3 = null;

		SmallCParserPass1.id_check_return id_check4 = null;

		SmallCParserPass1.compound_stmt_return compound_stmt5 = null;

		// Some useful variables
		// The ID of the declaration
		int declID = 0;
		// Whether or not it is a forward declaration
		boolean forward = false;

		try {
			// src/main/antlr3/compiler/Expr.g:380:9: ( type_specifier (
			// var_decl_list[$type_specifier.type, $type_specifier.pointer] ';'
			// | id_check[true] '(' ( param_decl_list ) ')' ( ( ';' )=>|) (
			// compound_stmt[false] | ';' ) ) | STDIOIMPORT )
			int alt5 = 2;
			int LA5_0 = input.LA(1);

			if ((LA5_0 == CHAR || LA5_0 == CONST || LA5_0 == FLOAT
					|| LA5_0 == INT || LA5_0 == VOID)) {
				alt5 = 1;
			} else if ((LA5_0 == STDIOIMPORT)) {
				alt5 = 2;
			} else {
				if (state.backtracking > 0) {
					state.failed = true;
					return nodes;
				}
				NoViableAltException nvae = new NoViableAltException("", 5, 0,
						input);

				throw nvae;

			}
			switch (alt5) {
			case 1:
			// src/main/antlr3/compiler/Expr.g:380:11: type_specifier (
			// var_decl_list[$type_specifier.type, $type_specifier.pointer] ';'
			// | id_check[true] '(' ( param_decl_list ) ')' ( ( ';' )=>|) (
			// compound_stmt[false] | ';' ) )
			{
				pushFollow(FOLLOW_type_specifier_in_declaration215);
				type_specifier1 = type_specifier();

				state._fsp--;
				if (state.failed)
					return nodes;

				if (state.backtracking == 0) {
					// We always return a list of Node's, so just initialize it
					// here
					nodes = new ArrayList<Node>();
				}

				// src/main/antlr3/compiler/Expr.g:386:14: (
				// var_decl_list[$type_specifier.type, $type_specifier.pointer]
				// ';' | id_check[true] '(' ( param_decl_list ) ')' ( ( ';'
				// )=>|) ( compound_stmt[false] | ';' ) )
				int alt4 = 2;
				int LA4_0 = input.LA(1);

				if ((LA4_0 == ID)) {
					int LA4_1 = input.LA(2);

					if ((LA4_1 == 35 || LA4_1 == 39 || LA4_1 == 42 || LA4_1 == 47)) {
						alt4 = 1;
					} else if ((LA4_1 == 32)) {
						alt4 = 2;
					} else {
						if (state.backtracking > 0) {
							state.failed = true;
							return nodes;
						}
						NoViableAltException nvae = new NoViableAltException(
								"", 4, 1, input);

						throw nvae;

					}
				} else {
					if (state.backtracking > 0) {
						state.failed = true;
						return nodes;
					}
					NoViableAltException nvae = new NoViableAltException("", 4,
							0, input);

					throw nvae;

				}
				switch (alt4) {
				case 1:
				// src/main/antlr3/compiler/Expr.g:388:16:
				// var_decl_list[$type_specifier.type, $type_specifier.pointer]
				// ';'
				{
					if (state.backtracking == 0) {
						if ((type_specifier1 != null ? type_specifier1.fulltype
								: null).getPrimary().type == PrimaryType.PType.VOID) {
							throwError(
									"Void variables are not allowed",
									(type_specifier1 != null ? ((Token) type_specifier1.start)
											: null).getLine());
						}
					}

					pushFollow(FOLLOW_var_decl_list_in_declaration324);
					var_decl_list2 = var_decl_list(
							(type_specifier1 != null ? type_specifier1.type
									: null),
							(type_specifier1 != null ? type_specifier1.pointer
									: null));

					state._fsp--;
					if (state.failed)
						return nodes;

					match(input, 39, FOLLOW_39_in_declaration327);
					if (state.failed)
						return nodes;

					if (state.backtracking == 0) {
						// Add each of the specified variables in the list that
						// must be returned
						for (int i = 0; i < var_decl_list2.size(); i++) {
							if (var_decl_list2.get(i) != null) {
								nodes.add(var_decl_list2.get(i));
							}
						}
					}

				}
					break;
				case 2:
				// src/main/antlr3/compiler/Expr.g:403:16: id_check[true] '(' (
				// param_decl_list ) ')' ( ( ';' )=>|) ( compound_stmt[false] |
				// ';' )
				{
					pushFollow(FOLLOW_id_check_in_declaration382);
					id_check4 = id_check(true);

					state._fsp--;
					if (state.failed)
						return nodes;

					match(input, 32, FOLLOW_32_in_declaration423);
					if (state.failed)
						return nodes;

					// src/main/antlr3/compiler/Expr.g:406:19: ( param_decl_list
					// )
					// src/main/antlr3/compiler/Expr.g:406:20: param_decl_list
					{
						pushFollow(FOLLOW_param_decl_list_in_declaration445);
						param_decl_list3 = param_decl_list();

						state._fsp--;
						if (state.failed)
							return nodes;

					}

					match(input, 33, FOLLOW_33_in_declaration467);
					if (state.failed)
						return nodes;

					// src/main/antlr3/compiler/Expr.g:411:19: ( ( ';' )=>|)
					int alt2 = 2;
					int LA2_0 = input.LA(1);

					if ((LA2_0 == 49)) {
						int LA2_1 = input.LA(2);

						if ((synpred1_Expr())) {
							alt2 = 1;
						} else if ((true)) {
							alt2 = 2;
						} else {
							if (state.backtracking > 0) {
								state.failed = true;
								return nodes;
							}
							NoViableAltException nvae = new NoViableAltException(
									"", 2, 1, input);

							throw nvae;

						}
					} else if ((LA2_0 == 39)) {
						int LA2_2 = input.LA(2);

						if ((synpred1_Expr())) {
							alt2 = 1;
						} else if ((true)) {
							alt2 = 2;
						} else {
							if (state.backtracking > 0) {
								state.failed = true;
								return nodes;
							}
							NoViableAltException nvae = new NoViableAltException(
									"", 2, 2, input);

							throw nvae;

						}
					} else {
						if (state.backtracking > 0) {
							state.failed = true;
							return nodes;
						}
						NoViableAltException nvae = new NoViableAltException(
								"", 2, 0, input);

						throw nvae;

					}
					switch (alt2) {
					case 1:
					// src/main/antlr3/compiler/Expr.g:411:20: ( ';' )=>
					{
						if (state.backtracking == 0) {
							forward = true;
						}

					}
						break;
					case 2:
					// src/main/antlr3/compiler/Expr.g:412:21:
					{
						if (state.backtracking == 0) {
							forward = false;
						}

					}
						break;

					}

					if (state.backtracking == 0) {
						// Fetch the type of the function
                        CType[] decl_list = new CType[(param_decl_list3!=null?param_decl_list3.typelist:null).size()];
                        String[] name_list = new String[(param_decl_list3!=null?param_decl_list3.typelist:null).size()];
                        for(int i = 0; i < (param_decl_list3!=null?param_decl_list3.typelist:null).size(); i++){
                           decl_list[i] = (param_decl_list3!=null?param_decl_list3.typelist:null).get(i);
                           name_list[i] = (param_decl_list3!=null?param_decl_list3.declarations:null).get(i);
                        }
						CType type = new FunctionType((type_specifier1 != null ? type_specifier1.fulltype
										: null), decl_list, name_list);
						if (forward) {
							this.forwardDeclare(
									(id_check4 != null ? input.toString(
											id_check4.start, id_check4.stop)
											: null),
									type,
									(id_check4 != null ? ((Token) id_check4.start)
											: null).getLine());
						} else {
							this.declare(
									(id_check4 != null ? input.toString(
											id_check4.start, id_check4.stop)
											: null),
									type,
									(id_check4 != null ? ((Token) id_check4.start)
											: null).getLine());
						}
						declID = stb
								.getByName(
										(id_check4 != null ? input
												.toString(id_check4.start,
														id_check4.stop) : null))
								.getID();
						// Entering the new scope is now possible since the
						// declaration of the function has happened
						stb.newScope();
						// Now add all parameters in the new scope, if it is not
						// a forward declaration
						// Update the return type that we expect (appending to
						// the 'return stack')
						this.returnTypes
								.add((type_specifier1 != null ? type_specifier1.fulltype
										: null));
						if ((param_decl_list3 != null ? param_decl_list3.declarations
								: null) != null
								&& !forward) {
							// Actually, it doesn't really matter whether or not
							// it is a forward declaration
							// though it is an optimisation not to declare these
							// parameters now
							// furthermore, forward declarations might not have
							// all parameters named, so
							// this also prevents compilation errors
							for (int i = 0; i < (param_decl_list3 != null ? param_decl_list3.declarations
									: null).size(); i++) {
								String name = (param_decl_list3 != null ? param_decl_list3.declarations
										: null).get(i);
								CType typel = (param_decl_list3 != null ? param_decl_list3.typelist
										: null).get(i);
								this.declare(
										name,
										typel,
										(param_decl_list3 != null ? ((Token) param_decl_list3.start)
												: null).getLine());
							}
						}

					}

					// src/main/antlr3/compiler/Expr.g:451:19: (
					// compound_stmt[false] | ';' )
					int alt3 = 2;
					int LA3_0 = input.LA(1);

					if ((LA3_0 == 49)) {
						alt3 = 1;
					} else if ((LA3_0 == 39)) {
						alt3 = 2;
					} else {
						if (state.backtracking > 0) {
							state.failed = true;
							return nodes;
						}
						NoViableAltException nvae = new NoViableAltException(
								"", 3, 0, input);

						throw nvae;

					}
					switch (alt3) {
					case 1:
					// src/main/antlr3/compiler/Expr.g:451:20:
					// compound_stmt[false]
					{
						pushFollow(FOLLOW_compound_stmt_in_declaration747);
						compound_stmt5 = compound_stmt(false);

						state._fsp--;
						if (state.failed)
							return nodes;

					}
						break;
					case 2:
					// src/main/antlr3/compiler/Expr.g:451:43: ';'
					{
						match(input, 39, FOLLOW_39_in_declaration752);
						if (state.failed)
							return nodes;

					}
						break;

					}

					if (state.backtracking == 0) {
						if ((id_check4 != null ? input.toString(
								id_check4.start, id_check4.stop) : null)
								.equals("main")
								&& (type_specifier1 != null ? input.toString(
										type_specifier1.start,
										type_specifier1.stop) : null)
										.equals("void")
								&& (compound_stmt5 != null ? compound_stmt5.node
										: null) != null) {
							// It is the void main function and is not a forward
							// declaration
							foundmain = true;
						}
						if ((compound_stmt5 != null ? compound_stmt5.node
								: null) != null) {
							// It is a defining occurence (no forward
							// declaration)
							// Check whether all parameters are named
							if ((param_decl_list3 != null ? param_decl_list3.noname
									: false)) {
								throwError(
										"Parameter not named in function "
												+ (id_check4 != null ? input.toString(
														id_check4.start,
														id_check4.stop)
														: null),
										(param_decl_list3 != null ? ((Token) param_decl_list3.start)
												: null).getLine());
							}
							if (!defined.containsKey(declID)) {
								// Mention that it is defined
								defined.put(declID, true);
							} else {
								// Already defined, but we redefine it...
								// this is NEVER allowed in C
								throwError(
										"Redefinition of already defined function",
										0);
							}
							if ((compound_stmt5 != null ? compound_stmt5.returns
									: false)) {
								// Function returns :)
							} else {
								// Function doesn't return
								// Not necessarily a problem if the type is void
								if (this.returnTypes.get(
										this.returnTypes.size() - 1)
										.getPrimary().type == PrimaryType.PType.VOID) {
									// Not a problem after all
								} else {
									// Sadly, this is a problem
									throwError(
											"Expected a return, but none found",
											(compound_stmt5 != null ? ((Token) compound_stmt5.start)
													: null).getLine());
								}
							}

							// Note that there is no need to put the formal
							// parameters (and their names)
							// in the AST, since we only reference them with
							// their ID and type checking
							// will do all required checking to guarantee that
							// binding is correct
							// This saves us some space in the AST.
						}
						// Scope done
						stb.exitScope();
						if ((compound_stmt5 != null ? compound_stmt5.node
								: null) != null) {
							// Make the actual node that must be returned
							tmpnode = new FunctionDeclNode(stb.getByName(
									(id_check4 != null ? input.toString(
											id_check4.start, id_check4.stop)
											: null)).getID(), stb.getDeepSize());
							// Now append the compound statement
							tmpnode.appendChild((compound_stmt5 != null ? compound_stmt5.node
									: null));
							nodes.add(tmpnode);
						}
						
						// Also pop from the list of returntypes
						// It might seem like nothing has happened between
						// pushing and popping,
						// but when a return call is parset, it will access this
						// list
						this.returnTypes.remove(this.returnTypes.size() - 1);
						
						///////////////////////////////////////////////////////////
						//                                                       //
						//                   Weaving                             //
						//                  =========                            //
						// This is added directly in the code, there is nothing  //
						// present in the grammar file.                          //
						//                                                       //
						//                                                       //
						// TODO MARK FOR EASY ACCESS                             //
						//                                                       //
						///////////////////////////////////////////////////////////
						
						// Create the joinpoint.
						String fName = input.toString(id_check4.start, id_check4.stop);
						TableEntry function = stb.getByName(fName);
						FunctionType fType = (FunctionType) function.getNewType();
						CType[] fArgs = fType.argsType;
						CType retType = fType.returnType;

						// Get all the parameters.
						ArrayList<Argument> argsType = new ArrayList<Argument>();
						for (int i = 0; i < fArgs.length; i++) {
							TableEntry argEntry = stb.getByID(function.getID() + i + 1);
							argsType.add(new Argument(argEntry.getName(), fArgs[i]));
						}

						// We let the weaver know that we encountered this joinpoint!
						MethodPointcutRule funcExec = new MethodPointcutRule(MethodPointcutRule.JoinPoint.EXECUTE, retType, fName, argsType);
						Weaver.addJoinpoint(funcExec);

						// Note: it is not certain that this joinpoint will ever occur!
						MethodPointcutRule funcCall = new MethodPointcutRule(MethodPointcutRule.JoinPoint.CALL, retType, fName, argsType);
						Weaver.addJoinpoint(funcCall);
						
						///////////////////////////////////////////////////////////
						//                                                       //
						//		              End Of Weaving                     //
						//	                  ==============                     //
						//                                                       //
						///////////////////////////////////////////////////////////
					}

				}
					break;

				}

			}
				break;
			case 2:
			// src/main/antlr3/compiler/Expr.g:509:11: STDIOIMPORT
			{
				match(input, STDIOIMPORT, FOLLOW_STDIOIMPORT_in_declaration840);
				if (state.failed)
					return nodes;

				if (state.backtracking == 0) {
					stdio = true;
				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return nodes;
	}

	// $ANTLR end "declaration"

	public static class type_specifier_return extends ParserRuleReturnScope {
		public CType type;
		public CType pointer;
		public CType fulltype;
	};

	// $ANTLR start "type_specifier"
	// src/main/antlr3/compiler/Expr.g:515:1: type_specifier returns [Type type,
	// Type pointer, Type fulltype] : basetype pointer_operator ;
	public final SmallCParserPass1.type_specifier_return type_specifier()
			throws RecognitionException {
		SmallCParserPass1.type_specifier_return retval = new SmallCParserPass1.type_specifier_return();
		retval.start = input.LT(1);

		CType basetype6 = null;

		CType pointer_operator7 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:516:9: ( basetype
			// pointer_operator )
			// src/main/antlr3/compiler/Expr.g:516:11: basetype pointer_operator
			{
				pushFollow(FOLLOW_basetype_in_type_specifier884);
				basetype6 = basetype();

				state._fsp--;
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_pointer_operator_in_type_specifier886);
				pointer_operator7 = pointer_operator();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.type = basetype6;
					retval.pointer = pointer_operator7;
					if (retval.pointer == null) {
						retval.fulltype = retval.type.clone();
					} else {
						CType tmp = pointer_operator7.clone();
						tmp.setBottom(retval.type.clone());
						retval.fulltype = tmp;
					}
				}

			}

			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "type_specifier"

	public static class array_operator_return extends ParserRuleReturnScope {
		public Node node;
		public CType type;
		public List<ASTNode> dimensions;
	};

	// $ANTLR start "array_operator"
	// src/main/antlr3/compiler/Expr.g:534:1: array_operator[Node left, Type
	// lefttype, boolean param] returns [Node node, Type type, List<ASTNode>
	// dimensions] : (a= '[' ( expr )? ']' )+ ;
	public final SmallCParserPass1.array_operator_return array_operator(Node left,
			CType lefttype, boolean param) throws RecognitionException {
		SmallCParserPass1.array_operator_return retval = new SmallCParserPass1.array_operator_return();
		retval.start = input.LT(1);

		Token a = null;
		SmallCParserPass1.expr_return expr8 = null;

		if (left == null) {
			retval.node = null;
		} else {
			retval.node = new ArrayNode();
			retval.node.appendChild(left);
		}
		int dimension = 0;
		retval.dimensions = new ArrayList<ASTNode>();

		try {
			// src/main/antlr3/compiler/Expr.g:546:9: ( (a= '[' ( expr )? ']' )+
			// )
			// src/main/antlr3/compiler/Expr.g:546:11: (a= '[' ( expr )? ']' )+
			{
				// src/main/antlr3/compiler/Expr.g:546:11: (a= '[' ( expr )? ']'
				// )+
				int cnt7 = 0;
				loop7: do {
					int alt7 = 2;
					int LA7_0 = input.LA(1);

					if ((LA7_0 == 47)) {
						alt7 = 1;
					}

					switch (alt7) {
					case 1:
					// src/main/antlr3/compiler/Expr.g:547:11: a= '[' ( expr )?
					// ']'
					{
						a = (Token) match(input, 47,
								FOLLOW_47_in_array_operator964);
						if (state.failed)
							return retval;

						// src/main/antlr3/compiler/Expr.g:547:17: ( expr )?
						int alt6 = 2;
						int LA6_0 = input.LA(1);

						if ((LA6_0 == CHARCONST || LA6_0 == DEREFERENCE
								|| LA6_0 == FLOATCONST || LA6_0 == ID
								|| LA6_0 == INTCONST
								|| (LA6_0 >= POINTER && LA6_0 <= READ)
								|| LA6_0 == STRING || LA6_0 == WRITE
								|| LA6_0 == 29 || LA6_0 == 32 || LA6_0 == 36)) {
							alt6 = 1;
						}
						switch (alt6) {
						case 1:
						// src/main/antlr3/compiler/Expr.g:547:17: expr
						{
							pushFollow(FOLLOW_expr_in_array_operator966);
							expr8 = expr();

							state._fsp--;
							if (state.failed)
								return retval;

						}
							break;

						}

						match(input, 48, FOLLOW_48_in_array_operator969);
						if (state.failed)
							return retval;

						if (state.backtracking == 0) {
							if ((expr8 != null ? expr8.node : null) == null) {
								// No length specified, this is probably a
								// problem
								// UNLESS we are in a parameter declaration
								// (dynamic array)
								// So check whether or not this is the case and
								// possibly throw an error
								if (!param) {
									throwError("No array size given",
											a.getLine());
								}
							}
							if (retval.node != null) {
								retval.node
										.appendChild((expr8 != null ? expr8.node
												: null));
							}
							dimension += 1;
							retval.dimensions
									.add((ASTNode) (expr8 != null ? expr8.node
											: null));
						}

					}
						break;

					default:
						if (cnt7 >= 1)
							break loop7;
						if (state.backtracking > 0) {
							state.failed = true;
							return retval;
						}
						EarlyExitException eee = new EarlyExitException(7,
								input);
						throw eee;
					}
					cnt7++;
				} while (true);

				if (state.backtracking == 0) {
					if (left == null) {
						// A declaration
						retval.type = new ArrayType(lefttype, dimension);
					} else {
						// An access
						if (lefttype instanceof ArrayType) {
							ArrayType vartype = (ArrayType) lefttype;
							int remainingDimensions = vartype.dimensions
									- dimension;
							if (remainingDimensions == 0) {
								retval.type = vartype.getType();
							} else {
								retval.type = new ArrayType(vartype.getType(),
										dimension);
							}
						} else {
							throwError("Array access to a non-array type",
									((Token) retval.start).getLine());
						}
					}
				}

			}

			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "array_operator"

	// $ANTLR start "const_specifier"
	// src/main/antlr3/compiler/Expr.g:589:1: const_specifier returns [boolean
	// isconst] : ( CONST |);
	public final boolean const_specifier() throws RecognitionException {
		boolean isconst = false;

		try {
			// src/main/antlr3/compiler/Expr.g:590:9: ( CONST |)
			int alt8 = 2;
			int LA8_0 = input.LA(1);

			if ((LA8_0 == CONST)) {
				alt8 = 1;
			} else if ((LA8_0 == CHAR || LA8_0 == FLOAT || LA8_0 == INT || LA8_0 == VOID)) {
				alt8 = 2;
			} else {
				if (state.backtracking > 0) {
					state.failed = true;
					return isconst;
				}
				NoViableAltException nvae = new NoViableAltException("", 8, 0,
						input);

				throw nvae;

			}
			switch (alt8) {
			case 1:
			// src/main/antlr3/compiler/Expr.g:590:11: CONST
			{
				match(input, CONST, FOLLOW_CONST_in_const_specifier1040);
				if (state.failed)
					return isconst;

				if (state.backtracking == 0) {
					isconst = true;
				}

			}
				break;
			case 2:
			// src/main/antlr3/compiler/Expr.g:591:11:
			{
				if (state.backtracking == 0) {
					isconst = false;
				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return isconst;
	}

	// $ANTLR end "const_specifier"

	// $ANTLR start "basetype"
	// src/main/antlr3/compiler/Expr.g:597:1: basetype returns [Type type] :
	// const_specifier ( INT | CHAR | FLOAT | VOID ) ;
	public final CType basetype() throws RecognitionException {
		CType type = null;

		boolean const_specifier9 = false;

		try {
			// src/main/antlr3/compiler/Expr.g:598:9: ( const_specifier ( INT |
			// CHAR | FLOAT | VOID ) )
			// src/main/antlr3/compiler/Expr.g:598:11: const_specifier ( INT |
			// CHAR | FLOAT | VOID )
			{
				pushFollow(FOLLOW_const_specifier_in_basetype1086);
				const_specifier9 = const_specifier();

				state._fsp--;
				if (state.failed)
					return type;

				// src/main/antlr3/compiler/Expr.g:599:11: ( INT | CHAR | FLOAT
				// | VOID )
				int alt9 = 4;
				switch (input.LA(1)) {
				case INT: {
					alt9 = 1;
				}
					break;
				case CHAR: {
					alt9 = 2;
				}
					break;
				case FLOAT: {
					alt9 = 3;
				}
					break;
				case VOID: {
					alt9 = 4;
				}
					break;
				default:
					if (state.backtracking > 0) {
						state.failed = true;
						return type;
					}
					NoViableAltException nvae = new NoViableAltException("", 9,
							0, input);

					throw nvae;

				}

				switch (alt9) {
				case 1:
				// src/main/antlr3/compiler/Expr.g:599:13: INT
				{
					match(input, INT, FOLLOW_INT_in_basetype1101);
					if (state.failed)
						return type;

					if (state.backtracking == 0) {
						type = new PrimaryType(PrimaryType.PType.INT,
								const_specifier9);
					}

				}
					break;
				case 2:
				// src/main/antlr3/compiler/Expr.g:600:13: CHAR
				{
					match(input, CHAR, FOLLOW_CHAR_in_basetype1117);
					if (state.failed)
						return type;

					if (state.backtracking == 0) {
						type = new PrimaryType(PrimaryType.PType.CHAR,
								const_specifier9);
					}

				}
					break;
				case 3:
				// src/main/antlr3/compiler/Expr.g:601:13: FLOAT
				{
					match(input, FLOAT, FOLLOW_FLOAT_in_basetype1133);
					if (state.failed)
						return type;

					if (state.backtracking == 0) {
						type = new PrimaryType(PrimaryType.PType.FLOAT,
								const_specifier9);
					}

				}
					break;
				case 4:
				// src/main/antlr3/compiler/Expr.g:602:13: VOID
				{
					match(input, VOID, FOLLOW_VOID_in_basetype1149);
					if (state.failed)
						return type;

					if (state.backtracking == 0) {
						type = new PrimaryType(PrimaryType.PType.VOID,
								const_specifier9);
					}

				}
					break;

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return type;
	}

	// $ANTLR end "basetype"

	// $ANTLR start "pointer_operator"
	// src/main/antlr3/compiler/Expr.g:607:1: pointer_operator returns [Type
	// type] : ( POINTER e= pointer_operator |);
	public final CType pointer_operator() throws RecognitionException {
		CType type = null;

		CType e = null;

		try {
			// src/main/antlr3/compiler/Expr.g:608:9: ( POINTER e=
			// pointer_operator |)
			int alt10 = 2;
			int LA10_0 = input.LA(1);

			if ((LA10_0 == POINTER)) {
				alt10 = 1;
			} else if ((LA10_0 == ID || LA10_0 == 33 || LA10_0 == 35 || LA10_0 == 47)) {
				alt10 = 2;
			} else {
				if (state.backtracking > 0) {
					state.failed = true;
					return type;
				}
				NoViableAltException nvae = new NoViableAltException("", 10, 0,
						input);

				throw nvae;

			}
			switch (alt10) {
			case 1:
			// src/main/antlr3/compiler/Expr.g:608:11: POINTER e=
			// pointer_operator
			{
				match(input, POINTER, FOLLOW_POINTER_in_pointer_operator1201);
				if (state.failed)
					return type;

				pushFollow(FOLLOW_pointer_operator_in_pointer_operator1205);
				e = pointer_operator();

				state._fsp--;
				if (state.failed)
					return type;

				if (state.backtracking == 0) {
					type = new PointerType(e);
				}

			}
				break;
			case 2:
			// src/main/antlr3/compiler/Expr.g:609:11:
			{
				if (state.backtracking == 0) {
					type = null;
				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return type;
	}

	// $ANTLR end "pointer_operator"

	public static class param_decl_list_return extends ParserRuleReturnScope {
		public List<CType> typelist;
		public ArrayList<String> declarations;
		public boolean noname;
	};

	// $ANTLR start "param_decl_list"
	// src/main/antlr3/compiler/Expr.g:615:1: param_decl_list returns
	// [List<Type> typelist, ArrayList<String> declarations, boolean noname] :
	// (a= param_decl ( ',' b= param_decl )* | ( 'void' )=> 'void' |);
	public final SmallCParserPass1.param_decl_list_return param_decl_list()
			throws RecognitionException {
		SmallCParserPass1.param_decl_list_return retval = new SmallCParserPass1.param_decl_list_return();
		retval.start = input.LT(1);

		SmallCParserPass1.param_decl_return a = null;

		SmallCParserPass1.param_decl_return b = null;

		try {
			// src/main/antlr3/compiler/Expr.g:616:9: (a= param_decl ( ',' b=
			// param_decl )* | ( 'void' )=> 'void' |)
			int alt12 = 3;
			switch (input.LA(1)) {
			case CHAR:
			case CONST:
			case FLOAT:
			case INT: {
				alt12 = 1;
			}
				break;
			case VOID: {
				int LA12_2 = input.LA(2);

				if ((true)) {
					alt12 = 1;
				} else if ((synpred2_Expr())) {
					alt12 = 2;
				} else {
					if (state.backtracking > 0) {
						state.failed = true;
						return retval;
					}
					NoViableAltException nvae = new NoViableAltException("",
							12, 2, input);

					throw nvae;

				}
			}
				break;
			case 33: {
				alt12 = 3;
			}
				break;
			default:
				if (state.backtracking > 0) {
					state.failed = true;
					return retval;
				}
				NoViableAltException nvae = new NoViableAltException("", 12, 0,
						input);

				throw nvae;

			}

			switch (alt12) {
			case 1:
			// src/main/antlr3/compiler/Expr.g:616:11: a= param_decl ( ',' b=
			// param_decl )*
			{
				pushFollow(FOLLOW_param_decl_in_param_decl_list1253);
				a = param_decl();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.typelist = new ArrayList<CType>();
					retval.typelist.add((a != null ? a.type : null));
					retval.declarations = new ArrayList<String>();
					retval.declarations.add((a != null ? a.name : null));
					retval.noname = (a != null ? a.noname : false);
				}

				// src/main/antlr3/compiler/Expr.g:624:13: ( ',' b= param_decl
				// )*
				loop11: do {
					int alt11 = 2;
					int LA11_0 = input.LA(1);

					if ((LA11_0 == 35)) {
						alt11 = 1;
					}

					switch (alt11) {
					case 1:
					// src/main/antlr3/compiler/Expr.g:624:14: ',' b= param_decl
					{
						match(input, 35, FOLLOW_35_in_param_decl_list1283);
						if (state.failed)
							return retval;

						pushFollow(FOLLOW_param_decl_in_param_decl_list1287);
						b = param_decl();

						state._fsp--;
						if (state.failed)
							return retval;

						if (state.backtracking == 0) {
							retval.typelist.add((b != null ? b.type : null));
							retval.declarations
									.add((b != null ? b.name : null));
							retval.noname |= (b != null ? b.noname : false);
						}

					}
						break;

					default:
						break loop11;
					}
				} while (true);

				if (state.backtracking == 0) {
					if (retval.noname) {
						// In this case, we are sure that it is not a
						// definition,
						// so there is no need to allocate space for these
						// variables
						retval.declarations = new ArrayList<String>();
					}
				}

			}
				break;
			case 2:
			// src/main/antlr3/compiler/Expr.g:638:11: ( 'void' )=> 'void'
			{
				match(input, VOID, FOLLOW_VOID_in_param_decl_list1346);
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.typelist = new ArrayList<CType>();
					retval.noname = false;
					retval.declarations = new ArrayList<String>();
				}

			}
				break;
			case 3:
			// src/main/antlr3/compiler/Expr.g:644:13:
			{
				if (state.backtracking == 0) {
					retval.typelist = new ArrayList<CType>();
					retval.noname = false;
					retval.declarations = new ArrayList<String>();
				}

			}
				break;

			}
			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "param_decl_list"

	public static class param_decl_return extends ParserRuleReturnScope {
		public CType type;
		public String name;
		public boolean noname;
	};

	// $ANTLR start "param_decl"
	// src/main/antlr3/compiler/Expr.g:651:1: param_decl returns [Type type,
	// String name, boolean noname] : type_specifier ( id_check[true] )? (
	// array_operator[null, $type_specifier.fulltype, true] )? ;
	public final SmallCParserPass1.param_decl_return param_decl()
			throws RecognitionException {
		SmallCParserPass1.param_decl_return retval = new SmallCParserPass1.param_decl_return();
		retval.start = input.LT(1);

		SmallCParserPass1.type_specifier_return type_specifier10 = null;

		SmallCParserPass1.array_operator_return array_operator11 = null;

		SmallCParserPass1.id_check_return id_check12 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:652:9: ( type_specifier (
			// id_check[true] )? ( array_operator[null,
			// $type_specifier.fulltype, true] )? )
			// src/main/antlr3/compiler/Expr.g:652:11: type_specifier (
			// id_check[true] )? ( array_operator[null,
			// $type_specifier.fulltype, true] )?
			{
				pushFollow(FOLLOW_type_specifier_in_param_decl1403);
				type_specifier10 = type_specifier();

				state._fsp--;
				if (state.failed)
					return retval;

				// src/main/antlr3/compiler/Expr.g:652:26: ( id_check[true] )?
				int alt13 = 2;
				int LA13_0 = input.LA(1);

				if ((LA13_0 == ID)) {
					alt13 = 1;
				}
				switch (alt13) {
				case 1:
				// src/main/antlr3/compiler/Expr.g:652:26: id_check[true]
				{
					pushFollow(FOLLOW_id_check_in_param_decl1405);
					id_check12 = id_check(true);

					state._fsp--;
					if (state.failed)
						return retval;

				}
					break;

				}

				// src/main/antlr3/compiler/Expr.g:652:42: (
				// array_operator[null, $type_specifier.fulltype, true] )?
				int alt14 = 2;
				int LA14_0 = input.LA(1);

				if ((LA14_0 == 47)) {
					alt14 = 1;
				}
				switch (alt14) {
				case 1:
				// src/main/antlr3/compiler/Expr.g:652:42: array_operator[null,
				// $type_specifier.fulltype, true]
				{
					pushFollow(FOLLOW_array_operator_in_param_decl1409);
					array_operator11 = array_operator(
							null,
							(type_specifier10 != null ? type_specifier10.fulltype
									: null), true);

					state._fsp--;
					if (state.failed)
						return retval;

				}
					break;

				}

				if (state.backtracking == 0) {
					retval.type = (type_specifier10 != null ? type_specifier10.fulltype
							: null);
					if ((array_operator11 != null ? array_operator11.type
							: null) != null) {
						retval.type = (array_operator11 != null ? array_operator11.type
								: null);
					}

					// Check whether or not a name is specified in this
					// parameter declaration
					// parameter names are not mandatory in forward declarations
					// but they are mandatory in the real definition
					if ((id_check12 != null ? input.toString(id_check12.start,
							id_check12.stop) : null) != null) {
						retval.noname = false;
						retval.name = (id_check12 != null ? input.toString(
								id_check12.start, id_check12.stop) : null);
					} else {
						retval.noname = true;
					}
				}

			}

			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "param_decl"

	public static class compound_stmt_return extends ParserRuleReturnScope {
		public Node node;
		public boolean returns;
		public boolean stops;
	};

	// $ANTLR start "compound_stmt"
	// src/main/antlr3/compiler/Expr.g:673:1: compound_stmt[boolean
	// increaseScope] returns [Node node, boolean returns, boolean stops] : '{'
	// ( declaration )* ( stmt )* '}' ;
	public final SmallCParserPass1.compound_stmt_return compound_stmt(
			boolean increaseScope) throws RecognitionException {
		SmallCParserPass1.compound_stmt_return retval = new SmallCParserPass1.compound_stmt_return();
		retval.start = input.LT(1);

		ArrayList<Node> declaration13 = null;

		SmallCParserPass1.stmt_return stmt14 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:674:9: ( '{' ( declaration )* (
			// stmt )* '}' )
			// src/main/antlr3/compiler/Expr.g:674:11: '{' ( declaration )* (
			// stmt )* '}'
			{
				match(input, 49, FOLLOW_49_in_compound_stmt1458);
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					this.labelID += 1;
					retval.node = new BlockNode(this.labelID);
					if (increaseScope) {
						// System.out.println("NEW SCOPE");
						stb.newScope();
					}
					retval.returns = false;
					retval.stops = false;
				}

				// src/main/antlr3/compiler/Expr.g:686:13: ( declaration )*
				loop15: do {
					int alt15 = 2;
					int LA15_0 = input.LA(1);

					if ((LA15_0 == CHAR || LA15_0 == CONST || LA15_0 == FLOAT
							|| LA15_0 == INT || LA15_0 == STDIOIMPORT || LA15_0 == VOID)) {
						alt15 = 1;
					}

					switch (alt15) {
					case 1:
					// src/main/antlr3/compiler/Expr.g:686:14: declaration
					{
						pushFollow(FOLLOW_declaration_in_compound_stmt1500);
						declaration13 = declaration();

						state._fsp--;
						if (state.failed)
							return retval;

						if (state.backtracking == 0) {
							for (int i = 0; i < declaration13.size(); i++) {
								Node decl = declaration13.get(i);
								if (decl instanceof FunctionDeclNode) {
									retval.node.prependChild(decl);
								} else {
									retval.node.appendChild(decl);
								}
							}
						}

					}
						break;

					default:
						break loop15;
					}
				} while (true);

				// src/main/antlr3/compiler/Expr.g:700:13: ( stmt )*
				loop16: do {
					int alt16 = 2;
					int LA16_0 = input.LA(1);

					if ((LA16_0 == BREAK || LA16_0 == CHARCONST
							|| (LA16_0 >= CONTINUE && LA16_0 <= DEREFERENCE)
							|| (LA16_0 >= FLOATCONST && LA16_0 <= IF)
							|| LA16_0 == INTCONST
							|| (LA16_0 >= POINTER && LA16_0 <= RETURN)
							|| LA16_0 == STRING
							|| (LA16_0 >= WHILE && LA16_0 <= WRITE)
							|| LA16_0 == 29 || LA16_0 == 32 || LA16_0 == 36
							|| LA16_0 == 39 || LA16_0 == 49)) {
						alt16 = 1;
					}

					switch (alt16) {
					case 1:
					// src/main/antlr3/compiler/Expr.g:700:14: stmt
					{
						pushFollow(FOLLOW_stmt_in_compound_stmt1561);
						stmt14 = stmt();

						state._fsp--;
						if (state.failed)
							return retval;

						if (state.backtracking == 0) {
							if (retval.stops || retval.returns) {
								throwError(
										"Dead code starting here",
										(stmt14 != null ? ((Token) stmt14.start)
												: null).getLine());
							}
							retval.node
									.appendChild((stmt14 != null ? stmt14.node
											: null));
							retval.returns |= (stmt14 != null ? stmt14.returns
									: false);
							retval.stops |= (stmt14 != null ? stmt14.stops
									: false);
						}

					}
						break;

					default:
						break loop16;
					}
				} while (true);

				match(input, 51, FOLLOW_51_in_compound_stmt1606);
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					if (increaseScope) {
						// System.out.println("EXIT SCOPE");
						stb.exitScope();
					}
				}

			}

			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "compound_stmt"

	// $ANTLR start "var_decl_list"
	// src/main/antlr3/compiler/Expr.g:719:1: var_decl_list[Type type, Type
	// pointer] returns [ArrayList<Node> nodes] : f= variable_decl[tmptype] (
	// ',' pointer_operator e= variable_decl[tmptype] )* ;
	public final ArrayList<Node> var_decl_list(CType type, CType pointer)
			throws RecognitionException {
		ArrayList<Node> nodes = null;

		Node f = null;

		Node e = null;

		CType pointer_operator15 = null;

		CType tmptype = null;
		if (pointer == null) {
			tmptype = type.clone();
		} else {
			tmptype = pointer.clone();
			tmptype.setBottom(type);
		}

		try {
			// src/main/antlr3/compiler/Expr.g:730:9: (f= variable_decl[tmptype]
			// ( ',' pointer_operator e= variable_decl[tmptype] )* )
			// src/main/antlr3/compiler/Expr.g:730:11: f= variable_decl[tmptype]
			// ( ',' pointer_operator e= variable_decl[tmptype] )*
			{
				pushFollow(FOLLOW_variable_decl_in_var_decl_list1666);
				f = variable_decl(tmptype);

				state._fsp--;
				if (state.failed)
					return nodes;

				if (state.backtracking == 0) {
					nodes = new ArrayList<Node>();
					nodes.add(f);
				}

				// src/main/antlr3/compiler/Expr.g:735:11: ( ','
				// pointer_operator e= variable_decl[tmptype] )*
				loop17: do {
					int alt17 = 2;
					int LA17_0 = input.LA(1);

					if ((LA17_0 == 35)) {
						alt17 = 1;
					}

					switch (alt17) {
					case 1:
					// src/main/antlr3/compiler/Expr.g:735:12: ','
					// pointer_operator e= variable_decl[tmptype]
					{
						match(input, 35, FOLLOW_35_in_var_decl_list1695);
						if (state.failed)
							return nodes;

						pushFollow(FOLLOW_pointer_operator_in_var_decl_list1697);
						pointer_operator15 = pointer_operator();

						state._fsp--;
						if (state.failed)
							return nodes;

						if (state.backtracking == 0) {
							if (pointer_operator15 != null) {
								tmptype = pointer_operator15.clone();
								tmptype.setBottom(type);
							} else {
								tmptype = type.clone();
							}
						}

						pushFollow(FOLLOW_variable_decl_in_var_decl_list1726);
						e = variable_decl(tmptype);

						state._fsp--;
						if (state.failed)
							return nodes;

						if (state.backtracking == 0) {
							nodes.add(e);
						}

					}
						break;

					default:
						break loop17;
					}
				} while (true);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return nodes;
	}

	// $ANTLR end "var_decl_list"

	// $ANTLR start "variable_decl"
	// src/main/antlr3/compiler/Expr.g:755:1: variable_decl[Type type] returns
	// [Node node] : id_check[true] ( '=' expr | array_operator[null, $type,
	// false] )? ;
	public final Node variable_decl(CType type) throws RecognitionException {
		Node node = null;

		SmallCParserPass1.array_operator_return array_operator16 = null;

		SmallCParserPass1.id_check_return id_check17 = null;

		SmallCParserPass1.expr_return expr18 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:756:9: ( id_check[true] ( '='
			// expr | array_operator[null, $type, false] )? )
			// src/main/antlr3/compiler/Expr.g:756:11: id_check[true] ( '=' expr
			// | array_operator[null, $type, false] )?
			{
				pushFollow(FOLLOW_id_check_in_variable_decl1788);
				id_check17 = id_check(true);

				state._fsp--;
				if (state.failed)
					return node;

				// src/main/antlr3/compiler/Expr.g:757:11: ( '=' expr |
				// array_operator[null, $type, false] )?
				int alt18 = 3;
				int LA18_0 = input.LA(1);

				if ((LA18_0 == 42)) {
					alt18 = 1;
				} else if ((LA18_0 == 47)) {
					alt18 = 2;
				}
				switch (alt18) {
				case 1:
				// src/main/antlr3/compiler/Expr.g:757:12: '=' expr
				{
					match(input, 42, FOLLOW_42_in_variable_decl1803);
					if (state.failed)
						return node;

					pushFollow(FOLLOW_expr_in_variable_decl1805);
					expr18 = expr();

					state._fsp--;
					if (state.failed)
						return node;

				}
					break;
				case 2:
				// src/main/antlr3/compiler/Expr.g:757:23: array_operator[null,
				// $type, false]
				{
					pushFollow(FOLLOW_array_operator_in_variable_decl1809);
					array_operator16 = array_operator(null, type, false);

					state._fsp--;
					if (state.failed)
						return node;

				}
					break;

				}

				if (state.backtracking == 0) {
					if ((array_operator16 != null ? array_operator16.type
							: null) != null) {
						type = (array_operator16 != null ? array_operator16.type
								: null);
					}
					
					///////////////////////////////////////////////////////////
					//                                                       //
					//                   Weaving                             //
					//                  =========                            //
					// This is added directly in the code, there is nothing  //
					// present in the grammar file.                          //
					//                                                       //
					//                                                       //
					// TODO MARK FOR EASY ACCESS                             //
					//                                                       //
					///////////////////////////////////////////////////////////
					
					// Global declaration?
					if (this.returnTypes.size() == 0) {
						// Create the joinpoint.
						// Create the joinpoint.
						String vName = input.toString(id_check17.start, id_check17.stop);
						Argument arg = new Argument(vName, type);

						// We let the weaver know that we encountered this joinpoint!
						MemberPointcutRule varSet = new MemberPointcutRule(MemberPointcutRule.JoinPoint.SET, arg);
						Weaver.addJoinpoint(varSet);

						// Note: it is not certain that this joinpoint will ever occur!
						MemberPointcutRule varGet = new MemberPointcutRule(MemberPointcutRule.JoinPoint.GET, arg);
						Weaver.addJoinpoint(varGet);
					}
					
					///////////////////////////////////////////////////////////
					//                                                       //
					//		              End Of Weaving                     //
					//	                  ==============                     //
					//                                                       //
					///////////////////////////////////////////////////////////

					this.declare(
							(id_check17 != null ? input.toString(
									id_check17.start, id_check17.stop) : null),
							type,
							(id_check17 != null ? ((Token) id_check17.start)
									: null).getLine());
					defined.put(
							(stb.getByName((id_check17 != null ? input
									.toString(id_check17.start, id_check17.stop)
									: null)).getID()), true);
					if ((expr18 != null ? expr18.node : null) != null) {
						node = new AssignNode();
						node.appendChild(new VarNode(stb.getByName(
								(id_check17 != null ? input.toString(
										id_check17.start, id_check17.stop)
										: null)).getID(), this.returnTypes
								.size(), true));
						node.appendChild((expr18 != null ? expr18.node : null));
						compatibleTypes(
								stb.getByName(
										(id_check17 != null ? input.toString(
												id_check17.start,
												id_check17.stop) : null))
										.getNewType(),
								(expr18 != null ? expr18.proptype : null),
								(expr18 != null ? ((Token) expr18.start) : null)
										.getLine());
					} else if (type instanceof ArrayType
							&& (array_operator16 != null ? array_operator16.dimensions
									: null) != null) {
						// An array, so we must create a descriptor on the stack
						node = new ArrayDescriptorNode(stb.getByName(
								(id_check17 != null ? input.toString(
										id_check17.start, id_check17.stop)
										: null)).getID());
						for (ASTNode astnode : (array_operator16 != null ? array_operator16.dimensions
								: null)) {
							node.appendChild(astnode);
						}
					} else if (type instanceof PrimaryType) {
						// No initialiser, initialise to 0 ourselves
						node = new AssignNode();
						node.appendChild(new VarNode(stb.getByName(
								(id_check17 != null ? input.toString(
										id_check17.start, id_check17.stop)
										: null)).getID(), this.returnTypes
								.size(), true));
						PType vartype = ((PrimaryType) stb.getByName(
								(id_check17 != null ? input.toString(
										id_check17.start, id_check17.stop)
										: null)).getNewType()).type;
						switch (vartype) {
						case INT:
							node.appendChild(new IntNode(0));
							break;
						case FLOAT:
							node.appendChild(new FloatNode(0));
							break;
						case CHAR:
							node.appendChild(new CharNode((char) 0));
							break;
						case VOID:
						default:
							break;
						}
						// Don't check for compatible types, we will force the
						// initialisation with 0
					}
				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return node;
	}

	// $ANTLR end "variable_decl"

	public static class stmt_return extends ParserRuleReturnScope {
		public Node node;
		public boolean returns;
		public boolean stops;
	};

	// $ANTLR start "stmt"
	// src/main/antlr3/compiler/Expr.g:805:1: stmt returns [Node node, boolean
	// returns, boolean stops] : ( compound_stmt[true] | cond_stmt | while_stmt
	// | for_stmt | expr ';' | BREAK ';' | CONTINUE ';' {...}?| RETURN ( expr )?
	// ';' | ';' );
	public final SmallCParserPass1.stmt_return stmt() throws RecognitionException {
		SmallCParserPass1.stmt_return retval = new SmallCParserPass1.stmt_return();
		retval.start = input.LT(1);

		Token BREAK24 = null;
		Token CONTINUE25 = null;
		Token RETURN27 = null;
		SmallCParserPass1.compound_stmt_return compound_stmt19 = null;

		SmallCParserPass1.cond_stmt_return cond_stmt20 = null;

		SmallCParserPass1.while_stmt_return while_stmt21 = null;

		SmallCParserPass1.for_stmt_return for_stmt22 = null;

		SmallCParserPass1.expr_return expr23 = null;

		SmallCParserPass1.expr_return expr26 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:806:9: ( compound_stmt[true] |
			// cond_stmt | while_stmt | for_stmt | expr ';' | BREAK ';' |
			// CONTINUE ';' {...}?| RETURN ( expr )? ';' | ';' )
			int alt20 = 9;
			switch (input.LA(1)) {
			case 49: {
				alt20 = 1;
			}
				break;
			case IF: {
				alt20 = 2;
			}
				break;
			case WHILE: {
				alt20 = 3;
			}
				break;
			case FOR: {
				alt20 = 4;
			}
				break;
			case CHARCONST:
			case DEREFERENCE:
			case FLOATCONST:
			case ID:
			case INTCONST:
			case POINTER:
			case READ:
			case STRING:
			case WRITE:
			case 29:
			case 32:
			case 36: {
				alt20 = 5;
			}
				break;
			case BREAK: {
				alt20 = 6;
			}
				break;
			case CONTINUE: {
				alt20 = 7;
			}
				break;
			case RETURN: {
				alt20 = 8;
			}
				break;
			case 39: {
				alt20 = 9;
			}
				break;
			default:
				if (state.backtracking > 0) {
					state.failed = true;
					return retval;
				}
				NoViableAltException nvae = new NoViableAltException("", 20, 0,
						input);

				throw nvae;

			}

			switch (alt20) {
			case 1:
			// src/main/antlr3/compiler/Expr.g:806:11: compound_stmt[true]
			{
				pushFollow(FOLLOW_compound_stmt_in_stmt1861);
				compound_stmt19 = compound_stmt(true);

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (compound_stmt19 != null ? compound_stmt19.node
							: null);
					retval.returns = (compound_stmt19 != null ? compound_stmt19.returns
							: false);
					retval.stops = (compound_stmt19 != null ? compound_stmt19.stops
							: false);
				}

			}
				break;
			case 2:
			// src/main/antlr3/compiler/Expr.g:812:11: cond_stmt
			{
				pushFollow(FOLLOW_cond_stmt_in_stmt1890);
				cond_stmt20 = cond_stmt();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (cond_stmt20 != null ? cond_stmt20.node
							: null);
					retval.returns = (cond_stmt20 != null ? cond_stmt20.returns
							: false);
					retval.stops = (cond_stmt20 != null ? cond_stmt20.stops
							: false);
				}

			}
				break;
			case 3:
			// src/main/antlr3/compiler/Expr.g:819:11: while_stmt
			{
				pushFollow(FOLLOW_while_stmt_in_stmt1929);
				while_stmt21 = while_stmt();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (while_stmt21 != null ? while_stmt21.node
							: null);
					retval.returns = (while_stmt21 != null ? while_stmt21.returns
							: false);
					retval.stops = false;
				}

			}
				break;
			case 4:
			// src/main/antlr3/compiler/Expr.g:825:11: for_stmt
			{
				pushFollow(FOLLOW_for_stmt_in_stmt1957);
				for_stmt22 = for_stmt();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (for_stmt22 != null ? for_stmt22.node : null);
					retval.returns = (for_stmt22 != null ? for_stmt22.returns
							: false);
					retval.stops = false;
				}

			}
				break;
			case 5:
			// src/main/antlr3/compiler/Expr.g:831:11: expr ';'
			{
				pushFollow(FOLLOW_expr_in_stmt1985);
				expr23 = expr();

				state._fsp--;
				if (state.failed)
					return retval;

				match(input, 39, FOLLOW_39_in_stmt1987);
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (expr23 != null ? expr23.node : null);
					retval.returns = false;
					retval.stops = false;
				}

			}
				break;
			case 6:
			// src/main/antlr3/compiler/Expr.g:837:11: BREAK ';'
			{
				BREAK24 = (Token) match(input, BREAK, FOLLOW_BREAK_in_stmt2015);
				if (state.failed)
					return retval;

				match(input, 39, FOLLOW_39_in_stmt2017);
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					if (whiledepth <= 0) {
						throwError("Break outside of a loop", BREAK24.getLine());
					}
					retval.node = new BreakNode(this.loopList.get(this.loopList
							.size() - 1));
					retval.returns = false;
					retval.stops = true;
				}

			}
				break;
			case 7:
			// src/main/antlr3/compiler/Expr.g:846:11: CONTINUE ';' {...}?
			{
				CONTINUE25 = (Token) match(input, CONTINUE,
						FOLLOW_CONTINUE_in_stmt2048);
				if (state.failed)
					return retval;

				match(input, 39, FOLLOW_39_in_stmt2050);
				if (state.failed)
					return retval;

				if (!((whiledepth > 0))) {
					if (state.backtracking > 0) {
						state.failed = true;
						return retval;
					}
					throw new FailedPredicateException(input, "stmt",
							"whiledepth > 0");
				}

				if (state.backtracking == 0) {
					if (whiledepth <= 0) {

						throwError("Continue outside of a loop",
								CONTINUE25.getLine());
					}
					retval.node = new ContinueNode(
							this.loopList.get(this.loopList.size() - 1));
					retval.returns = false;
					retval.stops = true;
				}

			}
				break;
			case 8:
			// src/main/antlr3/compiler/Expr.g:857:11: RETURN ( expr )? ';'
			{
				RETURN27 = (Token) match(input, RETURN,
						FOLLOW_RETURN_in_stmt2099);
				if (state.failed)
					return retval;

				// src/main/antlr3/compiler/Expr.g:857:18: ( expr )?
				int alt19 = 2;
				int LA19_0 = input.LA(1);

				if ((LA19_0 == CHARCONST || LA19_0 == DEREFERENCE
						|| LA19_0 == FLOATCONST || LA19_0 == ID
						|| LA19_0 == INTCONST
						|| (LA19_0 >= POINTER && LA19_0 <= READ)
						|| LA19_0 == STRING || LA19_0 == WRITE || LA19_0 == 29
						|| LA19_0 == 32 || LA19_0 == 36)) {
					alt19 = 1;
				}
				switch (alt19) {
				case 1:
				// src/main/antlr3/compiler/Expr.g:857:18: expr
				{
					pushFollow(FOLLOW_expr_in_stmt2101);
					expr26 = expr();

					state._fsp--;
					if (state.failed)
						return retval;

				}
					break;

				}

				match(input, 39, FOLLOW_39_in_stmt2104);
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = new ReturnNode(this.returnTypes.get(
							this.returnTypes.size() - 1).getChar());
					if ((expr26 != null ? expr26.node : null) != null) {
						retval.node.appendChild((expr26 != null ? expr26.node
								: null));
						compatibleTypes(
								(expr26 != null ? expr26.proptype : null),
								this.returnTypes.get(this.returnTypes.size() - 1),
								(expr26 != null ? ((Token) expr26.start) : null)
										.getLine());
					} else {
						compatibleTypes(
								new PrimaryType(PrimaryType.PType.VOID),
								this.returnTypes.get(this.returnTypes.size() - 1),
								RETURN27.getLine());
					}
					retval.returns = true;
					retval.stops = true;
				}

			}
				break;
			case 9:
			// src/main/antlr3/compiler/Expr.g:870:11: ';'
			{
				match(input, 39, FOLLOW_39_in_stmt2134);
				if (state.failed)
					return retval;

			}
				break;

			}
			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "stmt"

	public static class cond_stmt_return extends ParserRuleReturnScope {
		public Node node;
		public boolean returns;
		public boolean stops;
	};

	// $ANTLR start "cond_stmt"
	// src/main/antlr3/compiler/Expr.g:874:1: cond_stmt returns [Node node,
	// boolean returns, boolean stops] : IF '(' expr ')' e= stmt ( ( ELSE )=>
	// ELSE f= stmt )? ;
	public final SmallCParserPass1.cond_stmt_return cond_stmt()
			throws RecognitionException {
		SmallCParserPass1.cond_stmt_return retval = new SmallCParserPass1.cond_stmt_return();
		retval.start = input.LT(1);

		SmallCParserPass1.stmt_return e = null;

		SmallCParserPass1.stmt_return f = null;

		SmallCParserPass1.expr_return expr28 = null;

		int ifID = 0;

		try {
			// src/main/antlr3/compiler/Expr.g:878:9: ( IF '(' expr ')' e= stmt
			// ( ( ELSE )=> ELSE f= stmt )? )
			// src/main/antlr3/compiler/Expr.g:878:11: IF '(' expr ')' e= stmt (
			// ( ELSE )=> ELSE f= stmt )?
			{
				match(input, IF, FOLLOW_IF_in_cond_stmt2176);
				if (state.failed)
					return retval;

				match(input, 32, FOLLOW_32_in_cond_stmt2178);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_expr_in_cond_stmt2180);
				expr28 = expr();

				state._fsp--;
				if (state.failed)
					return retval;

				match(input, 33, FOLLOW_33_in_cond_stmt2182);
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					this.labelID += 1;
					ifID = this.labelID;
				}

				pushFollow(FOLLOW_stmt_in_cond_stmt2209);
				e = stmt();

				state._fsp--;
				if (state.failed)
					return retval;

				// src/main/antlr3/compiler/Expr.g:884:11: ( ( ELSE )=> ELSE f=
				// stmt )?
				int alt21 = 2;
				int LA21_0 = input.LA(1);

				if ((LA21_0 == ELSE)) {
					int LA21_1 = input.LA(2);

					if ((synpred3_Expr())) {
						alt21 = 1;
					}
				}
				switch (alt21) {
				case 1:
				// src/main/antlr3/compiler/Expr.g:884:12: ( ELSE )=> ELSE f=
				// stmt
				{
					match(input, ELSE, FOLLOW_ELSE_in_cond_stmt2228);
					if (state.failed)
						return retval;

					pushFollow(FOLLOW_stmt_in_cond_stmt2232);
					f = stmt();

					state._fsp--;
					if (state.failed)
						return retval;

				}
					break;

				}

				if (state.backtracking == 0) {
					if (certainTrue((expr28 != null ? expr28.node : null))) {
						retval.node = (e != null ? e.node : null);
						retval.returns = (e != null ? e.returns : false);
						retval.stops = (e != null ? e.stops : false);
					} else if (certainFalse((expr28 != null ? expr28.node
							: null))) {
						if ((f != null ? f.node : null) != null) {
							retval.node = (f != null ? f.node : null);
							retval.returns = (f != null ? f.returns : false);
							retval.stops = (f != null ? f.stops : false);
						} else {
							// the if is always false and there isn't even an
							// else
							// this code has no use...
							this.labelID += 1;
							retval.node = new BlockNode(this.labelID);
							retval.returns = false;
							retval.stops = false;
						}
					} else {
						retval.node = new IfNode(ifID);
						retval.node.appendChild((expr28 != null ? expr28.node
								: null));
						retval.node.appendChild((e != null ? e.node : null));
						retval.returns = (e != null ? e.returns : false);
						retval.stops = (e != null ? e.stops : false);
						if ((f != null ? f.node : null) != null) {
							retval.node
									.appendChild((f != null ? f.node : null));
							retval.returns &= (f != null ? f.returns : false);
							retval.stops &= (f != null ? f.stops : false);
						} else {
							// No else node and not certain about anything
							// So it might be that we miss the return/stop in
							// the imaginary else part
							retval.returns = false;
							retval.stops = false;
						}
					}
				}

			}

			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "cond_stmt"

	public static class while_stmt_return extends ParserRuleReturnScope {
		public Node node;
		public boolean returns;
	};

	// $ANTLR start "while_stmt"
	// src/main/antlr3/compiler/Expr.g:926:1: while_stmt returns [Node node,
	// boolean returns] : WHILE '(' expr ')' stmt ;
	public final SmallCParserPass1.while_stmt_return while_stmt()
			throws RecognitionException {
		SmallCParserPass1.while_stmt_return retval = new SmallCParserPass1.while_stmt_return();
		retval.start = input.LT(1);

		SmallCParserPass1.expr_return expr29 = null;

		SmallCParserPass1.stmt_return stmt30 = null;

		int whileID = 0;

		try {
			// src/main/antlr3/compiler/Expr.g:930:9: ( WHILE '(' expr ')' stmt
			// )
			// src/main/antlr3/compiler/Expr.g:930:11: WHILE '(' expr ')' stmt
			{
				match(input, WHILE, FOLLOW_WHILE_in_while_stmt2293);
				if (state.failed)
					return retval;

				match(input, 32, FOLLOW_32_in_while_stmt2295);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_expr_in_while_stmt2297);
				expr29 = expr();

				state._fsp--;
				if (state.failed)
					return retval;

				match(input, 33, FOLLOW_33_in_while_stmt2299);
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					whiledepth += 1;
				}

				if (state.backtracking == 0) {
					this.labelID += 1;
					whileID = this.labelID;
					this.loopList.add(whileID);
				}

				pushFollow(FOLLOW_stmt_in_while_stmt2349);
				stmt30 = stmt();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					whiledepth -= 1;
				}

				if (state.backtracking == 0) {
					if (certainFalse((expr29 != null ? expr29.node : null))) {
						this.labelID += 1;
						retval.node = new BlockNode(this.labelID);
						retval.returns = false;
					}
					// No (very) fast path for a certainTrue node, and it rarely
					// happens...
					else {
						retval.node = new WhileNode(whileID);
						retval.node.appendChild((expr29 != null ? expr29.node
								: null));
						retval.node.appendChild((stmt30 != null ? stmt30.node
								: null));
						// It most likely is possible to skip the loop
						// completely
						if (this.certainTrue((expr29 != null ? expr29.node
								: null))) {
							// We know for sure that the loop will run at least
							// once
							retval.returns = (stmt30 != null ? stmt30.returns
									: false);
						} else {
							// We are uncertain about whether the loop will run
							// or not
							retval.returns = false;
						}
					}
					this.loopList.remove(this.loopList.size() - 1);
				}

			}

			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "while_stmt"

	public static class for_stmt_return extends ParserRuleReturnScope {
		public Node node;
		public boolean returns;
	};

	// $ANTLR start "for_stmt"
	// src/main/antlr3/compiler/Expr.g:964:1: for_stmt returns [Node node,
	// boolean returns] : FOR '(' init= for_op ';' cond= for_op ';' action=
	// for_op ')' stmt ;
	public final SmallCParserPass1.for_stmt_return for_stmt()
			throws RecognitionException {
		SmallCParserPass1.for_stmt_return retval = new SmallCParserPass1.for_stmt_return();
		retval.start = input.LT(1);

		Node init = null;

		Node cond = null;

		Node action = null;

		SmallCParserPass1.stmt_return stmt31 = null;

		int forID = 0;

		try {
			// src/main/antlr3/compiler/Expr.g:968:9: ( FOR '(' init= for_op ';'
			// cond= for_op ';' action= for_op ')' stmt )
			// src/main/antlr3/compiler/Expr.g:968:11: FOR '(' init= for_op ';'
			// cond= for_op ';' action= for_op ')' stmt
			{
				match(input, FOR, FOLLOW_FOR_in_for_stmt2431);
				if (state.failed)
					return retval;

				match(input, 32, FOLLOW_32_in_for_stmt2433);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_for_op_in_for_stmt2437);
				init = for_op();

				state._fsp--;
				if (state.failed)
					return retval;

				match(input, 39, FOLLOW_39_in_for_stmt2439);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_for_op_in_for_stmt2443);
				cond = for_op();

				state._fsp--;
				if (state.failed)
					return retval;

				match(input, 39, FOLLOW_39_in_for_stmt2445);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_for_op_in_for_stmt2449);
				action = for_op();

				state._fsp--;
				if (state.failed)
					return retval;

				match(input, 33, FOLLOW_33_in_for_stmt2451);
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					whiledepth += 1;
				}

				if (state.backtracking == 0) {
					this.labelID += 1;
					forID = this.labelID;
					this.loopList.add(forID);
				}

				pushFollow(FOLLOW_stmt_in_for_stmt2499);
				stmt31 = stmt();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					whiledepth -= 1;
				}

				if (state.backtracking == 0) {
					if (certainFalse(cond)) {
						// The init might have some side effects, so evaluate it
						// for the sake of correctness
						retval.node = init;
						retval.returns = false;
					} else {
						retval.node = new ForNode(forID);
						retval.node.appendChild(init);
						retval.node.appendChild(cond);
						retval.node.appendChild(action);
						retval.node.appendChild((stmt31 != null ? stmt31.node
								: null));
						if (this.certainTrue(cond)) {
							// We know for sure that the loop will run at least
							// once
							retval.returns = (stmt31 != null ? stmt31.returns
									: false);
						} else {
							// We are uncertain about whether the loop will run
							// or not
							retval.returns = false;
						}
					}
					this.loopList.remove(this.loopList.size() - 1);
				}

			}

			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "for_stmt"

	// $ANTLR start "for_op"
	// src/main/antlr3/compiler/Expr.g:1004:1: for_op returns [Node node] : (
	// expr |);
	public final Node for_op() throws RecognitionException {
		Node node = null;

		SmallCParserPass1.expr_return expr32 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1005:9: ( expr |)
			int alt22 = 2;
			int LA22_0 = input.LA(1);

			if ((LA22_0 == CHARCONST || LA22_0 == DEREFERENCE
					|| LA22_0 == FLOATCONST || LA22_0 == ID
					|| LA22_0 == INTCONST
					|| (LA22_0 >= POINTER && LA22_0 <= READ)
					|| LA22_0 == STRING || LA22_0 == WRITE || LA22_0 == 29
					|| LA22_0 == 32 || LA22_0 == 36)) {
				alt22 = 1;
			} else if ((LA22_0 == 33 || LA22_0 == 39)) {
				alt22 = 2;
			} else {
				if (state.backtracking > 0) {
					state.failed = true;
					return node;
				}
				NoViableAltException nvae = new NoViableAltException("", 22, 0,
						input);

				throw nvae;

			}
			switch (alt22) {
			case 1:
			// src/main/antlr3/compiler/Expr.g:1005:11: expr
			{
				pushFollow(FOLLOW_expr_in_for_op2562);
				expr32 = expr();

				state._fsp--;
				if (state.failed)
					return node;

				if (state.backtracking == 0) {
					node = (expr32 != null ? expr32.node : null);
				}

			}
				break;
			case 2:
			// src/main/antlr3/compiler/Expr.g:1010:11:
			{
				if (state.backtracking == 0) {
					// An IntNode, since this could happen as the second
					// argument
					// of a while, where this means to keep going. Int 1 is the
					// same
					// as True in C.
					node = new IntNode(1);
				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return node;
	}

	// $ANTLR end "for_op"

	public static class arg_list_return extends ParserRuleReturnScope {
		public Node node;
		public List<CType> proptype;
	};

	// $ANTLR start "arg_list"
	// src/main/antlr3/compiler/Expr.g:1018:1: arg_list returns [Node node,
	// List<Type> proptype] : (a= expr ( ',' b= expr )* |);
	public final SmallCParserPass1.arg_list_return arg_list()
			throws RecognitionException {
		SmallCParserPass1.arg_list_return retval = new SmallCParserPass1.arg_list_return();
		retval.start = input.LT(1);

		SmallCParserPass1.expr_return a = null;

		SmallCParserPass1.expr_return b = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1019:9: (a= expr ( ',' b= expr )*
			// |)
			int alt24 = 2;
			int LA24_0 = input.LA(1);

			if ((LA24_0 == CHARCONST || LA24_0 == DEREFERENCE
					|| LA24_0 == FLOATCONST || LA24_0 == ID
					|| LA24_0 == INTCONST
					|| (LA24_0 >= POINTER && LA24_0 <= READ)
					|| LA24_0 == STRING || LA24_0 == WRITE || LA24_0 == 29
					|| LA24_0 == 32 || LA24_0 == 36)) {
				alt24 = 1;
			} else if ((LA24_0 == 33)) {
				alt24 = 2;
			} else {
				if (state.backtracking > 0) {
					state.failed = true;
					return retval;
				}
				NoViableAltException nvae = new NoViableAltException("", 24, 0,
						input);

				throw nvae;

			}
			switch (alt24) {
			case 1:
			// src/main/antlr3/compiler/Expr.g:1019:11: a= expr ( ',' b= expr )*
			{
				pushFollow(FOLLOW_expr_in_arg_list2628);
				a = expr();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = new ArgsNode();
					retval.node.appendChild((a != null ? a.node : null));
					retval.proptype = new ArrayList<CType>();
					retval.proptype.add((a != null ? a.proptype : null));
				}

				// src/main/antlr3/compiler/Expr.g:1026:13: ( ',' b= expr )*
				loop23: do {
					int alt23 = 2;
					int LA23_0 = input.LA(1);

					if ((LA23_0 == 35)) {
						alt23 = 1;
					}

					switch (alt23) {
					case 1:
					// src/main/antlr3/compiler/Expr.g:1026:14: ',' b= expr
					{
						match(input, 35, FOLLOW_35_in_arg_list2658);
						if (state.failed)
							return retval;

						pushFollow(FOLLOW_expr_in_arg_list2662);
						b = expr();

						state._fsp--;
						if (state.failed)
							return retval;

						if (state.backtracking == 0) {
							retval.node
									.appendChild((b != null ? b.node : null));
							retval.proptype
									.add((b != null ? b.proptype : null));
						}

					}
						break;

					default:
						break loop23;
					}
				} while (true);

			}
				break;
			case 2:
			// src/main/antlr3/compiler/Expr.g:1033:13:
			{
				if (state.backtracking == 0) {
					retval.node = new ArgsNode();
					retval.proptype = new ArrayList<CType>();
				}

			}
				break;

			}
			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "arg_list"

	public static class expr_return extends ParserRuleReturnScope {
		public Node node;
		public CType proptype;
	};

	// $ANTLR start "expr"
	// src/main/antlr3/compiler/Expr.g:1041:1: expr returns [Node node, Type
	// proptype] : ( ( pointer_id '=' )=> pointer_id '=' e= expr | condition |
	// STRING );
	public final SmallCParserPass1.expr_return expr() throws RecognitionException {
		SmallCParserPass1.expr_return retval = new SmallCParserPass1.expr_return();
		retval.start = input.LT(1);

		Token STRING35 = null;
		SmallCParserPass1.expr_return e = null;

		SmallCParserPass1.pointer_id_return pointer_id33 = null;

		SmallCParserPass1.condition_return condition34 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1042:9: ( ( pointer_id '=' )=>
			// pointer_id '=' e= expr | condition | STRING )
			int alt25 = 3;
			switch (input.LA(1)) {
			case POINTER: {
				int LA25_1 = input.LA(2);

				if ((synpred4_Expr())) {
					alt25 = 1;
				} else if ((true)) {
					alt25 = 2;
				} else {
					if (state.backtracking > 0) {
						state.failed = true;
						return retval;
					}
					NoViableAltException nvae = new NoViableAltException("",
							25, 1, input);

					throw nvae;

				}
			}
				break;
			case DEREFERENCE: {
				int LA25_2 = input.LA(2);

				if ((synpred4_Expr())) {
					alt25 = 1;
				} else if ((true)) {
					alt25 = 2;
				} else {
					if (state.backtracking > 0) {
						state.failed = true;
						return retval;
					}
					NoViableAltException nvae = new NoViableAltException("",
							25, 2, input);

					throw nvae;

				}
			}
				break;
			case READ: {
				int LA25_3 = input.LA(2);

				if ((synpred4_Expr())) {
					alt25 = 1;
				} else if ((true)) {
					alt25 = 2;
				} else {
					if (state.backtracking > 0) {
						state.failed = true;
						return retval;
					}
					NoViableAltException nvae = new NoViableAltException("",
							25, 3, input);

					throw nvae;

				}
			}
				break;
			case WRITE: {
				int LA25_4 = input.LA(2);

				if ((synpred4_Expr())) {
					alt25 = 1;
				} else if ((true)) {
					alt25 = 2;
				} else {
					if (state.backtracking > 0) {
						state.failed = true;
						return retval;
					}
					NoViableAltException nvae = new NoViableAltException("",
							25, 4, input);

					throw nvae;

				}
			}
				break;
			case ID: {
				int LA25_5 = input.LA(2);

				if ((synpred4_Expr())) {
					alt25 = 1;
				} else if ((true)) {
					alt25 = 2;
				} else {
					if (state.backtracking > 0) {
						state.failed = true;
						return retval;
					}
					NoViableAltException nvae = new NoViableAltException("",
							25, 5, input);

					throw nvae;

				}
			}
				break;
			case CHARCONST:
			case FLOATCONST:
			case INTCONST:
			case 29:
			case 32:
			case 36: {
				alt25 = 2;
			}
				break;
			case STRING: {
				alt25 = 3;
			}
				break;
			default:
				if (state.backtracking > 0) {
					state.failed = true;
					return retval;
				}
				NoViableAltException nvae = new NoViableAltException("", 25, 0,
						input);

				throw nvae;

			}

			switch (alt25) {
			case 1:
			// src/main/antlr3/compiler/Expr.g:1042:11: ( pointer_id '=' )=>
			// pointer_id '=' e= expr
			{
				pushFollow(FOLLOW_pointer_id_in_expr2772);
				pointer_id33 = pointer_id();

				state._fsp--;
				if (state.failed)
					return retval;

				match(input, 42, FOLLOW_42_in_expr2774);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_expr_in_expr2778);
				e = expr();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = new AssignNode();
					CType vartype = (pointer_id33 != null ? pointer_id33.proptype
							: null);
					CType idtype = stb.getByName(
							(pointer_id33 != null ? pointer_id33.name : null))
							.getNewType();
					if (idtype.getPrimary().constant) {
						throwError("Const variable "
								+ (pointer_id33 != null ? pointer_id33.name
										: null) + " is read-only",
								(pointer_id33 != null ? pointer_id33.linenumber
										: 0));
					}
					retval.node
							.appendChild((pointer_id33 != null ? pointer_id33.node
									: null));
					retval.node.appendChild((e != null ? e.node : null));
					retval.proptype = compatibleTypes(vartype,
							(e != null ? e.proptype : null),
							(e != null ? ((Token) e.start) : null).getLine());
				}

			}
				break;
			case 2:
			// src/main/antlr3/compiler/Expr.g:1055:11: condition
			{
				pushFollow(FOLLOW_condition_in_expr2806);
				condition34 = condition();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (condition34 != null ? condition34.node
							: null);
					retval.proptype = (condition34 != null ? condition34.proptype
							: null);
				}

			}
				break;
			case 3:
			// src/main/antlr3/compiler/Expr.g:1060:11: STRING
			{
				STRING35 = (Token) match(input, STRING,
						FOLLOW_STRING_in_expr2834);
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					this.labelID += 1;
					CType type = new ArrayType(new PrimaryType(PType.CHAR), 1);
					String varname = "0_STR_" + this.labelID;
					this.declare(varname, type, STRING35.getLine());
					int id = stb.getByName(varname).getID();
					// StringNode does not require the nesting depth, as it will
					// be constructed locally
					retval.node = new StringNode(
							(STRING35 != null ? STRING35.getText() : null));
					// Add the node to actually allocate the memory on the
					// stack/heap
					retval.node.appendChild(new ArrayDescriptorNode(id));
					// Second argument is the actual variable that must be
					// returned
					// TODO: mark
					retval.node.appendChild(new VarNode(id, this.returnTypes
							.size(), false));
					// Add 1 for the \0 character at the end, but subtract 2 for
					// the " at both sides
					retval.node.getChildren()[0].appendChild(new IntNode(
							(STRING35 != null ? STRING35.getText() : null)
									.length() - 1));
					retval.proptype = new ArrayType(new PrimaryType(PType.CHAR,
							true), 1);
				}

			}
				break;

			}
			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "expr"

	public static class condition_return extends ParserRuleReturnScope {
		public Node node;
		public CType proptype;
	};

	// $ANTLR start "condition"
	// src/main/antlr3/compiler/Expr.g:1081:1: condition returns [Node node,
	// Type proptype] : disjunction ;
	public final SmallCParserPass1.condition_return condition()
			throws RecognitionException {
		SmallCParserPass1.condition_return retval = new SmallCParserPass1.condition_return();
		retval.start = input.LT(1);

		SmallCParserPass1.disjunction_return disjunction36 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1082:9: ( disjunction )
			// src/main/antlr3/compiler/Expr.g:1082:11: disjunction
			{
				pushFollow(FOLLOW_disjunction_in_condition2882);
				disjunction36 = disjunction();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (disjunction36 != null ? disjunction36.node
							: null);
					retval.proptype = (disjunction36 != null ? disjunction36.proptype
							: null);
				}

			}

			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "condition"

	public static class x_con_dis_return extends ParserRuleReturnScope {
		public Node node;
		public CType proptype;
	};

	// $ANTLR start "x_con_dis"
	// src/main/antlr3/compiler/Expr.g:1089:1: x_con_dis returns [Node node,
	// Type proptype] : '?' a= expr ':' b= condition ;
	public final SmallCParserPass1.x_con_dis_return x_con_dis()
			throws RecognitionException {
		SmallCParserPass1.x_con_dis_return retval = new SmallCParserPass1.x_con_dis_return();
		retval.start = input.LT(1);

		SmallCParserPass1.expr_return a = null;

		SmallCParserPass1.condition_return b = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1090:9: ( '?' a= expr ':' b=
			// condition )
			// src/main/antlr3/compiler/Expr.g:1090:11: '?' a= expr ':' b=
			// condition
			{
				match(input, 46, FOLLOW_46_in_x_con_dis2937);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_expr_in_x_con_dis2941);
				a = expr();

				state._fsp--;
				if (state.failed)
					return retval;

				match(input, 38, FOLLOW_38_in_x_con_dis2943);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_condition_in_x_con_dis2947);
				b = condition();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					this.labelID += 1;
					retval.node = new OperatorNode(Operator.TIF, this.labelID);
					retval.node.appendChild((a != null ? a.node : null));
					retval.node.appendChild((b != null ? b.node : null));
					retval.proptype = compatibleTypes((a != null ? a.proptype
							: null), (b != null ? b.proptype : null),
							(a != null ? ((Token) a.start) : null).getLine());
				}

			}

			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "x_con_dis"

	public static class disjunction_return extends ParserRuleReturnScope {
		public Node node;
		public CType proptype;
	};

	// $ANTLR start "disjunction"
	// src/main/antlr3/compiler/Expr.g:1101:1: disjunction returns [Node node,
	// Type proptype] : conjunction y_disj[$conjunction.node] ;
	public final SmallCParserPass1.disjunction_return disjunction()
			throws RecognitionException {
		SmallCParserPass1.disjunction_return retval = new SmallCParserPass1.disjunction_return();
		retval.start = input.LT(1);

		SmallCParserPass1.conjunction_return conjunction37 = null;

		SmallCParserPass1.y_disj_return y_disj38 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1102:9: ( conjunction
			// y_disj[$conjunction.node] )
			// src/main/antlr3/compiler/Expr.g:1102:11: conjunction
			// y_disj[$conjunction.node]
			{
				pushFollow(FOLLOW_conjunction_in_disjunction2995);
				conjunction37 = conjunction();

				state._fsp--;
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_y_disj_in_disjunction2997);
				y_disj38 = y_disj((conjunction37 != null ? conjunction37.node
						: null));

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (y_disj38 != null ? y_disj38.node : null);
					if ((y_disj38 != null ? y_disj38.proptype : null) == null) {
						retval.proptype = (conjunction37 != null ? conjunction37.proptype
								: null);
					} else {
						retval.proptype = (y_disj38 != null ? y_disj38.proptype
								: null);
					}
				}

			}

			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "disjunction"

	public static class y_disj_return extends ParserRuleReturnScope {
		public Node node;
		public CType proptype;
	};

	// $ANTLR start "y_disj"
	// src/main/antlr3/compiler/Expr.g:1114:1: y_disj[Node left] returns [Node
	// node, Type proptype] : ( ( '?' )=> x_con_dis e= y_disj[$node] |);
	public final SmallCParserPass1.y_disj_return y_disj(Node left)
			throws RecognitionException {
		SmallCParserPass1.y_disj_return retval = new SmallCParserPass1.y_disj_return();
		retval.start = input.LT(1);

		SmallCParserPass1.y_disj_return e = null;

		SmallCParserPass1.x_con_dis_return x_con_dis39 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1115:9: ( ( '?' )=> x_con_dis e=
			// y_disj[$node] |)
			int alt26 = 2;
			int LA26_0 = input.LA(1);

			if ((LA26_0 == 46)) {
				int LA26_1 = input.LA(2);

				if ((synpred5_Expr())) {
					alt26 = 1;
				} else if ((true)) {
					alt26 = 2;
				} else {
					if (state.backtracking > 0) {
						state.failed = true;
						return retval;
					}
					NoViableAltException nvae = new NoViableAltException("",
							26, 1, input);

					throw nvae;

				}
			} else if ((LA26_0 == 33 || LA26_0 == 35
					|| (LA26_0 >= 38 && LA26_0 <= 39) || LA26_0 == 48)) {
				alt26 = 2;
			} else {
				if (state.backtracking > 0) {
					state.failed = true;
					return retval;
				}
				NoViableAltException nvae = new NoViableAltException("", 26, 0,
						input);

				throw nvae;

			}
			switch (alt26) {
			case 1:
			// src/main/antlr3/compiler/Expr.g:1115:11: ( '?' )=> x_con_dis e=
			// y_disj[$node]
			{
				pushFollow(FOLLOW_x_con_dis_in_y_disj3060);
				x_con_dis39 = x_con_dis();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (x_con_dis39 != null ? x_con_dis39.node
							: null);
					retval.node.prependChild(left);
				}

				pushFollow(FOLLOW_y_disj_in_y_disj3096);
				e = y_disj(retval.node);

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.proptype = compatibleTypes(
							(x_con_dis39 != null ? x_con_dis39.proptype : null),
							(e != null ? e.proptype : null),
							(x_con_dis39 != null ? ((Token) x_con_dis39.start)
									: null).getLine());
				}

			}
				break;
			case 2:
			// src/main/antlr3/compiler/Expr.g:1124:11:
			{
				if (state.backtracking == 0) {
					retval.node = left;
					retval.proptype = null;
				}

			}
				break;

			}
			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "y_disj"

	public static class conjunction_return extends ParserRuleReturnScope {
		public Node node;
		public CType proptype;
	};

	// $ANTLR start "conjunction"
	// src/main/antlr3/compiler/Expr.g:1128:1: conjunction returns [Node node,
	// Type proptype] : comparison y_conj[$comparison.node] ;
	public final SmallCParserPass1.conjunction_return conjunction()
			throws RecognitionException {
		SmallCParserPass1.conjunction_return retval = new SmallCParserPass1.conjunction_return();
		retval.start = input.LT(1);

		SmallCParserPass1.comparison_return comparison40 = null;

		SmallCParserPass1.y_conj_return y_conj41 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1129:9: ( comparison
			// y_conj[$comparison.node] )
			// src/main/antlr3/compiler/Expr.g:1129:11: comparison
			// y_conj[$comparison.node]
			{
				pushFollow(FOLLOW_comparison_in_conjunction3156);
				comparison40 = comparison();

				state._fsp--;
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_y_conj_in_conjunction3158);
				y_conj41 = y_conj((comparison40 != null ? comparison40.node
						: null));

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (y_conj41 != null ? y_conj41.node : null);
					retval.proptype = compatibleTypes(
							(comparison40 != null ? comparison40.proptype
									: null),
							(y_conj41 != null ? y_conj41.proptype : null),
							(comparison40 != null ? ((Token) comparison40.start)
									: null).getLine());
				}

			}

			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "conjunction"

	public static class y_conj_return extends ParserRuleReturnScope {
		public Node node;
		public CType proptype;
	};

	// $ANTLR start "y_conj"
	// src/main/antlr3/compiler/Expr.g:1136:1: y_conj[Node left] returns [Node
	// node, Type proptype] : ( '&&' comparison e= y_conj[$node] | '||'
	// comparison e= y_conj[$node] |);
	public final SmallCParserPass1.y_conj_return y_conj(Node left)
			throws RecognitionException {
		SmallCParserPass1.y_conj_return retval = new SmallCParserPass1.y_conj_return();
		retval.start = input.LT(1);

		SmallCParserPass1.y_conj_return e = null;

		SmallCParserPass1.comparison_return comparison42 = null;

		SmallCParserPass1.comparison_return comparison43 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1137:9: ( '&&' comparison e=
			// y_conj[$node] | '||' comparison e= y_conj[$node] |)
			int alt27 = 3;
			switch (input.LA(1)) {
			case 31: {
				alt27 = 1;
			}
				break;
			case 50: {
				alt27 = 2;
			}
				break;
			case 33:
			case 35:
			case 38:
			case 39:
			case 46:
			case 48: {
				alt27 = 3;
			}
				break;
			default:
				if (state.backtracking > 0) {
					state.failed = true;
					return retval;
				}
				NoViableAltException nvae = new NoViableAltException("", 27, 0,
						input);

				throw nvae;

			}

			switch (alt27) {
			case 1:
			// src/main/antlr3/compiler/Expr.g:1137:11: '&&' comparison e=
			// y_conj[$node]
			{
				match(input, 31, FOLLOW_31_in_y_conj3220);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_comparison_in_y_conj3222);
				comparison42 = comparison();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = new OperatorNode(Operator.AND);
					retval.node.appendChild(left);
					retval.node
							.appendChild((comparison42 != null ? comparison42.node
									: null));
				}

				pushFollow(FOLLOW_y_conj_in_y_conj3252);
				e = y_conj(retval.node);

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (e != null ? e.node : null);
					retval.proptype = compatibleTypes(
							(comparison42 != null ? comparison42.proptype
									: null),
							(e != null ? e.proptype : null),
							(comparison42 != null ? ((Token) comparison42.start)
									: null).getLine());
				}

			}
				break;
			case 2:
			// src/main/antlr3/compiler/Expr.g:1148:11: '||' comparison e=
			// y_conj[$node]
			{
				match(input, 50, FOLLOW_50_in_y_conj3279);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_comparison_in_y_conj3281);
				comparison43 = comparison();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = new OperatorNode(Operator.OR);
					retval.node.appendChild(left);
					retval.node
							.appendChild((comparison43 != null ? comparison43.node
									: null));
				}

				pushFollow(FOLLOW_y_conj_in_y_conj3309);
				e = y_conj(retval.node);

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (e != null ? e.node : null);
					retval.proptype = compatibleTypes(
							(comparison43 != null ? comparison43.proptype
									: null),
							(e != null ? e.proptype : null),
							(comparison43 != null ? ((Token) comparison43.start)
									: null).getLine());
				}

			}
				break;
			case 3:
			// src/main/antlr3/compiler/Expr.g:1159:11:
			{
				if (state.backtracking == 0) {
					retval.node = left;
					retval.proptype = null;
				}

			}
				break;

			}
			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "y_conj"

	public static class equality_return extends ParserRuleReturnScope {
		public Node node;
		public CType proptype;
	};

	// $ANTLR start "equality"
	// src/main/antlr3/compiler/Expr.g:1162:1: equality returns [Node node, Type
	// proptype] : ( '==' relation | '!=' relation );
	public final SmallCParserPass1.equality_return equality()
			throws RecognitionException {
		SmallCParserPass1.equality_return retval = new SmallCParserPass1.equality_return();
		retval.start = input.LT(1);

		SmallCParserPass1.relation_return relation44 = null;

		SmallCParserPass1.relation_return relation45 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1163:9: ( '==' relation | '!='
			// relation )
			int alt28 = 2;
			int LA28_0 = input.LA(1);

			if ((LA28_0 == 43)) {
				alt28 = 1;
			} else if ((LA28_0 == 30)) {
				alt28 = 2;
			} else {
				if (state.backtracking > 0) {
					state.failed = true;
					return retval;
				}
				NoViableAltException nvae = new NoViableAltException("", 28, 0,
						input);

				throw nvae;

			}
			switch (alt28) {
			case 1:
			// src/main/antlr3/compiler/Expr.g:1163:11: '==' relation
			{
				match(input, 43, FOLLOW_43_in_equality3373);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_relation_in_equality3375);
				relation44 = relation();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = new OperatorNode(Operator.EQ);
					retval.node
							.appendChild((relation44 != null ? relation44.node
									: null));
					retval.proptype = (relation44 != null ? relation44.proptype
							: null);
				}

			}
				break;
			case 2:
			// src/main/antlr3/compiler/Expr.g:1169:11: '!=' relation
			{
				match(input, 30, FOLLOW_30_in_equality3403);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_relation_in_equality3405);
				relation45 = relation();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = new OperatorNode(Operator.NEQ);
					retval.node
							.appendChild((relation45 != null ? relation45.node
									: null));
					retval.proptype = (relation45 != null ? relation45.proptype
							: null);
				}

			}
				break;

			}
			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "equality"

	public static class comparison_return extends ParserRuleReturnScope {
		public Node node;
		public CType proptype;
	};

	// $ANTLR start "comparison"
	// src/main/antlr3/compiler/Expr.g:1177:1: comparison returns [Node node,
	// Type proptype] : a= relation (b= equality )? ;
	public final SmallCParserPass1.comparison_return comparison()
			throws RecognitionException {
		SmallCParserPass1.comparison_return retval = new SmallCParserPass1.comparison_return();
		retval.start = input.LT(1);

		SmallCParserPass1.relation_return a = null;

		SmallCParserPass1.equality_return b = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1178:9: (a= relation (b= equality
			// )? )
			// src/main/antlr3/compiler/Expr.g:1178:11: a= relation (b= equality
			// )?
			{
				pushFollow(FOLLOW_relation_in_comparison3460);
				a = relation();

				state._fsp--;
				if (state.failed)
					return retval;

				// src/main/antlr3/compiler/Expr.g:1178:23: (b= equality )?
				int alt29 = 2;
				int LA29_0 = input.LA(1);

				if ((LA29_0 == 30 || LA29_0 == 43)) {
					alt29 = 1;
				}
				switch (alt29) {
				case 1:
				// src/main/antlr3/compiler/Expr.g:1178:23: b= equality
				{
					pushFollow(FOLLOW_equality_in_comparison3464);
					b = equality();

					state._fsp--;
					if (state.failed)
						return retval;

				}
					break;

				}

				if (state.backtracking == 0) {
					if ((b != null ? b.node : null) != null) {
						retval.node = (b != null ? b.node : null);
						retval.node.prependChild((a != null ? a.node : null));
						// Now we have a node that might be optimised
						Node node = optimizeOperator(retval.node,
								(a != null ? ((Token) a.start) : null)
										.getLine());
						if (node == null) {
							// No optimisation could happen
							retval.proptype = compatibleTypes(
									(a != null ? a.proptype : null),
									(b != null ? b.proptype : null),
									(a != null ? ((Token) a.start) : null)
											.getLine());
						} else {
							retval.node = node;
							retval.proptype = new PrimaryType(
									PrimaryType.PType.INT);
						}
					} else {
						retval.node = (a != null ? a.node : null);
						retval.proptype = (a != null ? a.proptype : null);
					}

				}

			}

			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "comparison"

	public static class relation_return extends ParserRuleReturnScope {
		public Node node;
		public CType proptype;
	};

	// $ANTLR start "relation"
	// src/main/antlr3/compiler/Expr.g:1202:1: relation returns [Node node, Type
	// proptype] : sum y_relation ;
	public final SmallCParserPass1.relation_return relation()
			throws RecognitionException {
		SmallCParserPass1.relation_return retval = new SmallCParserPass1.relation_return();
		retval.start = input.LT(1);

		SmallCParserPass1.y_relation_return y_relation46 = null;

		SmallCParserPass1.sum_return sum47 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1203:9: ( sum y_relation )
			// src/main/antlr3/compiler/Expr.g:1203:11: sum y_relation
			{
				pushFollow(FOLLOW_sum_in_relation3511);
				sum47 = sum();

				state._fsp--;
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_y_relation_in_relation3513);
				y_relation46 = y_relation();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					if ((y_relation46 != null ? y_relation46.node : null) != null) {
						retval.node = (y_relation46 != null ? y_relation46.node
								: null);
						retval.node.prependChild((sum47 != null ? sum47.node
								: null));
						// Now we have a node that might be optimised
						Node node = optimizeOperator(retval.node,
								(sum47 != null ? ((Token) sum47.start) : null)
										.getLine());
						if (node != null) {
							retval.node = node;
							retval.proptype = new PrimaryType(
									PrimaryType.PType.INT);
						}
						// Else, no need to do anything else, as we still
						// haven't overwritten the original code
					} else {
						retval.node = (sum47 != null ? sum47.node : null);
					}

					if (retval.proptype == null) {
						retval.proptype = compatibleTypes(
								(sum47 != null ? sum47.proptype : null),
								(y_relation46 != null ? y_relation46.proptype
										: null),
								(sum47 != null ? ((Token) sum47.start) : null)
										.getLine());
					}
				}

			}

			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "relation"

	public static class y_relation_return extends ParserRuleReturnScope {
		public Node node;
		public CType proptype;
	};

	// $ANTLR start "y_relation"
	// src/main/antlr3/compiler/Expr.g:1226:1: y_relation returns [Node node,
	// Type proptype] : ( '>' sum | '<' sum | '<=' sum | '>=' sum |);
	public final SmallCParserPass1.y_relation_return y_relation()
			throws RecognitionException {
		SmallCParserPass1.y_relation_return retval = new SmallCParserPass1.y_relation_return();
		retval.start = input.LT(1);

		SmallCParserPass1.sum_return sum48 = null;

		SmallCParserPass1.sum_return sum49 = null;

		SmallCParserPass1.sum_return sum50 = null;

		SmallCParserPass1.sum_return sum51 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1227:9: ( '>' sum | '<' sum |
			// '<=' sum | '>=' sum |)
			int alt30 = 5;
			switch (input.LA(1)) {
			case 44: {
				alt30 = 1;
			}
				break;
			case 40: {
				alt30 = 2;
			}
				break;
			case 41: {
				alt30 = 3;
			}
				break;
			case 45: {
				alt30 = 4;
			}
				break;
			case 30:
			case 31:
			case 33:
			case 35:
			case 38:
			case 39:
			case 43:
			case 46:
			case 48:
			case 50: {
				alt30 = 5;
			}
				break;
			default:
				if (state.backtracking > 0) {
					state.failed = true;
					return retval;
				}
				NoViableAltException nvae = new NoViableAltException("", 30, 0,
						input);

				throw nvae;

			}

			switch (alt30) {
			case 1:
			// src/main/antlr3/compiler/Expr.g:1227:11: '>' sum
			{
				match(input, 44, FOLLOW_44_in_y_relation3567);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_sum_in_y_relation3569);
				sum48 = sum();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = new OperatorNode(Operator.GT);
					retval.node
							.appendChild((sum48 != null ? sum48.node : null));
					retval.proptype = (sum48 != null ? sum48.proptype : null);
				}

			}
				break;
			case 2:
			// src/main/antlr3/compiler/Expr.g:1233:11: '<' sum
			{
				match(input, 40, FOLLOW_40_in_y_relation3598);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_sum_in_y_relation3600);
				sum49 = sum();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = new OperatorNode(Operator.LT);
					retval.node
							.appendChild((sum49 != null ? sum49.node : null));
					retval.proptype = (sum49 != null ? sum49.proptype : null);
				}

			}
				break;
			case 3:
			// src/main/antlr3/compiler/Expr.g:1239:11: '<=' sum
			{
				match(input, 41, FOLLOW_41_in_y_relation3629);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_sum_in_y_relation3631);
				sum50 = sum();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = new OperatorNode(Operator.LEQ);
					retval.node
							.appendChild((sum50 != null ? sum50.node : null));
					retval.proptype = (sum50 != null ? sum50.proptype : null);
				}

			}
				break;
			case 4:
			// src/main/antlr3/compiler/Expr.g:1245:11: '>=' sum
			{
				match(input, 45, FOLLOW_45_in_y_relation3660);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_sum_in_y_relation3662);
				sum51 = sum();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = new OperatorNode(Operator.GEQ);
					retval.node
							.appendChild((sum51 != null ? sum51.node : null));
					retval.proptype = (sum51 != null ? sum51.proptype : null);
				}

			}
				break;
			case 5:
			// src/main/antlr3/compiler/Expr.g:1251:11:
			{
				if (state.backtracking == 0) {
					retval.node = null;
					retval.proptype = null;
				}

			}
				break;

			}
			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "y_relation"

	public static class sum_return extends ParserRuleReturnScope {
		public Node node;
		public CType proptype;
	};

	// $ANTLR start "sum"
	// src/main/antlr3/compiler/Expr.g:1255:1: sum returns [Node node, Type
	// proptype] : term y_sum[$term.node] ;
	public final SmallCParserPass1.sum_return sum() throws RecognitionException {
		SmallCParserPass1.sum_return retval = new SmallCParserPass1.sum_return();
		retval.start = input.LT(1);

		SmallCParserPass1.term_return term52 = null;

		SmallCParserPass1.y_sum_return y_sum53 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1256:9: ( term y_sum[$term.node]
			// )
			// src/main/antlr3/compiler/Expr.g:1256:11: term y_sum[$term.node]
			{
				pushFollow(FOLLOW_term_in_sum3721);
				term52 = term();

				state._fsp--;
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_y_sum_in_sum3723);
				y_sum53 = y_sum((term52 != null ? term52.node : null));

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (y_sum53 != null ? y_sum53.node : null);
					retval.proptype = compatibleTypes(
							(term52 != null ? term52.proptype : null),
							(y_sum53 != null ? y_sum53.proptype : null),
							(term52 != null ? ((Token) term52.start) : null)
									.getLine());
				}

			}

			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "sum"

	public static class y_sum_return extends ParserRuleReturnScope {
		public Node node;
		public CType proptype;
	};

	// $ANTLR start "y_sum"
	// src/main/antlr3/compiler/Expr.g:1263:1: y_sum[Node left] returns [Node
	// node, Type proptype] : ( '+' term e= y_sum[$node] | '-' term e=
	// y_sum[$node] |);
	public final SmallCParserPass1.y_sum_return y_sum(Node left)
			throws RecognitionException {
		SmallCParserPass1.y_sum_return retval = new SmallCParserPass1.y_sum_return();
		retval.start = input.LT(1);

		SmallCParserPass1.y_sum_return e = null;

		SmallCParserPass1.term_return term54 = null;

		SmallCParserPass1.term_return term55 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1264:9: ( '+' term e=
			// y_sum[$node] | '-' term e= y_sum[$node] |)
			int alt31 = 3;
			switch (input.LA(1)) {
			case 34: {
				alt31 = 1;
			}
				break;
			case 36: {
				alt31 = 2;
			}
				break;
			case 30:
			case 31:
			case 33:
			case 35:
			case 38:
			case 39:
			case 40:
			case 41:
			case 43:
			case 44:
			case 45:
			case 46:
			case 48:
			case 50: {
				alt31 = 3;
			}
				break;
			default:
				if (state.backtracking > 0) {
					state.failed = true;
					return retval;
				}
				NoViableAltException nvae = new NoViableAltException("", 31, 0,
						input);

				throw nvae;

			}

			switch (alt31) {
			case 1:
			// src/main/antlr3/compiler/Expr.g:1264:11: '+' term e= y_sum[$node]
			{
				match(input, 34, FOLLOW_34_in_y_sum3781);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_term_in_y_sum3783);
				term54 = term();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = new OperatorNode(Operator.ADD);
					retval.node.appendChild(left);
					retval.node.appendChild((term54 != null ? term54.node
							: null));
					Node node = optimizeOperator(retval.node,
							(term54 != null ? ((Token) term54.start) : null)
									.getLine());
					if (node != null) {
						retval.node = node;
					}
				}

				pushFollow(FOLLOW_y_sum_in_y_sum3813);
				e = y_sum(retval.node);

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (e != null ? e.node : null);
					retval.proptype = compatibleTypes(
							(term54 != null ? term54.proptype : null),
							(e != null ? e.proptype : null),
							(term54 != null ? ((Token) term54.start) : null)
									.getLine());
				}

			}
				break;
			case 2:
			// src/main/antlr3/compiler/Expr.g:1279:11: '-' term e= y_sum[$node]
			{
				match(input, 36, FOLLOW_36_in_y_sum3840);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_term_in_y_sum3842);
				term55 = term();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = new OperatorNode(Operator.SUB);
					retval.node.appendChild(left);
					retval.node.appendChild((term55 != null ? term55.node
							: null));
					Node node = optimizeOperator(retval.node,
							(term55 != null ? ((Token) term55.start) : null)
									.getLine());
					if (node != null) {
						retval.node = node;
					}
				}

				pushFollow(FOLLOW_y_sum_in_y_sum3872);
				e = y_sum(retval.node);

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (e != null ? e.node : null);
					retval.proptype = compatibleTypes(
							(term55 != null ? term55.proptype : null),
							(e != null ? e.proptype : null),
							(term55 != null ? ((Token) term55.start) : null)
									.getLine());
				}

			}
				break;
			case 3:
			// src/main/antlr3/compiler/Expr.g:1294:11:
			{
				if (state.backtracking == 0) {
					retval.node = left;
					retval.proptype = null;
				}

			}
				break;

			}
			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "y_sum"

	public static class term_return extends ParserRuleReturnScope {
		public Node node;
		public CType proptype;
	};

	// $ANTLR start "term"
	// src/main/antlr3/compiler/Expr.g:1298:1: term returns [Node node, Type
	// proptype] : factor y_term[$factor.node] ;
	public final SmallCParserPass1.term_return term() throws RecognitionException {
		SmallCParserPass1.term_return retval = new SmallCParserPass1.term_return();
		retval.start = input.LT(1);

		SmallCParserPass1.factor_return factor56 = null;

		SmallCParserPass1.y_term_return y_term57 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1299:9: ( factor
			// y_term[$factor.node] )
			// src/main/antlr3/compiler/Expr.g:1299:11: factor
			// y_term[$factor.node]
			{
				pushFollow(FOLLOW_factor_in_term3929);
				factor56 = factor();

				state._fsp--;
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_y_term_in_term3931);
				y_term57 = y_term((factor56 != null ? factor56.node : null));

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (y_term57 != null ? y_term57.node : null);
					retval.proptype = compatibleTypes(
							(factor56 != null ? factor56.proptype : null),
							(y_term57 != null ? y_term57.proptype : null),
							(factor56 != null ? ((Token) factor56.start) : null)
									.getLine());
				}

			}

			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "term"

	public static class y_term_return extends ParserRuleReturnScope {
		public Node node;
		public CType proptype;
	};

	// $ANTLR start "y_term"
	// src/main/antlr3/compiler/Expr.g:1306:1: y_term[Node left] returns [Node
	// node, Type proptype] : ( '*' factor e= y_term[$node] | '/' factor e=
	// y_term[$node] |);
	public final SmallCParserPass1.y_term_return y_term(Node left)
			throws RecognitionException {
		SmallCParserPass1.y_term_return retval = new SmallCParserPass1.y_term_return();
		retval.start = input.LT(1);

		SmallCParserPass1.y_term_return e = null;

		SmallCParserPass1.factor_return factor58 = null;

		SmallCParserPass1.factor_return factor59 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1307:9: ( '*' factor e=
			// y_term[$node] | '/' factor e= y_term[$node] |)
			int alt32 = 3;
			switch (input.LA(1)) {
			case POINTER: {
				alt32 = 1;
			}
				break;
			case 37: {
				alt32 = 2;
			}
				break;
			case 30:
			case 31:
			case 33:
			case 34:
			case 35:
			case 36:
			case 38:
			case 39:
			case 40:
			case 41:
			case 43:
			case 44:
			case 45:
			case 46:
			case 48:
			case 50: {
				alt32 = 3;
			}
				break;
			default:
				if (state.backtracking > 0) {
					state.failed = true;
					return retval;
				}
				NoViableAltException nvae = new NoViableAltException("", 32, 0,
						input);

				throw nvae;

			}

			switch (alt32) {
			case 1:
			// src/main/antlr3/compiler/Expr.g:1307:11: '*' factor e=
			// y_term[$node]
			{
				match(input, POINTER, FOLLOW_POINTER_in_y_term3982);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_factor_in_y_term3984);
				factor58 = factor();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = new OperatorNode(Operator.MULT);
					retval.node.appendChild(left);
					retval.node.appendChild((factor58 != null ? factor58.node
							: null));
					Node node = optimizeOperator(
							retval.node,
							(factor58 != null ? ((Token) factor58.start) : null)
									.getLine());
					if (node != null) {
						retval.node = node;
					}
				}

				pushFollow(FOLLOW_y_term_in_y_term4015);
				e = y_term(retval.node);

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (e != null ? e.node : null);
					retval.proptype = compatibleTypes(
							(factor58 != null ? factor58.proptype : null),
							(e != null ? e.proptype : null),
							(factor58 != null ? ((Token) factor58.start) : null)
									.getLine());
				}

			}
				break;
			case 2:
			// src/main/antlr3/compiler/Expr.g:1322:11: '/' factor e=
			// y_term[$node]
			{
				match(input, 37, FOLLOW_37_in_y_term4042);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_factor_in_y_term4044);
				factor59 = factor();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = new OperatorNode(Operator.DIV);
					retval.node.appendChild(left);
					retval.node.appendChild((factor59 != null ? factor59.node
							: null));
					Node node = optimizeOperator(
							retval.node,
							(factor59 != null ? ((Token) factor59.start) : null)
									.getLine());
					if (node != null) {
						retval.node = node;
					}
				}

				pushFollow(FOLLOW_y_term_in_y_term4074);
				e = y_term(retval.node);

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (e != null ? e.node : null);
					retval.proptype = compatibleTypes(
							(factor59 != null ? factor59.proptype : null),
							(e != null ? e.proptype : null),
							(factor59 != null ? ((Token) factor59.start) : null)
									.getLine());
				}

			}
				break;
			case 3:
			// src/main/antlr3/compiler/Expr.g:1355:11:
			{
				if (state.backtracking == 0) {
					retval.node = left;
					retval.proptype = null;
				}

			}
				break;

			}
			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "y_term"

	public static class factor_return extends ParserRuleReturnScope {
		public Node node;
		public CType proptype;
	};

	// $ANTLR start "factor"
	// src/main/antlr3/compiler/Expr.g:1359:1: factor returns [Node node, Type
	// proptype] : ( '!' e= factor | '-' e= factor | primary );
	public final SmallCParserPass1.factor_return factor() throws RecognitionException {
		SmallCParserPass1.factor_return retval = new SmallCParserPass1.factor_return();
		retval.start = input.LT(1);

		SmallCParserPass1.factor_return e = null;

		SmallCParserPass1.primary_return primary60 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1360:9: ( '!' e= factor | '-' e=
			// factor | primary )
			int alt33 = 3;
			switch (input.LA(1)) {
			case 29: {
				alt33 = 1;
			}
				break;
			case 36: {
				alt33 = 2;
			}
				break;
			case CHARCONST:
			case DEREFERENCE:
			case FLOATCONST:
			case ID:
			case INTCONST:
			case POINTER:
			case READ:
			case WRITE:
			case 32: {
				alt33 = 3;
			}
				break;
			default:
				if (state.backtracking > 0) {
					state.failed = true;
					return retval;
				}
				NoViableAltException nvae = new NoViableAltException("", 33, 0,
						input);

				throw nvae;

			}

			switch (alt33) {
			case 1:
			// src/main/antlr3/compiler/Expr.g:1360:11: '!' e= factor
			{
				match(input, 29, FOLLOW_29_in_factor4150);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_factor_in_factor4154);
				e = factor();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					Comparable value = getValue((e != null ? e.node : null));
					if (value != null) {
						if (value.equals(0) || value.equals(0f)) {
							retval.node = new IntNode(1);
						} else {
							retval.node = new IntNode(0);
						}
					} else {
						retval.node = new OperatorNode(Operator.NOT);
						retval.node.appendChild((e != null ? e.node : null));
					}
					retval.proptype = new PrimaryType(PrimaryType.PType.INT);
				}

			}
				break;
			case 2:
			// src/main/antlr3/compiler/Expr.g:1377:11: '-' e= factor
			{
				match(input, 36, FOLLOW_36_in_factor4185);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_factor_in_factor4189);
				e = factor();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					if ((e != null ? e.node : null) instanceof IntNode) {
						retval.node = new IntNode(
								-((IntNode) (e != null ? e.node : null))
										.getValue());
					} else if ((e != null ? e.node : null) instanceof FloatNode) {
						retval.node = new FloatNode(
								-((FloatNode) (e != null ? e.node : null))
										.getValue());
					} else if ((e != null ? e.node : null) instanceof CharNode) {
						retval.node = new IntNode(
								-((CharNode) (e != null ? e.node : null))
										.getValue());
						// Force it into an integer
						retval.proptype = new PrimaryType(PrimaryType.PType.INT);
					} else {
						retval.node = new OperatorNode(Operator.NEG);
						retval.node.appendChild((e != null ? e.node : null));
					}
					if (retval.proptype == null) {
						retval.proptype = (e != null ? e.proptype : null);
					}
				}

			}
				break;
			case 3:
			// src/main/antlr3/compiler/Expr.g:1398:11: primary
			{
				pushFollow(FOLLOW_primary_in_factor4220);
				primary60 = primary();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (primary60 != null ? primary60.node : null);
					retval.proptype = (primary60 != null ? primary60.proptype
							: null);
				}

			}
				break;

			}
			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "factor"

	public static class primary_return extends ParserRuleReturnScope {
		public Node node;
		public CType proptype;
		public String name;
	};

	// $ANTLR start "primary"
	// src/main/antlr3/compiler/Expr.g:1407:1: primary returns [Node node, Type
	// proptype, String name] : ( num_check | char_check | float_check |
	// pointer_id | '(' expr ')' );
	public final SmallCParserPass1.primary_return primary()
			throws RecognitionException {
		SmallCParserPass1.primary_return retval = new SmallCParserPass1.primary_return();
		retval.start = input.LT(1);

		IntNode num_check61 = null;

		CharNode char_check62 = null;

		FloatNode float_check63 = null;

		SmallCParserPass1.pointer_id_return pointer_id64 = null;

		SmallCParserPass1.expr_return expr65 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1408:9: ( num_check | char_check
			// | float_check | pointer_id | '(' expr ')' )
			int alt34 = 5;
			switch (input.LA(1)) {
			case INTCONST: {
				alt34 = 1;
			}
				break;
			case CHARCONST: {
				alt34 = 2;
			}
				break;
			case FLOATCONST: {
				alt34 = 3;
			}
				break;
			case DEREFERENCE:
			case ID:
			case POINTER:
			case READ:
			case WRITE: {
				alt34 = 4;
			}
				break;
			case 32: {
				alt34 = 5;
			}
				break;
			default:
				if (state.backtracking > 0) {
					state.failed = true;
					return retval;
				}
				NoViableAltException nvae = new NoViableAltException("", 34, 0,
						input);

				throw nvae;

			}

			switch (alt34) {
			case 1:
			// src/main/antlr3/compiler/Expr.g:1408:11: num_check
			{
				pushFollow(FOLLOW_num_check_in_primary4269);
				num_check61 = num_check();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = num_check61;
					retval.proptype = new PrimaryType(PrimaryType.PType.INT,
							true);
				}

			}
				break;
			case 2:
			// src/main/antlr3/compiler/Expr.g:1413:11: char_check
			{
				pushFollow(FOLLOW_char_check_in_primary4300);
				char_check62 = char_check();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = char_check62;
					retval.proptype = new PrimaryType(PrimaryType.PType.CHAR,
							true);
				}

			}
				break;
			case 3:
			// src/main/antlr3/compiler/Expr.g:1418:11: float_check
			{
				pushFollow(FOLLOW_float_check_in_primary4330);
				float_check63 = float_check();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = float_check63;
					retval.proptype = new PrimaryType(PrimaryType.PType.FLOAT,
							true);
				}

			}
				break;
			case 4:
			// src/main/antlr3/compiler/Expr.g:1423:11: pointer_id
			{
				pushFollow(FOLLOW_pointer_id_in_primary4361);
				pointer_id64 = pointer_id();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (pointer_id64 != null ? pointer_id64.node
							: null);
					retval.proptype = (pointer_id64 != null ? pointer_id64.proptype
							: null);
					retval.name = (pointer_id64 != null ? pointer_id64.name
							: null);
				}

			}
				break;
			case 5:
			// src/main/antlr3/compiler/Expr.g:1429:11: '(' expr ')'
			{
				match(input, 32, FOLLOW_32_in_primary4392);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_expr_in_primary4394);
				expr65 = expr();

				state._fsp--;
				if (state.failed)
					return retval;

				match(input, 33, FOLLOW_33_in_primary4396);
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (expr65 != null ? expr65.node : null);
					retval.proptype = (expr65 != null ? expr65.proptype : null);
				}

			}
				break;

			}
			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "primary"

	public static class pointer_id_return extends ParserRuleReturnScope {
		public Node node;
		public String name;
		public CType proptype;
		public int linenumber;
	};

	// $ANTLR start "pointer_id"
	// src/main/antlr3/compiler/Expr.g:1436:1: pointer_id returns [Node node,
	// String name, Type proptype, int linenumber] : ( POINTER e= primary |
	// DEREFERENCE e= primary | READ '(' a= expr ( ',' b= expr )* ')' | WRITE
	// '(' a= expr ( ',' b= expr )* ')' | id_check[false] ( '(' arg_list ')' )?
	// ( array_operator[$node, $proptype, false] )? );
	public final SmallCParserPass1.pointer_id_return pointer_id()
			throws RecognitionException {
		SmallCParserPass1.pointer_id_return retval = new SmallCParserPass1.pointer_id_return();
		retval.start = input.LT(1);

		Token POINTER66 = null;
		Token DEREFERENCE67 = null;
		Token READ68 = null;
		Token WRITE69 = null;
		SmallCParserPass1.primary_return e = null;

		SmallCParserPass1.expr_return a = null;

		SmallCParserPass1.expr_return b = null;

		SmallCParserPass1.id_check_return id_check70 = null;

		SmallCParserPass1.arg_list_return arg_list71 = null;

		SmallCParserPass1.array_operator_return array_operator72 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1437:9: ( POINTER e= primary |
			// DEREFERENCE e= primary | READ '(' a= expr ( ',' b= expr )* ')' |
			// WRITE '(' a= expr ( ',' b= expr )* ')' | id_check[false] ( '('
			// arg_list ')' )? ( array_operator[$node, $proptype, false] )? )
			int alt39 = 5;
			switch (input.LA(1)) {
			case POINTER: {
				alt39 = 1;
			}
				break;
			case DEREFERENCE: {
				alt39 = 2;
			}
				break;
			case READ: {
				alt39 = 3;
			}
				break;
			case WRITE: {
				alt39 = 4;
			}
				break;
			case ID: {
				alt39 = 5;
			}
				break;
			default:
				if (state.backtracking > 0) {
					state.failed = true;
					return retval;
				}
				NoViableAltException nvae = new NoViableAltException("", 39, 0,
						input);

				throw nvae;

			}

			switch (alt39) {
			case 1:
			// src/main/antlr3/compiler/Expr.g:1437:11: POINTER e= primary
			{
				POINTER66 = (Token) match(input, POINTER,
						FOLLOW_POINTER_in_pointer_id4444);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_primary_in_pointer_id4448);
				e = primary();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = new OperatorNode(Operator.PNTR);
					retval.node.appendChild((e != null ? e.node : null));
					retval.name = (e != null ? e.name : null);
					if ((e != null ? e.proptype : null) instanceof PointerType) {
						retval.proptype = ((PointerType) (e != null ? e.proptype
								: null)).refType;
					} else {
						throwError(
								"Type error: trying to dereference a non-pointer type",
								POINTER66.getLine());
					}
					retval.linenumber = POINTER66.getLine();
				}

			}
				break;
			case 2:
			// src/main/antlr3/compiler/Expr.g:1450:11: DEREFERENCE e= primary
			{
				DEREFERENCE67 = (Token) match(input, DEREFERENCE,
						FOLLOW_DEREFERENCE_in_pointer_id4481);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_primary_in_pointer_id4485);
				e = primary();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = new OperatorNode(Operator.ADDR);
					retval.node.appendChild((e != null ? e.node : null));
					retval.name = (e != null ? e.name : null);
					// Add another layer of indirection
					if ((e != null ? e.proptype : null) instanceof ArrayType) {
						// Array by reference passing, allow
						retval.proptype = (e != null ? e.proptype : null);
					} else {
						retval.proptype = new PointerType(
								(e != null ? e.proptype : null));
					}
					retval.linenumber = DEREFERENCE67.getLine();
				}

			}
				break;
			case 3:
			// src/main/antlr3/compiler/Expr.g:1465:11: READ '(' a= expr ( ','
			// b= expr )* ')'
			{
				READ68 = (Token) match(input, READ,
						FOLLOW_READ_in_pointer_id4517);
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					if (!stdio) {
						throwError("scanf called, but no import of stdio.h",
								READ68.getLine());
					}
					retval.linenumber = READ68.getLine();
					retval.proptype = new PrimaryType(PType.INT);
					retval.name = "scanf";
				}

				if (state.backtracking == 0) {
					retval.node = new ScanfNode(this.returnTypes.size());
				}

				match(input, 32, FOLLOW_32_in_pointer_id4577);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_expr_in_pointer_id4581);
				a = expr();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node.appendChild((a != null ? a.node : null));
				}

				// src/main/antlr3/compiler/Expr.g:1479:14: ( ',' b= expr )*
				loop35: do {
					int alt35 = 2;
					int LA35_0 = input.LA(1);

					if ((LA35_0 == 35)) {
						alt35 = 1;
					}

					switch (alt35) {
					case 1:
					// src/main/antlr3/compiler/Expr.g:1479:15: ',' b= expr
					{
						match(input, 35, FOLLOW_35_in_pointer_id4616);
						if (state.failed)
							return retval;

						pushFollow(FOLLOW_expr_in_pointer_id4620);
						b = expr();

						state._fsp--;
						if (state.failed)
							return retval;

						if (state.backtracking == 0) {
							retval.node
									.appendChild((b != null ? b.node : null));
						}

					}
						break;

					default:
						break loop35;
					}
				} while (true);

				match(input, 33, FOLLOW_33_in_pointer_id4656);
				if (state.failed)
					return retval;

			}
				break;
			case 4:
			// src/main/antlr3/compiler/Expr.g:1484:11: WRITE '(' a= expr ( ','
			// b= expr )* ')'
			{
				WRITE69 = (Token) match(input, WRITE,
						FOLLOW_WRITE_in_pointer_id4668);
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					if (!stdio) {
						throwError("printf called, but no import of stdio.h",
								WRITE69.getLine());
					}
					retval.linenumber = WRITE69.getLine();
					retval.proptype = new PrimaryType(PType.INT);
					retval.name = "printf";
				}

				if (state.backtracking == 0) {
					retval.node = new PrintfNode(this.returnTypes.size());
				}

				match(input, 32, FOLLOW_32_in_pointer_id4727);
				if (state.failed)
					return retval;

				pushFollow(FOLLOW_expr_in_pointer_id4731);
				a = expr();

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node.appendChild((a != null ? a.node : null));
				}

				// src/main/antlr3/compiler/Expr.g:1498:13: ( ',' b= expr )*
				loop36: do {
					int alt36 = 2;
					int LA36_0 = input.LA(1);

					if ((LA36_0 == 35)) {
						alt36 = 1;
					}

					switch (alt36) {
					case 1:
					// src/main/antlr3/compiler/Expr.g:1498:14: ',' b= expr
					{
						match(input, 35, FOLLOW_35_in_pointer_id4765);
						if (state.failed)
							return retval;

						pushFollow(FOLLOW_expr_in_pointer_id4769);
						b = expr();

						state._fsp--;
						if (state.failed)
							return retval;

						if (state.backtracking == 0) {
							retval.node
									.appendChild((b != null ? b.node : null));
						}

					}
						break;

					default:
						break loop36;
					}
				} while (true);

				match(input, 33, FOLLOW_33_in_pointer_id4804);
				if (state.failed)
					return retval;

			}
				break;
			case 5:
			// src/main/antlr3/compiler/Expr.g:1503:11: id_check[false] ( '('
			// arg_list ')' )? ( array_operator[$node, $proptype, false] )?
			{
				pushFollow(FOLLOW_id_check_in_pointer_id4816);
				id_check70 = id_check(false);

				state._fsp--;
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					retval.node = (id_check70 != null ? id_check70.node : null);
					retval.name = (id_check70 != null ? input.toString(
							id_check70.start, id_check70.stop) : null);
					CType type = stb.getByName(retval.name).getNewType();
					if (type instanceof FunctionType) {
						retval.proptype = ((FunctionType) type).returnType;
					} else {
						retval.proptype = type;
					}
					retval.linenumber = (id_check70 != null ? id_check70.linenumber
							: 0);
				}

				// src/main/antlr3/compiler/Expr.g:1516:11: ( '(' arg_list ')'
				// )?
				int alt37 = 2;
				int LA37_0 = input.LA(1);

				if ((LA37_0 == 32)) {
					alt37 = 1;
				}
				switch (alt37) {
				case 1:
				// src/main/antlr3/compiler/Expr.g:1516:12: '(' arg_list ')'
				{
					match(input, 32, FOLLOW_32_in_pointer_id4850);
					if (state.failed)
						return retval;

					pushFollow(FOLLOW_arg_list_in_pointer_id4852);
					arg_list71 = arg_list();

					state._fsp--;
					if (state.failed)
						return retval;

					match(input, 33, FOLLOW_33_in_pointer_id4854);
					if (state.failed)
						return retval;

				}
					break;

				}

				if (state.backtracking == 0) {
					if ((arg_list71 != null ? arg_list71.node : null) != null) {
						// A function call
						tmpnode = retval.node;
						retval.node = new FunctionCallNode(stb.getByName(retval.name).getID(), this.returnTypes.size());
						// Check the parameters type
						CType func = stb.getByName((id_check70 != null ? input.toString(
								id_check70.start, id_check70.stop)
								: null)).getNewType();
						CType[] formalargs = null;
						if (func instanceof FunctionType) {
							formalargs = ((FunctionType) func).argsType;
						} else {
							throwError("Calling a variable as a function",
									(id_check70 != null ? id_check70.linenumber
											: 0));
						}
						CType[] convertedList = new CType[(arg_list71 != null ? arg_list71.proptype
								: null).size()];
						if ((arg_list71 != null ? arg_list71.proptype : null)
								.size() != formalargs.length) {
							throwError(
									"Number of formal and actual parameters don't match",
									(id_check70 != null ? id_check70.linenumber
											: 0));
						}
						for (int i = 0; i < (arg_list71 != null ? arg_list71.proptype
								: null).size(); i++) {
							convertedList[i] = (arg_list71 != null ? arg_list71.proptype
									: null).get(i);
							if (!convertedList[i].same(formalargs[i])) {
								throwError(
										"Incompatible arguments for function",
										(id_check70 != null ? id_check70.linenumber
												: 0));
							}
						}
						retval.node
								.appendChild((arg_list71 != null ? arg_list71.node
										: null));
						mustExist.add(stb.getByName((id_check70 != null ? input.toString(
								id_check70.start, id_check70.stop)
								: null)).getID());
						// Propagate the return type upwards

					}
				}

				// src/main/antlr3/compiler/Expr.g:1547:11: (
				// array_operator[$node, $proptype, false] )?
				int alt38 = 2;
				int LA38_0 = input.LA(1);

				if ((LA38_0 == 47)) {
					alt38 = 1;
				}
				switch (alt38) {
				case 1:
				// src/main/antlr3/compiler/Expr.g:1547:11:
				// array_operator[$node, $proptype, false]
				{
					pushFollow(FOLLOW_array_operator_in_pointer_id4907);
					array_operator72 = array_operator(retval.node,
							retval.proptype, false);

					state._fsp--;
					if (state.failed)
						return retval;

				}
					break;

				}

				if (state.backtracking == 0) {
					if ((array_operator72 != null ? array_operator72.node
							: null) != null) {
						retval.node = (array_operator72 != null ? array_operator72.node
								: null);
						retval.proptype = (array_operator72 != null ? array_operator72.type
								: null);
					}
				}

			}
				break;

			}
			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "pointer_id"

	public static class id_check_return extends ParserRuleReturnScope {
		public Node node;
		public int linenumber;
	};

	// $ANTLR start "id_check"
	// src/main/antlr3/compiler/Expr.g:1558:1: id_check[boolean declaration]
	// returns [Node node, int linenumber] : ID ;
	public final SmallCParserPass1.id_check_return id_check(boolean declaration)
			throws RecognitionException {
		SmallCParserPass1.id_check_return retval = new SmallCParserPass1.id_check_return();
		retval.start = input.LT(1);

		Token ID73 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1559:9: ( ID )
			// src/main/antlr3/compiler/Expr.g:1559:11: ID
			{
				ID73 = (Token) match(input, ID, FOLLOW_ID_in_id_check4961);
				if (state.failed)
					return retval;

				if (state.backtracking == 0) {
					if ((ID73 != null ? ID73.getText() : null).length() > 16) {
						throwError(
								"Variable name of "
										+ (ID73 != null ? ID73.getText() : null)
										+ " is too long", ID73.getLine());
					}
					boolean def = false;
					if (stb.exists((ID73 != null ? ID73.getText() : null))
							&& defined.containsKey(stb.getByName(
									(ID73 != null ? ID73.getText() : null))
									.getID())) {
						def = true;
					}
					if (declaration) {
						if (stb.existsInScope((ID73 != null ? ID73.getText()
								: null)) && def) {
							throwError(
									"Variable "
											+ (ID73 != null ? ID73.getText()
													: null)
											+ " is already defined in this scope",
									ID73.getLine());
						}
						retval.node = null;
					} else {
						// Not a declaration, so it must exist at the end
						if (!stb.exists((ID73 != null ? ID73.getText() : null))) {
							throwError(
									"Variable "
											+ (ID73 != null ? ID73.getText()
													: null)
											+ " is used but not declared",
									ID73.getLine());
						}
						// mustExist.add(stb.getByName((ID73!=null?ID73.getText():null)).getID());
						retval.node = new VarNode(
								stb.getByName(
										(ID73 != null ? ID73.getText() : null))
										.getID(), this.returnTypes.size(),
								false);
					}
					retval.linenumber = ID73.getLine();
				}

			}

			retval.stop = input.LT(-1);

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return retval;
	}

	// $ANTLR end "id_check"

	// $ANTLR start "num_check"
	// src/main/antlr3/compiler/Expr.g:1587:1: num_check returns [IntNode node]
	// : INTCONST ;
	public final IntNode num_check() throws RecognitionException {
		IntNode node = null;

		Token INTCONST74 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1588:9: ( INTCONST )
			// src/main/antlr3/compiler/Expr.g:1588:11: INTCONST
			{
				INTCONST74 = (Token) match(input, INTCONST,
						FOLLOW_INTCONST_in_num_check5007);
				if (state.failed)
					return node;

				if (state.backtracking == 0) {
					int tmp = Integer.parseInt((INTCONST74 != null ? INTCONST74
							.getText() : null));
					if (tmp < -32768 || tmp > 32767) {
						throwError("Integer is out of bounds",
								INTCONST74.getLine());
					}
				}

				if (state.backtracking == 0) {
					node = new IntNode(
							Integer.parseInt((INTCONST74 != null ? INTCONST74
									.getText() : null)));
				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return node;
	}

	// $ANTLR end "num_check"

	// $ANTLR start "float_check"
	// src/main/antlr3/compiler/Expr.g:1598:1: float_check returns [FloatNode
	// node] : FLOATCONST ;
	public final FloatNode float_check() throws RecognitionException {
		FloatNode node = null;

		Token FLOATCONST75 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1599:9: ( FLOATCONST )
			// src/main/antlr3/compiler/Expr.g:1599:11: FLOATCONST
			{
				FLOATCONST75 = (Token) match(input, FLOATCONST,
						FOLLOW_FLOATCONST_in_float_check5076);
				if (state.failed)
					return node;

				if (state.backtracking == 0) {
					node = new FloatNode(
							Float.parseFloat((FLOATCONST75 != null ? FLOATCONST75
									.getText() : null)));
				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return node;
	}

	// $ANTLR end "float_check"

	// $ANTLR start "char_check"
	// src/main/antlr3/compiler/Expr.g:1603:1: char_check returns [CharNode
	// node] : CHARCONST ;
	public final CharNode char_check() throws RecognitionException {
		CharNode node = null;

		Token CHARCONST76 = null;

		try {
			// src/main/antlr3/compiler/Expr.g:1604:9: ( CHARCONST )
			// src/main/antlr3/compiler/Expr.g:1604:11: CHARCONST
			{
				CHARCONST76 = (Token) match(input, CHARCONST,
						FOLLOW_CHARCONST_in_char_check5129);
				if (state.failed)
					return node;

				if (state.backtracking == 0) {

					if ((CHARCONST76 != null ? CHARCONST76.getText() : null)
							.equals("'\\0'")) {
						node = new CharNode('\0');
					} else if ((CHARCONST76 != null ? CHARCONST76.getText()
							: null).equals("'\\n'")) {
						node = new CharNode('\n');
					} else {
						node = new CharNode(
								(CHARCONST76 != null ? CHARCONST76.getText()
										: null).charAt(1));
					}
				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		}

		finally {
			// do for sure before leaving
		}
		return node;
	}

	// $ANTLR end "char_check"

	// $ANTLR start synpred1_Expr
	public final void synpred1_Expr_fragment() throws RecognitionException {
		// src/main/antlr3/compiler/Expr.g:411:20: ( ';' )
		// src/main/antlr3/compiler/Expr.g:411:21: ';'
		{
			match(input, 39, FOLLOW_39_in_synpred1_Expr565);
			if (state.failed)
				return;

		}

	}

	// $ANTLR end synpred1_Expr

	// $ANTLR start synpred2_Expr
	public final void synpred2_Expr_fragment() throws RecognitionException {
		// src/main/antlr3/compiler/Expr.g:638:11: ( 'void' )
		// src/main/antlr3/compiler/Expr.g:638:12: 'void'
		{
			match(input, VOID, FOLLOW_VOID_in_synpred2_Expr1343);
			if (state.failed)
				return;

		}

	}

	// $ANTLR end synpred2_Expr

	// $ANTLR start synpred3_Expr
	public final void synpred3_Expr_fragment() throws RecognitionException {
		// src/main/antlr3/compiler/Expr.g:884:12: ( ELSE )
		// src/main/antlr3/compiler/Expr.g:884:13: ELSE
		{
			match(input, ELSE, FOLLOW_ELSE_in_synpred3_Expr2224);
			if (state.failed)
				return;

		}

	}

	// $ANTLR end synpred3_Expr

	// $ANTLR start synpred4_Expr
	public final void synpred4_Expr_fragment() throws RecognitionException {
		// src/main/antlr3/compiler/Expr.g:1042:11: ( pointer_id '=' )
		// src/main/antlr3/compiler/Expr.g:1042:12: pointer_id '='
		{
			pushFollow(FOLLOW_pointer_id_in_synpred4_Expr2751);
			pointer_id();

			state._fsp--;
			if (state.failed)
				return;

			match(input, 42, FOLLOW_42_in_synpred4_Expr2753);
			if (state.failed)
				return;

		}

	}

	// $ANTLR end synpred4_Expr

	// $ANTLR start synpred5_Expr
	public final void synpred5_Expr_fragment() throws RecognitionException {
		// src/main/antlr3/compiler/Expr.g:1115:11: ( '?' )
		// src/main/antlr3/compiler/Expr.g:1115:12: '?'
		{
			match(input, 46, FOLLOW_46_in_synpred5_Expr3056);
			if (state.failed)
				return;

		}

	}

	// $ANTLR end synpred5_Expr

	// Delegated rules

	public final boolean synpred1_Expr() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred1_Expr_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: " + re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed = false;
		return success;
	}

	public final boolean synpred4_Expr() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred4_Expr_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: " + re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed = false;
		return success;
	}

	public final boolean synpred5_Expr() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred5_Expr_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: " + re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed = false;
		return success;
	}

	public final boolean synpred2_Expr() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred2_Expr_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: " + re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed = false;
		return success;
	}

	public final boolean synpred3_Expr() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred3_Expr_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: " + re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed = false;
		return success;
	}

	public static final BitSet FOLLOW_declaration_in_smallc_program92 = new BitSet(
			new long[] { 0x0000000002821120L });
	public static final BitSet FOLLOW_EOF_in_smallc_program124 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_type_specifier_in_declaration215 = new BitSet(
			new long[] { 0x0000000000008000L });
	public static final BitSet FOLLOW_var_decl_list_in_declaration324 = new BitSet(
			new long[] { 0x0000008000000000L });
	public static final BitSet FOLLOW_39_in_declaration327 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_id_check_in_declaration382 = new BitSet(
			new long[] { 0x0000000100000000L });
	public static final BitSet FOLLOW_32_in_declaration423 = new BitSet(
			new long[] { 0x0000000202021120L });
	public static final BitSet FOLLOW_param_decl_list_in_declaration445 = new BitSet(
			new long[] { 0x0000000200000000L });
	public static final BitSet FOLLOW_33_in_declaration467 = new BitSet(
			new long[] { 0x0002008000000000L });
	public static final BitSet FOLLOW_compound_stmt_in_declaration747 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_39_in_declaration752 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_STDIOIMPORT_in_declaration840 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_basetype_in_type_specifier884 = new BitSet(
			new long[] { 0x0000000000100000L });
	public static final BitSet FOLLOW_pointer_operator_in_type_specifier886 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_47_in_array_operator964 = new BitSet(
			new long[] { 0x000100112934A440L });
	public static final BitSet FOLLOW_expr_in_array_operator966 = new BitSet(
			new long[] { 0x0001000000000000L });
	public static final BitSet FOLLOW_48_in_array_operator969 = new BitSet(
			new long[] { 0x0000800000000002L });
	public static final BitSet FOLLOW_CONST_in_const_specifier1040 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_const_specifier_in_basetype1086 = new BitSet(
			new long[] { 0x0000000002021020L });
	public static final BitSet FOLLOW_INT_in_basetype1101 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_CHAR_in_basetype1117 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_FLOAT_in_basetype1133 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_VOID_in_basetype1149 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_POINTER_in_pointer_operator1201 = new BitSet(
			new long[] { 0x0000000000100000L });
	public static final BitSet FOLLOW_pointer_operator_in_pointer_operator1205 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_param_decl_in_param_decl_list1253 = new BitSet(
			new long[] { 0x0000000800000002L });
	public static final BitSet FOLLOW_35_in_param_decl_list1283 = new BitSet(
			new long[] { 0x0000000002021120L });
	public static final BitSet FOLLOW_param_decl_in_param_decl_list1287 = new BitSet(
			new long[] { 0x0000000800000002L });
	public static final BitSet FOLLOW_VOID_in_param_decl_list1346 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_type_specifier_in_param_decl1403 = new BitSet(
			new long[] { 0x0000800000008002L });
	public static final BitSet FOLLOW_id_check_in_param_decl1405 = new BitSet(
			new long[] { 0x0000800000000002L });
	public static final BitSet FOLLOW_array_operator_in_param_decl1409 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_49_in_compound_stmt1458 = new BitSet(
			new long[] { 0x000A00912FF7F770L });
	public static final BitSet FOLLOW_declaration_in_compound_stmt1500 = new BitSet(
			new long[] { 0x000A00912FF7F770L });
	public static final BitSet FOLLOW_stmt_in_compound_stmt1561 = new BitSet(
			new long[] { 0x000A00912D75E650L });
	public static final BitSet FOLLOW_51_in_compound_stmt1606 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_variable_decl_in_var_decl_list1666 = new BitSet(
			new long[] { 0x0000000800000002L });
	public static final BitSet FOLLOW_35_in_var_decl_list1695 = new BitSet(
			new long[] { 0x0000000000108000L });
	public static final BitSet FOLLOW_pointer_operator_in_var_decl_list1697 = new BitSet(
			new long[] { 0x0000000000008000L });
	public static final BitSet FOLLOW_variable_decl_in_var_decl_list1726 = new BitSet(
			new long[] { 0x0000000800000002L });
	public static final BitSet FOLLOW_id_check_in_variable_decl1788 = new BitSet(
			new long[] { 0x0000840000000002L });
	public static final BitSet FOLLOW_42_in_variable_decl1803 = new BitSet(
			new long[] { 0x000000112934A440L });
	public static final BitSet FOLLOW_expr_in_variable_decl1805 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_array_operator_in_variable_decl1809 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_compound_stmt_in_stmt1861 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_cond_stmt_in_stmt1890 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_while_stmt_in_stmt1929 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_for_stmt_in_stmt1957 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_expr_in_stmt1985 = new BitSet(
			new long[] { 0x0000008000000000L });
	public static final BitSet FOLLOW_39_in_stmt1987 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_BREAK_in_stmt2015 = new BitSet(
			new long[] { 0x0000008000000000L });
	public static final BitSet FOLLOW_39_in_stmt2017 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_CONTINUE_in_stmt2048 = new BitSet(
			new long[] { 0x0000008000000000L });
	public static final BitSet FOLLOW_39_in_stmt2050 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_RETURN_in_stmt2099 = new BitSet(
			new long[] { 0x000000912934A440L });
	public static final BitSet FOLLOW_expr_in_stmt2101 = new BitSet(
			new long[] { 0x0000008000000000L });
	public static final BitSet FOLLOW_39_in_stmt2104 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_39_in_stmt2134 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_IF_in_cond_stmt2176 = new BitSet(
			new long[] { 0x0000000100000000L });
	public static final BitSet FOLLOW_32_in_cond_stmt2178 = new BitSet(
			new long[] { 0x000000112934A440L });
	public static final BitSet FOLLOW_expr_in_cond_stmt2180 = new BitSet(
			new long[] { 0x0000000200000000L });
	public static final BitSet FOLLOW_33_in_cond_stmt2182 = new BitSet(
			new long[] { 0x000200912D75E650L });
	public static final BitSet FOLLOW_stmt_in_cond_stmt2209 = new BitSet(
			new long[] { 0x0000000000000802L });
	public static final BitSet FOLLOW_ELSE_in_cond_stmt2228 = new BitSet(
			new long[] { 0x000200912D75E650L });
	public static final BitSet FOLLOW_stmt_in_cond_stmt2232 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_WHILE_in_while_stmt2293 = new BitSet(
			new long[] { 0x0000000100000000L });
	public static final BitSet FOLLOW_32_in_while_stmt2295 = new BitSet(
			new long[] { 0x000000112934A440L });
	public static final BitSet FOLLOW_expr_in_while_stmt2297 = new BitSet(
			new long[] { 0x0000000200000000L });
	public static final BitSet FOLLOW_33_in_while_stmt2299 = new BitSet(
			new long[] { 0x000200912D75E650L });
	public static final BitSet FOLLOW_stmt_in_while_stmt2349 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_FOR_in_for_stmt2431 = new BitSet(
			new long[] { 0x0000000100000000L });
	public static final BitSet FOLLOW_32_in_for_stmt2433 = new BitSet(
			new long[] { 0x000000912934A440L });
	public static final BitSet FOLLOW_for_op_in_for_stmt2437 = new BitSet(
			new long[] { 0x0000008000000000L });
	public static final BitSet FOLLOW_39_in_for_stmt2439 = new BitSet(
			new long[] { 0x000000912934A440L });
	public static final BitSet FOLLOW_for_op_in_for_stmt2443 = new BitSet(
			new long[] { 0x0000008000000000L });
	public static final BitSet FOLLOW_39_in_for_stmt2445 = new BitSet(
			new long[] { 0x000000132934A440L });
	public static final BitSet FOLLOW_for_op_in_for_stmt2449 = new BitSet(
			new long[] { 0x0000000200000000L });
	public static final BitSet FOLLOW_33_in_for_stmt2451 = new BitSet(
			new long[] { 0x000200912D75E650L });
	public static final BitSet FOLLOW_stmt_in_for_stmt2499 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_expr_in_for_op2562 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_expr_in_arg_list2628 = new BitSet(
			new long[] { 0x0000000800000002L });
	public static final BitSet FOLLOW_35_in_arg_list2658 = new BitSet(
			new long[] { 0x000000112934A440L });
	public static final BitSet FOLLOW_expr_in_arg_list2662 = new BitSet(
			new long[] { 0x0000000800000002L });
	public static final BitSet FOLLOW_pointer_id_in_expr2772 = new BitSet(
			new long[] { 0x0000040000000000L });
	public static final BitSet FOLLOW_42_in_expr2774 = new BitSet(
			new long[] { 0x000000112934A440L });
	public static final BitSet FOLLOW_expr_in_expr2778 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_condition_in_expr2806 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_STRING_in_expr2834 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_disjunction_in_condition2882 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_46_in_x_con_dis2937 = new BitSet(
			new long[] { 0x000000112934A440L });
	public static final BitSet FOLLOW_expr_in_x_con_dis2941 = new BitSet(
			new long[] { 0x0000004000000000L });
	public static final BitSet FOLLOW_38_in_x_con_dis2943 = new BitSet(
			new long[] { 0x000000112834A440L });
	public static final BitSet FOLLOW_condition_in_x_con_dis2947 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_conjunction_in_disjunction2995 = new BitSet(
			new long[] { 0x0000400000000000L });
	public static final BitSet FOLLOW_y_disj_in_disjunction2997 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_x_con_dis_in_y_disj3060 = new BitSet(
			new long[] { 0x0000400000000000L });
	public static final BitSet FOLLOW_y_disj_in_y_disj3096 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_comparison_in_conjunction3156 = new BitSet(
			new long[] { 0x0004000080000000L });
	public static final BitSet FOLLOW_y_conj_in_conjunction3158 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_31_in_y_conj3220 = new BitSet(
			new long[] { 0x000000112834A440L });
	public static final BitSet FOLLOW_comparison_in_y_conj3222 = new BitSet(
			new long[] { 0x0004000080000000L });
	public static final BitSet FOLLOW_y_conj_in_y_conj3252 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_50_in_y_conj3279 = new BitSet(
			new long[] { 0x000000112834A440L });
	public static final BitSet FOLLOW_comparison_in_y_conj3281 = new BitSet(
			new long[] { 0x0004000080000000L });
	public static final BitSet FOLLOW_y_conj_in_y_conj3309 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_43_in_equality3373 = new BitSet(
			new long[] { 0x000000112834A440L });
	public static final BitSet FOLLOW_relation_in_equality3375 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_30_in_equality3403 = new BitSet(
			new long[] { 0x000000112834A440L });
	public static final BitSet FOLLOW_relation_in_equality3405 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_relation_in_comparison3460 = new BitSet(
			new long[] { 0x0000080040000002L });
	public static final BitSet FOLLOW_equality_in_comparison3464 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_sum_in_relation3511 = new BitSet(
			new long[] { 0x0000330000000000L });
	public static final BitSet FOLLOW_y_relation_in_relation3513 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_44_in_y_relation3567 = new BitSet(
			new long[] { 0x000000112834A440L });
	public static final BitSet FOLLOW_sum_in_y_relation3569 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_40_in_y_relation3598 = new BitSet(
			new long[] { 0x000000112834A440L });
	public static final BitSet FOLLOW_sum_in_y_relation3600 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_41_in_y_relation3629 = new BitSet(
			new long[] { 0x000000112834A440L });
	public static final BitSet FOLLOW_sum_in_y_relation3631 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_45_in_y_relation3660 = new BitSet(
			new long[] { 0x000000112834A440L });
	public static final BitSet FOLLOW_sum_in_y_relation3662 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_term_in_sum3721 = new BitSet(
			new long[] { 0x0000001400000000L });
	public static final BitSet FOLLOW_y_sum_in_sum3723 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_34_in_y_sum3781 = new BitSet(
			new long[] { 0x000000112834A440L });
	public static final BitSet FOLLOW_term_in_y_sum3783 = new BitSet(
			new long[] { 0x0000001400000000L });
	public static final BitSet FOLLOW_y_sum_in_y_sum3813 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_36_in_y_sum3840 = new BitSet(
			new long[] { 0x000000112834A440L });
	public static final BitSet FOLLOW_term_in_y_sum3842 = new BitSet(
			new long[] { 0x0000001400000000L });
	public static final BitSet FOLLOW_y_sum_in_y_sum3872 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_factor_in_term3929 = new BitSet(
			new long[] { 0x0000002000100000L });
	public static final BitSet FOLLOW_y_term_in_term3931 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_POINTER_in_y_term3982 = new BitSet(
			new long[] { 0x000000112834A440L });
	public static final BitSet FOLLOW_factor_in_y_term3984 = new BitSet(
			new long[] { 0x0000002000100000L });
	public static final BitSet FOLLOW_y_term_in_y_term4015 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_37_in_y_term4042 = new BitSet(
			new long[] { 0x000000112834A440L });
	public static final BitSet FOLLOW_factor_in_y_term4044 = new BitSet(
			new long[] { 0x0000002000100000L });
	public static final BitSet FOLLOW_y_term_in_y_term4074 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_29_in_factor4150 = new BitSet(
			new long[] { 0x000000112834A440L });
	public static final BitSet FOLLOW_factor_in_factor4154 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_36_in_factor4185 = new BitSet(
			new long[] { 0x000000112834A440L });
	public static final BitSet FOLLOW_factor_in_factor4189 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_primary_in_factor4220 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_num_check_in_primary4269 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_char_check_in_primary4300 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_float_check_in_primary4330 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_pointer_id_in_primary4361 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_32_in_primary4392 = new BitSet(
			new long[] { 0x000000112934A440L });
	public static final BitSet FOLLOW_expr_in_primary4394 = new BitSet(
			new long[] { 0x0000000200000000L });
	public static final BitSet FOLLOW_33_in_primary4396 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_POINTER_in_pointer_id4444 = new BitSet(
			new long[] { 0x000000010834A440L });
	public static final BitSet FOLLOW_primary_in_pointer_id4448 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_DEREFERENCE_in_pointer_id4481 = new BitSet(
			new long[] { 0x000000010834A440L });
	public static final BitSet FOLLOW_primary_in_pointer_id4485 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_READ_in_pointer_id4517 = new BitSet(
			new long[] { 0x0000000100000000L });
	public static final BitSet FOLLOW_32_in_pointer_id4577 = new BitSet(
			new long[] { 0x000000112934A440L });
	public static final BitSet FOLLOW_expr_in_pointer_id4581 = new BitSet(
			new long[] { 0x0000000A00000000L });
	public static final BitSet FOLLOW_35_in_pointer_id4616 = new BitSet(
			new long[] { 0x000000112934A440L });
	public static final BitSet FOLLOW_expr_in_pointer_id4620 = new BitSet(
			new long[] { 0x0000000A00000000L });
	public static final BitSet FOLLOW_33_in_pointer_id4656 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_WRITE_in_pointer_id4668 = new BitSet(
			new long[] { 0x0000000100000000L });
	public static final BitSet FOLLOW_32_in_pointer_id4727 = new BitSet(
			new long[] { 0x000000112934A440L });
	public static final BitSet FOLLOW_expr_in_pointer_id4731 = new BitSet(
			new long[] { 0x0000000A00000000L });
	public static final BitSet FOLLOW_35_in_pointer_id4765 = new BitSet(
			new long[] { 0x000000112934A440L });
	public static final BitSet FOLLOW_expr_in_pointer_id4769 = new BitSet(
			new long[] { 0x0000000A00000000L });
	public static final BitSet FOLLOW_33_in_pointer_id4804 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_id_check_in_pointer_id4816 = new BitSet(
			new long[] { 0x0000800100000002L });
	public static final BitSet FOLLOW_32_in_pointer_id4850 = new BitSet(
			new long[] { 0x000000132934A440L });
	public static final BitSet FOLLOW_arg_list_in_pointer_id4852 = new BitSet(
			new long[] { 0x0000000200000000L });
	public static final BitSet FOLLOW_33_in_pointer_id4854 = new BitSet(
			new long[] { 0x0000800000000002L });
	public static final BitSet FOLLOW_array_operator_in_pointer_id4907 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_ID_in_id_check4961 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_INTCONST_in_num_check5007 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_FLOATCONST_in_float_check5076 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_CHARCONST_in_char_check5129 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_39_in_synpred1_Expr565 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_VOID_in_synpred2_Expr1343 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_ELSE_in_synpred3_Expr2224 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_pointer_id_in_synpred4_Expr2751 = new BitSet(
			new long[] { 0x0000040000000000L });
	public static final BitSet FOLLOW_42_in_synpred4_Expr2753 = new BitSet(
			new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_46_in_synpred5_Expr3056 = new BitSet(
			new long[] { 0x0000000000000002L });

}