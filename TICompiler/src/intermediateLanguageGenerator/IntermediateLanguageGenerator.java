package intermediateLanguageGenerator;

import java.util.ArrayList;

import intermediateLanguageGenerator.nodes.*;
import parser.nodes.MainLevelPN;

public class IntermediateLanguageGenerator {

	MainLevelPN mainPN = new MainLevelPN();
	MainLevelILPN main = new MainLevelILPN();
	
	private int uniqueNameCount = 0;
	
	public MainLevelILPN generatorIL(MainLevelPN mainLevelPN) {
		mainPN = mainLevelPN;
		
		parseInstructionSequence();
		
		System.out.println(main);
		return main;
	}
	
	private void parseInstructionSequence() {
		main.add(new OpenScopeILPN());

		for(String varName : mainPN.main.variables.keySet()) {
			main.add(new CreateVariableILPN(varName, mainPN.main.variables.get(varName)));
		}
		
		main.add(new CloseScopeILPN());
	}
	
	public int getUniqueNameNum() {
		return uniqueNameCount++;
	}

}
