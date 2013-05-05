package edu.zhku.fr.priv.tag;

import java.util.Collection;

import edu.zhku.fr.ConfigCenter;
import edu.zhku.fr.Key;
import edu.zhku.fr.domain.Role;
import edu.zhku.fr.domain.User;

/**
 * 验证函数，自定义函数，在jsp页面中使用，包括验证用户是否包含角色，角色是否包含某个权限等等
 *
 * @author XJQ
 * @since 2013-1-21
 */
public class Validate {

	/**
	 * 验证用户user是否是Role角色
	 * @param user	要验证的用户
	 * @param role	角色
	 * @return 有就发挥true,否则返回false
	 */
	public static boolean hasRole(User user, Role role) {
		if(user == null || role == null) return false;
		for(Role r : user.getRoles()) {
			if(r.getId() == role.getId()) return true;
		}
		return false;
	}
	
	/**
	 * 判断是否是男的
	 * @param user
	 * @return true表示是男的，false表示女的
	 */
	public static boolean isMan(User user) {
		return user == null ? false : "男".equals(user.getSex()) ? true : false;
	}
	
	/**
	 * 验证是否是超级管理员
	 * @param user
	 * @return true 表示是超级管理员，false 表示不是超级管理员
	 */
	public static boolean isAdmin(User user) {
		return user == null ? false : user.isAdmin();
	}
	
	/**
	 * 判断给定的权限id是否在指定的权限ID数组中
	 * @param cid	要检查的当前权限id
	 * @param privIds 权限容器组
	 * @return
	 */
	public static boolean isInPrivs(Long cid, Long[] privIds) {
		if(privIds == null || cid == null) return false;
		
		for(Long id : privIds) {
			if(cid.equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断两个字符串是否相等
	 * @param first
	 * @param second
	 * @return
	 */
	public static boolean isEqual(String first, String second) {
		if(first != null) {
			return first.equals(second);
		}
		return first == null && second == null;
	}
	
	/**
	 * 超过20字符之后就不显示了
	 * @param description
	 * @return
	 */
	public static String fixLen(String description, Integer maxLen) {
	    if(description == null) return "";
	    maxLen = maxLen == null ? 15 : maxLen;
	    if(description.length() >= maxLen) {
	        return description.substring(0, maxLen) + "......";
	    }
	    return description;
	}
	
	/**
	 * 判断用户是否有权限
	 * @param user
	 * @param action
	 * @return
	 */
	public static boolean hasPriv(User user, String action) {
	    if(action == null) return true;
	    action = getSimpleHref(action);
	    boolean isInPriv = ConfigCenter.getConfig(Key.ALL_PRIV_ACTIONS, Collection.class).contains(action);
	    if(user == null && isInPriv) return false;
	    if(!isInPriv) return true;
	    return user.hasPrivilege(action);
	}
	
	private static String getSimpleHref(String action) {
        if (action == null || "".equals(action)) {
            return null;
        }
        String simpleHref = action;
        // 除去开头的多余部分
        if (action.startsWith("/") || action.startsWith("\\")) {
            simpleHref = simpleHref.substring(1);
        }
        // 除去UI后最
        int index = simpleHref.lastIndexOf("UI");
        if (index > -1) {
            simpleHref = simpleHref.substring(0, index);
        } else {
            // 除去后缀.html
            index = simpleHref.lastIndexOf(".");
            if (index > -1) {
                simpleHref = simpleHref.substring(0, index);
            }
        }
        return simpleHref;
    }
}
