package edu.zhku.json;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.zhku.json.reader.BeanReader;
import edu.zhku.json.reader.CollectionReader;
import edu.zhku.json.reader.DateReader;
import edu.zhku.json.reader.JsonReader;
import edu.zhku.json.reader.MapReader;
import edu.zhku.json.reader.PrimitiveArrayReader;
import edu.zhku.json.reader.PrimitiveReader;
import edu.zhku.json.reader.StringReader;
import edu.zhku.json.writer.DateWriter;
import edu.zhku.json.writer.JsonWriter;
import edu.zhku.json.writer.PrimitiveArrayWriter;
import edu.zhku.json.writer.PrimitiveWriter;

/**
 * Json操作对象
 * @author arvin
 *
 */
public class Json {
	private static final Map<Class<?>, JsonReader> readers = new HashMap<Class<?>, JsonReader>();
	private static final Map<Class<?>, JsonWriter> writers = new HashMap<Class<?>, JsonWriter>();
	
	// 初始化, 注册默认的reader和writer
	static {
		// 原始类型读写器
		registerJsonReader(new PrimitiveReader(), JsonUtils.getPrimitiveClasses());
		registerJsonReader(new StringReader(), JsonUtils.getStringClasses());
		registerJsonReader(new BeanReader(), JsonUtils.getBeanClasses());
		registerJsonReader(new MapReader(), JsonUtils.getMapClasses());
		registerJsonReader(new CollectionReader(), JsonUtils.getCollectionClasses());
		registerJsonReader(new DateReader(), Date.class, java.sql.Date.class, Time.class, Timestamp.class);
		registerJsonReader(new PrimitiveArrayReader(), JsonUtils.getPrimitivearrayclasses());
		
		// 注册JsonWriter
		JsonWriter writer = new PrimitiveWriter();
		registerJsonWriter(writer, JsonUtils.getPrimitiveClasses());
		registerJsonWriter(writer, JsonUtils.getStringClasses());
		registerJsonWriter(new DateWriter(), Date.class);
		registerJsonWriter(new PrimitiveArrayWriter(), JsonUtils.getPrimitivearrayclasses());
	}
	
	// ------------------------------------------------------------------------------
	private Json() {
	}

	/**
	 * 注册JsonReader
	 * @param reader
	 * @param type
	 */
	public static void registerJsonReader(JsonReader reader, Class<?>... types) {
		if(reader != null && types != null) {
			for (Class<?> type : types) {
				readers.put(type, reader);
			}
		}
	} 
	
	/**
	 * 注册JsonWriter
	 * @param writer
	 * @param type
	 */
	public static void registerJsonWriter(JsonWriter writer, Class<?> ... types) {
		if(writer != null && types != null) {
			for (Class<?> type : types) {
				writers.put(type, writer);
			}
		}
	}
	
	/**
	 * 把对象转成json字符串
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		if(obj == null) return "null";
		String result = getJsonReader(obj.getClass()).read(obj);
		if(result == null || "".equals(result)) {
			return "null";
		}
		return result.replaceAll("\"+", "\"");
	}
	
	/**
	 * 把一个json字符串转成对象
	 * @param json
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String json, Class<T> type) {
		if(json == null || "".equals(json) || type == null) return null;
		return (T) getJsonWriter(type).write(json, type);
	}


	/**
	 * 获取指定的JsonReader
	 * @param type
	 * @return
	 */
	private static JsonReader getJsonReader(Class<?> type) {
		JsonReader reader = readers.get(type);
		return reader == null ? readers.get(Object.class) : reader;
	}
	
	/**
	 * 获取之地您的JsonWriter
	 * @param type
	 * @return
	 */
	private static JsonWriter getJsonWriter(Class<? extends Object> type) {
		JsonWriter writer = writers.get(type);
		return writer == null ? writers.get(Object.class) : writer;
	}

}
