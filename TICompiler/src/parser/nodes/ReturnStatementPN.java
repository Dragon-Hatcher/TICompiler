package parser.nodes;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

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
		for(String i : sString) {
			statementStrings.add("  " + i);
		}
		
		return "(Return:\n" + String.join("\n", statementStrings)  + "\n)\n";
	}

	public FunctionCallPN checkFunctionNameAndLength(Map<String, FunctionDeclerationPN> functions) {
		return null;
	}

}
