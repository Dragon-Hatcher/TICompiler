package intermediateLanguageGenerator.nodes;

public class SetILPN extends ILParseNode {

	String sete = "";
	String seter = "";
	
	public SetILPN(String sete, String seter) {
		this.sete = sete;
		this.seter = seter;
	}

	@Override
	public String toString() {
		return "Set " + sete + " to " + seter;
	}

}
