package parser;

import java.util.ArrayList;

import langaugeConstructs.TokenValues;
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
			if(isKw("func")) {
				pop();
				mainLevel.functions.add(parseFunctionDecleration());
			} else if (isKw("main")) {
				Token next = pop();
				System.out.println(next);
				if(!mainFound) {
					mainLevel.main = parseInstructionSequence("{", "}");
					mainFound = true;
				} else {
					throw new DuplicateMainException("Only one main function is allowed. A second was found at line " + next.line + ", col " + next.col + ".");
				}
			} else {
				Token next = pop();
				throw new IllegalTokenOnRootLevelException("Invalid token " + next.text + " of type " + next.type.toString() + " at line " + next.line + ", col " + next.col + ". Only main and func are allowed on the root level.");
			}
		}
		
		if(!mainFound) {
			throw new MissingMainException("There is no main.");
		}
		
		return mainLevel;
	}
	
	private FunctionDeclerationPN parseFunctionDecleration() throws Exception {
		return new FunctionDeclerationPN();
	}
	
	private InstructionSequencePN parseInstructionSequence(String start, String end) throws Exception {		
		InstructionSequencePN instructions = new InstructionSequencePN();

		if(!isSep(start)) {
			throw new IllegalInstructionSequenceStartException("Instruction sequences must start with " + start + ". The sequence on line " + peek().line + ", col " + peek().col + " starts with " + peek().text + ".");
		} else {
			pop();
		}
		
		while(!isSep(end)) {
			
			if(isSep("(")) {
				instructions.instructions.add(new AssignmentPN("test", parseExpression("(", ")")));
			} else {
				Token next = pop();
			}
			
			if(eof()) {
				throw new UnterminatedInstructionSequenceException("The instruction sequence at the end of the file must terminate with " + end + ".");
			}
		}
		pop();

		return instructions;
	}
	
	private Evaluable parseExpression(String start, String end) throws Exception {
		if(!isSep(start)) {
			throw new IllegalExpressionStartException("The expression on " + peek().lcText() + " must start with " + start + ". Instead is starts with " + peek());
		} else {
			pop();
		}
		
		Evaluable ret = parseBinary(parseEvaluableAtom(), 0);
		
		if(!isSep(end)) {
			throw new IllegalExpressionEndException("The expression on " + peek().lcText() + " must end with " + start + ". Instead is ends with " + peek());
		} else {
			pop();
		}
		
		return ret;
	}
	
	private Evaluable parseBinary(Evaluable left, int myPres) throws Exception {
		if(isOp()) {
			int hisPres = TokenValues.operatorPrecedence.get(peek().text);
			if(hisPres > myPres) {
				Token op = pop();
				Evaluable right = parseBinary(parseEvaluableAtom(), hisPres);
				BinaryExpressionPN binary = new BinaryExpressionPN(left, right, op.text);
				return parseBinary(binary, myPres);
			}
		}
		return left;
	}
	
	private Evaluable parseEvaluableAtom() throws Exception {
		if(isNumLit()) {
			return new NumLitteralPN(pop().text);
		} else if(isSep("(")) {
			return parseExpression("(", ")");
		} else if (isId()) {
			String name = pop().text;
			if(isSep("(")) {
				return parseFunctionCall(name);
			} else {
				return new VariableUsePN(name);
			}
		} else {
			throw new IllegalTokenInExpressionException("Illegal use of token " + peek() + " in an expression on " + peek().lcText() + ".");
		}
	}
		
	private FunctionCallPN parseFunctionCall(String name) {
		return new FunctionCallPN();
	}
	
	private boolean isSep(String punc) {
		return peek().hasProps(punc, TokenType.SEPERATOR);
	}

	private boolean isKw(String punc) {
		return peek().hasProps(punc, TokenType.KEYWORD);
	}

	private boolean isOp() {
		return peek().type == TokenType.OPERATOR;
	}
	
	private boolean isId() {
		return peek().type == TokenType.IDENTIFIER;
	}
	
	private boolean isNumLit() {
		return peek().type == TokenType.LITERAL_NUM;
	}
	
	private Token peek() {
		//TODO exception for trying to get something after the end
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
 