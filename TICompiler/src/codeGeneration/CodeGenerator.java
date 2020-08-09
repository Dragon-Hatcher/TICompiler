package codeGeneration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import intermediateLanguageGenerator.nodes.*;
import toolkit.Copy;

public class CodeGenerator {

	final int recurLimit = 20;

	StringBuilder output = new StringBuilder();
	StringBuilder code = new StringBuilder();
	StringBuilder vars = new StringBuilder();
	Map<ArrayList<Integer>, Set<String>> sectionVars = new HashMap<ArrayList<Integer>, Set<String>>();
	ArrayList<ILParseNode> instructions;

	Map<String, Integer> typeSizes = new HashMap<String, Integer>();

	public CodeGenerator() {
		typeSizes.put("int", 2);
		typeSizes.put("$pointer", 3);
		typeSizes.put("char", 1);
		typeSizes.put("bool", 1);
	}

	public String generateAssembly(MainLevelILPN main) throws Exception {
		vars.append("s_mem_start = saveSScreen\r\n");
		vars.append("s_area_stack_pointer = s_mem_start + 3\r\n");
		vars.append("s_mem_stack_pointer = s_area_stack_pointer + " + recurLimit * 3 + " + 3\r\n" + "");

		instructions = main.instructions;

		int start = 0;
		int end = 0;
		int func = 0;
		for (int i = 0; i < instructions.size(); i++) {
			if (instructions.get(i) instanceof LabelILPN) {
				LabelILPN iL = (LabelILPN) instructions.get(i);
				if (iL.label.startsWith("func_start_" + func)) {
					start = i;
				} else if (iL.label.startsWith("func_end_" + func)) {
					end = i + 2;
					ArrayList<Integer> section = new ArrayList<Integer>();
					section.add(func++);
					code.append(parseSequence(start + 3, end, section, 0));
				}
			}
		}

		output.append("#include \"includes\\ti84pce.inc\"\r\n");
		output.append(" \r\n");
		output.append(vars);
		output.append(" \r\n");
		output.append(" .assume ADL=1\r\n");
		output.append(" .org userMem-2\r\n");
		output.append(" .db tExtTok,tAsm84CeCmp\r\n" + "\r\n");
		output.append(" ld HL, s_area_stack_pointer + 3\r\n");
		output.append(" ld (s_area_stack_pointer), HL\r\n");
		output.append(" ld HL, s_mem_stack_pointer + 3\r\n");
		output.append(" ld (s_mem_stack_pointer), HL\r\n");
		output.append(" \r\n");
		output.append(" call func_start_0\r\n");
		output.append(" ret\r\n");
		output.append(" \r\n");
		output.append(code);
		return output.toString();
	}

	private StringBuilder parseSequence(int start, int end, ArrayList<Integer> section, int memUse) throws Exception {

		Set<String> myVars = new HashSet<String>();
		sectionVars.put(Copy.deepCopyArray(section), myVars);

		StringBuilder seqCode = new StringBuilder();
		StringBuilder startString = new StringBuilder();
		int myMemUse = memUse;

		if (section.size() == 1) {
			startString.append("func_start_" + section.get(0) + ":\r\n");
			startString.append(" ld DE, s_mem_stack_pointer\r\n");
			startString.append(" ld HL, (s_area_stack_pointer)\r\n");
			startString.append(" INC HL\r\n");
			startString.append(" INC HL\r\n");
			startString.append(" INC HL\r\n");
			startString.append(loadTypeFromPointerToPointer("$pointer", Register.DE, Register.HL));
			startString.append(" DEC HL\r\n");
			startString.append(" DEC HL\r\n");
			startString.append(" ld (s_area_stack_pointer), HL\r\n");
		}

		int rStart = 0;
		int rEnd = 0;
		int rDepth = 0;
		int rSection = 0;
		for (int i = start; i < end; i++) {
			ILParseNode ins = instructions.get(i);

			if (ins instanceof OpenScopeILPN) {
				rDepth++;
				if (rDepth == 1) {
					rStart = i;
				}
			} else if (ins instanceof CloseScopeILPN) {
				if (rDepth == 1) {
					rEnd = i;
					section.add(rSection++);
					seqCode.append(parseSequence(rStart + 1, rEnd, section, myMemUse));
					section.remove(section.size() - 1);
				}
				rDepth--;
			}

			if (rDepth <= 0) {
				if (ins instanceof SetReturnVarToILPN) {
					SetReturnVarToILPN insSRV = (SetReturnVarToILPN) ins;
					seqCode.append(";Set return to var " + insSRV.returnVar + "\r\n");
					seqCode.append(getVarInRegister(insSRV.returnVar, section, Register.DE));
					seqCode.append(" ld HL, (s_mem_stack_pointer)\r\n");
					seqCode.append(" ld (HL), DE\r\n");
				} else if (ins instanceof SetParamToVarILPN) {
					SetParamToVarILPN insSPV = (SetParamToVarILPN) ins;
					seqCode.append(";Set param to var " + insSPV.varToSetParamTo + "\r\n");

					seqCode.append(getVarInRegister(insSPV.varToSetParamTo, section, Register.DE));
					seqCode.append(" ld HL, (s_mem_stack_pointer)\r\n");

					int byteCount = typeSizes.get("$pointer");
					for (String type : insSPV.previousParams) {
						byteCount += typeSizes.get(type);
					}

					seqCode.append(" ld BC, " + byteCount + "\r\n");
					seqCode.append(" ADD HL, BC\r\n");
					seqCode.append(loadTypeFromPointerToPointer(insSPV.type, Register.DE, Register.HL));

				} else if (ins instanceof SetResultILPN) {
					seqCode.append(parseSetResult((SetResultILPN)ins, section));
				} else if (ins instanceof SetILPN) {
					SetILPN insS = (SetILPN) ins;
					seqCode.append(";Set " + insS.sete + " to " + insS.seter + " \r\n");
					seqCode.append(getVarInRegister(insS.sete, section, Register.DE));
					if (insS.seter instanceof BoolILPN) {
						seqCode.append(loadRegister(Register.HL, Register.DE));
						seqCode.append(" ld (HL), " + (((BoolILPN) insS.seter).trueOrFalse ? 1 : 0) + "\r\n");
					} else if (insS.seter instanceof NumILPN) {
						seqCode.append(loadRegister(Register.HL, Register.DE));
						seqCode.append(" ld DE, " + Integer.parseInt(((NumILPN) insS.seter).num) + "\r\n");
						seqCode.append(" ld (HL), E\r\n");
						seqCode.append(" INC HL\r\n");
						seqCode.append(" ld (HL), D\r\n");
					} else if (insS.seter instanceof VarUseILPN) {
						VarUseILPN seterVU = (VarUseILPN) insS.seter;
						seqCode.append(getVarInRegister(seterVU.name, section, Register.BC));
						seqCode.append(loadRegister(Register.HL, Register.DE));
						seqCode.append(loadTypeFromPointerToPointer(insS.type, Register.BC, Register.HL));
					} else {
						throw new Exception("Can't parse ILEvaluable");
					}

				} else if (ins instanceof ReturnValueILPN) {
					ReturnValueILPN insR = (ReturnValueILPN) ins;

					seqCode.append(";Return Variable \r\n");
					seqCode.append(getVarInRegister("s_return_adress", section, Register.DE));
					seqCode.append(indirectRegisterIntoSelf(Register.DE));
					seqCode.append(getVarInRegister(insR.varToReturn, section, Register.BC));
					seqCode.append(loadTypeFromPointerToPointer(insR.type, Register.BC, Register.DE));

				} else if (ins instanceof CallILPN) {
					seqCode.append(";Call Function \r\n");
					seqCode.append(" call " + ((CallILPN) ins).funcToCall + "\r\n");
				} else if (ins instanceof CallCloseScopeILPN) {
					CallCloseScopeILPN insCCS = (CallCloseScopeILPN) ins;
					for (int ccs = 0; ccs < insCCS.scopesToClose; ccs++) {
						seqCode.append(" call close_scope_"
								+ sectionCode(new ArrayList<Integer>(section.subList(0, section.size() - ccs)))
								+ "\r\n");
					}
				} else if (ins instanceof CreateVariableILPN) {
					CreateVariableILPN insCV = (CreateVariableILPN) ins;
					vars.append("u_" + insCV.name + "_" + sectionCode(section) + " = " + myMemUse + "\r\n");
					myMemUse += typeSizes.get(insCV.type);
					myVars.add(insCV.name);
				} else if (ins instanceof LabelILPN) {
					seqCode.append(((LabelILPN) ins).label + ":\r\n");
				} else if (ins instanceof GotoILPN) {
					GotoILPN insGT = (GotoILPN) ins;
					if (insGT.ifVar.equals("")) {
						seqCode.append(";Unconditional Goto\r\n");
						seqCode.append(" jp " + insGT.label + "\r\n");
					} else {
						seqCode.append(";Goto conditioned on " + insGT.ifVar + "\r\n");
						seqCode.append(getVarInRegisterHL(insGT.ifVar, section, Register.DE));
						seqCode.append(" ld A, (HL)\r\n");
						seqCode.append(" cp 1\r\n");
						seqCode.append(" jp Z, " + insGT.label + "\r\n");
					}
				} else if (ins instanceof PrintILPN) {
					seqCode.append(";Print\r\n");
					seqCode.append(" ld A, 65\r\n");
					seqCode.append(" call _PutC\r\n");
				}
				// seqCode.append(";---------\r\n");
			}
		}

		seqCode.append(";Open Scope Scope\r\n");
		startString.append(" ld HL, (s_mem_stack_pointer)\r\n");
		startString.append(" ld DE, " + (myMemUse - memUse) + "\r\n");
		startString.append(" ADD HL, DE\r\n");
		startString.append(" ld (s_mem_stack_pointer), HL\r\n");

		seqCode.insert(0, startString);

		// close scope
		seqCode.append(";Close Scope\r\n");
		seqCode.append(" call close_scope_" + sectionCode(section) + "\r\n");
		seqCode.append(" jp end_close_scope_" + sectionCode(section) + "\r\n");
		seqCode.append("close_scope_" + sectionCode(section) + ":\r\n");
		seqCode.append(" ld HL, (s_mem_stack_pointer)\r\n");
		seqCode.append(" ld DE, " + (myMemUse - memUse) + "\r\n");
		seqCode.append(" SCF    ; Force carry = 1\r\n");
		seqCode.append(" CCF    ; Flip carry so it is \r\n");
		seqCode.append(" SBC HL, DE\r\n");
		seqCode.append(" ld (s_mem_stack_pointer), HL\r\n");
		seqCode.append(" ret\r\n");
		seqCode.append("end_close_scope_" + sectionCode(section) + ":\r\n");

		if (section.size() == 1) {
			seqCode.append(";Close Area\r\n");
			seqCode.append(" ld HL, (s_area_stack_pointer)\r\n");
			seqCode.append(" DEC HL\r\n");
			seqCode.append(" DEC HL\r\n");
			seqCode.append(" DEC HL\r\n");
			seqCode.append(" ld (s_area_stack_pointer), HL\r\n");
			seqCode.append(" ret\r\n\r\n");
		}

		return seqCode;
	}

	private String sectionCode(ArrayList<Integer> section) {
		StringJoiner joiner = new StringJoiner("_");
		for (Integer i : section) {
			joiner.add(i.toString());
		}
		return joiner.toString();
	}

	private String findVariableName(String varName, ArrayList<Integer> mySection) throws Exception {
		ArrayList<Integer> zero = new ArrayList<Integer>();
		zero.add(0);
		if (sectionVars.get(zero).contains(varName)) {
			return "u_" + varName + "_0";
		} else {
			for (int i = mySection.size(); i >= 1; i--) {
				if (sectionVars.get(mySection.subList(0, i)).contains(varName)) {
					return "u_" + varName + "_" + sectionCode(new ArrayList<Integer>(mySection.subList(0, i)));
				}
			}
		}
		throw new Exception("Could not find var");
	}

	private boolean isMainVariable(String varName) {
		ArrayList<Integer> zero = new ArrayList<Integer>();
		zero.add(0);
		return sectionVars.get(zero).contains(varName);
	}

	// Destroys: HL, Gives var pointer in reg
	private String getVarInRegister(String rawVarName, ArrayList<Integer> section, Register reg) throws Exception {
		reg.assertNotHLWord();

		StringBuilder ret = new StringBuilder();

		String varName = findVariableName(rawVarName, section);
		if (isMainVariable(rawVarName)) {
			ret.append(" ld HL, s_mem_stack_pointer + 3 \r\n");
		} else {
			ret.append(" ld HL, (s_area_stack_pointer) \r\n");
			ret.append(" ld " + reg + ", (HL)\r\n");
			ret.append(" PUSH " + reg + "\r\n");
			ret.append(" POP HL\r\n");
			ret.append(loadRegister(Register.BC, Register.HL));
		}
		ret.append(" ld " + reg + ", " + varName + "\r\n");
		ret.append(" ADD HL, " + reg + "\r\n");
		ret.append(loadRegister(reg, Register.HL));

		return ret.toString();
	}

	// Destroys: HL, Gives var pointer in reg
	private String getVarInRegisterHL(String rawVarName, ArrayList<Integer> section, Register reg) throws Exception {
		reg.assertNotHLWord();

		StringBuilder ret = new StringBuilder();

		String varName = findVariableName(rawVarName, section);
		if (isMainVariable(rawVarName)) {
			ret.append(" ld HL, s_mem_stack_pointer + 3 \r\n");
		} else {
			ret.append(" ld HL, (s_area_stack_pointer) \r\n");
			ret.append(" ld C, (HL)\r\n");
			ret.append(" INC HL\r\n");
			ret.append(" ld B, (HL)\r\n");
			ret.append(loadRegister(Register.BC, Register.HL));
		}
		ret.append(" ld " + reg + ", " + varName + "\r\n" + " ADD HL, " + reg + "\r\n");

		return ret.toString();
	}

	private String loadRegister(Register destination, Register source) throws Exception {
		if (destination.isWord() && source.isWord()) {
			return " PUSH " + source + "\r\n" + " POP " + destination + "\r\n";
		} else {
			return " ld " + destination + ", " + source + "\r\n";
		}
	}

	// destroys A
	private String loadTypeFromPointerToPointer(String type, Register source, Register destination)
			throws Exception {
		source.assertWord();
		destination.assertWord();

		StringBuilder ret = new StringBuilder();

		int count = typeSizes.get(type);
		for (int i = 0; i < count; i++) {
			ret.append(" ld A, (" + source + ")\r\n");
			ret.append(" ld (" + destination + "), A\r\n");
			if (i != count - 1) {
				ret.append(" INC " + source + "\r\n");
				ret.append(" INC " + destination + " \r\n");
			}
		}

		return ret.toString();
	}

	// destroys HL
	private String indirectRegisterIntoSelf(Register toIndirect) throws Exception {
		StringBuilder ret = new StringBuilder();
		ret.append(" ex " + toIndirect + ", HL\r\n");
		ret.append(" ld " + toIndirect + ", (HL)\r\n");
		return ret.toString();
	}
	
	private String parseSetResult(SetResultILPN set, ArrayList<Integer> section) throws Exception {
		switch(set.operator) {
		case AND:
			switch(set.lrTypes) {
			case "bool":
				return parseAndBool(set, section);
			default:
				throw new Exception("Don't know how to handle type " + set.lrTypes + " for op " + set.operator);
			}
		case OR:
			switch(set.lrTypes) {
			case "bool":
				return parseOrBool(set, section);
			default:
				throw new Exception("Don't know how to handle type " + set.lrTypes + " for op " + set.operator);
			}
		case NOT_EQUAL:
			switch(set.lrTypes) {
			case "bool":
				return parseXorBool(set, section);
			default:
				throw new Exception("Don't know how to handle type " + set.lrTypes + " for op " + set.operator);
			}
		default:
			throw new Exception("Don't know how to handle operator " + set.operator);
		}
	}
	
	private String parseAndBool(SetResultILPN set, ArrayList<Integer> section) throws Exception {
		StringBuilder ret = new StringBuilder();

		if(set.left instanceof BoolILPN) {
			boolean boolType = ((BoolILPN)set.left).trueOrFalse;
			ret.append(" ld A, " + (boolType ? 1 : 0) + "\r\n");
		} else if (set.left instanceof VarUseILPN) {
			ret.append(getVarInRegisterHL(((VarUseILPN)set.left).name, section, Register.DE));
			ret.append(" ld A, (HL)\r\n");
		} else {
			throw new Exception("Left isn't bool or var use.");
		}
		if(set.right instanceof BoolILPN) {
			boolean boolType = ((BoolILPN)set.right).trueOrFalse;
			ret.append(" ld B, " + (boolType ? 1 : 0) + "\r\n");
		} else if (set.right instanceof VarUseILPN) {
			ret.append(getVarInRegisterHL(((VarUseILPN)set.right).name, section, Register.DE));
			ret.append(" ld B, (HL)\r\n");
		} else {
			throw new Exception("Left isn't bool or var use.");
		}
		ret.append(" AND A, B\r\n");
		ret.append(getVarInRegisterHL(set.sete, section, Register.BC));
		ret.append(" ld (HL), A");

		return ret.toString();
	}
	
	private String parseOrBool(SetResultILPN set, ArrayList<Integer> section) throws Exception {
		StringBuilder ret = new StringBuilder();

		if(set.left instanceof BoolILPN) {
			boolean boolType = ((BoolILPN)set.left).trueOrFalse;
			ret.append(" ld A, " + (boolType ? 1 : 0) + "\r\n");
		} else if (set.left instanceof VarUseILPN) {
			ret.append(getVarInRegisterHL(((VarUseILPN)set.left).name, section, Register.DE));
			ret.append(" ld A, (HL)\r\n");
		} else {
			throw new Exception("Left isn't bool or var use.");
		}
		if(set.right instanceof BoolILPN) {
			boolean boolType = ((BoolILPN)set.right).trueOrFalse;
			ret.append(" ld B, " + (boolType ? 1 : 0) + "\r\n");
		} else if (set.right instanceof VarUseILPN) {
			ret.append(getVarInRegisterHL(((VarUseILPN)set.right).name, section, Register.DE));
			ret.append(" ld B, (HL)\r\n");
		} else {
			throw new Exception("Left isn't bool or var use.");
		}
		ret.append(" OR A, B\r\n");
		ret.append(getVarInRegisterHL(set.sete, section, Register.BC));
		ret.append(" ld (HL), A");

		return ret.toString();
	}

	private String parseXorBool(SetResultILPN set, ArrayList<Integer> section) throws Exception {
		StringBuilder ret = new StringBuilder();

		if(set.left instanceof BoolILPN) {
			boolean boolType = ((BoolILPN)set.left).trueOrFalse;
			ret.append(" ld A, " + (boolType ? 1 : 0) + "\r\n");
		} else if (set.left instanceof VarUseILPN) {
			ret.append(getVarInRegisterHL(((VarUseILPN)set.left).name, section, Register.DE));
			ret.append(" ld A, (HL)\r\n");
		} else {
			throw new Exception("Left isn't bool or var use.");
		}
		if(set.right instanceof BoolILPN) {
			boolean boolType = ((BoolILPN)set.right).trueOrFalse;
			ret.append(" ld B, " + (boolType ? 1 : 0) + "\r\n");
		} else if (set.right instanceof VarUseILPN) {
			ret.append(getVarInRegisterHL(((VarUseILPN)set.right).name, section, Register.DE));
			ret.append(" ld B, (HL)\r\n");
		} else {
			throw new Exception("Left isn't bool or var use.");
		}
		ret.append(" XOR A, B\r\n");
		ret.append(getVarInRegisterHL(set.sete, section, Register.BC));
		ret.append(" ld (HL), A");

		return ret.toString();
	}

}
