package com.how2java.tmall.interceptor;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class OtherInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OrderItemService orderItemService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //搜索栏下的分类集合信息
        List<Category> cs = categoryService.list();
        request.getSession().setAttribute("cs",cs);
//        request.setAttribute("cs",cs);
        //给用于返回首页的contextPath赋值
        HttpSession session = request.getSession();
        String contextPath = session.getServletContext().getContextPath();
        session.setAttribute("contextPath",contextPath+"/forehome");
//        request.setAttribute("contextPath",contextPath+"/forehome");
        //为购物车中的数量赋值
        User user = (User)session.getAttribute("user");
        int cartTotalItemNumber = 0;
        if (null != user){
            List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
            for (OrderItem oi:orderItems){
                cartTotalItemNumber += oi.getNumber();
            }
        }
        session.setAttribute("cartTotalItemNumber",cartTotalItemNumber);
//        request.setAttribute("cartTotalItemNumber",cartTotalItemNumber);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        super.afterCompletion(request, response, handler, ex);
    }
}
