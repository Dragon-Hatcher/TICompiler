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
		mainLevel.lineCol(peek().line, peek().col);
		
		boolean mainFound = false;

		while (!eof()) {
			if (isKw("func")) {
				int line = peek().line;
				int col = peek().col;
				pop();
				FunctionDeclerationPN lastFunc = parseFunctionDecleration();
				lastFunc.lineCol(line, col);
				mainLevel.functions.add(lastFunc);
			} else if (isKw("main")) {
				int line = peek().line;
				int col = peek().col;
				pop();
				if (!mainFound) {
					mainLevel.main = parseInstructionSequence("{", "}");
					mainLevel.main.lineCol(line, col);
					mainFound = true;
				} else {
					throw new DuplicateMainException(
							"Only one main function is allowed. One was found at " + mainLevel.main.lcText() + ". A second was found at line " + line + ", col " + col + ".");
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

		if (!isSep(")")) {
			while (true) {
				int line = peek().line;
				int col = peek().col;

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
				param.lineCol(line, col);
				function.parameters.add(param);

				try {
					eatToken(new Token(",", TokenType.SEPERATOR));
				} catch (Exception e) {
					eatToken(new Token(")", TokenType.SEPERATOR));
					break;
				}
			}
		} else {
			eatToken(new Token(")", TokenType.SEPERATOR));
		}

		if (isSep("->")) {
			pop();
			
			if (isKw("void")) {
				pop();
			} else if (isId()) {
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
		int line = peek().line;
		int col = peek().col;
		eatToken(new Token("return", TokenType.KEYWORD));
		if(isSep(";")) {
			eatToken(new Token(";", TokenType.SEPERATOR));
			ReturnStatementPN ret = new ReturnStatementPN();
			ret.lineCol(line, col);
			return new ReturnStatementPN();
		} else {
			ReturnStatementPN ret = new ReturnStatementPN(parseExpression(new Token(";", TokenType.SEPERATOR)));
			ret.lineCol(line, col);
			return ret;
		}
	}

	private InstructionSequencePN parseInstructionSequence(String start, String end) throws Exception {
		int lineS = peek().line;
		int colS = peek().col;
		try {
			eatToken(new Token(start, TokenType.SEPERATOR));
		} catch (Exception e) {
			throw new IllegalInstructionSequenceStartException(
					"The instruction sequence on " + peek().lcText() + " must start with " + start + ". Instead it starts with " + peek() + ".");
		}

		InstructionSequencePN instructions = new InstructionSequencePN();
		instructions.lineCol(lineS, colS);
		
		while (!isSep(end)) {

			if (isId()) {
				int line = peek().line;
				int col = peek().col;
				Token id = pop();
				if (isSep("(")) {
					FunctionCallPN call = parseFunctionCall(id.text);
					call.lineCol(line, col);
					instructions.instructions.add(call);
					eatToken(new Token(";", TokenType.SEPERATOR));
				} else if (isAssign()) {
					Token op = peek();
					Evaluable toAssign = parseExpression(new Token(peek().text, TokenType.ASSIGNMENT), new Token(";", TokenType.SEPERATOR));
					if(!op.text.equals("=")) {
						VariableUsePN var = new VariableUsePN(id.text);
						toAssign = new BinaryExpressionPN(var, toAssign, op.text.substring(0, op.text.length() - 1));
					}
					AssignmentPN assign = new AssignmentPN(id.text, "=", toAssign);
					assign.lineCol(line, col);
					instructions.instructions.add(assign);
				}
			} else if (isKw("print")) {
				instructions.instructions.add(new PrintPN());
				eatToken(new Token("print", TokenType.KEYWORD));
				eatToken(new Token(";", TokenType.SEPERATOR));
			} else if (isKw("var")) {
				instructions.instructions.addAll(parseVarDecleration(new Token(";", TokenType.SEPERATOR)));
			} else if (isKw("if")) {
				instructions.instructions.add(parseIf());
			} else if (isKw("return")) {
				instructions.instructions.add(parseReturnStatement());
			} else if (isKw("while")) {
				instructions.instructions.add(parseWhile());
			} else if (isKw("break")) {
				BreakPN breakPN = new BreakPN();
				breakPN.lineCol(peek().line, peek().col);
				instructions.instructions.add(breakPN);
				eatToken(new Token("break", TokenType.KEYWORD));
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

	private WhileLoopPN parseWhile() throws Exception {
		int line = peek().line;
		int col = peek().col;
		eatToken(new Token("while", TokenType.KEYWORD));
		Evaluable expression = parseExpression(new Token("(", TokenType.SEPERATOR),
				new Token(")", TokenType.SEPERATOR));
		InstructionSequencePN whileInstructions = parseInstructionSequence("{", "}");
		WhileLoopPN whileLoop = new WhileLoopPN(expression, whileInstructions);
		whileLoop.lineCol(line, col);
		return whileLoop;
	}
	
	private IfPN parseIf() throws Exception {
		int line = peek().line;
		int col = peek().col;
		eatToken(new Token("if", TokenType.KEYWORD));
		Evaluable expression = parseExpression(new Token("(", TokenType.SEPERATOR),
				new Token(")", TokenType.SEPERATOR));
		InstructionSequencePN ifInstructions = parseInstructionSequence("{", "}");

		if (!isKw("else")) {
			IfPN ifPN = new IfPN(expression, ifInstructions, null);
			ifPN.lineCol(line, col);
			return ifPN;
		} else {
			pop();
			InstructionSequencePN elseBody = new InstructionSequencePN();

			if (isKw("if")) {
				elseBody.instructions.add(parseIf());
			} else {
				elseBody = parseInstructionSequence("{", "}");
			}

			IfPN ifPN = new IfPN(expression, ifInstructions, elseBody);
			ifPN.lineCol(line, col);
			return ifPN;
		}
	}

	private ArrayList<Instruction> parseVarDecleration(Token end) throws Exception {
		int line = peek().line;
		int col = peek().col;
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
					"Expected type identifier after var name on " + peek().lcText() + ", but instead found " + peek() + ".");
		}

		AssignmentPN assign = null;
		if (isAssign("=")) {
			Evaluable expression = parseExpression(new Token("=", TokenType.ASSIGNMENT),
					new Token(";", TokenType.SEPERATOR));
			assign = new AssignmentPN(name, "=", expression);
			assign.lineCol(line, col);
		} else {
			throw new UnexpectedTokenException(
					"Expected = after var type on " + peek().lcText() + ", but instead found " + peek() + ".");
		}

		VariableDeclerationPN decleration = new VariableDeclerationPN(type, name);
		decleration.lineCol(line, col);		
		
		ArrayList<Instruction> ret = new ArrayList<Instruction>();
		ret.add(decleration);
		ret.add(assign);
		return ret;
	}

	private Evaluable parseExpression(Token start, Token end) throws Exception {
		try {
			eatToken(start);
		} catch (Exception e) {
			throw new IllegalExpressionStartException("The expression on " + peek().lcText() + " must start with "
					+ start + ". Instead is starts with " + peek() + ".");
		}

		int line = peek().line;
		int col = peek().col;
		Evaluable ret = parseExpression(end);
		((ParseNode)ret).lineCol(line, col);
		return ret;
	}

	private Evaluable parseExpression(Token end) throws Exception {
		int line = peek().line;
		int col = peek().col;
		Evaluable ret = parseExpression();
		((ParseNode)ret).lineCol(line, col);
		
		try {
			eatToken(end);
		} catch (Exception e) {
			throw new IllegalExpressionEndException("The expression on " + peek().lcText() + " must end with " + end
					+ ". Instead is ends with " + peek());
		}

		return ret;
	}

	private Evaluable parseExpression() throws Exception {
		int line = peek().line;
		int col = peek().col;
		Evaluable ret = parseBinary(parseEvaluableAtom(), 0);
		((ParseNode)ret).lineCol(line, col);
		return ret;
	}

	private Evaluable parseBinary(Evaluable left, int myPres) throws Exception {
		if (isOp()) {
			int hisPres = TokenValues.operatorPrecedence.get(peek().text);
			if (hisPres > myPres) {
				int line = peek().line;
				int col = peek().col;
				Token op = pop();
				Evaluable right = parseBinary(parseEvaluableAtom(), hisPres);
				BinaryExpressionPN binary = new BinaryExpressionPN(left, right, op.text);
				binary.lineCol(line, col);
				Evaluable ret = parseBinary(binary, myPres);
				return ret;
			}
		}
		return left;
	}

	private Evaluable parseEvaluableAtom() throws Exception {
		int line = peek().line;
		int col = peek().col;
		if (isNumLit()) {
			NumLitteralPN ret =  new NumLitteralPN(pop().text);
			ret.lineCol(line, col);
			return ret;
		} else if (isKw("true") || isKw("false")) {
			BooleanLitteralPN ret = new BooleanLitteralPN(pop().text);
			ret.lineCol(line, col);
			return ret;
		} else if (isSep("(")) {
			return parseExpression(new Token("(", TokenType.SEPERATOR), new Token(")", TokenType.SEPERATOR));
		} else if (isId()) {
			String name = pop().text;
			if (isSep("(")) {
				FunctionCallPN ret = parseFunctionCall(name);
				ret.lineCol(line, col);
				return ret;
			} else {
				VariableUsePN ret = new VariableUsePN(name);
				ret.lineCol(line, col);
				return ret;
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

		if (!isSep(")")) {
			while (true) {
				call.params.add(parseExpression());

				try {
					eatToken(new Token(",", TokenType.SEPERATOR));
				} catch (Exception e) {
					eatToken(new Token(")", TokenType.SEPERATOR));
					break;
				}
			}
		} else {
			eatToken(new Token(")", TokenType.SEPERATOR));
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
