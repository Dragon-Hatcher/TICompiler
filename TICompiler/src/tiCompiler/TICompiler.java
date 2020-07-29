package tiCompiler;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import lexer.Lexer;
import lexer.Token;
import lexer.UnableToLexCharacterException;
import lexer.UnknownOperatorException;
import lexer.UnknownSeperatorException;
import parser.Parser;

public class TICompiler {

	public static void main(String[] args) {
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
		try {
			System.out.println(parser.parse(tokens));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		return data;
	}
}
