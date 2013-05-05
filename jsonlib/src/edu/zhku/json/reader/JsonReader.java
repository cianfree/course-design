package edu.zhku.json.reader;

import edu.zhku.json.JsonParseException;

/**
 * Json Reader, 把对象读成Json字符串
 * @author arvin
 *
 */
public interface JsonReader {
	
	/**
	 * 把src读成Json字符串
	 * @param src
	 * @return
	 */
	public String read(Object src) throws JsonParseException;

}
