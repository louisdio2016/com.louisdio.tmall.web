package com.how2java.tmall.cache;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyBatisRedisCache implements Cache {
    private String id;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    private static Logger logger = LoggerFactory.getLogger(MyBatisRedisCache.class);

    private JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
    @Autowired
    private static JedisConnectionFactory jedisConnectionFactory;

    public static JedisConnectionFactory getJedisConnectionFactory() {
        return jedisConnectionFactory;
    }

    public static void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
        MyBatisRedisCache.jedisConnectionFactory = jedisConnectionFactory;
    }
    public MyBatisRedisCache(final String id){
        if (id == null){
            throw new IllegalArgumentException("必须传入id");
        }
        logger.debug("MyBatisRedisCache:id="+id);
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public int getSize() {
        int result = 0;
        JedisConnection connection = null;
        try {
            logger.debug("获取缓存数量");
            connection = jedisConnectionFactory.getConnection();
            result = Integer.valueOf(connection.dbSize().toString());
        }catch (JedisConnectionException e){
            e.printStackTrace();
        }finally {
            if (connection != null)
                connection.close();
        }
        return result;
    }

    @Override
    public void putObject(Object key, Object value) {
        JedisConnection connection = null;
        try {
            logger.debug("将"+key.toString()+":"+value.toString()+"放入redis缓存");
            connection = jedisConnectionFactory.getConnection();
            connection.set(jdkSerializationRedisSerializer.serialize(key.toString()),jdkSerializationRedisSerializer.serialize(value));
            connection.expire(jdkSerializationRedisSerializer.serialize(key.toString()),30);
        }catch (JedisConnectionException e){
            e.printStackTrace();
        }finally {
            if (connection != null)
                connection.close();
        }
    }

    @Override
    public Object getObject(Object key) {
        Object result = null;
        JedisConnection connection = null;
        try {
            logger.debug("从缓存中获取------"+key.toString()+":"+jdkSerializationRedisSerializer.serialize(key.toString()));
            connection = jedisConnectionFactory.getConnection();
            result = jdkSerializationRedisSerializer.deserialize(connection.get(jdkSerializationRedisSerializer.serialize(key.toString())));
        }catch (Exception e){
            logger.error("MyBatisRedisCache获取数据异常");
            e.printStackTrace();
        }finally {
            if (connection != null)
                connection.close();
        }
        return result;
    }

    @Override
    public Object removeObject(Object key) {
        JedisConnection connection = null;
        Object result = null;
        try {
            logger.debug("从缓存中移除------"+key.toString()+":"+jdkSerializationRedisSerializer.serialize(key.toString()));
            connection = jedisConnectionFactory.getConnection();
            result = connection.expire(jdkSerializationRedisSerializer.serialize(key.toString()),0);
        }catch (Exception e){
            logger.error("MyBatisRedisCache移除数据异常");
            e.printStackTrace();
        }finally {
            if (connection != null)
                connection.close();
        }
        return result;
    }
    @Override
    public void clear() {
        JedisConnection connection = null;
        try {
            connection = jedisConnectionFactory.getConnection();
            connection.flushAll();
            logger.debug("出现新增、修改、删除操作，清空对应Mapper缓存=======>"+this.id);
        }catch (Exception e){
            logger.error("MyBatisRedisCache清空数据异常");
            e.printStackTrace();
        }finally {
            if (connection != null)
                connection.close();
        }
    }
}
