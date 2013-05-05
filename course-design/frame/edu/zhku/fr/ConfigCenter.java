package edu.zhku.fr;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.zhku.fr.expections.NoServletContextException;
import edu.zhku.fr.expections.NoSuchKeyException;

/**
 * 配置中心，初始化框架所需要的配置
 *
 * @author XJQ
 * @since 2013-3-26
 */
public class ConfigCenter {
	private static ServletContextEvent context;
	
	/**
	 * 容器对象，用来存储相关的初始化参数
	 */
	private static Map<Key, Object> container = new HashMap<Key, Object>();
	
	/**
	 * 单例模式
	 */
	private ConfigCenter() {}
	
	/**
	 * 
	 * @param context
	 * @throws NoServletContextException 
	 * @throws IOException 
	 * @throws NoSuchKeyException 
	 */
	public static final void init(ServletContextEvent sc) throws NoServletContextException, IOException, NoSuchKeyException {
		if(context != null) {
			return ;
		}
		if(sc == null) {
			throw new NoServletContextException();
		}
		context = sc;
		fixedContextParameter();
		// 设置ApplicationContext
		setConfig(Key.ACX, WebApplicationContextUtils.getWebApplicationContext(sc.getServletContext()));
	}
	
	/**
	 * 泛型方法
	 * @param key
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getConfig(Key key, Class<T> clazz) {
		return (T) container.get(key);
	}
	
	/**
	 * 获取配置
	 * @param key
	 * @return
	 */
	public static Object getConfig(Key key) {
		return container.get(key);
	}
	
	/**
	 * 获取配置，以字符串形式返回
	 * @param key
	 * @return
	 */
	public static String getConfigString(Key key) {
		return getConfig(key).toString();
	}

	/**
	 * 处理上下文配置初始化参数
	 */
	private static void fixedContextParameter() {
		Enumeration<?> enums = context.getServletContext().getInitParameterNames();
		while(enums.hasMoreElements()) {
			String name = enums.nextElement().toString();
			Key key = Key.key(name);
			if(key != null) {
				container.put(//
						key,//
						getDefaultValue(//
								key,//
								context.getServletContext().getInitParameter(name)));
			}
		}
	}
	
	/**
	 * 获取默认配置值
	 * @param key
	 * @param value
	 * @return
	 */
	private static Object getDefaultValue(Key key, String value) {
		if(value == null || "".equals(value)) {
			return key.value();
		}
		return value;
	}
	
	public static void addKey(Key key) {
	    if(container.get(key) == null) {
	        container.put(key, getDefaultValue(key, context.getServletContext().getInitParameter(key.keys())));
	        key.addKey(key);
	    }
	}

    public static void setConfig(Key key, Object val) {
        container.put(key, val);
    }

    public static void appendContainer(Map<Key, Object> subContainer) {
        container.putAll(subContainer);
    }

    public static Map<Key, Object> getContainer() {
        return container;
    }
}
