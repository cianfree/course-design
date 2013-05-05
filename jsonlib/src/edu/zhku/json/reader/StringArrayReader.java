package edu.zhku.json.reader;

/**
 * 
 * @deprecated
 * @see PrimitiveArrayReader
 * {@link PrimitiveArrayReader}
 * @author arvin
 *
 */
@Deprecated
public class StringArrayReader implements JsonReader {

	@Override
	public String read(Object src) {
		if(src == null) return "null";
		if(src.getClass() == char[].class) {
			char[] arr = (char[]) src;
			StringBuilder json = new StringBuilder("[");
			if(arr.length == 0) return "null";
			for (char c : arr) {
	            json.append("\"").append(c).append("\",");
            }
			return json.substring(0, json.length() - 1) + "]";
		} else if(src.getClass() == Character[].class) {
			Character[] arr = (Character[]) src;
			StringBuilder json = new StringBuilder("[");
			if(arr.length == 0) return "null";
			for (Character c : arr) {
	            json.append("\"").append(c).append("\",");
            }
			return json.substring(0, json.length() - 1) + "]";
		} else if(src.getClass() == String[].class) {
			String[] arr = (String[]) src;
			StringBuilder json = new StringBuilder("[");
			if(arr.length == 0) return "null";
			for (String val : arr) {
	            json.append("\"").append(val).append("\",");
            }
			return json.substring(0, json.length() - 1) + "]";
		}
		return "null";
	}

}
