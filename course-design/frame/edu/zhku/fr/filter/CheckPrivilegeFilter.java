package edu.zhku.fr.filter;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.zhku.fr.Key;
import edu.zhku.fr.ModuleService;
import edu.zhku.fr.domain.User;
import edu.zhku.fr.log.Log;

public class CheckPrivilegeFilter implements Filter {

    private String noPrivUrl = "frame/noPrivilege.html";
    private String noModuleUrl = "frame/noModuleDeploy.html";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.noPrivUrl = filterConfig.getInitParameter("noPrivUrl");
        this.noModuleUrl = filterConfig.getInitParameter("noModuleUrl");
        Log.info("filter init......: " + this.noPrivUrl);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        checkRootUrl(request);
        if(!ModuleService.hasDeploy() && !request.getRequestURI().contains(noModuleUrl)) {
            response.sendRedirect(rootUrl + noModuleUrl);
            return ;
        }
        // 获取请求的URL
        String action = this.getSimpleAction(request);
        // 如果这个Action是在权限的控制范围之内的就要进行权限的检查
        if (isInPrivs(action, (Collection<String>) request.getSession().getServletContext().getAttribute("allPrivActions"))) {
            // 获取当前用户
            User user = (User) request.getSession().getAttribute(Key.CURRENT_USER);
            if (user != null && user.hasPrivilege(action)) {
                chain.doFilter(request, response);
            } else {
                // 如果没有权限就重定向到没有权限界面
                response.sendRedirect(rootUrl + noPrivUrl);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private void checkRootUrl(HttpServletRequest request) {
        if(rootUrl == null) {
            rootUrl = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort() + request.getContextPath()
                    + "/";
        }
    }

    @Override
    public void destroy() {
        Log.info("filter destory");
    }

    /**
     * 判断指定的action是否在控制范围内
     * 
     * @param action
     * @param actions
     * @return
     */
    private boolean isInPrivs(String action, Collection<String> actions) {
        if (action == null || actions == null) return false;
        for (String a : actions) {
            if (a.equals(action))
                return true;
        }
        return false;
    }
    
    /**
     * 获取动作的简单形式
     * 
     * @param request
     * @return
     */
    private String getSimpleAction(HttpServletRequest request) {
        String http = request.getRequestURL().toString();
        http = http.replace(rootUrl, "");
        http = http.replace("UI", "");
        http = http.replace(".html", "");
        return http;
    }
    
    private static String rootUrl ;

}
