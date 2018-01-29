package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.AdminUser;
import com.how2java.tmall.pojo.Role;
import com.how2java.tmall.pojo.UserRoleVo;
import com.how2java.tmall.service.AdminUserService;
import com.how2java.tmall.service.RoleService;
import com.how2java.tmall.util.Page;
import com.how2java.tmall.util.SHAUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class AdminUserController {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private RoleService roleService;
    @RequestMapping("admin_user_list")
    public String list(Model model,Page page){
        //分页
        PageHelper.offsetPage(page.getStart(),page.getCount());
//        List<AdminUser> adminUserList = adminUserService.list(page.getStart(),page.getCount());
        List<AdminUser> adminUserList = adminUserService.list();
        //装填Role
        adminUserService.fillRoles(adminUserList);
        //取总AdminUser数
        int total = (int)new PageInfo<>(adminUserList).getTotal();
        page.setTotal(total);
        //绑定数据
        model.addAttribute("adminUsers",adminUserList);
        model.addAttribute("page",page);
        List<Role> roleList = roleService.list();
        model.addAttribute("roles",roleList);
        return "admin/listAdminUser";
    }

    @RequestMapping(value = "admin_user_edit" ,produces = "application/json;charset=UTF-8")
    public @ResponseBody AdminUser edit(Integer auid){
        AdminUser adminUser = adminUserService.get(auid);
        return adminUser;
    }

    @RequestMapping("admin_user_update")
    public String update(UserRoleVo userRoleVo){
        adminUserService.update(userRoleVo);
        return "redirect:admin_user_list";
    }

    @RequestMapping("admin_user_delete")
    public String delete(Integer aucid){
        adminUserService.delete(aucid);
        return "redirect:admin_user_list";
    }

    @RequestMapping("admin_user_add")
    public String add(UserRoleVo userRoleVo){
        userRoleVo.getAdminUser().setCreatetime(new Date());
        AdminUser adminUser = userRoleVo.getAdminUser();
        //将明文密码进行SHA加密
        String shaPassword = SHAUtil.sha(adminUser.getPassword(), adminUser.getName());
        adminUser.setPassword(shaPassword);
        adminUserService.add(userRoleVo);
        return "redirect:admin_user_list";
    }
}
