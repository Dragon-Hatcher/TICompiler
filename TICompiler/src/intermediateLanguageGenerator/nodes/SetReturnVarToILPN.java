package intermediateLanguageGenerator.nodes;

import java.util.Map;

public class SetReturnVarToILPN extends ILParseNode {

	String returnVar = "";
	
	public SetReturnVarToILPN(String returnVar) {
		this.returnVar = returnVar;
	}

	@Override
	public String toString() {
		return "Set return var to " + returnVar;
	}

	public void updateHighestUsedTemp(Map<String, Integer> uses) {		
		if (isTemp(returnVar)) {
			String type = getTempType(returnVar);
			int num = getTempNum(returnVar);
			uses.put(type, Math.max(uses.get(type), num + 1));
		}
	}
}
