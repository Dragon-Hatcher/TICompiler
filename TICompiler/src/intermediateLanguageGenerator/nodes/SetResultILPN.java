package intermediateLanguageGenerator.nodes;

public class SetResultILPN extends ILParseNode {

	String sete;
	ILEvaluable left;
	ILEvaluable right;
	Ops operator;
	
	public SetResultILPN(String sete, ILEvaluable left, ILEvaluable right, Ops operator) {
		this.sete = sete;
		this.left = left;
		this.right = right;
		this.operator = operator;
	}

	@Override
	public String toString() {
		return "Set " + sete + " to " + left + " " + operator.toString() + " " + right;
	}

}
