package lexer;

public class Token {
	public String text;
	public TokenType type;
	
	public int line = 0;
	public int col = 0;
	
	public Token(String text, TokenType type) {
		this.text = text;
		this.type = type;
	}
	
	@Override
	public String toString() {
		return type.toString() + ": " + text;
	}
	
	public boolean equals(Token other) {
		return other.text.equals(text) && other.type == type;
	}
	
	public boolean hasProps(String text, TokenType type) {
		return this.text.equals(text) && this.type == type;
	}
	
	public void lineCol(int line, int col) {
		this.line = line + 1;
		this.col = col + 1;
	}
	
	public String lcText() {
		return "line " + line + ", col " + col; 
	}
}
