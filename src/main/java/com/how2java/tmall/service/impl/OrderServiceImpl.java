package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.OrderMapper;
import com.how2java.tmall.pojo.*;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ProductService productService;

    @Override
    public Order get(int id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        setUser(order);
        return order;
    }

    @Override
    public List<Order> list() {
        OrderExample orderExample = new OrderExample();
        orderExample.setOrderByClause("id DESC");
        List<Order> orders = orderMapper.selectByExample(orderExample);
        setUser(orders);
        return orders;
    }

    @Override
    public List<Order> list(int uid, String excludedStatus) {
        OrderExample example = new OrderExample();
        example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(excludedStatus);
        example.setOrderByClause("id desc");
        List<Order> orders = orderMapper.selectByExample(example);
        setUser(orders);
        return orders;
    }

    public void setUser(List<Order> orders){
        for (Order o:orders){
            setUser(o);
        }
    }

    public void setUser(Order o){
        User user = userService.get(o.getUid());
        o.setUser(user);
    }

    @Override
    public List<Order> list(int uid) {
        return null;
    }

    @Override
    public void update(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public void add(Order order) {
        orderMapper.insertSelective(order);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = "Exception")
    public float add(Order o, List<OrderItem> ois) {
        float total = 0;
        add(o);
        for (OrderItem oi:ois){
            oi.setOid(o.getId());
            orderItemService.update(oi);
            productService.updateStock(oi.getPid(),oi.getNumber());
            total += oi.getProduct().getPromotePrice()*oi.getNumber();
        }
        return total;
    }

    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }
}
