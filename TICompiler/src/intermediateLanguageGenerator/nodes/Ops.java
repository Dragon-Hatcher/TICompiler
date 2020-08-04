package intermediateLanguageGenerator.nodes;

public enum Ops {
	PLUS,
	MINUS,
	MULTIPLY,
	DIVIDE,
	AND,
	OR,
	XOR,
	EQUAL,
	NOT_EQUAL;
	
	public static Ops fromPNOp(String parseNodeOp) throws Exception {
		switch(parseNodeOp) {
		case "+": return PLUS;
		case "-": return MINUS;
		case "*": return MULTIPLY;
		case "/": return DIVIDE;
		case "&&": return AND;
		case "||": return OR;
		case "==": return EQUAL;
		case "!=": return NOT_EQUAL;
		default: throw new Exception("Parse node op " + parseNodeOp + " doesn't convert");
		}
	}
}
