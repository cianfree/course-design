package edu.zhku.json.reader;

public class StringReader implements JsonReader {

	@Override
	public String read(Object src) {
		return src == null ? "null" : "\"" + src.toString().trim() + "\"";
	}

}
