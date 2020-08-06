package intermediateLanguageGenerator.nodes;

import java.util.Map;

public class ReturnValueILPN extends ILParseNode {

	String varToReturn = "";

	public ReturnValueILPN(String varToReturn) {
		this.varToReturn = varToReturn;
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
