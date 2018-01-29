package com.how2java.tmall.test;

import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.impl.CategoryServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateService {
    @Bean(name = "CategoryServiceImpl")
    public CategoryServiceImpl getCategoryService(){
        return new CategoryServiceImpl();
    }
}
