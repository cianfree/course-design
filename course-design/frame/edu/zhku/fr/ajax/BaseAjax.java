package edu.zhku.fr.ajax;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.zhku.ajax.dispatcher.KVList;
import edu.zhku.fr.ConfigCenter;
import edu.zhku.fr.Key;
import edu.zhku.fr.dao.QueryHelper;
import edu.zhku.fr.domain.User;

public abstract class BaseAjax {

    protected static QueryHelper queryHelper;

    public static void setQueryHelper(QueryHelper qh) {
        queryHelper = qh;
    }

    // ---------------------------------------------------------------

    /**
     * 获取默认的分页大小
     * 
     * @return
     */
    protected static Integer getDefaultPageSize() {
        return (Integer) ConfigCenter.getConfig(Key.DEFAULT_PAGE_SIZE);
    }

    /**
     * 获取当前页
     * 
     * @param currentPage
     *            当前页，可能是空的
     * @return
     */
    protected static Integer getCurrentPage(Integer currentPage) {
        return currentPage == null ? 1 : currentPage;
    }

    /**
     * 获取每页的大小，其中如果没有在配置文件中配置就默认是每页12条记录
     * 
     * @param pageSize
     * @return
     */
    protected static Integer getPageSize(Integer pageSize) {
        return pageSize == null //
        ? getDefaultPageSize()//
                : pageSize;
    }

    /**
     * 获取当前登录用户的帐号
     * 
     * @param session
     * @return
     */
    protected static String getCurrentAccount(KVList kvList) {
        if (kvList != null) {
            User user = getCurrentUser(kvList);
            if (user != null)
                return user.getAccount();
        }
        return null;
    }

    /**
     * 获取当前用户
     * 
     * @param session
     * @return
     */
    protected static User getCurrentUser(KVList kvList) {
        HttpSession session = kvList.getSession();
        if (session != null) {
            return (User) session.getAttribute(Key.CURRENT_USER);
        }
        return null;
    }

    protected static HttpSession getSession(KVList kvList) {
        return kvList.getSession();
    }

    protected static HttpServletRequest getRequest(KVList kvList) {
        return kvList.getRequest();
    }

    protected static ServletContext getApplication(KVList kvList) {
        return kvList.getApplication();
    }
    
    protected static <T> T getBean(Class<T> clazz, KVList kvList) {
        return WebApplicationContextUtils.getWebApplicationContext(getApplication(kvList)).getBean(clazz);
    }
}
