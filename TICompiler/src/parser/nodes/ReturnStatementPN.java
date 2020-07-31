package parser.nodes;

import java.util.ArrayList;

public class ReturnStatementPN extends ParseNode implements Instruction {

	Evaluable statement = null;
	
	public ReturnStatementPN(Evaluable statement) {
		this.statement = statement;
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

}
