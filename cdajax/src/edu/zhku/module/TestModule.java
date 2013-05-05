package edu.zhku.module;

import edu.zhku.ajax.dispatcher.KVList;
import edu.zhku.ajax.dispatcher.NANException;

/**
 * 用于测试的模块
 * @author arvin
 *
 */
@AjaxModule("test")
public class TestModule {

	@AjaxMethod("getHi")
	public static String sayHi(KVList kvList) {
		return "Hi, " + kvList.getString("name");
	}
	
	@AjaxMethod("getDoubleAge")
	public static int getAge(KVList kvList) {
		try {
			return kvList.getInt("age") * 2;
		} catch (NANException e) {
		}
		return 0;
	}
	

}
