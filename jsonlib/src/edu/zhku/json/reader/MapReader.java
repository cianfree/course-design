package edu.zhku.json.reader;

import java.util.Map;
import java.util.Set;

import edu.zhku.json.Json;

public class MapReader implements JsonReader {

	@SuppressWarnings("unchecked")
	@Override
	public String read(Object src) {
		if(src == null ) return "null";
		Map<Object, Object> map = null;
		StringBuilder json = new StringBuilder("{");
		try {
			map = (Map<Object, Object>) src;
			Set<Map.Entry<Object, Object>> sets = map.entrySet();
			for (Map.Entry<Object, Object> entry : sets) {
				Object keyObj = entry.getKey();
				Object valObj = entry.getValue();
				String key = Json.toJson(keyObj);
				String value = Json.toJson(valObj);
				if(!"null".equals(value)) {
					json.append(key).append(":").append(value).append(",");
				}
			}
			return json.substring(0, json.length() - 1) + "}";
		} catch (Exception e) {
		}
		return "null";
	}

}
