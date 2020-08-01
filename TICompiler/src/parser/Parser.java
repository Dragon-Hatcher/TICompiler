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

		for (int i = tokens.size() - 1; i >= 0; i--) {
			if (tokens.get(i).type == TokenType.COMMENT) {
				tokens.remove(i);
			}
		}

		return parseMainLevel();
	}

	private MainLevelPN parseMainLevel() throws Exception {
		MainLevelPN mainLevel = new MainLevelPN();

		boolean mainFound = false;

		while (!eof()) {
			if (isKw("func")) {
				pop();
				mainLevel.functions.add(parseFunctionDecleration());
			} else if (isKw("main")) {
				Token next = pop();
				if (!mainFound) {
					mainLevel.main = parseInstructionSequence("{", "}");
					mainFound = true;
				} else {
					throw new DuplicateMainException(
							"Only one main function is allowed. A second was found at " + next.lcText() + ".");
				}
			} else {
				Token next = pop();
				throw new IllegalTokenOnRootLevelException("Invalid token " + next + " at " + next.lcText()
						+ ". Only main and func are allowed on the root level.");
			}
		}

		if (!mainFound) {
			throw new MissingMainException("There is no main.");
		}

		return mainLevel;
	}

	private FunctionDeclerationPN parseFunctionDecleration() throws Exception {
		FunctionDeclerationPN function = new FunctionDeclerationPN();

		if (isId()) {
			function.name = pop().text;
		} else {
			throw new UnexpectedTokenException(
					"Expected identifier after func on " + peek().lcText() + ", but instead found " + peek() + ".");
		}

		eatToken(new Token("(", TokenType.SEPERATOR));
		while (true) {
			String type;
			if (isId()) {
				type = pop().text;
			} else {
				throw new UnexpectedTokenException("Expected type in function parameter list on " + peek().lcText()
						+ ", but instead found " + peek() + ".");
			}

			String varName = "";
			if (isId()) {
				varName = pop().text;
			} else {
				throw new UnexpectedTokenException("Expected identifier after func list on " + peek().lcText()
						+ ", but instead found " + peek() + ".");
			}

			VariableDeclerationPN param = new VariableDeclerationPN(type, varName);
			function.parameters.add(param);

			try {
				eatToken(new Token(",", TokenType.SEPERATOR));
			} catch (Exception e) {
				eatToken(new Token(")", TokenType.SEPERATOR));
				break;
			}
		}

		if (isSep("->")) {
			pop();

			if (isId()) {
				function.returnType = pop().text;
			} else {
				throw new UnexpectedTokenException(
						"Expected return type after -> on " + peek().lcText() + ", but instead found " + peek() + ".");
			}
		}

		function.instructions = parseInstructionSequence("{", "}");

		return function;
	}

	private ReturnStatementPN parseReturnStatement() throws Exception {
		return new ReturnStatementPN(
				parseExpression(new Token("return", TokenType.KEYWORD), new Token(";", TokenType.SEPERATOR)));
	}

	private InstructionSequencePN parseInstructionSequence(String start, String end) throws Exception {
		try {
			eatToken(new Token(start, TokenType.SEPERATOR));
		} catch (Exception e) {
			throw new IllegalInstructionSequenceStartException(
					"Instruction sequences must start with " + start + ". The sequence on line " + peek().line
							+ ", col " + peek().col + " starts with " + peek().text + ".");
		}

		InstructionSequencePN instructions = new InstructionSequencePN();

		while (!isSep(end)) {
			
			if (isId()) {
				Token id = pop();
				if (isSep("(")) {
					instructions.instructions.add(parseFunctionCall(id.text));
				} else if (isAssign()) {
					instructions.instructions.add(new AssignmentPN(id.text, peek().text, parseExpression(
							new Token(peek().text, TokenType.ASSIGNMENT), new Token(";", TokenType.SEPERATOR))));
				}
			} else if (isKw("var")) {
				instructions.instructions.addAll(parseVarDecleration(new Token(";", TokenType.SEPERATOR)));
			} else if (isKw("if")) {
				instructions.instructions.add(parseIf());
			} else if (isKw("return")) {
				instructions.instructions.add(parseReturnStatement());
			} else if (isKw("while")) {
				pop();
				Evaluable expression = parseExpression(new Token("(", TokenType.SEPERATOR),
						new Token(")", TokenType.SEPERATOR));
				InstructionSequencePN ifInstructions = parseInstructionSequence("{", "}");
				instructions.instructions.add(new WhileLoopPN(expression, ifInstructions));
			} else if (isKw("break")) {
				instructions.instructions.add(new BreakPN());
				pop();
				eatToken(new Token(";", TokenType.SEPERATOR));
			} else {
				throw new UnexpectedTokenException("Unexpected token " + peek() + " on " + peek().lcText());
			}

			if (eof()) {
				throw new UnterminatedInstructionSequenceException(
						"The instruction sequence at the end of the file must terminate with " + end + ".");
			}
		}
		pop();

		return instructions;
	}

	private IfPN parseIf() throws Exception {
		pop();
		Evaluable expression = parseExpression(new Token("(", TokenType.SEPERATOR),
				new Token(")", TokenType.SEPERATOR));
		InstructionSequencePN ifInstructions = parseInstructionSequence("{", "}");

		if(!isKw("else")) {
			return new IfPN(expression, ifInstructions, null);
		} else {
			pop();
			InstructionSequencePN elseBody = new InstructionSequencePN();

			if(isKw("if")) {
				elseBody.instructions.add(parseIf());
			} else {
				elseBody = parseInstructionSequence("{", "}");
			}
			
			return new IfPN(expression, ifInstructions, elseBody);
		}
	}
	
	private ArrayList<Instruction> parseVarDecleration(Token end) throws Exception {
		eatToken(new Token("var", TokenType.KEYWORD));

		String name;
		if (isId()) {
			name = pop().text;
		} else {
			throw new UnexpectedTokenException(
					"Expected identifier after var on " + peek().lcText() + ", but instead found " + peek() + ".");
		}

		eatToken(new Token(":", TokenType.SEPERATOR));

		String type;
		if (isId()) {
			type = pop().text;
		} else {
			throw new UnexpectedTokenException(
					"Expected identifier after var name on " + peek().lcText() + ", but instead found " + peek() + ".");
		}

		AssignmentPN possibleAssign = null;
		if (isAssign("=")) {
			Evaluable expression = parseExpression(new Token("=", TokenType.ASSIGNMENT),
					new Token(";", TokenType.SEPERATOR));
			possibleAssign = new AssignmentPN(name, "=", expression);
		}

		VariableDeclerationPN decleration = new VariableDeclerationPN(type, name);

		ArrayList<Instruction> ret = new ArrayList<Instruction>();
		ret.add(decleration);
		if (possibleAssign != null) {
			ret.add(possibleAssign);
		}
		return ret;
	}

	private Evaluable parseExpression(Token start, Token end) throws Exception {
		try {
			eatToken(start);
		} catch (Exception e) {
			throw new IllegalExpressionStartException("The expression on " + peek().lcText() + " must start with "
					+ start + ". Instead is starts with " + peek() + ".");
		}

		return parseExpression(end);
	}

	private Evaluable parseExpression(Token end) throws Exception {
		Evaluable ret = parseExpression();

		try {
			eatToken(end);
		} catch (Exception e) {
			throw new IllegalExpressionEndException("The expression on " + peek().lcText() + " must end with " + end
					+ ". Instead is ends with " + peek());
		}

		return ret;
	}

	private Evaluable parseExpression() throws Exception {
		return parseBinary(parseEvaluableAtom(), 0);
	}

	private Evaluable parseBinary(Evaluable left, int myPres) throws Exception {
		if (isOp()) {
			int hisPres = TokenValues.operatorPrecedence.get(peek().text);
			if (hisPres > myPres) {
				Token op = pop();
				Evaluable right = parseBinary(parseEvaluableAtom(), hisPres);
				BinaryExpressionPN binary = new BinaryExpressionPN(left, right, op.text);
				return parseBinary(binary, myPres);
			}
		}
		return left;
	}

	private Evaluable parseEvaluableAtom() throws Exception {
		if (isNumLit()) {
			return new NumLitteralPN(pop().text);
		} else if (isKw("true") || isKw("false")) {
			return new BooleanLitteralPN(pop().text);
		} else if (isSep("(")) {
			return parseExpression(new Token("(", TokenType.SEPERATOR), new Token(")", TokenType.SEPERATOR));
		} else if (isId()) {
			String name = pop().text;
			if (isSep("(")) {
				return parseFunctionCall(name);
			} else {
				return new VariableUsePN(name);
			}
		} else {
			throw new IllegalTokenInExpressionException(
					"Illegal use of token " + peek() + " in an expression on " + peek().lcText() + ".");
		}
	}

	private FunctionCallPN parseFunctionCall(String name) throws Exception {
		eatToken(new Token("(", TokenType.SEPERATOR));

		FunctionCallPN call = new FunctionCallPN();
		call.functionName = name;

		while (true) {
			call.params.add(parseExpression());
			try {
				eatToken(new Token(",", TokenType.SEPERATOR));
			} catch (Exception e) {
				eatToken(new Token(")", TokenType.SEPERATOR));
				break;
			}
		}

		return call;
	}

	private boolean isSep(String punc) {
		return peek().hasProps(punc, TokenType.SEPERATOR);
	}

	private boolean isKw(String kw) {
		return peek().hasProps(kw, TokenType.KEYWORD);
	}

	private boolean isOp() {
		return peek().type == TokenType.OPERATOR;
	}

	private boolean isId() {
		return peek().type == TokenType.IDENTIFIER;
	}

	private boolean isAssign() {
		return peek().type == TokenType.ASSIGNMENT;
	}

	private boolean isAssign(String assign) {
		return peek().hasProps(assign, TokenType.ASSIGNMENT);
	}

	private boolean isNumLit() {
		return peek().type == TokenType.LITERAL_NUM;
	}

	private void eatToken(Token token) throws Exception {
		if (peek().equals(token)) {
			pop();
		} else {
			throw new UnexpectedTokenException(
					"Exspected token " + token + " on line " + peek().lcText() + ". Instead found " + peek() + ".");
		}
	}

	private Token peek() {
		// TODO exception for trying to get something after the end
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
