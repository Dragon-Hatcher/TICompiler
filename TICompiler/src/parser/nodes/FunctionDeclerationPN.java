package parser.nodes;

import java.util.ArrayList;
import java.util.Map;

import parser.exceptions.DuplicateVariableException;

public class FunctionDeclerationPN extends ParseNode {

	public String name = "";
	public ArrayList<VariableDeclerationPN> parameters = new ArrayList<VariableDeclerationPN>();
	public String returnType = ""; // empty for void
	public InstructionSequencePN instructions = new InstructionSequencePN();

	@Override
	public String toString() {
		ArrayList<String> paramLines = new ArrayList<String>();
		ArrayList<String> bodyLines = new ArrayList<String>();

		for (VariableDeclerationPN p : parameters) {
			String[] pLines = p.toString().split("\n");
			for (String j : pLines) {
				paramLines.add("    " + j);
			}
		}

		String[] iLines = instructions.toString().split("\n");
		for (String j : iLines) {
			bodyLines.add("  " + j);
		}

		return "(Function:\n" + "  (Name: " + name + ")\n" + "  (Params:\n" + String.join("\n", paramLines) + "\n  )\n"
				+ String.join("\n", bodyLines) + "\n" + ")\n";
	}

	public void findVariables() throws Exception {
		for (VariableDeclerationPN i : parameters) {
			if (variables.containsKey(i.name)) {
				throw new DuplicateVariableException("Duplicate variable name " + i.name + ".");
			}
			variables.put(i.name, i.type);
		}
	}

	@Override
	public void setSubParseNodeVariables(Map<String, String> superVariables) throws Exception {
		this.variables = superVariables;
		findVariables();
		instructions.setSubParseNodeVariables(this.variables);
	}

	public void setFunctions(Map<String, FunctionDeclerationPN> functions) {
		this.functions = functions;
		((ParseNode) instructions).setFunctions(functions);
	}

	public void checkTypes(String returnType) throws Exception {
		instructions.checkTypes(returnType);
	}

}
