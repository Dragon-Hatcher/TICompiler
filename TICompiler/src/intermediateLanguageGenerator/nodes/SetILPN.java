package intermediateLanguageGenerator.nodes;

import java.util.Map;

public class SetILPN extends ILParseNode {

	public String sete = "";
	public String type;
	public ILEvaluable seter;

	public SetILPN(String sete, ILEvaluable seter, String type) {
		this.sete = sete;
		this.seter = seter;
		this.type = type;
	}

	@Override
	public String toString() {
		return "Set " + sete + " to " + seter;
	}

	@Override
	public void updateHighestUsedTemp(Map<String, Integer> uses) {
		if (isTemp(sete)) {
			String type = getTempType(sete);
			int num = getTempNum(sete);
			uses.put(type, Math.max(uses.get(type), num + 1));
		}

		if (seter instanceof VarUseILPN) {
			VarUseILPN seterVarUse = (VarUseILPN) seter;
			if (isTemp(seterVarUse.name)) {
				String type = getTempType(seterVarUse.name);
				int num = getTempNum(seterVarUse.name);
				uses.put(type, Math.max(uses.get(type), num + 1));
			}
		}
	}

}
