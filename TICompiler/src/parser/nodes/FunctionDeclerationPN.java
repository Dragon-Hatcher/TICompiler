package parser.nodes;

import java.util.ArrayList;

public class FunctionDeclerationPN extends ParseNode {

	public String name = "";
	public ArrayList<VariableDeclerationPN> parameters = new ArrayList<VariableDeclerationPN>();
	public InstructionSequencePN instructions = new InstructionSequencePN();

	@Override
	public String toString() {
		ArrayList<String> paramLines = new ArrayList<String>();
		ArrayList<String> bodyLines = new ArrayList<String>();

		for(VariableDeclerationPN p : parameters) {
			String[] pLines = p.toString().split("\n");
			for(String j : pLines) {
				paramLines.add("  " + j);
			}
		}
		
		String[] iLines = instructions.toString().split("\n");
		for (String j : iLines) {
			bodyLines.add("  " + j);
		}

		return "(Function:\n" +
		"  (Name: " + name + ")\n" + 
		"  (Params:\n" +
		String.join("\n", paramLines) + 
		"  )\n" +
		String.join("\n", bodyLines) + "\n" +
		")\n";
	}

}
