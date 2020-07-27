package lexer;

public class UnknownOperatorException extends Exception {
    public UnknownOperatorException(String errorMessage) {
        super(errorMessage);
    }
}
