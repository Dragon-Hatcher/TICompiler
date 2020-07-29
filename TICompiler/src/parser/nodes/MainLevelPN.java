package parser.nodes;

import java.util.ArrayList;

public class MainLevelPN extends ParseNode {

	public ArrayList<FunctionDeclerationPN> functions = new ArrayList<FunctionDeclerationPN>();
	public InstructionSequencePN main = new InstructionSequencePN();

	@Override
	public String toString() {
		ArrayList<String> functionLines = new ArrayList<String>();
		ArrayList<String> instructionLines = new ArrayList<String>();

		for(FunctionDeclerationPN f : functions) {
			String[] fLines = f.toString().split("\n");
			for(String j : fLines) {
				functionLines.add("    " + j);
			}
		}
		
		String[] iLines = main.toString().split("\n");
		for(String j : iLines) {
			instructionLines.add("  " + j);
		}
		
		return "(Main:\n" + 
		"  (Functions:\n" +
		String.join("\n", functionLines) + "\n" + 
		"  )\n" +
		String.join("\n", instructionLines) + "\n" +
		")\n";
	}

}
