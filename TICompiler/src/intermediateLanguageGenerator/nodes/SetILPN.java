package intermediateLanguageGenerator.nodes;

public class SetILPN extends ILParseNode {

	String sete = "";
	ILEvaluable seter;
	
	public SetILPN(String sete, ILEvaluable seter) {
		this.sete = sete;
		this.seter = seter;
	}

	@Override
	public String toString() {
		return "Set " + sete + " to " + seter;
	}

}
