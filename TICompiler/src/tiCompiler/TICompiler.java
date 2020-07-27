package tiCompiler;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import lexer.Lexer;
import lexer.UnableToLexCharacterException;
import lexer.UnknownOperatorException;
import lexer.UnknownSeperatorException;

public class TICompiler {

	public static void main(String[] args) {
		String code = "";
		try {
			code = readFileAsString("./res/testCode");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Lexer lexer = new Lexer();
		try {
			lexer.lex(code);
		} catch (UnknownOperatorException e) {
			e.printStackTrace();
		} catch (UnknownSeperatorException e) {
			e.printStackTrace();
		} catch (UnableToLexCharacterException e) {
			e.printStackTrace();
		}
	}

	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		return data;
	}
}
