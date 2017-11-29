package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;

import java.util.List;

public interface ProductService {
    List<Product> list(int cid);
    //List<Product> list(Page page);
    //int total();
    void add(Product product);
    void delete(int id);
    Product get(int id);
    void update(Product product);
    void setProductImage(Product product);
    void setProductImage(List<Product> productList);
    void fill(List<Category> categories);
    void fill(Category category);
    void fillByRow(List<Category> categories);
    void setSaleReviewQuantity(Product product);
}
