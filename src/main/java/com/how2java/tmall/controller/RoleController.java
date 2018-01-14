package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Menu;
import com.how2java.tmall.pojo.Role;
import com.how2java.tmall.pojo.RoleMenuVo;
import com.how2java.tmall.service.MenuService;
import com.how2java.tmall.service.RoleService;
import com.how2java.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@RequestMapping("")
@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @RequestMapping("admin_role_list")
    public String list(Model model, Page page){
        List<Role> roleList = null;
        try {
            PageHelper.offsetPage(page.getStart(),page.getCount());
            roleList = roleService.list();
            roleService.fillMenus(roleList);
            roleService.fillAdminUsers(roleList);
            int total = (int)new PageInfo<>(roleList).getTotal();
            page.setTotal(total);
        }catch (RuntimeException e){
            System.out.println("----------1-------------");
            e.printStackTrace();
        }

        try {
            model.addAttribute("page",page);
            model.addAttribute("roles",roleList);
            List<Menu> menuList = menuService.list();
            model.addAttribute("menuList",menuList);
        }catch (RuntimeException e){
            System.out.println("----------2-------------");
            e.printStackTrace();
        }
        return "admin/listRole";
    }

    @RequestMapping("admin_role_add")
    public String add(RoleMenuVo roleMenuVo){
        roleMenuVo.getRole().setCreatetime(new Date());
        roleService.add(roleMenuVo);
        return "redirect:admin_role_list";
    }

    @RequestMapping("admin_role_delete")
    public @ResponseBody String delete(Integer rid){
        try {
            //如果报异常，说明有AdminUser是具有当前Role的，需要捕获异常，将fail以json形式返回
            roleService.delete(rid);
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

    @RequestMapping("admin_role_edit")
    public @ResponseBody Role edit(Integer rid){
        //查找Role
        Role role = roleService.get(rid);
        return role;
    }

    @RequestMapping(value = "admin_role_update",method = RequestMethod.POST)
    public String update(RoleMenuVo roleMenuVo){
        try {
            roleService.update(roleMenuVo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:admin_role_list";
    }
}
