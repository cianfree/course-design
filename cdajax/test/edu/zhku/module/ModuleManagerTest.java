package edu.zhku.module;

import java.lang.reflect.Method;

import org.junit.Test;

import edu.zhku.ajax.dispatcher.KVList;
import edu.zhku.json.Json;

public class ModuleManagerTest {

	@Test
	public void test() throws Exception {
		AjaxModuleManager.registerModule(TestModule.class);
		String ajaxFun = "getDoubleAge";
		KVList kvList = new KVList();
		kvList.put("age", 18);
		String json = doAjax(ajaxFun, kvList);
		System.out.println(json);
	}

	private String doAjax(String ajaxFun, KVList kvList) throws Exception {
		Method handler = getHandler(ajaxFun);
		if(handler != null) {
			Object obj = handler.invoke(null, kvList);
			return Json.toJson(obj);
		}
		return "null";
	}

	private Method getHandler(String ajaxFun) {
		return AjaxModuleManager.getHandler(ajaxFun);
	}

}
