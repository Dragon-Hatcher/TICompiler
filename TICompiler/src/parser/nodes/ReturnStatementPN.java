package parser.nodes;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import parser.exceptions.MismatchedTypeException;

public class ReturnStatementPN extends ParseNode implements Instruction {

	Evaluable statement = null;

	public ReturnStatementPN(Evaluable statement) {
		this.statement = statement;
	}

	public ReturnStatementPN() {

	}

	@Override
	public String toString() {
		ArrayList<String> statementStrings = new ArrayList<String>();

		String[] sString = statement.toString().split("\n");
		for (String i : sString) {
			statementStrings.add("  " + i);
		}

		return "(Return:\n" + String.join("\n", statementStrings) + "\n)\n";
	}

	public FunctionCallPN checkFunctionNameAndLength(Map<String, FunctionDeclerationPN> functions) {
		return null;
	}

	public void setSubParseNodeVariables(Map<String, String> superVariables) throws Exception {
		this.variables = superVariables;
		if (statement != null) {
			((ParseNode) statement).setSubParseNodeVariables(superVariables);
		}
	}

	public void setFunctions(Map<String, FunctionDeclerationPN> functions) {
		this.functions = functions;
	}

	public void checkTypes(String returnType) throws Exception {
		if (statement != null) {
			if (!returnType.equals(statement.type())) {
				throw new MismatchedTypeException("Return should return type " + returnType
						+ ". Instead it returns type " + statement.type() + ".");
			}
			((ParseNode) statement).checkTypes(returnType);
		}
	}

}
