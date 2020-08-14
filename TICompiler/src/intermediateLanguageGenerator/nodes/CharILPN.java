package intermediateLanguageGenerator.nodes;

import langaugeConstructs.Characters;

public class CharILPN extends ILParseNode implements ILEvaluable {

	public Characters character;
	
	public CharILPN(Characters character) {
		this.character = character;
	}

	@Override
	public String toString() {
		return character.toString();
	}

}
