package edu.zhku.json;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonUtils {
	private static final Class<?>[] primitiveClasses = new Class<?>[]{boolean.class, Boolean.class, byte.class, Byte.class, int.class, Integer.class, long.class, Long.class, short.class, Short.class, float.class, Float.class, double.class, Double.class};
	private static final Class<?>[] stringClasses = new Class<?>[]{String.class, char.class, CharSequence.class, Character.class};
	private static final Class<?>[] beanClasses = new Class<?>[] {Object.class};
	private static final Class<?>[] mapClasses = new Class<?>[] {Map.class, HashMap.class, LinkedHashMap.class};
	private static final Class<?>[] collectionClasses = new Class<?>[] {Collection.class, Set.class, HashSet.class, LinkedHashSet.class, List.class, ArrayList.class, LinkedList.class};
	
	private static final Class<?>[] primitiveArrayClasses = new Class<?>[]{boolean[].class, Boolean[].class, byte[].class, Byte[].class, int[].class, Integer[].class, long[].class, Long[].class, short[].class, Short[].class, float[].class, Float[].class, double[].class, Double[].class, String[].class, char[].class, Character[].class};

	private JsonUtils() {}
	
	/**
	 * 获取所有属性
	 * @param type
	 * @return
	 */
	public static List<Field> getFileds(Class<?> type) {
		if(type == null) return null;
		List<Field> fields = new ArrayList<Field>();
		fields.addAll(Arrays.asList(type.getDeclaredFields()));
		Class<?> superClass = type.getSuperclass();
		while(superClass != null && superClass != Object.class) {
			fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
			superClass = superClass.getSuperclass();
		}
		return fields;
	}

	/**
	 * 将json字符串转成key-value的形式<br>
	 * json格式:<br>
	 * {<br>
	 * 	"key1": "value1",	// 或'valu1'<br>
	 * 	"key2": "value2"<br>
	 * }<br>
	 * @param json
	 * @return
	 */
	public static Map<String, String> getJsonMap(String json) {
		if(!isJsonObject(json)) return null;
		Map<String, String> maps = new HashMap<String, String>();
		json = json.substring(1, json.length() - 1);
		String[] attrs = json.split(" *, *");
		for (String attr : attrs) {
			int index = attr.indexOf(":");
			String key = attr.substring(0, index).trim().replaceFirst("'|\"", "");
			String val = attr.substring(index + 1).trim().replaceFirst("'|\"", "");
			// 删除多余的引号: ' 或 "
			if(key.endsWith("\'") || key.endsWith("\"")) key = key.substring(0, key.length() - 1).trim();
			if(val.endsWith("\'") || val.endsWith("\"")) val = val.substring(0, val.length() - 1).trim();
			maps.put(key, val);
		}
		return maps;
	}
	
	/**
	 * 判断是否符合Json格式<br>
	 * json格式的定义:<br>
	 * 1, 以 { 开头, } 结尾<br>
	 * 2, 以 [ 开头, ] 结尾<br>
	 * @param json
	 * @return
	 */
	public static boolean isJsonObject(String json) {
		return json != null && json.startsWith("{") && json.endsWith("}");
	}
	
	public boolean isJsonPrimitive(String json) {
		return json != null && !json.startsWith("{") && !json.startsWith("[");
	}
	
	/**
	 * 判断是否是Json数组
	 * 
	 * @param json
	 * @return
	 */
	public static boolean isJsonArray(String json) {
		return json != null && json.startsWith("[") && json.endsWith("]");
	}

	/**
	 * 根据给定的key获取类的属性,输入没有就返回null
	 * @param type
	 * @param key
	 * @return
	 */
	public static Field getFiled(Class<?> type, String name) {
		if(type == null || name == null) return null;
		Field field = null;
		do {
			try {
				field = type.getDeclaredField(name);
				type = type.getSuperclass();
			} catch (Exception e) {
				field = null;
			}
		} while(field == null && type.getSuperclass() != Object.class);
		return field;
	}

	// 获取json数组
	public static String[] getJsonArray(String json) {
		if(!isJsonArray(json)) return null;
		return json.substring(1, json.length() - 1).split(" *, *");
	}
	
	public static Class<?>[] getPrimitiveClasses() {
		return primitiveClasses;
	}
	
	public static Class<?>[] getStringClasses() {
		return stringClasses;
	}

	public static Class<?>[] getBeanClasses() {
		return beanClasses;
	}

	public static Class<?>[] getMapClasses() {
		return mapClasses;
	}

	public static Class<?>[] getCollectionClasses() {
		return collectionClasses;
	}

	public static Class<?>[] getPrimitivearrayclasses() {
	    return primitiveArrayClasses;
    }

}
