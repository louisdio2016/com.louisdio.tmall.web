package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("admin_product_list")
    public String list(int cid, Page page, Model model){
        Category category = categoryService.get(cid);
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Product> productList = productService.list(cid);
        page.setTotal((int)new PageInfo<>(productList).getTotal());
        page.setParam("&cid="+cid);
        //放入model
        model.addAttribute("pList",productList);
        model.addAttribute("page",page);
        model.addAttribute("c",category);
        return "admin/listProduct";
    }

    @RequestMapping("admin_product_add")
    public String add(Product product){
        productService.add(product);
        return "redirect:admin_product_list?cid="+product.getCid();
    }

    @RequestMapping("admin_product_edit")
    public String edit(int pid,Model model){
        Product product = productService.get(pid);
        Category category = categoryService.get(product.getCid());
        product.setCategory(category);
        model.addAttribute("p",product);
        return "admin/editProduct";
    }

    @RequestMapping("admin_product_update")
    public String update(Product product){
        productService.update(product);
        return "redirect:admin_product_list?cid="+product.getCid();
    }

    @RequestMapping("admin_product_delete")
    public String delete(int pid,int cid){
        productService.delete(pid);
        return "redirect:admin_product_list?cid="+cid;
    }
}
