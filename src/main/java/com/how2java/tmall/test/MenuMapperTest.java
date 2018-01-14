package com.how2java.tmall.test;

import com.how2java.tmall.mapper.MenuMapper;
import com.how2java.tmall.pojo.Menu;
import com.how2java.tmall.pojo.Role;
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

public class MenuMapperTest {
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
        MenuMapper mapper = session.getMapper(MenuMapper.class);
        Menu menu = mapper.selectByPrimaryKey(1);
        System.out.println(menu);
        for (Role role:menu.getRoles()){
            System.out.println(role);
        }
    }

    @Test
    public void testSelectMenuList(){
        MenuMapper mapper = session.getMapper(MenuMapper.class);
        List<Menu> menus = mapper.selectMenuList();
        for (Menu menu:menus){
            System.out.println(menu);
//            for (Role role:menu.getRoles()){
//                System.out.println(role);
//            }
            System.out.println("------------------------");
        }
    }

    @Test
    public void testInsertMenu(){
        MenuMapper mapper = session.getMapper(MenuMapper.class);
        Menu menu = new Menu();
        menu.setName("退货管理");
        menu.setCreatetime(new Date());
        mapper.insertMenu(menu);
        session.commit();
    }

    @Test
    public void testDeleteMenu(){
        MenuMapper mapper = session.getMapper(MenuMapper.class);
        mapper.deleteMenu(4);
        session.commit();
    }

    @Test
    public void testDeleteMenuList(){
        MenuMapper mapper = session.getMapper(MenuMapper.class);
        mapper.deleteMenuList(Arrays.asList(5,6,7));
        session.commit();
    }

    @Test
    public void testUpdateMenu(){
        MenuMapper mapper = session.getMapper(MenuMapper.class);
        Menu menu = new Menu();
        menu.setId(8);
        menu.setName("退款管理");
        menu.setCreatetime(new Date());
        mapper.updateMenu(menu);
        session.commit();
    }
}
