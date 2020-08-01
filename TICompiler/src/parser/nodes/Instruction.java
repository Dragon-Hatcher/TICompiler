package parser.nodes;

import java.util.Set;

public interface Instruction {
	public String toString();
	public boolean willReturn();
	public boolean hasIllegalBreak();
	public String hasIllegalDeclerationType(Set<String> types);
}