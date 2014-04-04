/**
 * 
 * @file OperatorNode.java
 * @description This file contains the operator node of the AST.
 * @author Chris Vesters
 * @date 22/2/13
 * 
 **/

package aofC.Cparser.ast;

import aofC.Cparser.compiler.CodeGenerator;
import aofC.Cparser.symbolTable.PointerType;
import aofC.Cparser.symbolTable.PrimaryType;


/**
 * 
 * This class represents an operator node in an AST.
 * 
 **/
public class OperatorNode extends TypedNode {

	private static final long serialVersionUID = -8637042212421920802L;
	
	private int labelID;

	/**
	 * 
	 * The supported operators:
	 * 
	 * ADD: + SUB: - MULT: * DIV: / MOD: % NOT: ! NEG: - AND: && OR: || TIF: ? :
	 * EQ: == NEQ: != GT: > LT: < GEQ: >= LEQ: <= ADDR: & PNTR: *
	 * 
	 * 
	 **/
	public enum Operator {
		ADD, SUB, MULT, DIV, MOD, NOT, NEG, AND, OR, TIF, EQ, NEQ, GT, LT, GEQ, LEQ, ADDR, PNTR
	}

	/**
	 * 
	 * The operator of the node.
	 * 
	 **/
	private Operator operator;

	/**
	 * 
	 * Constructor
	 * 
	 * @param op
	 *            The type of operator.
	 * 
	 **/
	public OperatorNode(Operator op) {
		this.operator = op;
		this.labelID = 0;
	}
	
	public OperatorNode(Operator op, int labelID){
		this.operator = op;
		this.labelID = labelID;
	}

	/**
	 * 
	 * This method will return the operator of the node.
	 * 
	 * @return The operator of the node.
	 * 
	 **/
	public Operator getOperator() {
		return operator;
	}

	/**
	 * 
	 * This method returns the name of the node. This will be used for pretty
	 * printing!
	 * 
	 * @return The name of the node.
	 * 
	 **/
	@Override
	protected String getName() {
		return this.getOperator().toString();
	}

	@Override
	/**
	 * If this is called as an lCode, we must still execute the 'real code' due to possible function
	 * calls with side effects. However, the result would clutter the stack, so we need to flush it
	 * 'somewhere'
	 */
	public void lCode(){
		if(this.operator != Operator.PNTR){
			this.toCode();
			CodeGenerator.insertCode("sro " + this.type.getChar() + " 0" );
		}
		else{
			// The pointer is used as an l-value, so we need to do something else
			TypedNode child = ((TypedNode) this.getChildren()[0]);
			child.rCode();
			this.type = ((PointerType) child.type).refType;
			// Loaded the value that was 'inside' on the stack
		}
	}
	
	@Override
	public void toCode() {
		// Generate code for the children
		if (this.operator.equals(Operator.ADDR)) {
			// No rCode evaluation needed
		} else {
			for (Node child : this.getChildren()) {
				// Child MUST be an AST node in this case, otherwise the AST is
				// corrupt
				assert child instanceof ASTNode;
				((ASTNode) child).rCode();
			}
		}

		String code = "";

		// Set the command
		switch (this.operator) {
		case ADD:
			code += "add";
			break;
		case SUB:
			code += "sub";
			break;
		case MULT:
			code += "mul";
			break;
		case DIV:
			code += "div";
			break;
		case MOD:
			CodeGenerator.insertCode(code);
			break;
		case NOT:
			code += "not";
			break;
		case NEG:
			code += "neg";
			break;
		case AND:
			code += "and";
			break;
		case OR:
			code += "or";
			break;
		case TIF:
			// Special...
			ASTNode cond = ((ASTNode) this.getChildren()[0]);
			ASTNode first = ((ASTNode) this.getChildren()[1]);
			ASTNode second = ((ASTNode) this.getChildren()[2]);
			// Actual code generation
			cond.rCode();
			CodeGenerator.insertCode("fjp l_tif_" + this.labelID);
			// Condition was True
			first.rCode();
			CodeGenerator.insertCode("ujp l_tif_end_" + this.labelID);
			// Condition was False
			CodeGenerator.insertLabel("l_tif_" + this.labelID);
			second.rCode();
			CodeGenerator.insertLabel("l_tif_end_" + this.labelID);
			break;
		case EQ:
			code += "equ";
			break;
		case NEQ:
			code += "neq";
			break;
		case GT:
			code += "grt";
			break;
		case LT:
			code += "les";
			break;
		case GEQ:
			code += "geq";
			break;
		case LEQ:
			code += "leq";
			break;
		case ADDR:
			// This just means that we have to fetch the address of the
			// node below, so just call the lCode function, which does exactly
			// that
			((ASTNode) this.getChildren()[0]).lCode();
			break;
		case PNTR:
			code += "ind";
			break;
		default:
			break;
		}

		// Set the type if necessary
		switch (this.operator) {
		case ADD:
		case SUB:
		case MULT:
		case DIV:
		case MOD:
		case NEG:
			this.type = ((TypedNode) this.getChildren()[0]).type;
			code += " " + this.type.getChar();
			break;
		case EQ:
		case NEQ:
		case GT:
		case LT:
		case GEQ:
		case LEQ:
			this.type = new PrimaryType(PrimaryType.PType.BOOL);
			code += " " + ((TypedNode) this.getChildren()[0]).getType();
			break;
		case ADDR:
			this.type = new PointerType(((TypedNode) this.getChildren()[0]).type);
			return;
		case TIF:
			this.type = ((TypedNode) this.getChildren()[0]).type;
			break;
		case PNTR:
			this.type = ((PointerType) ((TypedNode) this.getChildren()[0]).type).refType;
			code += " " + this.type.getChar();
			break;
		case NOT:
		case AND:
		case OR:
		default:
			// No type needed, just continue
			this.type = new PrimaryType(PrimaryType.PType.BOOL);
			break;
		}

		CodeGenerator.insertCode(code);
	}

	public int getEP() {
		// Dependent on the operator
		ASTNode left = (ASTNode) this.getChildren()[0];
		ASTNode right = null;
		switch (this.operator) {
		case ADD:
		case SUB:
		case MULT:
		case DIV:
		case MOD:
		case NEG:
		case EQ:
		case NEQ:
		case GT:
		case LT:
		case GEQ:
		case LEQ:
		case AND:
		case OR:
			right = (ASTNode) this.getChildren()[1];
			return Math.max(left.getEP(), right.getEP() + 1);
		case NOT:
		case ADDR:
		case PNTR:
			return left.getEP();
		case TIF:
			ASTNode middle = (ASTNode) this.getChildren()[1];
			right = (ASTNode) this.getChildren()[2];
			return Math.max(left.getEP(),
					Math.max(middle.getEP(), right.getEP()));
		default:
			return 0;
		}
	}
	
	@Override
	public String toString() {
		String str = new String();
		
		if (this.getChildren().length > 1) {
			// Operator is in between, print the first one!
			str += this.getChildren()[0].toString();
		}
		
		switch (this.operator) {
		// Unary operators
		case ADDR:
			str += "&";
			break;
		case PNTR:
			str += "*";
			break;
		case NOT:
			str += "!";
			break;
		case NEG:
			str += "-";
			break;
		
		// Binary operators
		case ADD:
			str += " + ";
			break;
		case SUB:
			str += " - ";
			break;
		case MULT:
			str += " * ";
			break;
		case DIV:
			str += " / ";
			break;
		case MOD:
			str += " % ";
			break;
		case EQ:
			str += " == ";
			break;
		case NEQ:
			str += " != ";
			break;
		case GT:
			str += " > ";
			break;
		case LT:
			str += " < ";
			break;
		case GEQ:
			str += " >= ";
			break;
		case LEQ:
			str += " <= ";
			break;
		case AND:
			str += " && ";
			break;
		case OR:
			str += " || ";
			break;
		
		// Ternary
		case TIF:
			str += " ? ";
			str += this.getChildren()[1].toString();
			str += " : ";
			break;
		}
		
		if (this.getChildren().length == 3) {
			str += this.getChildren()[2].toString();
		} else if (this.getChildren().length == 2) {
			str += this.getChildren()[1].toString();
		} else {
			str += this.getChildren()[0].toString();
		}
	
		return str;
	}
}