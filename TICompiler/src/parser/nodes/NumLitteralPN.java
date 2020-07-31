package parser.nodes;

public class NumLitteralPN extends ParseNode implements Evaluable {

	String num = "";
	
	public NumLitteralPN (String num) {
		this.num = num;
	}
	
	@Override
	public String toString() {
		return "("+num+")";
	}

}
