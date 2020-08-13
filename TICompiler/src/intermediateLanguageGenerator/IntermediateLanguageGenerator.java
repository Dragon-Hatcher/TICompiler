package intermediateLanguageGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import intermediateLanguageGenerator.nodes.*;
import parser.nodes.*;
import toolkit.Copy;

public class IntermediateLanguageGenerator {

	MainLevelPN mainPN = new MainLevelPN();
	MainLevelILPN main = new MainLevelILPN();

	ArrayList<String> functionNames = new ArrayList<String>();
	
	private int uniqueNameCount = 0;
	private int functionNameCount = 1;

	public MainLevelILPN generatorIL(MainLevelPN mainLevelPN) throws Exception {
		mainPN = mainLevelPN;

		for (FunctionDeclerationPN func : mainPN.functions) {
			functionNames.add(func.name);
		}
		
		Map<String, Integer> typeTemps = new HashMap<String, Integer>();
		typeTemps.put("int", 0);
		typeTemps.put("float", 0);
		typeTemps.put("bool", 0);
		typeTemps.put("char", 0);
		main.add(new LabelILPN("func_start_0"));
		main.add(new OpenAreaILPN());
		main.addAll(parseInstructionSequence(mainPN.main, typeTemps, -1, -1, 0, 1));
		main.add(new LabelILPN("func_end_0"));
		main.add(new CloseAreaILPN());

		for (FunctionDeclerationPN func : mainPN.functions) {
			main.addAll(parseFunction(func, typeTemps));
		}

		int start = 0;
		int end = 0;
		int func = 0;
		Map<String, Integer> superUses = new HashMap<String, Integer>();
		superUses.put("int", 0);
		superUses.put("float", 0);
		superUses.put("bool", 0);
		superUses.put("char", 0);
		for (int i = 0; i < main.instructions.size(); i++) {
			if (main.instructions.get(i) instanceof LabelILPN) {
				LabelILPN iL = (LabelILPN) main.instructions.get(i);
				if (iL.label.startsWith("func_start_" + func)) {
					start = i;
				} else if (iL.label.startsWith("func_end_" + func)) {
					end = i;
					Map<String, Integer> temps = findTemps(main.instructions, start, end, superUses);
					func++;
				}
			}
		}

		return main;
	}

	private ArrayList<ILParseNode> parseFunction(FunctionDeclerationPN func, Map<String, Integer> typeTemps)
			throws Exception {
		int name = getUniqueFuncNum();

		ArrayList<ILParseNode> ilNodes = new ArrayList<ILParseNode>();
		ilNodes.add(new LabelILPN("func_start_" + name));
		ilNodes.add(new OpenAreaILPN());
		ilNodes.addAll(parseInstructionSequence(func.instructions, typeTemps, -1, -1, name, 1));

		ArrayList<ILParseNode> params = new ArrayList<ILParseNode>();
		params.add(new CreateVariableILPN("s_return_adress", "$pointer"));
		for (VariableDeclerationPN param : func.parameters) {
			params.add(new CreateVariableILPN(param.name, param.type));
		}
		ilNodes.addAll(3, params);
		ilNodes.add(new LabelILPN("func_end_" + name));
		ilNodes.add(new CloseAreaILPN());

		return ilNodes;
	}

	private ArrayList<ILParseNode> parseInstructionSequence(InstructionSequencePN iS, Map<String, Integer> typeTemps,
			int whileLoopName, int layersSinceWhile, int funcName, int layersSinceFunc) throws Exception {
		ArrayList<ILParseNode> ilNodes = new ArrayList<ILParseNode>();

		ilNodes.add(new OpenScopeILPN());

		for (Instruction i : iS.instructions) {
			if (i instanceof VariableDeclerationPN) {
				VariableDeclerationPN iVD = ((VariableDeclerationPN) i);
				ilNodes.add(new CreateVariableILPN(iVD.name, iVD.type));
			}
		}

		for (Instruction i : iS.instructions) {
			if (i instanceof VariableDeclerationPN) {
			} else if (i instanceof AssignmentPN) {
				AssignmentPN iA = ((AssignmentPN) i);
				String varType = iA.getVarType();
				ArrayList<ILParseNode> toAssignInstructions = parseExpression(iA.toAssign, typeTemps.get(varType),
						typeTemps);
				ilNodes.addAll(toAssignInstructions);
				VarUseILPN result = new VarUseILPN(tempName(typeTemps, varType));
				ilNodes.add(new SetILPN(iA.varName, result, iA.toAssign.type()));
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
				} else {
					ilNodes.add(new GotoILPN("endif_" + name));					
				}
				ilNodes.add(new LabelILPN("then_" + name));
				ilNodes.addAll(parseInstructionSequence(iIf.instructions, typeTemps, whileLoopName,
						layersSinceWhile + 1, funcName, layersSinceFunc + 1));
				ilNodes.add(new GotoILPN("endif_" + name));
				if (iIf.elseInstructions != null) {
					ilNodes.add(new LabelILPN("else_" + name));
					ilNodes.addAll(parseInstructionSequence(iIf.elseInstructions, typeTemps, whileLoopName,
							layersSinceWhile + 1, funcName, layersSinceFunc + 1));
				}
				ilNodes.add(new LabelILPN("endif_" + name));
			} else if (i instanceof WhileLoopPN) {
				WhileLoopPN iWL = (WhileLoopPN) i;
				int name = getUniqueNameNum();

				ilNodes.add(new LabelILPN("condition_" + name));
				ilNodes.addAll(parseExpression(iWL.condition, typeTemps.get("bool"), typeTemps));
				ilNodes.add(new GotoILPN("while_" + name, tempName(typeTemps.get("bool"), "bool")));
				ilNodes.add(new GotoILPN("endwhile_" + name));

				ilNodes.add(new LabelILPN("while_" + name));
				ilNodes.addAll(
						parseInstructionSequence(iWL.instructions, typeTemps, name, 1, funcName, layersSinceFunc + 1));
				ilNodes.add(new GotoILPN("condition_" + name));

				ilNodes.add(new LabelILPN("endwhile_" + name));
			} else if (i instanceof BreakPN) {
				ilNodes.add(new CallCloseScopeILPN(layersSinceWhile));
				ilNodes.add(new GotoILPN("endwhile_" + whileLoopName));
			} else if (i instanceof ReturnStatementPN) {
				ReturnStatementPN iR = (ReturnStatementPN) i;

				if (iR.statement != null) {
					ilNodes.addAll(parseExpression(iR.statement, typeTemps.get(iR.statement.type()), typeTemps));
					ilNodes.add(new ReturnValueILPN(tempName(typeTemps, iR.statement.type()), iR.statement.type()));
				}

				ilNodes.add(new CallCloseScopeILPN(layersSinceFunc));
				ilNodes.add(new GotoILPN("func_end_" + funcName));
			} else if (i instanceof PrintPN){
				ilNodes.add(new PrintILPN());
			} else if (i instanceof RawPN) { 
				ilNodes.add(new RawILPN( ((RawPN)i).text ));
			} else if (i instanceof RawFPN) { 
				ilNodes.add(new RawFILPN( ((RawFPN)i).text ));
			} else {
				throw new Exception("Don't know how to parse " + i.getClass());
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
			ret.add(new SetILPN("s_temp" + tempToPutResult + "_" + ((NumLitteralPN) evaluable).type(), num, ((NumLitteralPN) evaluable).type()));
			return ret;
		} else if (evaluable instanceof BooleanLitteralPN) {
			BoolILPN bool = new BoolILPN(((BooleanLitteralPN) evaluable).trueOrFalse);
			ret.add(new SetILPN("s_temp" + tempToPutResult + "_bool", bool, "bool"));
			return ret;
		} else if (evaluable instanceof VariableUsePN) {
			VarUseILPN var = new VarUseILPN(((VariableUsePN) evaluable).name);
			ret.add(new SetILPN("s_temp" + tempToPutResult + "_" + ((VariableUsePN) evaluable).type(), var, ((VariableUsePN) evaluable).type()));
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
					Ops.fromPNOp(eBinary.op), eBinary.left.type()));

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

		ret.add(new SetReturnVarToILPN(tempName(tempToPutResult, returnType.equals("") ? "bool" : returnType)));
		ArrayList<String> previousTypes = new ArrayList<String>();
		for (Evaluable e : eFC.params) {
			typeTemps.put(e.type(), typeTemps.get(e.type()) + 1);
			ret.addAll(parseExpression(e, typeTemps.get(e.type()), typeTemps));
			ret.add(new SetParamToVarILPN(Copy.deepCopyArray(previousTypes), tempName(typeTemps, e.type()), e.type()));
			typeTemps.put(e.type(), typeTemps.get(e.type()) - 1);
			previousTypes.add(e.type());
		}

		ret.add(new CallILPN("func_start_" + (1 + functionNames.indexOf(eFC.functionName))));

		return ret;
	}

	private Map<String, Integer> findTemps(ArrayList<ILParseNode> code, int start, int end,
			Map<String, Integer> superUses) {
		
		Map<String, Integer> myUses = Copy.deepCopyMapSI(superUses);

//		System.out.println("---s");
		
		int depthCount = 0;
		for(int i = start; i < end; i++) {
			if(code.get(i) instanceof OpenScopeILPN) {
				depthCount++;
			} else if(code.get(i) instanceof CloseScopeILPN) {
				depthCount--;
			}
			
			if(depthCount == 1) {
				code.get(i).updateHighestUsedTemp(myUses);
			}
		}
		
		ArrayList<ILParseNode> newVars = new ArrayList<ILParseNode>();
		for(String type : superUses.keySet()) {
			for(int i = superUses.get(type); i < myUses.get(type); i++) {
//				System.out.println(superUses + " " + "s_temp" + i + "_" + type);
				newVars.add(new CreateVariableILPN("s_temp" + i + "_" + type, type));
			}
		}
//		System.out.println(">> " + newVars);
		
		int rStart = 0;
		int rEnd = 0;
		int rDepthCount = 0;
		for(int i = start; i < end + newVars.size(); i++) {
			if(code.get(i) instanceof OpenScopeILPN) {
				rDepthCount++;
			}
			
//			System.out.println(i + ": (" + end + ") " + rDepthCount + "; " + code.get(i));
			
			if(rDepthCount == 1 && (code.get(i) instanceof OpenScopeILPN)) {
				int j = i + 1;
				while(code.get(j) instanceof CreateVariableILPN) {
					j++;
				}
				code.addAll(j, newVars);
				//				System.out.println(">>>> " + newVars);
			}
			
			if(rDepthCount == 2 && code.get(i) instanceof OpenScopeILPN) {
				rStart = i;
			} else if(rDepthCount == 2 && code.get(i) instanceof CloseScopeILPN) {
				rEnd = i;
				int oldLength = code.size();
				findTemps(code, rStart, rEnd + 1, myUses);
				i += code.size() - oldLength;
				end += code.size() - oldLength;
			}
		
			if(code.get(i) instanceof CloseScopeILPN) {
				rDepthCount--;
			}
		}
		
//		System.out.println("---e");
		
		return myUses;
	}

	public int getUniqueNameNum() {
		return uniqueNameCount++;
	}

	public int getUniqueFuncNum() {
		return functionNameCount++;
	}

	private String tempName(Map<String, Integer> typeTemps, String type) {
		return "s_temp" + typeTemps.get(type) + "_" + type;
	}

	private String tempName(int tempNum, String type) {
		return "s_temp" + tempNum + "_" + type;
	}

}
