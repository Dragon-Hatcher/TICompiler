package langaugeConstructs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import static java.util.Map.Entry;

public class TokenValues {

	public static final HashSet<String> keywords = new HashSet<String>(Arrays.asList("var", "if", "true", "false", "main", "func"));
	public static final HashSet<String> operators = new HashSet<String>(
			Arrays.asList("==", "+", "-", "*", "/", "&&", "||"));
	public static final String assignment = "=";
	public static final HashSet<String> seperators = new HashSet<String>(Arrays.asList("{", "}", "(", ")", ";", ":"));

	public static final Map<String, Integer> operatorPrecedence = Map.ofEntries(
			Map.entry("(", 1500),
			Map.entry(")", 1500),
			Map.entry("*", 1200),
			Map.entry("/", 1200),
			Map.entry("+", 1100),
			Map.entry("-", 1100),
			Map.entry("==", 800),
			Map.entry("&&", 400),
			Map.entry("||", 300),
			Map.entry("=", 100)
	);
			
	public static final Map<String, Boolean> operatorAssociativityIsLTR = Map.ofEntries(
			Map.entry("(", true),
			Map.entry(")", true),
			Map.entry("==", true),
			Map.entry("*", true),
			Map.entry("/", true),
			Map.entry("+", true),
			Map.entry("-", true),
			Map.entry("&&", true),
			Map.entry("||", true),
			Map.entry("=", false)
	);
	
}
