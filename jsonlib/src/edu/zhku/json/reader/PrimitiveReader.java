package edu.zhku.json.reader;

/**
 * 原始类型reader, 即把原始类型的数据读为一个json字符串
 * @author arvin
 *
 */
public class PrimitiveReader implements JsonReader {

	@Override
	public String read(Object src) {
		return src == null ? "null" : src.toString().trim();
	}

}
