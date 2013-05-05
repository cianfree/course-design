package edu.zhku.module;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import edu.zhku.ajax.dispatcher.KVList;
import edu.zhku.fr.log.Log;

/**
 * 模块管理器
 * @author arvin
 *
 */
public class AjaxModuleManager {
	private static final Map<String, Map<String, Method>> modules = new HashMap<String, Map<String, Method>>();
	
	private AjaxModuleManager (){}
	
	// -------------------------------------------------------------------------------
	
	/**
	 * 注册模块
	 * @param modules
	 */
	public static void registerModule(Class<?> ... modules) throws Exception {
		for (Class<?> module : modules) {
			registerSingleModule(module);
		}
	}
	
	/**
	 * 注册单个模块
	 * @param module
	 */
	public static void registerSingleModule(Class<?> module) {
		if(isModule(module)) {
			String moduleKey = getModuleKey(module);
			Map<String, Method> methods = modules.get(moduleKey);
			if(methods == null) methods = new HashMap<String, Method>();
			modules.put(moduleKey, registerMethods(methods, module.getDeclaredMethods()));
			registerSingleModule(module.getSuperclass());
		}
	}

	/**
	 * 注册多个方法
	 * @param map
	 * @param methods
	 */
	private static Map<String, Method> registerMethods(Map<String, Method> map,
			Method...methods) {
		if(methods == null || map == null) return map; 
		for (Method method : methods) {
			if(isAjaxMethod(method)) {
			    String key = getAjaxKey(method);
			    Log.info("\tregister ajax method: " + key);
				map.put(key, method);
			}
		}
		return map;
	}

	/**
	 * 如果没有在Annotation中指定就用该方法名称
	 * @param method
	 * @return
	 */
	private static String getAjaxKey(Method method) {
		String key = method.getAnnotation(AjaxMethod.class).value();
		if(key == null) {
			key = method.getName();
		}
		return key;
	}

	private static boolean isAjaxMethod(Method method) {
		return method.getAnnotation(AjaxMethod.class) != null && isPublic(method) && isStatic(method) && isValidParameter(method);
	}

	private static String getModuleKey(Class<?> module) {
		AjaxModule am = module.getAnnotation(AjaxModule.class);
		return am.value() == null || am.value().length() == 0 ? "global" : am.value();
	}

	private static boolean isModule(Class<?> module) {
		return module != null && module.getAnnotation(AjaxModule.class) != null;
	}

	private static boolean isValidParameter(Method m) {
		return m != null && m.getParameterTypes().length == 1 && m.getParameterTypes()[0] == KVList.class;
	}

	private static boolean isStatic(Method method) {
		return method == null ? false : Modifier.isStatic(method.getModifiers());
	}
	
	private static boolean isPublic(Method method) {
		return method == null ? false : Modifier.isPublic(method.getModifiers());
	}

	public static Method getHandler(String ajaxFun) {
		if(ajaxFun == null) return null;
		String[] arr = ajaxFun.split(" *: *");
		String moduleKey = "global";
		String methodKey = null;
		if(arr.length == 1) methodKey = arr[0];
		else {
			moduleKey = arr[0];
			methodKey = arr[1];
		}
		Map<String, Method> module = modules.get(moduleKey);
		return module == null ? null : module.get(methodKey);
	}
}
