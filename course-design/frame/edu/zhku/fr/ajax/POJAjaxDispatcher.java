package edu.zhku.fr.ajax;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.zhku.fr.dao.QueryHelper;

@SuppressWarnings("serial")
public class POJAjaxDispatcher extends edu.zhku.ajax.dispatcher.AjaxDispatcher {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext acx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        try {
            BaseAjax.setQueryHelper(acx.getBean(QueryHelper.class));
        } catch (Exception e) {
        }
    }
}
