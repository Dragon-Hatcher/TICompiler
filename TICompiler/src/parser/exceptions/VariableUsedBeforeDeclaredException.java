package parser.exceptions;

public class VariableUsedBeforeDeclaredException extends Exception {
	public VariableUsedBeforeDeclaredException(String message) {
		super(message);
	}
}
