package parser.nodes;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import langaugeConstructs.TokenValues;
import parser.exceptions.*;

public class BinaryExpressionPN extends ParseNode implements Evaluable {

	Evaluable left = null;
	Evaluable right = null;
	String op = "";
	
	public BinaryExpressionPN(Evaluable left, Evaluable right, String op) {
		this.left = left;
		this.right = right;
		this.op = op;
	}

	@Override
	public String toString() {
		ArrayList<String> leftStrings = new ArrayList<String>();
		ArrayList<String> rightStrings = new ArrayList<String>();
		
		String[] lStrings = left.toString().split("\n");
		for(String i : lStrings) {
			leftStrings.add("  " + i);
		}

		String[] rStrings = right.toString().split("\n");
		for(String i : rStrings) {
			rightStrings.add("  " + i);
		}
		
		return "(" + op + "\n" + String.join("\n", leftStrings) + "\n" + String.join("\n", rightStrings) + "\n)\n";
	}

	@Override
	public void checkFunctionNameAndLength() throws Exception {
		((ParseNode)left).checkFunctionNameAndLength();
		((ParseNode)right).checkFunctionNameAndLength();
	}

	@Override
	public String type() throws Exception {
		String leftType = left.type();
		String rightType = right.type();
		
		if(!leftType.equals(rightType)) {
			throw new MismatchedTypeException("Mismatched types " + leftType + " and " + rightType + " in expression on " + lcText() + ".");
		}
		
		if(!TokenValues.opTypes.get(op).contains(leftType)) {
			throw new OperatorInvalidOnTypeException("Operator " + op + " invalid on type " + leftType + " on " + lcText() + ".");
		}
		
		return TokenValues.opReturnTypes.get(op).get(leftType);
	}

	@Override
	public void setSubParseNodeVariables(Map<String, String> superVariables) throws Exception {
		this.variables = superVariables;
		((ParseNode)left).setSubParseNodeVariables(superVariables);
		((ParseNode)right).setSubParseNodeVariables(superVariables);
	}
	
	@Override
	public void setFunctions(Map<String, FunctionDeclerationPN> functions) {
		this.functions = functions;
		((ParseNode)left).setFunctions(functions);
		((ParseNode)right).setFunctions(functions);
	}

	@Override
	public void checkTypes(String returnType) throws Exception {
		((ParseNode)left).checkTypes(returnType);
		((ParseNode)right).checkTypes(returnType);
	}

	@Override
	public void checkVariableUsedBeforeDeclared(Set<String> vars) throws Exception {
		((ParseNode)left).checkVariableUsedBeforeDeclared(vars);
		((ParseNode)right).checkVariableUsedBeforeDeclared(vars);
	}
}
