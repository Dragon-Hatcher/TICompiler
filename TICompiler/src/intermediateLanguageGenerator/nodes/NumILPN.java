package intermediateLanguageGenerator.nodes;

public class NumILPN extends ILParseNode implements ILEvaluable {

	public String num = "";
	
	public NumILPN(String num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return num;
	}

}
