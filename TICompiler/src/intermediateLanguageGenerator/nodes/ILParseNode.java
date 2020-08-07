package intermediateLanguageGenerator.nodes;

import java.util.Map;

public abstract class ILParseNode {

	public abstract String toString();

	public void updateHighestUsedTemp(Map<String, Integer> uses) {		
	}
	
	public boolean isTemp(String temp) {
		return temp.startsWith("s_temp");
	}
	
	public String getTempType(String temp) {
		return temp.split("_")[2];
	}

	public int getTempNum(String temp) {
		return Integer.parseInt(temp.split("_")[1].split("temp")[1]);
	}

}
