package parser.nodes;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import parser.exceptions.MismatchedTypeException;
import toolkit.Copy;

public class WhileLoopPN extends ParseNode implements Instruction, ContainsInstructionSequence {

	public Evaluable condition = null;
	public InstructionSequencePN instructions = new InstructionSequencePN();
	
	public WhileLoopPN(Evaluable condition, InstructionSequencePN instructions) {
		this.condition = condition;
		this.instructions = instructions;
	}

	@Override
	public String toString() {
		ArrayList<String> conditionLines = new ArrayList<String>();
		ArrayList<String> bodyLines = new ArrayList<String>();

		String[] cLines = condition.toString().split("\n");
		for (String j : cLines) {
			conditionLines.add("    " + j);
		}
		
		String[] iLines = instructions.toString().split("\n");
		for (String j : iLines) {
			bodyLines.add("    " + j);
		}

		return "(While:\n" +
		"  (Condition:\n" +
		String.join("\n", conditionLines) + "\n" + 
		"  )\n" +
		"  (Body:\n" +
		String.join("\n", bodyLines) +
		"\n  )\n" +
		")\n";
	}

	@Override
	public boolean willReturn() {
		return false;
	}

	@Override
	public void hasIllegalBreak() {
		//while loops can't have illegal breaks
	}

	@Override
	public void hasIllegalDeclerationType(Set<String> types) throws Exception {
		instructions.hasIllegalDeclerationType(types);
	}
	
	@Override
	public void checkFunctionNameAndLength() throws Exception {
		((ParseNode)condition).checkFunctionNameAndLength();
		instructions.checkFunctionNameAndLength();
	}

	@Override
	public void setSubParseNodeVariables(Map<String, String> superVariables) throws Exception {
		this.variables = superVariables;
		((ParseNode)condition).setSubParseNodeVariables(superVariables);
		instructions.setSubParseNodeVariables(Copy.deepCopyMapSS(superVariables));
	}
	
	@Override
	public void setFunctions(Map<String, FunctionDeclerationPN> functions) {
		this.functions = functions;
		((ParseNode)condition).setFunctions(functions);
		instructions.setFunctions(functions);
	}
	
	@Override
	public void checkTypes(String returnType) throws Exception {
		if(!condition.type().equals("bool")) {
			throw new MismatchedTypeException("The condition of a while statement must be of type bool. Instead it is of type " + condition.type() + " on " + ((ParseNode)condition).lcText() + ".");
		}
		
		instructions.checkTypes(returnType);
	}
	
	@Override
	public void checkVariableUsedBeforeDeclared(Set<String> vars) throws Exception {
		((ParseNode)condition).checkVariableUsedBeforeDeclared(vars);
		instructions.checkVariableUsedBeforeDeclared(vars);
	}


}
