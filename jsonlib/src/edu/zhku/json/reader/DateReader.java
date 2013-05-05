package edu.zhku.json.reader;

import java.text.SimpleDateFormat;

public class DateReader implements JsonReader {
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
	
	public void setFormat(String pattern) {
		SimpleDateFormat format = null;
		try {
			format = new SimpleDateFormat(pattern);
		} catch (Exception e) {
			format = this.format;
		}
		this.format = format;
	}

	@Override
	public String read(Object src) {
		if(src == null) return "null";
		return format.format(src);
	}

}
