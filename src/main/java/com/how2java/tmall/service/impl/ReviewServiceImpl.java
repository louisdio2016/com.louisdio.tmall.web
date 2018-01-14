package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.ReviewMapper;
import com.how2java.tmall.pojo.Review;
import com.how2java.tmall.pojo.ReviewExample;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.ReviewService;
import com.how2java.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private UserService userService;
    @Override
    public List<Review> list(int pid) {
        ReviewExample example = new ReviewExample();
        example.setOrderByClause("createDate desc");
        example.createCriteria().andPidEqualTo(pid);
        List<Review> reviews = reviewMapper.selectByExample(example);
        return reviews;
    }

    @Override
    public void add(Review review) {
        reviewMapper.insertSelective(review);
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Review get(int id) {
        return null;
    }

    @Override
    public void update(Review review) {

    }

    @Override
    public int getQuantity(int pid) {
        ReviewExample example = new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        List<Review> reviews = reviewMapper.selectByExample(example);
        return reviews.size();
    }

    @Override
    public void setUser(Review review) {
        int uid = review.getUid();
        User user = userService.get(uid);
        review.setUser(user);
    }

    @Override
    public void setUser(List<Review> reviews) {
        if (reviews.isEmpty() || reviews.size()==0)
            return;
        for (Review r:reviews){
            setUser(r);
        }
    }
}
