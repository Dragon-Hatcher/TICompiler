package intermediateLanguageGenerator.nodes;

import java.util.Map;

public class SetResultILPN extends ILParseNode {

	public String sete;
	public ILEvaluable left;
	public ILEvaluable right;
	public String lrTypes;
	public Ops operator;

	public SetResultILPN(String sete, ILEvaluable left, ILEvaluable right, Ops operator, String lrTypes) {
		this.sete = sete;
		this.left = left;
		this.right = right;
		this.operator = operator;
		this.lrTypes = lrTypes;
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
