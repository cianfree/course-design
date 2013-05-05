package edu.zhku.base.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import edu.zhku.base.BaseKey;
import edu.zhku.fr.ConfigCenter;
import edu.zhku.fr.dao.QueryHelper;
import edu.zhku.fr.domain.Role;
import edu.zhku.fr.domain.User;

/**
 * 基础Controller，用于提供常用的业务层组件Service,另外，提供公共的方法，如获取当前用户
 * 
 * @author XJQ
 * @since 2013-1-10
 */
public abstract class BaseController {

	@Resource
	protected QueryHelper queryHelper;
	
	// ---------------------------------------------------------------

	/**
	 * 获取默认的分页大小
	 * 
	 * @return
	 */
	protected Integer getDefaultPageSize() {
		return (Integer) ConfigCenter.getConfig(BaseKey.DEFAULT_PAGE_SIZE);
	}
	
	/**
	 * 获取当前页
	 * @param currentPage 当前页，可能是空的
	 * @return
	 */
	protected Integer getCurrentPage(Integer currentPage) {
		return currentPage == null ? 1 : currentPage;
	}
	
	/**
	 * 获取每页的大小，其中如果没有在配置文件中配置就默认是每页12条记录
	 * @param pageSize
	 * @return
	 */
	protected Integer getPageSize(Integer pageSize) {
		return pageSize == null //
				? getDefaultPageSize()//
				: pageSize;
	}
	
	/**
	 * 获取当前登录用户的帐号
	 * @param session
	 * @return
	 */
	protected String getCurrentAccount(HttpSession session) {
		if(session != null) {
			User user = getCurrentUser(session);
			if(user != null) return user.getAccount();
		}
		return null;
	}
	
	/**
	 * 获取当前用户
	 * @param session
	 * @return
	 */
	protected User getCurrentUser(HttpSession session) {
		if(session != null) {
			return (User) session.getAttribute(BaseKey.CURRENT_USER);
		}
		return null;
	}
	
	/**
     * 获取默认角色
     * 
     * @param session
     * @return
     */
    protected Role getDefaultRole(HttpSession session) {
        return (Role) session.getServletContext().getAttribute(
                BaseKey.DEFAULT_ROLE.keys());
    }
}
