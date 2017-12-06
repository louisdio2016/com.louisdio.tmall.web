package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderItemService {
    OrderItem get(int id);
    List<OrderItem> list(int oid);
    List<OrderItem> list();
    void update(OrderItem orderItem);
    void add(OrderItem orderItem);
    void delete(int id);
    void fill(List<Order> os);
    void fill(Order o);
    void setProduct(List<OrderItem> ois);
    void setProduct(OrderItem oi);
    int getQuantity(int pid);
    List<OrderItem> listByUser(int uid);
}
