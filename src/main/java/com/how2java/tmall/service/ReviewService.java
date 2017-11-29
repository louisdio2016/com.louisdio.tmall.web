package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Review;

import java.util.List;

public interface ReviewService {
    List<Review> list(int pid);
    void add(Review review);
    void delete(int id);
    Review get(int id);
    void update(Review review);
    int getQuantity(int pid);
    void setUser(Review review);
    void setUser(List<Review> reviews);
}
