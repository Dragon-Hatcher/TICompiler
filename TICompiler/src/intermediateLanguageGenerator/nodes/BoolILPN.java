package intermediateLanguageGenerator.nodes;

public class BoolILPN extends ILParseNode implements ILEvaluable {

	public boolean trueOrFalse;
	
	public BoolILPN(String trueOrFalse) {
		this.trueOrFalse = Boolean.getBoolean(trueOrFalse);
	}

	@Override
	public String toString() {
		return Boolean.toString(trueOrFalse);
	}

}
