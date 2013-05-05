package edu.zhku.json.writer;

import edu.zhku.json.JsonParseException;

/**
 * 把Json字符串写入一个对象
 * @author arvin
 *
 */
public interface JsonWriter {
	
	/**
	 * 如果转换出错就返回null
	 * @param json
	 * @param type
	 * @return
	 */
	public Object write(String json, Class<?> type) throws JsonParseException;

}
