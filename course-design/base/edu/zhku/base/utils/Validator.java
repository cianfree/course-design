package edu.zhku.base.utils;

/**
 * 验证器
 *
 * @author XJQ
 * @since 2013-1-11
 */
public class Validator {
	
	/**
	 * 验证字符串是否符合邮箱格式
	 * 
	 * @param email 要验证的邮箱字符串
	 * @return
	 */
	public static boolean isEmail(String email) {
		if(email == null) return false;
		return email.matches("\\w+@\\w+\\.(\\w+\\.\\w+)|\\w+@\\w+\\.(\\w+)");
	}
	/**
	 * 验证字符串是否在合理的长度内
	 * @param value
	 * @param minLength
	 * @param maxLength
	 * @return
	 */
	public static boolean length(String value, int minLength, int maxLength) {
		if(value == null || value.length() < minLength || value.length() > maxLength) return false;
		return true;
	}
	
	public static void main(String[] args) {
		String email = "xiajiqiu@163.com";
		System.out.println(Validator.isEmail(email));
	}
	
	/**
	 * 为空或者是为""被认为是empty
	 * @param n
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		return obj == null || "".equals(obj);
	}
}
