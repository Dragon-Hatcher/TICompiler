package intermediateLanguageGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import intermediateLanguageGenerator.nodes.*;
import parser.nodes.*;

public class IntermediateLanguageGenerator {

	MainLevelPN mainPN = new MainLevelPN();
	MainLevelILPN main = new MainLevelILPN();

	private int uniqueNameCount = 0;

	public MainLevelILPN generatorIL(MainLevelPN mainLevelPN) throws Exception {
		mainPN = mainLevelPN;

		Map<String, Integer> typeTemps = new HashMap<String, Integer>();
		typeTemps.put("int", 0);
		typeTemps.put("float", 0);
		typeTemps.put("bool", 0);
		typeTemps.put("char", 0);
		main.addAll(parseInstructionSequence(mainPN.main, typeTemps));

		System.out.println(main);
		return main;
	}

	private ArrayList<ILParseNode> parseInstructionSequence(InstructionSequencePN iS, Map<String, Integer> typeTemps)
			throws Exception {
		ArrayList<ILParseNode> ilNodes = new ArrayList<ILParseNode>();

		ilNodes.add(new OpenScopeILPN());

		for (Instruction i : iS.instructions) {
			if (i instanceof VariableDeclerationPN) {
				VariableDeclerationPN iVD = ((VariableDeclerationPN) i);
				ilNodes.add(new CreateVariableILPN(iVD.name, iVD.type));
			}
		}

		for (Instruction i : iS.instructions) {
			if (i instanceof AssignmentPN) {
				AssignmentPN iA = ((AssignmentPN) i);
				String varType = iA.getVarType();
				ArrayList<ILParseNode> toAssignInstructions = parseExpression(iA.toAssign, typeTemps.get(varType),
						typeTemps);
				ilNodes.addAll(toAssignInstructions);
				VarUseILPN result = new VarUseILPN(tempName(typeTemps, varType));
				ilNodes.add(new SetILPN(iA.varName, result));
			} else if (i instanceof FunctionCallPN) {
				if (((FunctionCallPN) i).type().equals("")) {
					ilNodes.addAll(functionCallInstructions((Evaluable) i, 0, typeTemps));
				} else {
					ilNodes.addAll(functionCallInstructions((Evaluable) i, typeTemps.get(((FunctionCallPN) i).type()),
							typeTemps));
				}
			} else if (i instanceof IfPN) {
				IfPN iIf = (IfPN) i;
				int name = getUniqueNameNum();

				ilNodes.addAll(parseExpression(iIf.condition, typeTemps.get("bool"), typeTemps));
				ilNodes.add(new GotoILPN("then_" + name, tempName(typeTemps.get("bool"), "bool")));
				if (iIf.elseInstructions != null) {
					ilNodes.add(new GotoILPN("else_" + name));
				}
				ilNodes.add(new LabelILPN("then_" + name));
				ilNodes.addAll(parseInstructionSequence(iIf.instructions, typeTemps));
				ilNodes.add(new GotoILPN("endif_" + name));
				if (iIf.elseInstructions != null) {
					ilNodes.add(new LabelILPN("else_" + name));
					ilNodes.addAll(parseInstructionSequence(iIf.elseInstructions, typeTemps));
				}
				ilNodes.add(new LabelILPN("endif_" + name));
			}
		}

		ilNodes.add(new CloseScopeILPN());

		return ilNodes;
	}

	private ArrayList<ILParseNode> parseExpression(Evaluable evaluable, int tempToPutResult,
			Map<String, Integer> typeTemps) throws Exception {
		ArrayList<ILParseNode> ret = new ArrayList<ILParseNode>();
		if (evaluable instanceof NumLitteralPN) {
			NumILPN num = new NumILPN(((NumLitteralPN) evaluable).num);
			ret.add(new SetILPN("$_temp" + tempToPutResult + "_" + ((NumLitteralPN) evaluable).type(), num));
			return ret;
		} else if(evaluable instanceof BooleanLitteralPN) {
			BoolILPN bool = new BoolILPN(((BooleanLitteralPN) evaluable).trueOrFalse);
			ret.add(new SetILPN("$_temp" + tempToPutResult + "_bool", bool));
			return ret;
		} else if (evaluable instanceof VariableUsePN) {
			VarUseILPN var = new VarUseILPN(((VariableUsePN) evaluable).name);
			ret.add(new SetILPN("$_temp" + tempToPutResult + "_" + ((VariableUsePN) evaluable).type(), var));
			return ret;
		} else if (evaluable instanceof BinaryExpressionPN) {
			BinaryExpressionPN eBinary = ((BinaryExpressionPN) evaluable);

			String operandsType = eBinary.left.type();
			String resultType = eBinary.type();

			typeTemps.put(operandsType, typeTemps.get(operandsType) + 1);
			ret.addAll(parseExpression(eBinary.left, typeTemps.get(operandsType), typeTemps));
			VarUseILPN leftVar = new VarUseILPN(tempName(typeTemps, operandsType));

			typeTemps.put(operandsType, typeTemps.get(operandsType) + 1);
			VarUseILPN rightVar = new VarUseILPN(tempName(typeTemps, operandsType));
			ret.addAll(parseExpression(eBinary.right, typeTemps.get(operandsType), typeTemps));

			ret.add(new SetResultILPN(tempName(tempToPutResult, resultType), leftVar, rightVar,
					Ops.fromPNOp(eBinary.op)));

			typeTemps.put(operandsType, typeTemps.get(operandsType) - 2);

			return ret;
		} else if (evaluable instanceof FunctionCallPN) {
			ret.addAll(functionCallInstructions(evaluable, tempToPutResult, typeTemps));
			return ret;
		} else {
			throw new Exception("Unimplemented expression parse in IntermediateLanguageGeneration");
		}
	}

	private ArrayList<ILParseNode> functionCallInstructions(Evaluable evaluable, int tempToPutResult,
			Map<String, Integer> typeTemps) throws Exception {
		FunctionCallPN eFC = ((FunctionCallPN) evaluable);
		String returnType = eFC.type();

		ArrayList<ILParseNode> ret = new ArrayList<ILParseNode>();

		ret.add(new SetReturnVarToILPN(tempName(tempToPutResult, returnType)));
		int paramNumber = 0;
		for (Evaluable e : eFC.params) {
			typeTemps.put(e.type(), typeTemps.get(e.type()) + 1);
			ret.addAll(parseExpression(e, typeTemps.get(e.type()), typeTemps));
			ret.add(new SetParamToVarILPN(paramNumber++, tempName(typeTemps, e.type())));
			typeTemps.put(e.type(), typeTemps.get(e.type()) - 1);
		}

		ret.add(new CallILPN(eFC.functionName));

		return ret;
	}

	public int getUniqueNameNum() {
		return uniqueNameCount++;
	}

	private String tempName(Map<String, Integer> typeTemps, String type) {
		return "$_temp" + typeTemps.get(type) + "_" + type;
	}

	private String tempName(int tempNum, String type) {
		return "$_temp" + tempNum + "_" + type;
	}

}
