package edu.zhku.fr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.zhku.fr.domain.User;

/**
 * Key值
 *
 * @author XJQ
 * @since 2013-3-28
 */
public class Key {
	public static final Key LOG_LEVEL = new Key("log.level", "error");
	public static final Key LOG_TARGET = new Key("log.target", "console");
	public static final Key LOG_DIR = new Key("log.target.dir", "user.dir");
	public static final Key LOG_DATE_PATTERN = new Key("log.date.pattern", "yyyy-MM-dd HH:mm:ss");
	
	public static final Key FRAME_MODULE_CONFIG = new Key("programModulesConfigLocation", "frame/module-config.xml");
	public static final Key FRAME_HBM_CONFIG = new Key("hibernateOfSpringConfigLocation", "frame/applicationContext-hibernate.xml");
	public static final Key FRAME_SPRING_CONFIG = new Key("contextConfigLocation", "applicationContext.xml");
	
	public static final Key APP_DEPLOY = new Key("frame.isDeploy", false);
	
	public static final Key SUPER_ADMINS = new Key("super-admins", getList(User.class, new User("admin", "超级管理员", "admin", edu.zhku.fr.domain.User.ACTIVIED, "xiajiqiu1990@163.com")));
	public static final Key ROOT_URL = new Key("rootUrl", "http://localhost/");
	
	public static final Key DEFAULT_PAGE_SIZE = new Key("default-paging-size", 10);
	public static final Key ACX = new Key("acx", null);
	public static final Key TOP_PRIVILEGE = new Key("topPrivs", null);
	public static final Key ALL_PRIV_ACTIONS = new Key("allPrivActions", null);
	
	private static final Set<Key> elements = new HashSet<Key>();

	//---------------------------------------------------------------------
	public static final String CURRENT_USER = "currentUser";
	
	protected Object key;
	protected Object value;
	
	protected Key(Object key, Object defVal) {
		this.key = key;
		this.value = defVal;
	}
	
	protected Key(Object key) {
		this.key = key;
	}
	
	// --------------------------------------------------------
	static {
	    LOG_DATE_PATTERN.addKey(LOG_DATE_PATTERN)//
			.addKey(LOG_DIR)//
			.addKey(LOG_LEVEL)//
			.addKey(LOG_TARGET)//
			//
			.addKey(APP_DEPLOY)//
			//
			.addKey(FRAME_HBM_CONFIG)//
			.addKey(FRAME_MODULE_CONFIG)//
			.addKey(FRAME_SPRING_CONFIG)//
			.addKey(ROOT_URL)//
			.addKey(SUPER_ADMINS)//
			.addKey(DEFAULT_PAGE_SIZE)//
			.addKey(ACX)
		;
	}
	
	protected Key addKey(Key key) {
		elements.add(key);
		return key;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T value(Class<T> clazz) {
		return (T) value;
	}
	
	@Override
	public String toString() {
		return this.key.toString();
	}
	
	public Object value() {
		return this.value;
	}
	
	public void value(Object val) {
        this.value = val;
    }
	
	/**
	 * 获取字符串的值
	 * @return
	 */
	public String values() {
		return this.value.toString();
	}
	
	/**
	 * 获取key值
	 * @return
	 */
	public Object key() {
		return this.key;
	}
	
	/**
	 * 获取key的字符串形式
	 * @return
	 */
	public String keys() {
		return this.key.toString();
	}
	
	/**
	 * 根据key值获取Key对象，传过来的可能是Key的name，也可能是Key对象
	 * @param key
	 * @return
	 */
	public static Key getKey(Object key) {
		if(key == null || "".equals(key)) {
			return null;
		}
		if(key instanceof Key) {
			return (Key) key;
		}
		for(Key k : elements) {
			if(k.key.equals(key)) {
				return k;
			}
		}
		return null;
	}
	
	/**
	 * 获取key对象
	 * @param key
	 * @return
	 */
	public static Key key(Object key) {
		return getKey(key);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> getList(Class<T> clazz, Object obj) {
		List<T> list = new ArrayList<T>();
		list.add((T) obj);
		return list;
	}
	
	public static Set<Key> elements() {
	    return elements;
	}
	
}
