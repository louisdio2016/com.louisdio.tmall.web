package com.how2java.tmall.controller;

import com.how2java.tmall.pojo.AdminUser;
import com.how2java.tmall.service.AdminUserService;
import com.how2java.tmall.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@RequestMapping("")
@Controller
public class BackController {

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private RoleService roleService;
    @RequestMapping("checkAdminUser")
    public String checkAdminUser(HttpSession session){
        AdminUser adminUser = (AdminUser) session.getAttribute("adminUser");
        if (null == adminUser){
            return "admin/login";
        }
        //跳转到已有role的第一个menu的sign地址
        String sign = adminUser.getRoles().get(0).getMenus().get(0).getSign();
        return "redirect:"+sign+"_list";
    }

    @RequestMapping("checkLogin")
    public String checkLogin(AdminUser adminUser,HttpSession session,Model model){
//        String name = adminUser.getName();
//        String password = adminUser.getPassword();
//        AdminUser adminUser1 = adminUserService.get(name, password);
//        if(adminUser1 != null){
//            //根据Role填充Menu
//            roleService.fillMenus(adminUser1.getRoles());
//            session.setAttribute("adminUser",adminUser1);
//            String sign = adminUser1.getRoles().get(0).getMenus().get(0).getSign();
//            return "redirect:"+sign+"_list";
//        }
//        model.addAttribute("msg","用户名或密码不匹配");

        //shiro登录验证
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(adminUser.getName(), adminUser.getPassword());
            subject.login(token);
            AdminUser adminUser1 = (AdminUser) subject.getPrincipal();
            roleService.fillMenus(adminUser1.getRoles());
            session.setAttribute("adminUser",adminUser1);
            String sign = adminUser1.getRoles().get(0).getMenus().get(0).getSign();
            return "redirect:"+sign+"_list";
//            return "admin/listAdminUser";
        }catch (Exception e){
            model.addAttribute("msg","用户名或密码不匹配");
            return "admin/login";
        }
    }
    //注销
    @RequestMapping("logout")
    public String logout(HttpSession session){
        SecurityUtils.getSubject().logout();
        System.out.println(session.toString());
        return "admin/login";
    }
}
