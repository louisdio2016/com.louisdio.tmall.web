package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.CategoryMapper;
import com.how2java.tmall.mapper.ProductImageMapper;
import com.how2java.tmall.mapper.ProductMapper;
import com.how2java.tmall.pojo.*;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImageService productImageService;
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
        setProductImage(product);
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
