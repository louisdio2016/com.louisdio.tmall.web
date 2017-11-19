package com.how2java.tmall.controller;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class PropertyValueController {
    @Autowired
    private PropertyValueService propertyValueService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @RequestMapping("admin_productValue_edit")
    public String edit(int pid, Model model){
        List<PropertyValue> propertyValueList = propertyValueService.list(pid);
        Product product = productService.get(pid);
        Category category = categoryService.get(product.getCid());
        model.addAttribute("pvList",propertyValueList);
        model.addAttribute("p",product);
        model.addAttribute("c",category);
        return "admin/editPropertyValue";
    }

    @RequestMapping("admin_propertyValue_update")
    public String update(PropertyValue propertyValue){
        try {
            propertyValueService.update(propertyValue);

            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "false";
        }
    }
}
