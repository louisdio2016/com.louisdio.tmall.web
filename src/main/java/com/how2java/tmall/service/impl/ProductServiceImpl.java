package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.CategoryMapper;
import com.how2java.tmall.mapper.ProductImageMapper;
import com.how2java.tmall.mapper.ProductMapper;
import com.how2java.tmall.pojo.*;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private OrderItemService orderItemService;
    @Override
    public List<Product> list(int cid) {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andCidEqualTo(cid);
        productExample.setOrderByClause("id desc");
        List<Product> productList = productMapper.selectByExample(productExample);
        //设置productImage,用于在listProduct.jsp中显示
        setProductImage(productList);
        //setCategory(productList);
        Category category = categoryMapper.selectByPrimaryKey(cid);
        for (Product product:productList){
            product.setCategory(category);
        }
        return productList;
    }

    @Override
    public void add(Product product) {
        productMapper.insertSelective(product);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Product get(int id) {
        Product product = productMapper.selectByPrimaryKey(id);
        Category category = categoryMapper.selectByPrimaryKey(product.getCid());
        setProductImage(product);
        product.setCategory(category);
        return product;
    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public void setProductImage(Product product) {
        List<ProductImage> productImageList = productImageService.list(product.getId(), ProductImageService.type_single);
        if (!productImageList.isEmpty()){
            product.setProductImage(productImageList.get(0));
        }

    }

    @Override
    public void setProductImage(List<Product> productList) {
        for (Product product : productList){
            setProductImage(product);
        }
    }

    @Override
    public void fill(List<Category> categories) {
        for (Category category:categories){
            fill(category);
        }
    }

    @Override
    public void fill(Category category) {
        List<Product> list = list(category.getId());
        category.setProducts(list);
    }

    @Override
    public void fillByRow(List<Category> categories) {
        int productNumberEachRow = 8;
        for (Category category:categories){
            List<Product> products = category.getProducts();
            List<List<Product>> productRow = new ArrayList<>();
            for (int i=0;i<products.size();i+=productNumberEachRow){
                int size = i+productNumberEachRow;
                size = size>products.size()?products.size():size;
                productRow.add(products.subList(i,size));
            }
            category.setProductsByRow(productRow);
//            while (!products.isEmpty() || products.size() != 0){
//                List<Product> temp = new ArrayList<>();
//                for (int i=0;i<8 && i<products.size();i++){
//                    temp.add(products.remove(i));
//                }
//                productRow.add(temp);
//            }
        }
    }

    @Override
    public void setSaleReviewQuantity(Product product) {
        int saleQuantity = orderItemService.getQuantity(product.getId());
        int reviewQuantity = reviewService.getQuantity(product.getId());
        product.setSaleQuantity(saleQuantity);
        product.setReviewQuantity(reviewQuantity);
    }

    @Override
    public List<Product> search(String keyword) {
        ProductExample example = new ProductExample();
        example.createCriteria().andNameLike("%" + keyword + "%");
        example.setOrderByClause("id desc");
        List<Product> products = productMapper.selectByExample(example);
        setProductImage(products);
        for (Product product:products){
            Category category = categoryMapper.selectByPrimaryKey(product.getCid());
            product.setCategory(category);
        }
        return products;
    }

    @Override
    public void updateStock(int pid, int num) {
        Product product = productMapper.selectByPrimaryKey(pid);
        product.setStock(product.getStock() - num);
        update(product);
    }

    /*
    @Override
    public void setProductImage(Product product) {
        ProductImageExample example = new ProductImageExample();
        example.createCriteria().andPidEqualTo(product.getId());
        example.getOrderByClause("id DESC");
        List<ProductImage> productImages = productImageMapper.selectByExample(example);
        product.setProductImage(productImages.get(0));
    }

    @Override
    public void setProductImage(List<Product> productList) {
        ProductImageExample example = null;
        Product product = null;
        for (int i=0;i<productList.size();i++){
            product = productList.get(i);
            example = new ProductImageExample();
            example.createCriteria().andPidEqualTo(product.getId());
            example.getOrderByClause("id DESC");
            List<ProductImage> productImages = productImageMapper.selectByExample(example);
            product.setProductImage(productImages.get(0));
        }
    }
    */
}
