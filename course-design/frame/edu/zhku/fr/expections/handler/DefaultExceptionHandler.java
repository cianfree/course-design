package edu.zhku.fr.expections.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import edu.zhku.fr.log.Log;

@Component
public class DefaultExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mav = new ModelAndView(getErrorPage(request));
        Log.error("Request error: " + request.getRemoteAddr(), ex);
        return mav;
    }

    /**
     * 获取error page
     * 
     * @param request
     * @return
     */
    private String errorPage = null;
    private String getErrorPage(HttpServletRequest request) {
        if(errorPage == null) {
            errorPage = request.getServletContext().getInitParameter("errorPage");
            if(errorPage == null) {
                errorPage = "redirect:/frame/error.html";
            }
        }
        return errorPage;
    }

}
