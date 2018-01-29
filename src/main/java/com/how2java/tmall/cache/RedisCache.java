package com.how2java.tmall.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.*;

public class RedisCache implements Cache {
    private RedisTemplate<String,Object> redisTemplate;
    private String name;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this.redisTemplate;
    }

    @Override
    public ValueWrapper get(Object key) {
        System.out.println("RedisCache:get key===========================");
        final String strkey = key.toString();
        Object object = null;
        object = redisTemplate.execute(
                new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        byte[] key = strkey.getBytes();
                        byte[] value = connection.get(key);
                        if (value == null){
                            return null;
                        }
                        return toObject(value);
                    }
                }
        );
        return (object != null ? new SimpleValueWrapper(object):null);
    }

    private Object toObject(byte[] value) {
        Object object = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(value);
            ObjectInputStream ois = new ObjectInputStream(bis);
            object = ois.readObject();
            ois.close();
            bis.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        return object;
    }

    @Override
    public void put(Object key, Object value) {
        System.out.println("RedisCache:put key===========================");
        final String strkey = key.toString();
        final Object valuef = value;
        final long liveTime = 86400;
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keybytes = strkey.getBytes();
                byte[] valueb = toByteArray(valuef);
                connection.set(keybytes,valueb);
                if (liveTime > 0){
                    connection.expire(keybytes,liveTime);
                }
                return 1L;
            }
        });
    }

    private byte[] toByteArray(Object valuef) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(valuef);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return bytes;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return null;
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return null;
    }

    @Override
    public void evict(Object key) {
        System.out.println("RedisCache:del key===========================");
        final String keyf = key.toString();
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.del(keyf.getBytes());
            }
        });
    }

    @Override
    public void clear() {
        System.out.println("RedisCache:clear key===========================");
        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "OK";
            }
        });
    }
}
