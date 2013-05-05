package edu.zhku.json.writer;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateWriter implements JsonWriter {
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Object write(String json, Class<?> type) {
		try {
			return format.parse(json);
		} catch (ParseException e) {
		}
		return null;
	}
}
