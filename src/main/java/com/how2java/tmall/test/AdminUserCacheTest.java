package com.how2java.tmall.test;

import com.how2java.tmall.pojo.AdminUser;
import com.how2java.tmall.pojo.UserRoleVo;
import com.how2java.tmall.service.AdminUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
//@Transactional
public class AdminUserCacheTest {
    @Autowired
    private AdminUserService adminUserService;
    private Logger log = LoggerFactory.getLogger(CacheTest.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void testGet(){
        log.info("=============== testGet start ===============");
        log.info("first list:");
        System.out.println(adminUserService.list());
        log.info("second list:");
        System.out.println(adminUserService.list());
//        log.info("add:");
//        UserRoleVo userRoleVo = new UserRoleVo();
//        AdminUser adminUser = new AdminUser();
//        adminUser.setPassword("123");
//        adminUser.setName("tttt");
//        userRoleVo.setAdminUser(adminUser);
//        adminUserService.add(userRoleVo);
//        log.info("third list:");
//        System.out.println(adminUserService.list());
    }

    @Test
    public void testStringRedis(){
        stringRedisTemplate.opsForValue().set("test","test");
    }
}
