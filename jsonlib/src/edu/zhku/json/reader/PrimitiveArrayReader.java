package edu.zhku.json.reader;

import java.util.Arrays;

public class PrimitiveArrayReader implements JsonReader {

	@Override
	public String read(Object src) {
		if (src == null)
			return "null";
		Class<?> srcClass = src.getClass();
		if (srcClass == byte[].class) {
			return Arrays.toString((byte[]) src);
		} else if (srcClass == int[].class) {
			return Arrays.toString((int[]) src);
		} else if (srcClass == long[].class) {
			return Arrays.toString((long[]) src);
		} else if (srcClass == float[].class) {
			return Arrays.toString((float[]) src);
		} else if (srcClass == double[].class) {
			return Arrays.toString((double[]) src);
		} else if (srcClass == Byte[].class) {
			return Arrays.toString((Byte[]) src);
		} else if (srcClass == Integer[].class) {
			return Arrays.toString((Integer[]) src);
		} else if (srcClass == Long[].class) {
			return Arrays.toString((Long[]) src);
		} else if (srcClass == Float[].class) {
			return Arrays.toString((Float[]) src);
		} else if (srcClass == Double[].class) {
			return Arrays.toString((Double[]) src);
		} else if (src.getClass() == char[].class) {
			char[] arr = (char[]) src;
			StringBuilder json = new StringBuilder("[");
			if (arr.length == 0)
				return "null";
			for (char c : arr) {
				json.append("\"").append(c).append("\",");
			}
			return json.substring(0, json.length() - 1) + "]";
		} else if (src.getClass() == Character[].class) {
			Character[] arr = (Character[]) src;
			StringBuilder json = new StringBuilder("[");
			if (arr.length == 0)
				return "null";
			for (Character c : arr) {
				json.append("\"").append(c).append("\",");
			}
			return json.substring(0, json.length() - 1) + "]";
		} else if (src.getClass() == String[].class) {
			String[] arr = (String[]) src;
			StringBuilder json = new StringBuilder("[");
			if (arr.length == 0)
				return "null";
			for (String val : arr) {
				json.append("\"").append(val).append("\",");
			}
			return json.substring(0, json.length() - 1) + "]";
		}
		return "null";
	}

}
