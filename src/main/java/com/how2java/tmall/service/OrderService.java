package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Order;

import java.util.List;

public interface OrderService {

    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";
    Order get(int id);
    List<Order> list(int uid);
    List<Order> list();
    void update(Order order);
    void add(Order order);
    void delete(int id);
}
