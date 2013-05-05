package edu.zhku.ajax.dispatcher;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.zhku.fr.log.Log;
import edu.zhku.json.Json;
import edu.zhku.module.AjaxModuleManager;

@SuppressWarnings({ "serial" })
public class AjaxDispatcher extends HttpServlet {
    private static String ajaxRequestName;
    private static String emptyHandlerTips;
    private static String encoding;

    @Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ajaxRequestName = config.getInitParameter("ajaxRequestName");
		emptyHandlerTips = config.getInitParameter("emptyHandlerTips");
		encoding = config.getInitParameter("encoding");
		ajaxRequestName = ajaxRequestName == null ? "func" : ajaxRequestName ;
		emptyHandlerTips = emptyHandlerTips == null 
				? "Sorry, something wrong in server, please contact system manager!" 
			    : emptyHandlerTips ;
		encoding = encoding == null ? "utf-8" : encoding;
		// 注册模块
		try {
		    String configFile = config.getInitParameter("moduleConfig");
		    configFile = configFile==null? "ajaxmodule.properties" : configFile; 
		    InputStream is = this.getClass().getResourceAsStream("/" + configFile);
		    if(is == null) return ;
		    Properties pro = new Properties();
		    pro.load(is);
		    String modules = pro.get("modules").toString();
		    if(modules != null) {
		        String[] classes = modules.split(",");
		        for (String cls : classes) {
		            Log.info("register Class: " + cls);
                    AjaxModuleManager.registerSingleModule(Class.forName(cls));
                }
		    }
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1, 解析请求类型, 解决中文乱码问题
        processEncoding(request, response);

        // 2, 解析请求参数,并包装成对象KVMap
        KVList kvList = new KVList();
        processRequestParameters(kvList, request);

        // 3, 解析ajax请求的方法,方法的声明: public static [String, Collection<super ?>,
        // int, doouble, methodName(KVMap)
        Method handler = getHandlerMethod(request);

        // 4, 执行相应的方法,获得返回值
        String responseText = getResponseText(handler, kvList);
        // 5, 写出
        response.getWriter().write(responseText == null || responseText.length() == 0 ? "null" : responseText.trim());
    }

    /**
     * 获取响应数据
     * 
     * @param handler
     * @param responseType
     * @return
     * @throws Exception
     */
    protected String getResponseText(Method handler, KVList kvMap) {
        if (handler == null)
            return emptyHandlerTips;
        try {
            String json = Json.toJson(handler.invoke(null, kvMap));
            if(json.startsWith("\"")) json = json.substring(1);
            if(json.endsWith("\"")) json = json.substring(0, json.length() - 1);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emptyHandlerTips;
    }

    /**
     * 获取处理方法
     * 
     * @param request
     * @return
     */
    protected Method getHandlerMethod(HttpServletRequest request) {
        return AjaxModuleManager.getHandler(request.getParameter(ajaxRequestName));
    }

    /**
     * 处理请求参数
     * 
     * @param kvMap
     * @param request
     */
    protected void processRequestParameters(KVList kvList, HttpServletRequest request) {
        kvList.put("request", request);
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String key = names.nextElement();
            String val = getParameter(request, key);
            kvList.put(key, val);
        }
    }

    protected String getParameter(HttpServletRequest request, String key) {
        String value = null;
        if ("GET".equals(request.getMethod())) {
            value = request.getParameter(key);
            if (value != null) {
                try {
                    return new String(value.getBytes("ISO-8859-1"), encoding);
                } catch (UnsupportedEncodingException e) {
                }
            }
        }
        return value;
    }

    /**
     * 处理字符问题
     * 
     * @param request
     * @param response
     */
    protected void processEncoding(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding(encoding);
        } catch (UnsupportedEncodingException e) {
            encoding = "utf-8";
            try {
                request.setCharacterEncoding(encoding);
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }
        response.setContentType("text/html;charset=" + encoding);
    }

}
