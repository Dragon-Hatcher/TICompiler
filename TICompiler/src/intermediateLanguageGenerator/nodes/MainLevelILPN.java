package intermediateLanguageGenerator.nodes;

import java.util.ArrayList;

public class MainLevelILPN extends ILParseNode {

	ArrayList<ILParseNode> instructions = new ArrayList<ILParseNode>();
	
	public MainLevelILPN() {
	}

	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder();
		for(ILParseNode pn : instructions) {
			ret.append(pn.toString() + "\n");
		}
		return ret.toString();
	}

}
