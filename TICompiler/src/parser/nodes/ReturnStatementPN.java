package parser.nodes;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import parser.exceptions.MismatchedTypeException;

public class ReturnStatementPN extends ParseNode implements Instruction {

	public Evaluable statement = null;

	public ReturnStatementPN(Evaluable statement) {
		this.statement = statement;
	}

	public ReturnStatementPN() {
	}

	@Override
	public String toString() {
		ArrayList<String> statementStrings = new ArrayList<String>();

		if (statement != null) {
			String[] sString = statement.toString().split("\n");
			for (String i : sString) {
				statementStrings.add("  " + i);
			}
		}

		return "(Return:\n" + String.join("\n", statementStrings) + "\n)\n";
	}

	@Override
	public void checkFunctionNameAndLength() throws Exception {
		if (statement != null) {
			((ParseNode) statement).checkFunctionNameAndLength();
		}
	}

	@Override
	public void setSubParseNodeVariables(Map<String, String> superVariables) throws Exception {
		this.variables = superVariables;
		if (statement != null) {
			((ParseNode) statement).setSubParseNodeVariables(superVariables);
		}
	}

	@Override
	public void setFunctions(Map<String, FunctionDeclerationPN> functions) {
		this.functions = functions;
		if (statement != null) {
			((ParseNode) statement).setFunctions(functions);
		}
	}

	@Override
	public void checkTypes(String returnType) throws Exception {
		if (statement != null) {
			if (!returnType.equals(statement.type())) {
				throw new MismatchedTypeException("Return on " + lcText() + " should return type " + returnType
						+ ". Instead it returns type " + statement.type() + ".");
			}
			((ParseNode) statement).checkTypes(returnType);
		} else if (!returnType.equals("")) {
			throw new MismatchedTypeException(
					"Return  on " + lcText() + " should return type " + returnType + ". Instead it returns type void.");
		}
	}

	@Override
	public void checkVariableUsedBeforeDeclared(Set<String> vars) throws Exception {
		if (statement != null) {
			((ParseNode) statement).checkVariableUsedBeforeDeclared(vars);
		}
	}
}
