package toolkit;

import java.util.HashMap;
import java.util.Map;

public class Copy {

	private Copy() {
	}
	
	public static Map<String, String> deepCopyMap(Map<String, String> oldMap) {
		Map<String, String> newMap = new HashMap<String, String>();
		
		for(String key : oldMap.keySet()) {
			newMap.put(key, oldMap.get(key));
		}
		
		return newMap;
	}

}
