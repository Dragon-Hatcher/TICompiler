package intermediateLanguageGenerator.nodes;

import java.util.Map;

public class SetResultILPN extends ILParseNode {

	String sete;
	ILEvaluable left;
	ILEvaluable right;
	Ops operator;

	public SetResultILPN(String sete, ILEvaluable left, ILEvaluable right, Ops operator) {
		this.sete = sete;
		this.left = left;
		this.right = right;
		this.operator = operator;
	}

	@Override
	public String toString() {
		return "Set " + sete + " to " + left + " " + operator.toString() + " " + right;
	}

	@Override
	public void updateHighestUsedTemp(Map<String, Integer> uses) {
		if (isTemp(sete)) {
			String type = getTempType(sete);
			int num = getTempNum(sete);
			uses.put(type, Math.max(uses.get(type), num + 1));
		}

		if (left instanceof VarUseILPN) {
			VarUseILPN seterVarUse = (VarUseILPN) left;
			if (isTemp(seterVarUse.name)) {
				String type = getTempType(seterVarUse.name);
				int num = getTempNum(seterVarUse.name);
				uses.put(type, Math.max(uses.get(type), num + 1));
			}
		}

		if (right instanceof VarUseILPN) {
			VarUseILPN seterVarUse = (VarUseILPN) right;
			if (isTemp(seterVarUse.name)) {
				String type = getTempType(seterVarUse.name);
				int num = getTempNum(seterVarUse.name);
				uses.put(type, Math.max(uses.get(type), num + 1));
			}
		}
	}

}
