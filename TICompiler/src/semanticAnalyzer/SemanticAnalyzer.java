package semanticAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import parser.nodes.*;
import semanticAnalyzer.exceptions.*;

public class SemanticAnalyzer {

	Set<String> types = new HashSet<String>(Arrays.asList("int", "float", "bool", "char"));
	Map<String, FunctionDeclerationPN> functions = new HashMap<String, FunctionDeclerationPN>();
	
	public void analyze(MainLevelPN mainLevel) throws Exception {
		for(FunctionDeclerationPN i : mainLevel.functions) {												
			functions.put(i.name, i);
		}

		mainLevel.setSubParseNodeVariables(new HashMap<String, String>());
		mainLevel.setFunctions(functions);
		
		mainLevel.checkFunctionsReturn();
		mainLevel.hasIllegalBreaks();
		mainLevel.hasIllegalDeclerationType(types);
		mainLevel.checkFunctionNameAndLength();
		mainLevel.checkTypes("");
		mainLevel.checkVariableUsedBeforeDeclared(new HashSet<String>());
		
		// check functions return v/
		// check break is in a loop  v/
		// check illegal type declaration v/
		// check func param types v/
		// check function call exists v/
		// check function call length v/
		// check voids don't return with value v/
		// check types in assignment v/
		// check types in function calls v/
		// check that a variable isn't used before it is initialized v/ (Initialization now required)
		// check that a variable isn't used before it is declared v/
		
	}
	
	private boolean functionShouldReturn(FunctionDeclerationPN func) {
		return !func.returnType.equals("");
	}
	
}
