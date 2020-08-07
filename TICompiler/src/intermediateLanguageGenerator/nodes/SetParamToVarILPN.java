package intermediateLanguageGenerator.nodes;

import java.util.ArrayList;
import java.util.Map;

public class SetParamToVarILPN extends ILParseNode {

	public String varToSetParamTo;
	public String type;
	public ArrayList<String> previousParams;
	
	public SetParamToVarILPN(ArrayList<String> previousParams, String varToSetParamTo, String type) {
		this.previousParams = previousParams;
		this.varToSetParamTo = varToSetParamTo;
		this.type = type;
	}

	@Override
	public String toString() {
		return "Set param after " + previousParams + " to " + varToSetParamTo;
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
