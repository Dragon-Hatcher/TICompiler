package parser;

import java.util.ArrayList;

import lexer.Token;
import lexer.TokenType;
import parser.nodes.*;
import parser.exceptions.*;

public class Parser {
	
	private int position = 0;
	private ArrayList<Token> tokens = new ArrayList<Token>();
	
	public MainLevelPN parse(ArrayList<Token> tokens) throws Exception {
		this.tokens = tokens;
		
		for(int i = tokens.size() - 1; i >= 0; i--) {
			if(tokens.get(i).type == TokenType.COMMENT) {
				tokens.remove(i);
			}
		}
		
		return parseMainLevel();
	}
	
	private MainLevelPN parseMainLevel() throws Exception {
		MainLevelPN mainLevel = new MainLevelPN();
		
		boolean mainFound = false;
		
		while(!eof()) {
			Token next = pop();
			if(next.hasProps("func", TokenType.KEYWORD)) {
				mainLevel.functions.add(parseFunction());
			} else if (next.hasProps("main", TokenType.KEYWORD)) {
				if(!mainFound) {
					mainLevel.main = parseInstructionSequence();
					mainFound = true;
				} else {
					throw new DuplicateMainException("Only one main function is allowed. A second was found at line " + next.line + ", col " + next.col + ".");
				}
			} else {
				throw new IllegalTokenOnRootLevelException("Invalid token " + next.text + " of type " + next.type.toString() + " at line " + next.line + ", col " + next.col + ". Only main and func are allowed on the root level.");
			}
		}
		
		if(!mainFound) {
			throw new MissingMainException("There is no main.");
		}
		
		return mainLevel;
	}
	
	private FunctionDeclerationPN parseFunction() {
		while(!eof() && !peek().hasProps("{", TokenType.SEPERATOR)) {
			pop();
		}
		while(!eof() && !peek().hasProps("}", TokenType.SEPERATOR)) {
			pop();
		}
		pop();
			
		return new FunctionDeclerationPN();
	}
	
	private InstructionSequencePN parseInstructionSequence() {		
		InstructionSequencePN instructions = new InstructionSequencePN();
		
		int level = 0;

		do {
			Token next = pop();
			if(next.hasProps("{", TokenType.SEPERATOR)) {
				level++;
			} else if (next.hasProps("}", TokenType.SEPERATOR)) {
				level--;
			}
		} while(!eof() && level != 0);

		if(!eof()) {
			pop();
		}
		return new InstructionSequencePN();
	}
	
	private Token peek() {
		return tokens.get(position);
	}
	
	private Token pop() {
		Token token = tokens.get(position);
		position++;
		return token;
	}
	
	private boolean eof() {
		return tokens.size() <= position;
	}
	
}
 