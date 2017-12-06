package com.how2java.tmall.interceptor;


import com.how2java.tmall.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    /*
    返回false:从当前拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
    返回true:执行下一个拦截器，执行所有拦截器；再执行Controller;再从最后一个拦截器往回执行所有的postHandle()；
             再从最有一个拦截器往回执行所有的afterCompletion()
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String contextPath = session.getServletContext().getContextPath();
        String uri = request.getRequestURI();
        String[] noNeedAuthPage = {
                "home",
                "checkLogin",
                "register",
                "loginAjax",
                "login",
                "product",
                "category",
                "search"};
        uri = StringUtils.remove(uri, contextPath);
        if (uri.startsWith("/fore")){
            String method = StringUtils.substringAfterLast(uri, "/fore");
            if (!Arrays.asList(noNeedAuthPage).contains(method)){
                User user = (User)session.getAttribute("user");
                if (null == user){
                    response.sendRedirect("loginPage");
                    return false;
                }
            }
        }
        return true;
    }
    /*
    在业务处理器处理请求执行完成后，生成视图之前执行的动作
    可在modelAndView中加入数据，比如当前时间
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        super.postHandle(request, response, handler, modelAndView);
    }
    /*
    在DispatcherServlet完全处理完请求后被调用，可用于清理资源等
    当有拦截器抛出异常时，会从当前拦截器往回执行所有拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        super.afterCompletion(request, response, handler, ex);
    }
}
