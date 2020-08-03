package parser.nodes;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import parser.exceptions.MismatchedTypeException;
import toolkit.Copy;

public class IfPN extends ParseNode implements Instruction, ContainsInstructionSequence, ContainsEvaluable {
	
	Evaluable condition = null;
	InstructionSequencePN instructions = new InstructionSequencePN();
	InstructionSequencePN elseInstructions = null;
	
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

	public boolean willReturn() {
		return elseInstructions != null && elseInstructions.willReturn() && instructions.willReturn();
	}
	
	public boolean hasIllegalBreak() {
		if (elseInstructions != null) {
			return instructions.hasIllegalBreak() || elseInstructions.hasIllegalBreak();
		} else {
			return instructions.hasIllegalBreak();			
		}
	}

	public VariableDeclerationPN hasIllegalDeclerationType(Set<String> types) {
		VariableDeclerationPN ifBody = instructions.hasIllegalDeclerationType(types);
		if(ifBody != null) {
			return ifBody;
		}
		
		if(elseInstructions != null) {
			VariableDeclerationPN elseBody = elseInstructions.hasIllegalDeclerationType(types);
			return elseBody;
		} else {
			return null;
		}

	}

	@Override
	public FunctionCallPN checkFunctionNameAndLength(Map<String, FunctionDeclerationPN> functions) {
		FunctionCallPN condFC = null;
		if(condition instanceof ContainsEvaluable) {
			condFC = ((ContainsEvaluable)condition).checkFunctionNameAndLength(functions);
		}
		FunctionCallPN ifFC = instructions.checkFunctionNameAndLength(functions);
		if(condFC != null) {
			return condFC;
		} else if(ifFC != null) {
			return ifFC;
		}
		
		if(elseInstructions != null) {
			FunctionCallPN elseFC = elseInstructions.checkFunctionNameAndLength(functions);
			return elseFC;
		} else {
			return null;
		}
	}

	@Override
	public void setSubParseNodeVariables(Map<String, String> superVariables) throws Exception {
		this.variables = superVariables;
		((ParseNode)condition).setSubParseNodeVariables(superVariables);
		instructions.setSubParseNodeVariables(Copy.deepCopyMap(superVariables));
		if(elseInstructions != null) {
			elseInstructions.setSubParseNodeVariables(Copy.deepCopyMap(superVariables));
		}
	}

	public void setFunctions(Map<String, FunctionDeclerationPN> functions) {
		this.functions = functions;
		((ParseNode)condition).setFunctions(functions);
		((ParseNode)instructions).setFunctions(functions);
		if(elseInstructions != null) {
			((ParseNode)elseInstructions).setFunctions(functions);
		}
	}

	public void checkTypes(String returnType) throws Exception {
		if(!condition.type().equals("bool")) {
			throw new MismatchedTypeException("The condition of an if statement must be of type bool. Instead it is of type " + condition.type() + ".");
		}
		
		instructions.checkTypes(returnType);
		if(elseInstructions != null) {
			elseInstructions.checkTypes(returnType);
		}
	}
	
}
