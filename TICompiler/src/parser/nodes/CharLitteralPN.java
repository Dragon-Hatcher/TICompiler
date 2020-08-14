package parser.nodes;

import java.util.Map;

import langaugeConstructs.Characters;

public class CharLitteralPN extends ParseNode implements Evaluable {
	public Characters character;
	
	public CharLitteralPN(Characters characters) {
		this.character = characters;
	}
	
	@Override
	public String toString() {
		return "("+character+")";
	}

	@Override
	public String type() {
		return "char";
	}	
}
