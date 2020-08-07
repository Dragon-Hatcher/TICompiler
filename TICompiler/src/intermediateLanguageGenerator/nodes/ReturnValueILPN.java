package intermediateLanguageGenerator.nodes;

import java.util.Map;

public class ReturnValueILPN extends ILParseNode {

	public String varToReturn = "";
	public String type;

	public ReturnValueILPN(String varToReturn, String type) {
		this.varToReturn = varToReturn;
		this.type = type;
	}

	@Override
	public String toString() {
		return "Return var " + varToReturn;
	}

	@Override
	public void updateHighestUsedTemp(Map<String, Integer> uses) {
		if (isTemp(varToReturn)) {
			String type = getTempType(varToReturn);
			int num = getTempNum(varToReturn);
			uses.put(type, Math.max(uses.get(type), num + 1));
		}
	}
}
