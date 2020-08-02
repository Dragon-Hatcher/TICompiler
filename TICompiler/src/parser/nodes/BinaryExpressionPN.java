package parser.nodes;

import java.util.ArrayList;
import java.util.Map;

public class BinaryExpressionPN extends ParseNode implements Evaluable, ContainsEvaluable {

	Evaluable left = null;
	Evaluable right = null;
	String op = "";
	
	public BinaryExpressionPN(Evaluable left, Evaluable right, String op) {
		this.left = left;
		this.right = right;
		this.op = op;
	}

	@Override
	public String toString() {
		ArrayList<String> leftStrings = new ArrayList<String>();
		ArrayList<String> rightStrings = new ArrayList<String>();
		
		String[] lStrings = left.toString().split("\n");
		for(String i : lStrings) {
			leftStrings.add("  " + i);
		}

		String[] rStrings = right.toString().split("\n");
		for(String i : rStrings) {
			rightStrings.add("  " + i);
		}
		
		return "(" + op + "\n" + String.join("\n", leftStrings) + "\n" + String.join("\n", rightStrings) + "\n)\n";
	}

	public FunctionCallPN checkFunctionNameAndLength(Map<String, FunctionDeclerationPN> functions) {
		FunctionCallPN leftFC = null;
		FunctionCallPN rightFC = null;
		
		if(left instanceof ContainsEvaluable) {
			leftFC = ((ContainsEvaluable)left).checkFunctionNameAndLength(functions);
		}
		if(right instanceof ContainsEvaluable) {
			rightFC = ((ContainsEvaluable)right).checkFunctionNameAndLength(functions);
		}		
		
		if(leftFC != null) {
			return leftFC;
		} else {
			return rightFC;
		}
	}

}
