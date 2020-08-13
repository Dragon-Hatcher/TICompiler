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
		try {
			code = readFileAsString("./res/testCode");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Lexer lexer = new Lexer();
		ArrayList<Token> tokens = new ArrayList<Token>();
		try {
			tokens = lexer.lex(code);
		} catch (UnknownOperatorException e) {
			e.printStackTrace();
		} catch (UnknownSeperatorException e) {
			e.printStackTrace();
		} catch (UnableToLexCharacterException e) {
			e.printStackTrace();
		}
		
		Parser parser = new Parser();
		MainLevelPN mainLevelParseNode = null;
		try {
			mainLevelParseNode = parser.parse(tokens);
			//System.out.println(mainLevelParseNode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
		try {
			semanticAnalyzer.analyze(mainLevelParseNode);
		} catch (Exception e) {
			e.printStackTrace();			
		}
		
		IntermediateLanguageGenerator intermediateLanguageGenerator = new IntermediateLanguageGenerator();
		MainLevelILPN mainLevelILParseNode = null;
		try {
			mainLevelILParseNode = intermediateLanguageGenerator.generatorIL(mainLevelParseNode);
//			System.out.println(mainLevelILParseNode);
//			System.out.println("------");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		CodeGenerator codeGenerator = new CodeGenerator();
		System.out.println(codeGenerator.generateAssembly(mainLevelILParseNode));
	}

	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		return data;
	}
}
