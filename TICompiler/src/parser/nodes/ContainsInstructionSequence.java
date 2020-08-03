package parser.nodes;

import java.util.Map;
import java.util.Set;

public interface ContainsInstructionSequence {
	public boolean willReturn();
	public void hasIllegalBreak() throws Exception;
	public void hasIllegalDeclerationType(Set<String> types) throws Exception;
}
