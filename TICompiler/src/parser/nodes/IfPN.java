package parser.nodes;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import parser.exceptions.MismatchedTypeException;
import toolkit.Copy;

public class IfPN extends ParseNode implements Instruction, ContainsInstructionSequence {
	
	public Evaluable condition = null;
	public InstructionSequencePN instructions = new InstructionSequencePN();
	public InstructionSequencePN elseInstructions = null;
	
	public IfPN(Evaluable condition, InstructionSequencePN instructions, InstructionSequencePN elseInstructions) {
		this.condition = condition;
		this.instructions = instructions;
		this.elseInstructions = elseInstructions;
	}

	@Override
	public String toString() {
		ArrayList<String> conditionLines = new ArrayList<String>();
		ArrayList<String> bodyLines = new ArrayList<String>();
		ArrayList<String> elseBodyLines = new ArrayList<String>();
		
		String[] cLines = condition.toString().split("\n");
		for (String j : cLines) {
			conditionLines.add("    " + j);
		}
		
		String[] iLines = instructions.toString().split("\n");
		for (String j : iLines) {
			bodyLines.add("    " + j);
		}

		if(elseInstructions != null) {
			String[] eLines = elseInstructions.toString().split("\n");
			for (String j : eLines) {
				elseBodyLines.add("    " + j);
			}
		}
		
		return "(If:\n" +
		"  (Condition:\n" +
		String.join("\n", conditionLines) + "\n" + 
		"  )\n" +
		"  (Body:\n" +
		String.join("\n", bodyLines) +
		"\n  )\n" +
		"  (Else Body:\n" +
		String.join("\n", elseBodyLines) +
		"\n  )\n" +
		")\n";
	}

	@Override
	public boolean willReturn() {
		return elseInstructions != null && elseInstructions.willReturn() && instructions.willReturn();
	}
	
	@Override
	public void hasIllegalBreak() throws Exception {
		instructions.hasIllegalBreak();
		if (elseInstructions != null) {
			elseInstructions.hasIllegalBreak();
		}
	}

	@Override
	public void hasIllegalDeclerationType(Set<String> types) throws Exception {
		instructions.hasIllegalDeclerationType(types);
		if(elseInstructions != null) {
			elseInstructions.hasIllegalDeclerationType(types);
		}
	}

	@Override
	public void checkFunctionNameAndLength() throws Exception {
		((ParseNode)condition).checkFunctionNameAndLength();
		instructions.checkFunctionNameAndLength();
		if(elseInstructions != null) {
			elseInstructions.checkFunctionNameAndLength();
		}
	}

	@Override
	public void setSubParseNodeVariables(Map<String, String> superVariables) throws Exception {
		this.variables = superVariables;
		((ParseNode)condition).setSubParseNodeVariables(superVariables);
		instructions.setSubParseNodeVariables(Copy.deepCopyMapSS(superVariables));
		if(elseInstructions != null) {
			elseInstructions.setSubParseNodeVariables(Copy.deepCopyMapSS(superVariables));
		}
	}

	@Override
	public void setFunctions(Map<String, FunctionDeclerationPN> functions) {
		this.functions = functions;
		((ParseNode)condition).setFunctions(functions);
		((ParseNode)instructions).setFunctions(functions);
		if(elseInstructions != null) {
			((ParseNode)elseInstructions).setFunctions(functions);
		}
	}

	@Override
	public void checkTypes(String returnType) throws Exception {
		if(!condition.type().equals("bool")) {
			throw new MismatchedTypeException("The condition of an if statement must be of type bool. Instead it is of type " + condition.type() + " on " + ((ParseNode)condition).lcText() + ".");
		}
		
		instructions.checkTypes(returnType);
		if(elseInstructions != null) {
			elseInstructions.checkTypes(returnType);
		}
	}

	@Override
	public void checkVariableUsedBeforeDeclared(Set<String> vars) throws Exception {
		((ParseNode)condition).checkVariableUsedBeforeDeclared(vars);
		instructions.checkVariableUsedBeforeDeclared(vars);
		if(elseInstructions != null) {
			elseInstructions.checkVariableUsedBeforeDeclared(vars);
		}
	}
	
}
