package edu.zhku.json.writer;

import edu.zhku.json.JsonParseException;
import edu.zhku.json.JsonUtils;

public class PrimitiveArrayWriter implements JsonWriter {

	@Override
	public Object write(String json, Class<?> type) throws JsonParseException {
		if (type == null)
			return null;
		String[] array = JsonUtils.getJsonArray(json);
		if (int[].class == type) return getIntArray(array);
		if (long[].class == type) return getLongArray(array);
		if (String[].class == type) return array;
		if (char[].class == type) return getCharArray(array);
		if (Character[].class == type) return getArray(new Character[array.length], Character.class, array);
		if (Long[].class == type) return getArray(new Long[array.length], Long.class, array);
		if (double[].class == type) return getDoubleArray(array);
		if (float[].class == type) return getFloatArray(array);
		if (byte[].class == type) return getByteArray(array);
		if (Integer[].class == type) return getArray(new Integer[array.length], Integer.class, array);
		if (Float[].class == type) return getArray(new Float[array.length], Float.class, array);
		if (Double[].class == type) return getArray(new Double[array.length], Double.class, array);
		return null;
	}

	private char[] getCharArray(String[] array) {
		char[] arr = new char[array.length];
		for(int i=0; i<array.length; ++i) arr[i] = array[i].charAt(0);
		return arr;
	}

	private long[] getLongArray(String[] array) {
		long[] arr = new long[array.length];
		try {
			for (int i = 0; i < array.length; i++)
				arr[i] = Integer.parseInt(array[i]);
			return arr;
		} catch (NumberFormatException e) {
			throw new JsonParseException(e);
		}
	}

	private double[] getDoubleArray(String[] array) {
		double[] arr = new double[array.length];
		try {
			for (int i = 0; i < array.length; i++)
				arr[i] = Double.parseDouble(array[i]);
			return arr;
		} catch (NumberFormatException e) {
			throw new JsonParseException(e);
		}
	}

	private float[] getFloatArray(String[] array) {
		float[] arr = new float[array.length];
		try {
			for (int i = 0; i < array.length; i++)
				arr[i] = Float.parseFloat(array[i]);
			return arr;
		} catch (NumberFormatException e) {
			throw new JsonParseException(e);
		}
	}

	private byte[] getByteArray(String[] array) {
		byte[] arr = new byte[array.length];
		try {
			for (int i = 0; i < array.length; i++)
				arr[i] = Byte.parseByte(array[i]);
			return arr;
		} catch (NumberFormatException e) {
			throw new JsonParseException(e);
		}
	}

	private int[] getIntArray(String[] array) {
		int[] arr = new int[array.length];
		try {
			for (int i = 0; i < array.length; i++) {
				arr[i] = Integer.parseInt(array[i]);
			}
			return arr;
		} catch (NumberFormatException e) {
			throw new JsonParseException(e);
		}
	}

	private Object getArray(Object[] arr, Class<?> type, String[] array) {
		try {
			for (int i = 0; i < array.length; ++i) {
				arr[i] = type.cast(array[i]);
			}
			return arr;
		} catch (Exception e) {
			throw new JsonParseException(e);
		}
	}
}
