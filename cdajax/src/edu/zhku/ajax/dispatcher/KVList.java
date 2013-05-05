package edu.zhku.ajax.dispatcher;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 存放参数的类
 * @author arvin
 *
 */
public class KVList {
	private Map<String, Object> maps = new HashMap<String, Object>();

	public void put(String key, Object value) {
		maps.put(key, value);
	}
	
	public ServletContext getApplication() {
		return getRequest().getSession().getServletContext();
	}
	
	public HttpSession getSession() {
	    return getRequest().getSession();
	}
	
	public HttpServletRequest getRequest() {
	    return (HttpServletRequest) maps.get("request");
	}

	public String getString(String key) {
		Object obj = maps.get(key);
		return obj == null ? null : obj.toString();
	}
	
	public Integer getInt(String key) throws NANException {
		Object obj = maps.get(key);
		if(obj != null) {
			try {
				return Integer.parseInt(obj.toString());
			} catch (NumberFormatException e) {
				throw new NANException();
			}
		}
		return null;
	}
	
	public Long getLong(String key) throws NANException {
		Object obj = maps.get(key);
		if(obj != null) {
			try {
				return Long.parseLong(obj.toString());
			} catch (NumberFormatException e) {
				throw new NANException();
			}
		}
		return null;
	}
	
	public Double getDouble(String key) throws NANException {
		Object obj = maps.get(key);
		if(obj != null) {
			try {
				return Double.parseDouble(obj.toString());
			} catch (NumberFormatException e) {
				throw new NANException();
			}
		}
		return null;
	}
	
	public Float getFloat(String key) throws NANException {
		Object obj = maps.get(key);
		if(obj != null) {
			try {
				return Float.parseFloat(obj.toString());
			} catch (NumberFormatException e) {
				throw new NANException();
			}
		}
		return null;
	}
	
	
}
