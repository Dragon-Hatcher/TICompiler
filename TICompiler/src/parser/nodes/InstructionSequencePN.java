package parser.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import parser.exceptions.DuplicateVariableException;
import semanticAnalyzer.exceptions.IllegalBreakException;
import semanticAnalyzer.exceptions.IllegalTypeDeclerationException;
import toolkit.Copy;

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

	@Override
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
	
	@Override
	public void hasIllegalBreak() throws Exception {
		for(Instruction i : instructions) {
			if(i instanceof BreakPN) {
				throw new IllegalBreakException("Illegal break on " + ((BreakPN)i).lcText() + ".");
			} else if (i instanceof ContainsInstructionSequence) {
				((ContainsInstructionSequence)i).hasIllegalBreak();
			}
		}
	}
	
	@Override
	public void hasIllegalDeclerationType(Set<String> types) throws Exception {
		for(Instruction i : instructions) {
			if(i instanceof VariableDeclerationPN && !types.contains(((VariableDeclerationPN)i).type)) {
				throw new IllegalTypeDeclerationException("Illegal decleration of type " + ((VariableDeclerationPN)i).type + " for variable " + ((VariableDeclerationPN)i).name + " on " + ((VariableDeclerationPN)i).lcText() + ".");
			} else if (i instanceof ContainsInstructionSequence) {
				((ContainsInstructionSequence)i).hasIllegalDeclerationType(types);
			}
		}
	}

	@Override
	public void checkFunctionNameAndLength() throws Exception {
		for(Instruction i : instructions) {
			((ParseNode)i).checkFunctionNameAndLength();
		}	
	}
	
	public void findVariables() throws Exception {
		for(Instruction i : instructions) {
			if(i instanceof VariableDeclerationPN) {
				VariableDeclerationPN iVD = (VariableDeclerationPN)i;
				if(variables.containsKey(iVD.name)) {
					throw new DuplicateVariableException("Duplicate variable name " + iVD.name + " on " + lcText() + ".");
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
	
	@Override
	public void setFunctions(Map<String, FunctionDeclerationPN> functions) {
		this.functions = functions;
		for(Instruction i : instructions) {
			((ParseNode)i).setFunctions(functions);
		}	
	}

	@Override
	public void checkTypes(String returnType) throws Exception {
		for(Instruction i : instructions) {
			((ParseNode)i).checkTypes(returnType);
		}
	}
	
	@Override
	public void checkVariableUsedBeforeDeclared(Set<String> vars) throws Exception {
		Set<String> newVars = Copy.deepCopySet(vars);
		for(Instruction i : instructions) {
			((ParseNode)i).checkVariableUsedBeforeDeclared(newVars);
			if(i instanceof VariableDeclerationPN) {
				newVars.add(((VariableDeclerationPN)i).name);
			}
		}
	}

}
