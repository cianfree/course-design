package edu.zhku.json.reader;

import java.lang.reflect.Field;
import java.util.List;

import edu.zhku.json.JsonUtils;
import edu.zhku.json.Json;

/**
 * Bean对象JsonReader
 * @author arvin
 *
 */
public class BeanReader implements JsonReader {

	@Override
	public String read(Object src) {
		if(src == null) return "null";
		StringBuilder json = new StringBuilder("{");
		// 1, 反射解析类的属性
		List<Field> fields = JsonUtils.getFileds(src.getClass());
		// 2, 对该src调用get方法,获取属性,然后调用Json.toJson
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				Object attr = field.get(src);
				String value = Json.toJson(attr);
				if(!"null".equals(value)) {
					json.append(field.getName()).append(":").append(value).append(",");
				}
			}
			return json.substring(0, json.length() - 1) + "}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "null";
	}

}
