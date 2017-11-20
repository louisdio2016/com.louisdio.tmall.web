package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.OrderItemMapper;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.OrderItemExample;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ProductService productService;
    @Override
    public OrderItem get(int id) {
        return null;
    }

    @Override
    public List<OrderItem> list(int oid) {
        OrderItemExample example = new OrderItemExample();
        example.setOrderByClause("id DESC");
        example.createCriteria().andOidEqualTo(oid);
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        return orderItems;
    }
    public List<OrderItem> list(){
        OrderItemExample example = new OrderItemExample();
        example.setOrderByClause("id DESC");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        return orderItems;
    }

    //向order中装填orderitem,向orderitem中装填product
    public void fill(List<Order> os){
        for (Order o:os){
            fill(o);
        }
    }

    public void fill(Order o){
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andOidEqualTo(o.getId());
        example.setOrderByClause("id DESC");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);

        setProduct(orderItems);

        float total = 0;
        int totalNumber = 0;
        for (OrderItem oi:orderItems){
            total += oi.getNumber()*oi.getProduct().getPromotePrice();
            totalNumber+= oi.getNumber();
        }
        o.setTotal(total);
        o.setTotalNumber(totalNumber);
        o.setOrderItems(orderItems);
    }

    public void setProduct(List<OrderItem> ois){
        for (OrderItem oi:ois){
            setProduct(oi);
        }
    }

    public void setProduct(OrderItem oi){
        Product product = productService.get(oi.getPid());
        oi.setProduct(product);
    }

    @Override
    public void update(OrderItem orderItem) {

    }

    @Override
    public void add(OrderItem orderItem) {

    }

    @Override
    public void delete(int id) {

    }
}
