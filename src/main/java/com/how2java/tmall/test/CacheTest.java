package com.how2java.tmall.test;

import com.how2java.tmall.pojo.AdminUser;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.AdminUserService;
import com.how2java.tmall.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@Transactional
public class CacheTest {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AdminUserService adminUserService;
    private Logger log = LoggerFactory.getLogger(CacheTest.class);

    @Test
    public void testCategoryList(){
        log.info("log.info(..)====================================>"+log.getClass());
        List<Category> list = categoryService.list();
        System.out.println(list.toString());
    }

//    @Test
//    public void testCategoryGet(){
//        log.info("根据ID获得Category对象");
//        Category category = null;
//        for (int i=0;i<10;i++){
//            category = categoryService.get(60);
//        }
//        log.info(category.toString());
//    }
    //spring + ehcache : 342    252
    //取消@Transactional : 查询了数据库  248   905
    //启用mybatis二级缓存 : 390   482
    @Test
    public void testUserGet(){
        log.info("根据ID获得AdminUser对象");
        long start = System.currentTimeMillis();
        AdminUser adminUser = null;
        for (int i=0;i<10;i++){
            adminUser = adminUserService.get(36);
            log.info(adminUser.toString());
        }
        long end = System.currentTimeMillis();
        log.info("用时："+(end-start)+" 毫秒");
    }

    @Test
    public void testRedisCacheAdminUser(){
        log.info("根据ID获得AdminUser对象");
        long start = System.currentTimeMillis();
        AdminUser adminUser = null;
        for (int i=0;i<10;i++){
            adminUser = adminUserService.get(36);
            log.info(adminUser.toString());
        }
        long end = System.currentTimeMillis();
        log.info("用时："+(end-start)+" 毫秒");
    }
}
