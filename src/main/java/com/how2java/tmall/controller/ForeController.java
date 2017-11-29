package com.how2java.tmall.controller;

import com.how2java.tmall.pojo.*;
import com.how2java.tmall.service.*;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("")
public class ForeController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private PropertyValueService propertyValueService;

    /**
     * 主页
     * @param model
     * @return
     */
    @RequestMapping("forehome")
    public String home(Model model){
        List<Category> categoryList = categoryService.list();
        productService.fill(categoryList);
        productService.fillByRow(categoryList);
        model.addAttribute("cs",categoryList);
        return "fore/home";
    }

    /**
     * 注册
     * @param model
     * @param user
     * @return
     */
    @RequestMapping("foreregister")
    public String register(Model model,User user){
        //0.获取user内容，进行转义
        String name = user.getName();
        String password = user.getPassword();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        //1.查询user是否存在
        boolean exist = userService.isExist(name);
        //2.
        if (exist){
            String m = "用户名已存在";
            model.addAttribute("msg",m);
            model.addAttribute("user",null);
            return "fore/register";
        }

        userService.add(user);
        return "redirect:registerSuccessPage";
    }

    /**
     * 登录
     * @param user
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("forelogin")
    public String login(User user, HttpSession session,Model model){
        //1.转义
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        //2.查询
        User user1 = userService.get(user.getName(), user.getPassword());
        if (user1 == null){
            model.addAttribute("msg","账号或密码错误");
            return "fore/login";
        }
        //3.存入session
        session.setAttribute("user",user1);
        return "redirect:forehome";
    }

    /**
     * 注销功能
     * @param session
     * @return
     */
    @RequestMapping("forelogout")
    public String logout(HttpSession session){
        User user = (User)session.getAttribute("user");
        if (user != null){
            session.removeAttribute("user");
        }
        return "redirect:forehome";
    }

    /**
     * 显示单独Product
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping("foreproduct")
    public String product(int pid, Model model){
        Product product = productService.get(pid);
        List<ProductImage> singleImage = productImageService.list(pid, ProductImageService.type_single);
        List<ProductImage> detailImage = productImageService.list(pid, ProductImageService.type_detail);
        productService.setSaleReviewQuantity(product);
        product.setProductSingleImage(singleImage);
        product.setProductDetailImage(detailImage);

        List<Review> reviews = reviewService.list(pid);
        reviewService.setUser(reviews);

        List<PropertyValue> propertyValues = propertyValueService.list(pid);
        propertyValueService.setProperty(propertyValues);

        model.addAttribute("reviews",reviews);
        model.addAttribute("p",product);
        model.addAttribute("pvs",propertyValues);
        return "fore/product";
    }
}
