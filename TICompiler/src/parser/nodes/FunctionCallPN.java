package parser.nodes;

import java.util.ArrayList;
import java.util.Set;

public class FunctionCallPN extends ParseNode implements Evaluable, Instruction {

	public String functionName = "";
	public ArrayList<Evaluable> params = new ArrayList<Evaluable>();
	
	public String toString() {
		ArrayList<String> paramLines = new ArrayList<String>();
		
		for(Evaluable p : params) {
			String[] pLines = p.toString().split("\n");
			for(String j : pLines) {
				paramLines.add("    " + j);
			}
		}
		
		return "(Function Call:\n  (Name: " + functionName + ")\n  (Params:\n" + String.join("\n", paramLines) + "\n  )\n)\n";
	}
	
	public boolean willReturn() {
		return false;
	}

	public boolean hasIllegalBreak() {
		return false;
	}

	public String hasIllegalDeclerationType(Set<String> types) {
		return null;
	}

}
