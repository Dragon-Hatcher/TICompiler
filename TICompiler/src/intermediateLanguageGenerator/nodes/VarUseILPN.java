package intermediateLanguageGenerator.nodes;

import java.util.Map;

public class VarUseILPN extends ILParseNode implements ILEvaluable {

	public String name = "";

	public VarUseILPN(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "var " + name;
	}

	@Override
	public void updateHighestUsedTemp(Map<String, Integer> uses) {
		if (isTemp(name)) {
			String type = getTempType(name);
			int num = getTempNum(name);
			uses.put(type, Math.max(uses.get(type), num + 1));
		}
	}
}
