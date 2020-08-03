package intermediateLanguageGenerator;

import java.util.ArrayList;

import intermediateLanguageGenerator.nodes.*;
import parser.nodes.MainLevelPN;

public class IntermediateLanguageGenerator {

	public MainLevelILPN generatorIL(MainLevelPN mainLevelPN) {
		return new MainLevelILPN();
	}

}
