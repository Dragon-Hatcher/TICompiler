package intermediateLanguageGenerator.nodes;

import java.util.Map;

public class SetParamToVarILPN extends ILParseNode {

	int paramNumber;
	String varToSetParamTo;

	public SetParamToVarILPN(int paramNumber, String varToSetParamTo) {
		this.paramNumber = paramNumber;
		this.varToSetParamTo = varToSetParamTo;
	}

	@Override
	public String toString() {
		return "Set param " + paramNumber + " to " + varToSetParamTo;
	}

	@Override
	public void updateHighestUsedTemp(Map<String, Integer> uses) {
		if (isTemp(varToSetParamTo)) {
			String type = getTempType(varToSetParamTo);
			int num = getTempNum(varToSetParamTo);
			uses.put(type, Math.max(uses.get(type), num + 1));
		}
	}

}
