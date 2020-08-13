package langaugeConstructs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Map.Entry;

public class TokenValues {

	public static final HashSet<String> keywords = new HashSet<String>(Arrays.asList("var", "if", "else", "true", "false", "main", "func", "return", "while", "break", "void", "print", "raw", "rawf"));
	public static final HashSet<String> operators = new HashSet<String>(
			Arrays.asList("==", "!=", "+", "-", "*", "/", "&&", "||", "<", ">", "<=", ">="));
	public static final HashSet<String> assignments = new HashSet<String>(Arrays.asList("=", "+=", "-=", "*=", "/="));
	public static final HashSet<String> seperators = new HashSet<String>(Arrays.asList("{", "}", "(", ")", ";", ":", ",", "->"));

	public static final Map<String, Integer> operatorPrecedence = Map.ofEntries(
			Map.entry("(", 1500),
			Map.entry(")", 1500),
			Map.entry("*", 1200),
			Map.entry("/", 1200),
			Map.entry("+", 1100),
			Map.entry("-", 1100),
			Map.entry("<", 900),
			Map.entry("<=", 900),
			Map.entry(">", 900),
			Map.entry(">=", 900),
			Map.entry("==", 800),
			Map.entry("!=", 800),
			Map.entry("&&", 400),
			Map.entry("||", 300),
			Map.entry("=", 100),
			Map.entry("+=", 100),
			Map.entry("-=", 100),
			Map.entry("*=", 100),
			Map.entry("/=", 100)
	);
	
	public static final Map<String, Set<String>> opTypes = Map.ofEntries(
			Map.entry("+", Set.of("int", "float")), // v x
			Map.entry("-", Set.of("int", "float")), // v x
			Map.entry("*", Set.of("int", "float")), // x x
			Map.entry("/", Set.of("int", "float")), // x x
			Map.entry("<", Set.of("int", "float", "char")), // x x x 
			Map.entry("<=", Set.of("int", "float", "char")), // x x x
			Map.entry(">", Set.of("int", "float", "char")), // x x x
			Map.entry(">=", Set.of("int", "float", "char")), // x x x
			Map.entry("==", Set.of("int", "float", "char", "bool")), // v x x v
			Map.entry("!=", Set.of("int", "float", "char", "bool")), // x x x v
			Map.entry("&&", Set.of("bool")), // v
			Map.entry("||", Set.of("bool")) // v
	);
	
	public static final Map<String, Map<String, String>> opReturnTypes = Map.ofEntries(
			Map.entry("+", Map.ofEntries(   Map.entry("int", "int"),Map.entry("float", "float"))),
			Map.entry("-", Map.ofEntries(   Map.entry("int", "int"),Map.entry("float", "float"))),
			Map.entry("*", Map.ofEntries(   Map.entry("int", "int"),Map.entry("float", "float"))),
			Map.entry("/", Map.ofEntries(   Map.entry("int", "int"),Map.entry("float", "float"))),
			Map.entry("<", Map.ofEntries(   Map.entry("int", "bool"),Map.entry("float", "bool"),Map.entry("char", "bool"))),
			Map.entry("<=", Map.ofEntries(   Map.entry("int", "bool"),Map.entry("float", "bool"),Map.entry("char", "bool"))),
			Map.entry(">", Map.ofEntries(   Map.entry("int", "bool"),Map.entry("float", "bool"),Map.entry("char", "bool"))),
			Map.entry(">=", Map.ofEntries(   Map.entry("int", "bool"),Map.entry("float", "bool"),Map.entry("char", "bool"))),
			Map.entry("==", Map.ofEntries(   Map.entry("int", "bool"),Map.entry("float", "bool"),Map.entry("char", "bool"),Map.entry("bool", "bool"))),
			Map.entry("!=", Map.ofEntries(   Map.entry("int", "bool"),Map.entry("float", "bool"),Map.entry("char", "bool"),Map.entry("bool", "bool"))),
			Map.entry("&&", Map.ofEntries(   Map.entry("bool", "bool"))),
			Map.entry("||", Map.ofEntries(   Map.entry("bool", "bool")))
			);
	
	
}
