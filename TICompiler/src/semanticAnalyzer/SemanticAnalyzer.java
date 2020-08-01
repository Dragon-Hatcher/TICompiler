package semanticAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import parser.nodes.*;
import semanticAnalyzer.exceptions.*;

public class SemanticAnalyzer {

	Set<String> types = new HashSet<String>(Arrays.asList("int", "float", "bool"));
	
	public void analyze(MainLevelPN mainLevel) throws Exception {
		for(FunctionDeclerationPN i : mainLevel.functions) {
			if(functionShouldReturn(i) && !i.instructions.willReturn()) {
				throw new InccorectFunctionReturnException("Function \"" + i.name + "\" must return.");
			}
			
			if(i.instructions.hasIllegalBreak()) {
				throw new IllegalBreakException("Illegal break in function " + i.name + ".");
			}
			
			String iHITD = i.instructions.hasIllegalDeclerationType(types);
			if(iHITD != null) {
				throw new IllegalTypeDeclerationException("Illegal decleration of type " + iHITD + " in function " + i.name + ".");
			}
		}
		
		if(mainLevel.main.hasIllegalBreak()) {
			throw new IllegalBreakException("Illegal break in main.");
		}		

		String iHITD = mainLevel.main.hasIllegalDeclerationType(types);
		if(iHITD != null) {
			throw new IllegalTypeDeclerationException("Illegal decleration of type " + iHITD + " in main.");
		}

		// check functions return v/
		// check break is in a loop  v/
		// check illegal type declaration v/
		// check func param types
	}
	
	private boolean functionShouldReturn(FunctionDeclerationPN func) {
		return !func.returnType.equals("");
	}
	
}
