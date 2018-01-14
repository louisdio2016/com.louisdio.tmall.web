package com.how2java.tmall.test;

import com.how2java.tmall.mapper.AdminUserMapper;
import com.how2java.tmall.mapper.RoleMapper;
import com.how2java.tmall.pojo.AdminUser;
import com.how2java.tmall.pojo.Menu;
import com.how2java.tmall.pojo.Role;
import com.how2java.tmall.pojo.RoleMenuVo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RoleMapperTest {
    private SqlSessionFactory sqlSessionFactory;
    private InputStream inputStream;
    private SqlSession session;
    private static String resource = "sqlMapConfig.xml";


    @Before
    public void setUp() throws Exception{
        inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        session = sqlSessionFactory.openSession();
    }

    @After
    public void Close()throws Exception{
        session.close();
        inputStream.close();
    }

    @Test
    public void testSelectByPrimaryKey(){
        RoleMapper mapper = session.getMapper(RoleMapper.class);
        Role role = mapper.selectByPrimaryKey(18);
        System.out.println(role.toString());
        for (AdminUser user:role.getAdminUsers()){
            System.out.println(user.toString());
        }
        for (Menu menu:role.getMenus()){
            System.out.println(menu.toString());
        }
        }

    @Test
    public void testSelectRoleList(){
        RoleMapper mapper = session.getMapper(RoleMapper.class);
        List<Role> roles = mapper.selectRoleList();
        for (Role role:roles){
            System.out.println(role.toString());
            for (AdminUser user:role.getAdminUsers()){
                System.out.println(user.toString());
            }
            for (Menu menu:role.getMenus()){
                System.out.println(menu.toString());
            }
            System.out.println("------------------------");
        }
    }

    @Test
    public void testInsertRole(){
        RoleMapper mapper = session.getMapper(RoleMapper.class);
        Role role = new Role();
        role.setName("物流管理");
        role.setCreatetime(new Date());
        mapper.insertRole(role);
        RoleMenuVo roleMenuVo = new RoleMenuVo();
        roleMenuVo.setRoleId(role.getId());
        roleMenuVo.setMenuIds(Arrays.asList(1,2));
        mapper.insertRoleMenu(roleMenuVo);
        session.commit();
    }

    @Test
    public void testDeleteRole(){
        RoleMapper mapper = session.getMapper(RoleMapper.class);
        mapper.deleteRoleMenu(19);
        mapper.deleteRole(19);
        session.commit();
    }

    @Test
    public void testDeleteRoleList(){
        RoleMapper mapper = session.getMapper(RoleMapper.class);
        List<Integer> list = Arrays.asList(18, 19, 20, 21, 22, 23);
        mapper.deleteRoleMenuList(list);
        mapper.deleteRoleList(list);
        session.commit();
    }

    @Test
    public void testUpdateRole(){
        RoleMapper mapper = session.getMapper(RoleMapper.class);
        Role role = new Role();
        role.setId(14);
        role.setName("评价管理员");
        role.setCreatetime(new Date());
        mapper.updateRole(role);
        mapper.deleteRoleMenu(role.getId());
        RoleMenuVo roleMenuVo = new RoleMenuVo();
        roleMenuVo.setRoleId(role.getId());
        roleMenuVo.setMenuIds(Arrays.asList(1,2,3));
        mapper.insertRoleMenu(roleMenuVo);
        session.commit();
    }

}

