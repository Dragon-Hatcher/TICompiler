package tiCompiler;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import codeGeneration.CodeGenerator;
import intermediateLanguageGenerator.IntermediateLanguageGenerator;
import intermediateLanguageGenerator.nodes.MainLevelILPN;
import lexer.Lexer;
import lexer.Token;
import lexer.UnableToLexCharacterException;
import lexer.UnknownOperatorException;
import lexer.UnknownSeperatorException;
import parser.Parser;
import parser.nodes.MainLevelPN;
import parser.nodes.ParseNode;
import semanticAnalyzer.SemanticAnalyzer;

public class TICompiler {

	public static void main(String[] args) throws Exception {		
		String code = "";
		code = readFileAsString("./res/testCode");
		
		Lexer lexer = new Lexer();
		ArrayList<Token> tokens = new ArrayList<Token>();
		tokens = lexer.lex(code);
//		System.out.println(tokens);
		
		Parser parser = new Parser();
		MainLevelPN mainLevelParseNode = null;
		mainLevelParseNode = parser.parse(tokens);
		//System.out.println(mainLevelParseNode);
		
		SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
		semanticAnalyzer.analyze(mainLevelParseNode);
		
		IntermediateLanguageGenerator intermediateLanguageGenerator = new IntermediateLanguageGenerator();
		MainLevelILPN mainLevelILParseNode = null;
		mainLevelILParseNode = intermediateLanguageGenerator.generatorIL(mainLevelParseNode);
//		System.out.println(mainLevelILParseNode);
//		System.out.println("------");
		
		CodeGenerator codeGenerator = new CodeGenerator();
		System.out.println(codeGenerator.generateAssembly(mainLevelILParseNode));
	}

	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		return data;
	}
}
