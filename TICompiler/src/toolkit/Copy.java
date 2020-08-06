package toolkit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Copy {

	private Copy() {
	}
	
	public static Map<String, String> deepCopyMapSS(Map<String, String> oldMap) {
		Map<String, String> newMap = new HashMap<String, String>();
		
		for(String key : oldMap.keySet()) {
			newMap.put(key, oldMap.get(key));
		}
		
		return newMap;
	}

	public static Map<String, Integer> deepCopyMapSI(Map<String, Integer> oldMap) {
		Map<String, Integer> newMap = new HashMap<String, Integer>();
		
		for(String key : oldMap.keySet()) {
			newMap.put(key, oldMap.get(key));
		}
		
		return newMap;
	}

	public static Set<String> deepCopySet(Set<String> oldSet) {
		Set<String> newSet = new HashSet<String>();
		for(String val : oldSet) {
			newSet.add(val);
		}
		return newSet;
	}

}
