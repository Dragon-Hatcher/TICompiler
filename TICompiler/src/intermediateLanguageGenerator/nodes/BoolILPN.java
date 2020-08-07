package intermediateLanguageGenerator.nodes;

public class BoolILPN extends ILParseNode implements ILEvaluable {

	public boolean trueOrFalse;
	
	public BoolILPN(String trueOrFalse) {
		this.trueOrFalse = trueOrFalse.equals("true");
	}

	@Override
	public String toString() {
		return Boolean.toString(trueOrFalse);
	}

}
