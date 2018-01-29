package com.how2java.tmall.test;

import com.how2java.tmall.mapper.AdminUserMapper;
import com.how2java.tmall.pojo.AdminUser;
import com.how2java.tmall.pojo.Role;
import com.how2java.tmall.pojo.UserRoleVo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.*;

public class AdminUserMapperTest {
    private SqlSessionFactory sqlSessionFactory;
    private InputStream inputStream;
    private SqlSession session;
    private static String resource = "sqlMapConfig.xml";
    @Before
    public void setUp() throws Exception{
        String resource = "sqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @After
    public void Close()throws Exception{
        session.close();
//        inputStream.close();
    }

    @Test
    public void testSelectByPrimaryKey(){
        session = sqlSessionFactory.openSession();
        AdminUserMapper mapper = session.getMapper(AdminUserMapper.class);
        AdminUser adminUser = mapper.selectByPrimaryKey(11);
        System.out.println(adminUser.toString());
        List<Role> roles = adminUser.getRoles();
        for (Role r:roles){
            System.out.println(r.toString());
        }
    }

    @Test
    public void testSelectAdminUserList(){
        SqlSession session = sqlSessionFactory.openSession();
        AdminUserMapper mapper = session.getMapper(AdminUserMapper.class);
        List<AdminUser> adminUsers = mapper.selectAdminUserList();
        for (AdminUser a:adminUsers){
            System.out.println(a.toString());
            List<Role> roles = a.getRoles();
            for (Role r:roles){
                System.out.println(r.toString());
            }
            System.out.println("-------------------------------");
        }
    }

    @Test
    public void testInsertAdminUser(){
        SqlSession session = sqlSessionFactory.openSession();
        AdminUserMapper mapper = session.getMapper(AdminUserMapper.class);
        AdminUser adminUser = new AdminUser();
        adminUser.setName("test3");
        adminUser.setPassword("test3");
        adminUser.setCreatetime(new Date());
        mapper.insertAdminUser(adminUser);
        session.commit();
        int id = adminUser.getId();
//        Map<String, Object> map = new HashMap<String,Object>();
//        map.put("userId",id);
//        map.put("roleIds", Arrays.asList("11","12"));
        UserRoleVo userRoleVo = new UserRoleVo();
        userRoleVo.setUserId(id);
        userRoleVo.setRoleIds(Arrays.asList(12,13));
        mapper.insertAdminUserRole(userRoleVo);
        session.commit();
    }

    @Test
    public void testDeleteAdminUserList(){
        SqlSession session = sqlSessionFactory.openSession();
        AdminUserMapper mapper = session.getMapper(AdminUserMapper.class);
        mapper.deleteAdminUserList(Arrays.asList(20,21,22));
        session.commit();
        session.close();
    }

    @Test
    public void testDeleteAdminUserRoleList(){
        SqlSession session = sqlSessionFactory.openSession();
        AdminUserMapper mapper = session.getMapper(AdminUserMapper.class);
        //1.删除之后，再添加User-Role
        List<Integer> list = Arrays.asList(19, 23, 24);
        mapper.deleteAdminUserRoleList(list);
        mapper.deleteAdminUserList(list);
        session.commit();
        session.close();
    }

    @Test
    public void testUpdateAdminUserRole(){
        SqlSession session = sqlSessionFactory.openSession();
        AdminUserMapper mapper = session.getMapper(AdminUserMapper.class);
        //1.update:user_role
        //删除User所有Role关系
        mapper.deleteAdminUserRole(25);
        //插入User的关系
        UserRoleVo userRoleVo = new UserRoleVo();
        userRoleVo.setUserId(25);
        userRoleVo.setRoleIds(Arrays.asList(12,13));
        mapper.insertAdminUserRole(userRoleVo);
        session.commit();
        session.close();
    }

    @Test
    public void testUpdateAdminUser(){
        SqlSession session = sqlSessionFactory.openSession();
        AdminUserMapper mapper = session.getMapper(AdminUserMapper.class);
        AdminUser adminUser = new AdminUser();
        adminUser.setId(25);
        adminUser.setName("b");
        adminUser.setPassword("456");
        adminUser.setCreatetime(new Date());
        mapper.updateAdminUser(adminUser);
        session.commit();
        session.close();
    }

    @Test
    public void testFindRoles(){
        SqlSession session = sqlSessionFactory.openSession();
        AdminUserMapper mapper = session.getMapper(AdminUserMapper.class);
        Set<String> roles = mapper.findRoles("adam1");
        System.out.println(roles);
        session.close();
    }

    @Test
    public void testFindPermissions(){
        SqlSession session = sqlSessionFactory.openSession();
        AdminUserMapper mapper = session.getMapper(AdminUserMapper.class);
        Set<String> menus = mapper.findPermissions("adam1");
        System.out.println(menus);
        session.close();
    }
}
