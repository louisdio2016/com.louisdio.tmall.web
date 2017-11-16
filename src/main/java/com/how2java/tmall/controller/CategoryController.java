package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.util.ImageUtil;
import com.how2java.tmall.util.Page;
import com.how2java.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_category_list")
    public String list(Model model,Page page){
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Category> cs = categoryService.list();
        int total = (int)new PageInfo<>(cs).getTotal();
        //用于获得totalPage和last索引
        page.setTotal(total);
        model.addAttribute("cs",cs);
        model.addAttribute("page",page);
        return "admin/listCategory";
    }
    @RequestMapping("admin_category_add")
    public String add(Category category, HttpSession session, UploadedImageFile uploadFile)throws IOException{
        categoryService.add(category);
        File imageFloder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFloder, category.getId()+".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        uploadFile.getImage().transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img,"jpg",file);
        return "redirect:/admin_category_list";
    }
    @RequestMapping("admin_category_delete")
    public String delete(int cid, HttpSession session){
        categoryService.delete(cid);
        String path = session.getServletContext().getRealPath("img/category");
        File floder = new File(path);
        File file = new File(floder, cid + ".jpg");
        file.delete();
        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_edit")
    public String get(int cid,Model model){
        Category category = categoryService.get(cid);
        model.addAttribute("c",category);
        return "admin/editCategory";
    }

    @RequestMapping("admin_category_update")
    public String update(Category category,HttpSession session,UploadedImageFile uploadFile)throws IOException{
        categoryService.update(category);
        MultipartFile image = uploadFile.getImage();
        if (image != null && !image.isEmpty()){
            String floderPath = session.getServletContext().getRealPath("img/category");
            File floder = new File(floderPath);
            File file = new File(floder, category.getId() + ".jpg");
            if (!file.exists()){
                file.mkdirs();
                file.createNewFile();
            }
            image.transferTo(file);
            //将图片转化为IO流
            BufferedImage bufferedImage = ImageUtil.change2jpg(file);
            //将图片写入到指定位置
            ImageIO.write(bufferedImage,"jpg",file);
        }
        return "redirect:/admin_category_list";
    }
}
