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
		typeSizes.put("$pointer", 2);
		typeSizes.put("char", 1);
		typeSizes.put("bool", 1);
	}

	public String generateAssembly(MainLevelILPN main) throws Exception {
		vars.append("s_mem_start = saveSScreen\r\n" + "s_area_stack_pointer = s_mem_start + 2\r\n"
				+ "s_mem_stack_pointer = s_area_stack_pointer + " + recurLimit * 2 + " + 2\r\n" + "");

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
		output.append("\r\n" + vars);
		output.append("\r\n" + " .assume ADL=1\r\n" + " .org userMem-2\r\n" + " .db tExtTok,tAsm84CeCmp\r\n" + "\r\n");
		output.append(" ld HL, s_area_stack_pointer + 2\r\n" + " ld (s_area_stack_pointer), HL\r\n"
				+ " ld HL, s_mem_stack_pointer + 2\r\n" + " ld (s_mem_stack_pointer), HL\r\n" + "\r\n");
		output.append(" call func_start_0\r\n ret\r\n\r\n");
		output.append(code);
		return output.toString();
	}

	private StringBuilder parseSequence(int start, int end, ArrayList<Integer> section, int memUse) throws Exception {

		Set<String> myVars = new HashSet<String>();
		sectionVars.put(Copy.deepCopyArray(section), myVars);

		StringBuilder seqCode = new StringBuilder();
		StringBuilder startString = new StringBuilder();
		int myMemUse = memUse;
		// TODO set mem use for functions to be after mem use for main

		if (section.size() == 1) {
			startString.append("func_start_" + section.get(0) + ":\r\n" + " ld DE, (s_mem_stack_pointer)\r\n"
					+ " ld HL, (s_area_stack_pointer)\r\n" + " ld (HL), E\r\n" + " INC HL\r\n" + " ld (HL), D\r\n"
					+ " INC HL\r\n" + " ld (s_area_stack_pointer), HL\r\n" + "");
		}

		System.out.println("----s_" + sectionCode(section));

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
					System.out.println(rSection);
					seqCode.append(parseSequence(rStart + 1, rEnd, section, myMemUse));
					section.remove(section.size() - 1);
				}
				rDepth--;
			}

			if (rDepth <= 0) {
				if (ins instanceof SetReturnVarToILPN) {
					SetReturnVarToILPN insSRV = (SetReturnVarToILPN) ins;
					seqCode.append(getVarInRegister(insSRV.returnVar, section, Register.DE));						
					seqCode.append(" ld HL, (s_mem_stack_pointer)\r\n");
					seqCode.append(" ld (HL), E\r\n");
					seqCode.append(" INC HL\r\n");
					seqCode.append(" ld (HL), D\r\n");
				} else if (ins instanceof SetParamToVarILPN) {
					SetParamToVarILPN insSPV = (SetParamToVarILPN) ins;
					seqCode.append(getVarInRegister(insSPV.varToSetParamTo, section, Register.DE));						
					seqCode.append(" ld HL, (s_mem_stack_pointer)\r\n");
					
					int byteCount = typeSizes.get("$pointer");
					for (String type : insSPV.previousParams) {
						byteCount += typeSizes.get(type);
					}
					
					seqCode.append(" ld BC, " + byteCount + "\r\n");					
					seqCode.append(" ADD HL, BC");
					seqCode.append(loadTypeFromPointerToPointer(insSPV.type, Register.DE, Register.HL));

				} else if (ins instanceof SetResultILPN) {
					
				} else if (ins instanceof SetILPN) {
					SetILPN insS = (SetILPN) ins;
					seqCode.append(getVarInRegister(insS.sete, section, Register.DE));						
					if(insS.seter instanceof BoolILPN) {
						seqCode.append(loadRegister(Register.HL, Register.DE));
						seqCode.append(" ld (HL), " + (((BoolILPN)insS.seter).trueOrFalse ? 1 : 0) + "\r\n");
					} else if(insS.seter instanceof NumILPN) {
						seqCode.append(loadRegister(Register.HL, Register.DE));
						seqCode.append(" ld DE, " + Integer.parseInt(((NumILPN)insS.seter).num) + "\r\n");
						seqCode.append(" ld (HL), E\r\n");
						seqCode.append(" INC HL\r\n");
						seqCode.append(" ld (HL), D\r\n");
					} else if(insS.seter instanceof VarUseILPN) {
						VarUseILPN seterVU = (VarUseILPN)insS.seter;
						seqCode.append(getVarInRegister(seterVU.name, section, Register.BC));						
						seqCode.append(loadRegister(Register.HL, Register.DE));
						seqCode.append(loadTypeFromPointerToPointer(insS.type, Register.BC, Register.HL));
					} else {
						throw new Exception("Can't parse ILEvaluable");
					}
					
				} else if (ins instanceof ReturnValueILPN) {
					ReturnValueILPN insR = (ReturnValueILPN) ins;
				
					seqCode.append(getVarInRegister("s_return_adress", section, Register.DE));	
					seqCode.append(indirectRegisterIntoSelf(Register.DE, Register.BC));
					seqCode.append(getVarInRegister(insR.varToReturn, section, Register.BC));	
					seqCode.append(loadTypeFromPointerToPointer(insR.type, Register.BC, Register.DE));

				} else if (ins instanceof CallILPN) {
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
						seqCode.append(" jp " + insGT.label + "\r\n");
					} else {
						seqCode.append(getVarInRegister(insGT.ifVar, section, Register.DE));	
						seqCode.append(" ld A, (DE)\r\n");
						seqCode.append(" cp 1\r\n");
						seqCode.append(" jp Z, " + insGT.label + "\r\n");
					}
				}
				seqCode.append(";---------\r\n");
			}
		}

		startString.append(" ld HL, (s_mem_stack_pointer)\r\n" + " ld DE, " + (myMemUse - memUse) + "\r\n"
				+ " ADD HL, DE\r\n" + " ld (s_mem_stack_pointer), HL\r\n" + "");

		seqCode.insert(0, startString);

		// close scope
		seqCode.append(
				" call close_scope_" + sectionCode(section) + "\r\n" + " jp end_close_scope_" + sectionCode(section)
						+ "\r\n" + "close_scope_" + sectionCode(section) + ":\r\n" + " ld HL, (s_mem_stack_pointer)\r\n"
						+ " ld DE, " + (myMemUse - memUse) + "\r\n" + " SCF    ; Force carry = 1\r\n"
						+ " CCF    ; Flip carry so it is \r\n" + " SBC HL, DE\r\n" + " ld (s_mem_stack_pointer), HL\r\n"
						+ " ret\r\n" + "end_close_scope_" + sectionCode(section) + ":\r\n");

		if (section.size() == 1) {
			seqCode.append(" ld HL, (s_area_stack_pointer)\r\n" + " DEC HL\r\n" + " DEC HL\r\n"
					+ " ld (s_area_stack_pointer), HL\r\n" + " ret\r\n\r\n");
		}

		System.out.println("----e_" + sectionCode(section));

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
	
	//Destroys: HL, Gives var pointer in reg
	private String getVarInRegister(String rawVarName, ArrayList<Integer> section, Register reg) throws Exception {
		reg.assertNotHLWord();
		
		StringBuilder ret = new StringBuilder();

		String varName = findVariableName(rawVarName, section);
		if (isMainVariable(rawVarName)) {
			ret.append(" ld HL, s_area_stack_pointer + 2 \r\n");
		} else {
			ret.append(" ld HL, (s_area_stack_pointer) \r\n");
		}
		ret.append(" ld " + reg + ", " + varName + "\r\n" + 
				" ADD HL, " + reg + "\r\n" + 
				loadRegister(reg, Register.HL));
		
		return ret.toString();
	}
	
	private String loadRegister(Register destination, Register source) throws Exception {
		if(destination.isWord() && source.isWord()) {
			return " ld " + destination.high() + ", " + source.high() + "\r\n" + " ld " + destination.low() + ", " + source.low() + "\r\n";
		} else {
			return " ld " + destination + ", " + source + "\r\n";
		}
	}

	//destroys A
	private String loadTypeFromPointerToPointer(String type, Register source, Register destination) throws Exception {
		source.assertWord();
		destination.assertWord();
		
		StringBuilder ret = new StringBuilder();
		
		int count = typeSizes.get(type);
		for(int i = 0; i < count; i++) {
			ret.append(" ld A, (" + source + ")\r\n");
			ret.append(" ld (" + destination + "), A\r\n");
			ret.append(" INC " + source + "\r\n");
			ret.append(" INC " + destination + "\r\n");
		}
		
		return ret.toString();
	}
	
	//destroys a, using
	private String indirectRegisterIntoSelf(Register toIndirect, Register using) {
		StringBuilder ret = new StringBuilder();
		ret.append("ld A, (" + toIndirect + ")\r\n");
		ret.append("ld " + using.low() + ", A\r\n");
		ret.append("INC " + toIndirect + "\r\n");
		ret.append("ld A, (" + toIndirect + ")\r\n");
		ret.append("ld " + using.high() + ", A\r\n");
		ret.append("ld " + toIndirect + ", " + using + "\r\n");
		return ret.toString();
	}
}
