package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.ProductImageMapper;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.pojo.ProductImageExample;
import com.how2java.tmall.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    private ProductImageMapper productImageMapper;

    @Override
    public List<ProductImage> list(int pid, String type) {
        ProductImageExample example = new ProductImageExample();
        example.createCriteria().andTypeEqualTo(type).andPidEqualTo(pid);
        example.setOrderByClause("id DESC");
        List<ProductImage> productImageList = productImageMapper.selectByExample(example);
        return productImageList;
    }

    @Override
    public void add(ProductImage productImage) {
        productImageMapper.insertSelective(productImage);
    }

    @Override
    public void delete(int id) {
        productImageMapper.deleteByPrimaryKey(id);
    }
}
