package lexer;

public class UnableToLexCharacterException extends Exception {
    public UnableToLexCharacterException(String errorMessage) {
        super(errorMessage);
    }
}
