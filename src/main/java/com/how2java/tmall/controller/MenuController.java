package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Menu;
import com.how2java.tmall.service.MenuService;
import com.how2java.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @RequestMapping("admin_menu_list")
    public String list(Model model, Page page){
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Menu> menuList = menuService.list();
        menuService.fillRoles(menuList);
        int total = (int)new PageInfo<>(menuList).getTotal();
        page.setTotal(total);
        model.addAttribute("menuList",menuList);
        model.addAttribute("page",page);

        return "admin/listMenu";
    }

    @RequestMapping("admin_menu_add")
    public String add(Menu menu){
        menu.setCreatetime(new Date());
        menuService.add(menu);
        return "redirect:admin_menu_list";
    }

    @RequestMapping("admin_menu_edit")
    public @ResponseBody Menu edit(Integer mid){
        Menu menu = menuService.get(mid);
        return menu;
    }

    @RequestMapping("admin_menu_update")
    public String update(Menu menu){
        menuService.update(menu);
        return "redirect:admin_menu_list";
    }

    @RequestMapping("admin_menu_delete")
    public @ResponseBody String delete(Integer mid){
        try {
            menuService.delete(mid);
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }
}
