package parser.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import parser.exceptions.DuplicateVariableException;

public class InstructionSequencePN extends ParseNode implements Instruction, ContainsInstructionSequence {

	public ArrayList<Instruction> instructions = new ArrayList<Instruction>();
	
	@Override
	public String toString() {
		ArrayList<String> lines = new ArrayList<String>();

		for(Instruction i : instructions) {
			String[] iLines = i.toString().split("\n");
			for(String j : iLines) {
				lines.add("  " + j);
			}
		}
		
		return "(InstructionSequence:\n" + String.join("\n", lines) + "\n)\n";
	}

	public boolean willReturn() {
		for(Instruction i : instructions) {
			if(i instanceof ReturnStatementPN) {
				return true;
			} else if (i instanceof ContainsInstructionSequence && ((ContainsInstructionSequence)i).willReturn()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasIllegalBreak() {
		for(Instruction i : instructions) {
			if(i instanceof BreakPN) {
				return true;
			} else if (i instanceof ContainsInstructionSequence && ((ContainsInstructionSequence)i).hasIllegalBreak()) {
				return true;
			}
		}
		return false;
	}

	public VariableDeclerationPN hasIllegalDeclerationType(Set<String> types) {
		for(Instruction i : instructions) {
			if(i instanceof VariableDeclerationPN && !types.contains(((VariableDeclerationPN)i).type)) {
				return (VariableDeclerationPN)i;
			} else if (i instanceof ContainsInstructionSequence) {
				VariableDeclerationPN illegalDecleration = ((ContainsInstructionSequence)i).hasIllegalDeclerationType(types);
				if(illegalDecleration != null) {
					return illegalDecleration;
				}
			}
		}
		return null;
	}

	public FunctionCallPN checkFunctionNameAndLength(Map<String, FunctionDeclerationPN> functions) {
		for(Instruction i : instructions) {
			if(i instanceof FunctionCallPN && ((FunctionCallPN)i).hasBadFunctionNameOrLength(functions)) {
				return (FunctionCallPN)i;
			} else if (i instanceof ContainsInstructionSequence) {
				FunctionCallPN illegalFunction = ((ContainsInstructionSequence)i).checkFunctionNameAndLength(functions);
				if(illegalFunction != null) {
					return illegalFunction;
				}
			} else if (i instanceof ContainsEvaluable) {
				FunctionCallPN illegalFunction = ((ContainsEvaluable)i).checkFunctionNameAndLength(functions);
				if(illegalFunction != null) {
					return illegalFunction;
				}
			}
		}
		return null;
	}
	
	public void findVariables() throws Exception {
		for(Instruction i : instructions) {
			if(i instanceof VariableDeclerationPN) {
				VariableDeclerationPN iVD = (VariableDeclerationPN)i;
				if(variables.containsKey(iVD.name)) {
					throw new DuplicateVariableException("Duplicate variable name " + iVD.name + ".");
				}
				variables.put(iVD.name, iVD.type);
			}
		}
	}

	@Override
	public void setSubParseNodeVariables(Map<String, String> superVariables) throws Exception {
		this.variables = superVariables;
		findVariables();
		for(Instruction i : instructions) {
			((ParseNode)i).setSubParseNodeVariables(this.variables);
		}
	}
	
	public void setFunctions(Map<String, FunctionDeclerationPN> functions) {
		this.functions = functions;
		for(Instruction i : instructions) {
			((ParseNode)i).setFunctions(functions);
		}	
	}

	public void checkTypes(String returnType) throws Exception {
		for(Instruction i : instructions) {
			((ParseNode)i).checkTypes(returnType);
		}
	}
}
