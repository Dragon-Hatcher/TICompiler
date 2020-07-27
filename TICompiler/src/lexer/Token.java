package lexer;

public class Token {
	public String text;
	public TokenType type;
	
	public Token(String text, TokenType type) {
		this.text = text;
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type.toString() + ": " + text;
	}
}
