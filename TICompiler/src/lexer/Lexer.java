package lexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Lexer {

	private static final HashSet<String> keywords = new HashSet<String>(Arrays.asList("int", "if", "true", "false"));
	private static final HashSet<String> operators = new HashSet<String>(
			Arrays.asList("=", "==", "+", "-", "&&", "||"));
	private static final HashSet<String> seperators = new HashSet<String>(Arrays.asList("{", "}", "(", ")", ";"));

	private static final String matchKeywordsIdentifiers = "[A-Za-z]*";
	private static final String matchOperators = "[+*\\-\\/=&|]*";
	private static final String matchSeperators = "[{}();]";
	private static final String matchCommentStart = "(\\/\\/)";
	private static final String matchCommentCharacter = "[^\r]";
	private static final String matchNumbers = "[1-9]*";
	private static final char decimalPoint = '.';
	private static final String matchWhiteSpace = "[ \n\r\t]*";
	
	String code = "";
	int position = 0;
	int line = 0;
	int col = 0;

	public ArrayList<Token> lex(String code) throws UnknownOperatorException, UnknownSeperatorException, UnableToLexCharacterException {
		this.code = code;
		
		ArrayList<Token> tokens = new ArrayList<Token>();
		
		while(!eof()) {
			String c1 = peekMulti(1);
			
			if(peekMulti(2).matches(matchCommentStart)) {
				tokens.add(new Token(readWhileRegex(matchCommentCharacter), TokenType.COMMENT));
			} else if (c1.matches(matchKeywordsIdentifiers)) {
				String kwid = readWhileRegex(matchKeywordsIdentifiers);
				if(keywords.contains(kwid)) {
					tokens.add(new Token(kwid, TokenType.KEYWORD));
				} else {
					tokens.add(new Token(kwid, TokenType.IDENTIFIER));					
				}
			} else if (c1.matches(matchSeperators)) {
				if(seperators.contains(c1)) {
					tokens.add(new Token(c1, TokenType.SEPERATOR));
					pop();
				} else {
					throw new UnknownSeperatorException("Unknown operator " + c1 + " at line " + line + ", column " + col);
				}
			} else if (c1.matches(matchOperators)) {
				String possibleOp = readWhileRegex(matchOperators);
				if(operators.contains(possibleOp)) {
					tokens.add(new Token(possibleOp, TokenType.OPERATOR));
				} else {
					throw new UnknownOperatorException("Unknown operator \"" + possibleOp + "\" at line " + line + ", column " + col);
				}
			} else if(c1.matches(matchNumbers)) {
				tokens.add(new Token(readNumber(), TokenType.LITERAL_NUM));
			} else if(c1.matches(matchWhiteSpace)) {
				pop();
			} else {
				throw new UnableToLexCharacterException("Unable to lex character \'" + c1 + "\' at line " + line + ", column " + col);
			}
		}
		
		for(Token i : tokens) {
			System.out.println(i);
		}
		System.out.println("----");
		
		return tokens;
	}

	private String readNumber() {
		StringBuilder number = new StringBuilder();
		
		number.append(readWhileRegex(matchNumbers));
		if(peek() == decimalPoint) {
			number.append(pop());
			number.append(readWhileRegex(matchNumbers));			
		}
		
		return number.toString();
	}
	
	private String readWhileRegex(String regex) {
		StringBuilder string = new StringBuilder();

		while (!eof() && Character.toString(peek()).matches(regex)) {
			string.append(pop());
		}

		return string.toString();
	}

	private char pop() {
		char c = code.charAt(position);
		position++;
		if (c == '\n') {
			line++;
			col = 0;
		} else {
			col++;
		}
		return c;
	}

	private char peek() {
		return code.charAt(position);
	}

	private String peekMulti(int length) {
		return code.substring(position, Math.min(position + length, code.length()));
	}

	private boolean eof() {
		return code.length() <= position;
	}
}
