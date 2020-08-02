package parser.nodes;

public class BooleanLitteralPN implements Evaluable {
	String trueOrFalse = "";
	
	public BooleanLitteralPN(String trueOrFalse) {
		this.trueOrFalse = trueOrFalse;
	}
	
	@Override
	public String toString() {
		return "("+trueOrFalse+")";
	}	
	
}
