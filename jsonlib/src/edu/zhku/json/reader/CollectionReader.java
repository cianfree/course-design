package edu.zhku.json.reader;

import java.util.Collection;

import edu.zhku.json.Json;

public class CollectionReader implements JsonReader {

	@SuppressWarnings("unchecked")
	@Override
	public String read(Object src) {
		if(src == null) return "null";
		Collection<Object> collections = (Collection<Object>) src;
		if(collections.size() == 0) return "[]";
		StringBuilder json = new StringBuilder("[");
		for (Object obj : collections) {
			json.append(Json.toJson(obj)).append(",");
		}
		return json.substring(0, json.length() - 1) + "]";
	}

}
