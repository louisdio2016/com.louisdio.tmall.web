package com.how2java.tmall.pojo;

import com.how2java.tmall.service.OrderService;

import java.util.Date;
import java.util.List;

public class Order {
    private Integer id;

    private String orderCode;

    private String address;

    private String post;

    private String receiver;

    private String mobile;

    private String userMessage;

    private Date createDate;

    private Date payDate;

    private Date deliveryDate;

    private Date confirmDate;

    private Integer uid;

    private String status;

    private List<OrderItem> orderItems;

    private User user;

    private float total;

    private int totalNumber;

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    //计算order总价
    public float getTotal() {
//        float total = 0.00f;
//        float subTotal;
//        Product product = null;
//        int proNum;
//        //1.计算orderItem的小计
//        if (orderItems.isEmpty() || orderItems.size() == 0) {
//            return total;
//        }
//        for (OrderItem orderItem:orderItems){
//            product = orderItem.getProduct();
//            proNum = orderItem.getNumber();
//            subTotal = product.getPromotePrice() * proNum;
//            //2.修改product的stock
//            product.setStock(product.getStock() - proNum);
//            //3.计算order的总计
//            total += subTotal;
//        }
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    //计算商品总数
    public int getTotalNumber() {
//        int num = 0;
//        if (orderItems.isEmpty() || orderItems.size() == 0) {
//            return num;
//        }
//        for (OrderItem orderItem:orderItems){
//            num += orderItem.getNumber();
//        }
        return totalNumber;
    }
    public String getStatusDesc(){
        String desc="未知";
        switch (status){
            case OrderService.waitPay:
                desc="待付款";
                break;
            case OrderService.waitDelivery:
                desc="待发货";
                break;
            case OrderService.waitConfirm:
                desc="待收货";
                break;
            case OrderService.waitReview:
                desc="待评价";
                break;
            case OrderService.finish:
                desc="完成";
                break;
            case OrderService.delete:
                desc="删除";
                break;
            default:
                desc="未知";
        }
        return desc;
    }


    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage == null ? null : userMessage.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}