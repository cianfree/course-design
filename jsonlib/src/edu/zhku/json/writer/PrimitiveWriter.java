package edu.zhku.json.writer;

/**
 * 原始类型的转换器
 * @author arvin
 *
 */
public class PrimitiveWriter implements JsonWriter {

	@Override
	public Object write(String json, Class<?> type) {
		if(json == null || "".equals(json) || type == null) return null;
		if(type == String.class) return json;
		if(type == int.class || type == Integer.class) return getInt(json);
		if(type == float.class || type == Float.class) return getFloat(json);
		if(type == long.class || type == Long.class) return getLong(json);
		if(type == byte.class || type == Byte.class) return getByte(json);
		if(type == short.class || type == Short.class) return getShort(json);
		if(type == double.class || type == Double.class) return getDouble(json);
		if(type == char.class || type == Character.class) return getChar(json);
		return null;
	}

	private Short getShort(String json) {
		try {
			return Short.parseShort(json);
		} catch (NumberFormatException e) {
		}
		return null;
	}

	private Character getChar(String json) {
		try {
			return json.charAt(0);
		} catch (Exception e) {
		}
		return null;
	}

	private Double getDouble(String json) {
		try {
			return Double.parseDouble(json);
		} catch (NumberFormatException e) {
		}
		return null;
	}

	private Byte getByte(String json) {
		try {
			return Byte.parseByte(json);
		} catch (NumberFormatException e) {
		}
		return null;
	}

	private Long getLong(String json) {
		try {
			return Long.parseLong(json);
		} catch (NumberFormatException e) {
		}
		return null;
	}

	private Float getFloat(String json) {
		try {
			return Float.parseFloat(json);
		} catch (NumberFormatException e) {
		}
		return null;
	}

	private Integer getInt(String json) {
		try {
			return Integer.parseInt(json);
		} catch (NumberFormatException e) {
		}
		return null;
	}

}
