package com.how2java.tmall.comparator;

import com.how2java.tmall.pojo.Product;

import java.util.Comparator;

public class ProductSaleCountComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o1.getSaleQuantity() - o2.getSaleQuantity();
    }
}
