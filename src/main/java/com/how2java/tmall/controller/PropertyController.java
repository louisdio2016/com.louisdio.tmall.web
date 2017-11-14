package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("")
public class PropertyController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private PropertyService propertyService;

    @RequestMapping("admin_property_list")
    public String list(int cid, Model model, Page page){
        Category category = categoryService.get(cid);
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Property> propertyList = propertyService.list(cid);
        PageInfo<Property> propertyPageInfo = new PageInfo<>(propertyList);
        int total = (int) propertyPageInfo.getTotal();
        page.setTotal(total);
        page.setParam("&cid="+category.getId());
        //返回给页面数据
        model.addAttribute("pList",propertyList);
        model.addAttribute("page",page);
        model.addAttribute("c",category);
        return "admin/listProperty";
    }

    @RequestMapping("admin_property_add")
    public String add(Property property){
        propertyService.add(property);
        return "redirect:admin_property_list?cid="+property.getCid();
    }

    @RequestMapping("admin_property_edit")
    public String edit(int pid,Model model){
        Property property = propertyService.get(pid);
        Integer cid = property.getCid();
        Category category = categoryService.get(cid);
        model.addAttribute("c",category);
        model.addAttribute("p",property);
        return "admin/editProperty";
    }

    @RequestMapping("admin_property_update")
    public String update(Property property){
        Integer cid = property.getCid();
        propertyService.update(property);
        return "redirect:admin_property_list?cid="+cid;
    }

    @RequestMapping("admin_property_delete")
    public String delete(int pid){
        Property property = propertyService.get(pid);
        Integer cid = property.getCid();
        propertyService.delete(pid);
        return "redirect:admin_property_list?cid="+cid;
    }
}
