package com.how2java.tmall.controller;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.util.ImageUtil;
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
public class ProductImageController {
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @RequestMapping("admin_productImage_list")
    public String list(int pid, Model model){
        Product product = productService.get(pid);
        product.setCategory(categoryService.get(product.getCid()));
        List<ProductImage> pisDetail = productImageService.list(pid, ProductImageService.type_detail);
        List<ProductImage> pisSingle = productImageService.list(pid, ProductImageService.type_single);
        model.addAttribute("pisDetail",pisDetail);
        model.addAttribute("pisSingle",pisSingle);
        model.addAttribute("p",product);
        return "admin/listProductImage";
    }

    @RequestMapping("admin_productImage_add")
    public String add(ProductImage productImage, HttpSession session, UploadedImageFile uploadedImageFile)throws IOException{
        //先保存，保存后生成主键
        productImageService.add(productImage);
        String fileName = productImage.getId()+".jpg";
        String realPath ;
        String singlePath_small = null;
        String singlePath_middle = null;
        File folder;
        File destFile;
        MultipartFile image;
        BufferedImage bufferedImage;
        //获得realPath,new 文件夹
        if (ProductImageService.type_single.equals(productImage.getType())){
            realPath = session.getServletContext().getRealPath("img/productSingle");
            singlePath_small = session.getServletContext().getRealPath("img/productSingle_small");
            singlePath_middle = session.getServletContext().getRealPath("img/productSingle_middle");
        }else {
            realPath = session.getServletContext().getRealPath("img/productDetail");
        }
        //创建文件夹
        folder = new File(realPath);
        if (!folder.exists())
            folder.mkdirs();
        //复制文件
        try {
            destFile = new File(folder, fileName);
            image = uploadedImageFile.getImage();
            image.transferTo(destFile);
            bufferedImage = ImageUtil.change2jpg(destFile);
            ImageIO.write(bufferedImage,"jpg",destFile);
            //判断type是否为single,创建文件夹
            if (ProductImageService.type_single.equals(productImage.getType())){
                File smallFile = new File(singlePath_small, fileName);
                File middleFile = new File(singlePath_middle, fileName);
                ImageUtil.resizeImage(destFile,56,56,smallFile);
                ImageUtil.resizeImage(destFile,217,190,middleFile);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return "redirect:admin_productImage_list?pid="+productImage.getPid();
    }
    @RequestMapping("admin_productImage_delete")
    public String delete(int iid,int pid,String type,HttpSession session){
        productImageService.delete(iid);
        String fileName = iid+".jpg";
        String smallPath = null;
        String middlePath = null;
        File file = null;
        String realPath;
        //判断type,获得path
        if (ProductImageService.type_single.equals(type)){
            realPath = session.getServletContext().getRealPath("img/productSingle");
            smallPath = session.getServletContext().getRealPath("img/productSingle_small");
            middlePath = session.getServletContext().getRealPath("img/productSingle_middle");
        }else {
            realPath = session.getServletContext().getRealPath("img/productDetail");
        }
        file = new File(realPath,fileName);
        //如果存在就删除
        if (file.exists())
            file.delete();
        //判断type == single,则删除small和middle中的image
        if (ProductImageService.type_single.equals(type)){
            File smallFile = new File(smallPath,fileName);
            File middleFile = new File(middlePath,fileName);
            if (smallFile.exists())
                smallFile.delete();
            if (middleFile.exists())
                middleFile.delete();
        }
        return "redirect:admin_productImage_list?pid="+pid;
    }
}
