package parser.nodes;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import parser.exceptions.DuplicateVariableException;
import semanticAnalyzer.exceptions.IllegalTypeDeclerationException;

public class FunctionDeclerationPN extends ParseNode implements ContainsInstructionSequence {

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
				throw new DuplicateVariableException("Duplicate variable name " + i.name + " on " + lcText() + ".");
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

	@Override
	public void setFunctions(Map<String, FunctionDeclerationPN> functions) {
		this.functions = functions;
		((ParseNode) instructions).setFunctions(functions);
	}

	@Override
	public void checkTypes(String returnType) throws Exception {
		instructions.checkTypes(returnType);
	}
	
	@Override
	public void checkFunctionNameAndLength() throws Exception {
		instructions.checkFunctionNameAndLength();
	}

	@Override
	public boolean willReturn() {
		return instructions.willReturn();
	}

	@Override
	public void hasIllegalBreak() throws Exception {
		instructions.hasIllegalBreak();
	}

	@Override
	public void hasIllegalDeclerationType(Set<String> types) throws Exception {
		instructions.hasIllegalDeclerationType(types);
		for(VariableDeclerationPN v : parameters) {
			if(!types.contains(v.type)) {
				throw new IllegalTypeDeclerationException("Illegal decleration of type " + v.type + " for variable " + v.name + " on " + v.lcText() + ".");
			}
		}
	}
	
	public boolean shouldReturn() {
		return !returnType.equals("");
	}

}
