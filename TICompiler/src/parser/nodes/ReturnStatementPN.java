package parser.nodes;

import java.util.ArrayList;
import java.util.Set;

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

	public boolean willReturn() {
		return true;
	}

	public boolean hasIllegalBreak() {
		return false;
	}

	public String hasIllegalDeclerationType(Set<String> types) {
		return null;
	}

}
