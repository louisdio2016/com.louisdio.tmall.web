package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.how2java.tmall.comparator.*;
import com.how2java.tmall.pojo.*;
import com.how2java.tmall.service.*;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
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
    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin(HttpSession session){
        User user = (User)session.getAttribute("user");
        if (user == null)
            return "fail";
        return "success";
    }

    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String loginAjax(@RequestParam("name") String name,@RequestParam("password") String password, HttpSession session){
        //1.转义
        name = HtmlUtils.htmlEscape(name);
        //2.查询
        User user1 = userService.get(name, password);
        if (user1 == null){
            return "fail";
        }
        //3.存入session
        session.setAttribute("user",user1);
        return "success";
    }

    @RequestMapping("forecategory")
    public String category(int cid,Model model,String sort){
        Category category = categoryService.get(cid);
        productService.fill(category);
        List<Product> productList = category.getProducts();
        for (Product p:productList){
            productService.setSaleReviewQuantity(p);
        }
        //productService.setProductImage(productList);
        if(sort != null){
            switch (sort){
                case "review":
                    Collections.sort(category.getProducts(),new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(category.getProducts(),new ProductDateComparator());
                    break;
                case "saleCount":
                    Collections.sort(category.getProducts(),new ProductSaleCountComparator());
                case "price":
                    Collections.sort(category.getProducts(),new ProductPriceComparator());
                    break;
                case "all":
                    Collections.sort(category.getProducts(),new ProductAllComparator());
                    break;
            }
        }
        model.addAttribute("c",category);
        return "fore/category";
    }

    @RequestMapping("foresearch")
    public String search(Model model,String keyword){
        PageHelper.offsetPage(0,20);
        List<Product> products = productService.search(keyword);
        for (Product product:products){
            productService.setSaleReviewQuantity(product);
        }
        productService.setProductImage(products);
        model.addAttribute("ps",products);
        return "fore/searchResult";
    }

    @RequestMapping("forebuyone")
    public String buyone(int num,int pid,HttpSession session){
        User user = (User) session.getAttribute("user");
        int uid = user.getId();
        int oiid = 0;
        List<OrderItem> orderItems = orderItemService.listByUser(uid);
        boolean found = false;
        //购买后，两种情况，1.存在相同pid的orderitem
        for (OrderItem oi:orderItems){
            if (pid == oi.getPid()){
                oi.setNumber(oi.getNumber()+num);
                orderItemService.update(oi);
                oiid = oi.getId();
                found = true;
                break;
            }
        }
        //2.不存在相同pid的orderitem
        if(!found){
            OrderItem orderItem = new OrderItem();
            orderItem.setPid(pid);
            orderItem.setUid(uid);
            orderItem.setNumber(num);
            orderItemService.add(orderItem);
            oiid = orderItem.getId();
        }
        return "redirect:forebuy?oiid="+oiid;
    }

    @RequestMapping("forebuy")
    public String buy(String[] oiid,Model model,HttpSession session){
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        float total = 0.0f;
        for (String id:oiid){
            OrderItem orderItem = orderItemService.get(Integer.parseInt(id));
            orderItemService.setProduct(orderItem);
            total += orderItem.getProduct().getPromotePrice()*orderItem.getNumber();
            orderItems.add(orderItem);
        }
        model.addAttribute("total",total);
        //这里采用session保存，是由于在“提交订单”页面需要更新这些orderItem
        session.setAttribute("ois",orderItems);
        return "fore/buy";
    }

    @RequestMapping("foreaddCart")
    public String addCart(HttpSession session,int pid,int num){
        User user = (User)session.getAttribute("user");
        //查看当前user的购物车中是否有该product
        boolean found = false;
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        for (OrderItem oi:orderItems){
            if (oi.getPid() == pid && oi.getOid() != -1 && oi.getOid() != null){
                oi.setNumber(oi.getNumber()+num);
                orderItemService.update(oi);
                found = true;
                break;
            }
        }

        if (!found){
            OrderItem orderItem = new OrderItem();
            orderItem.setNumber(num);
            orderItem.setUid(user.getId());
            orderItem.setPid(pid);
//            orderItem.setOid(-1);
            orderItemService.add(orderItem);
        }
        return "success";
    }

    @RequestMapping("forecart")
    public String cart(HttpSession session,Model model){
        User user = (User)session.getAttribute("user");
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        orderItemService.setProduct(orderItems);
        model.addAttribute("ois",orderItems);
        return "fore/cart";
    }

    @RequestMapping("forechangeOrderItem")
    @ResponseBody
    public String changeOrderItem(HttpSession session,int pid,int num){
        User user = (User) session.getAttribute("user");
        if (null == user)
            return "fail";
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        for (OrderItem oi:orderItems){
            if (oi.getPid().intValue() == pid){
                oi.setNumber(num);
                orderItemService.update(oi);
                break;
            }
        }
        return "success";
    }

    @RequestMapping("foredeleteOrderItem")
    @ResponseBody
    public String deleteOrderItem(int oiid,HttpSession session){
        User user = (User) session.getAttribute("user");
        if (null == user)
            return "fail";
        orderItemService.delete(oiid);
        return "success";
    }

    @RequestMapping("forecreateOrder")
    public String createOrder(Order order,HttpSession session){
        User user = (User) session.getAttribute("user");
        order.setUser(user);
        order.setUid(user.getId());
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
        order.setCreateDate(new Date());
        order.setOrderCode(orderCode);
        order.setStatus(OrderService.waitPay);

        //获取之前保存在session中的ois
        List<OrderItem> ois = (List<OrderItem>) session.getAttribute("ois");
        //保存order,使order获得id
        float total = orderService.add(order, ois);

        return "redirect:forealipay?oid="+order.getId()+"&total="+total;
    }

    @RequestMapping("forealipay")
    public String alipay(int oid,float total){
        return "fore/alipay";
    }

    @RequestMapping("forepayed")
    public String payed(int oid,float total,Model model){
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date());
        orderService.update(order);
        model.addAttribute("o",order);
        return "fore/payed";
    }

    @RequestMapping("forebought")
    public String bought(HttpSession session,Model model){
        User user = (User)session.getAttribute("user");
        int uid = user.getId();
        List<Order> orders = orderService.list(uid, "delete");
        orderItemService.fill(orders);
        model.addAttribute("os",orders);
        return "fore/bought";
    }

    @RequestMapping("foreconfirmPay")
    public String confirmPay(int oid,Model model){
        Order order = orderService.get(oid);
        orderItemService.fill(order);
        model.addAttribute("o",order);
        return "fore/confirmPay";
    }

    @RequestMapping("foreorderConfirmed")
    public String orderConfirmed(int oid){
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitReview);
        order.setConfirmDate(new Date());
        orderService.update(order);
        return "fore/orderConfirmed";
    }

    @RequestMapping("foredeleteOrder")
    @ResponseBody
    public String deleteOrder(int oid){
        Order order = orderService.get(oid);
        order.setStatus(OrderService.delete);
        orderService.update(order);
        return "success";
    }

    @RequestMapping("forereview")
    public String review(int oid,Model model){
        Order order = orderService.get(oid);
        orderItemService.fill(order);
        Product product = order.getOrderItems().get(0).getProduct();
        productService.setSaleReviewQuantity(product);
        productService.setProductImage(product);

        List<Review> reviews = reviewService.list(product.getId());

        model.addAttribute("p",product);
        model.addAttribute("o",order);
        model.addAttribute("reviews",reviews);

        return "fore/review";
    }

    @RequestMapping("foredoreview")
    public String doreview(HttpSession session,Review review,int oid){
        //1.更新order的status
        Order order = orderService.get(oid);
        order.setStatus(OrderService.finish);
        orderService.update(order);
        //2.封装review,添加review
        User user = (User)session.getAttribute("user");
//        review.setUser(user);
        review.setUid(user.getId());
        review.setCreateDate(new Date());
        String content = review.getContent();
        content = HtmlUtils.htmlEscape(content);
        review.setContent(content);

        reviewService.add(review);
        //3.model添加product,oreder,reviews
//        orderItemService.fill(order);
//        Product product = order.getOrderItems().get(0).getProduct();
//        productService.setSaleReviewQuantity(product);
//        productService.setProductImage(product);
//        List<Review> reviews = reviewService.list(product.getId());
//
//        model.addAttribute("p",product);
//        model.addAttribute("o",order);
//        model.addAttribute("reviews",reviews);

        return "redirect:forereview?oid="+oid+"&showonly=true";
    }
}
