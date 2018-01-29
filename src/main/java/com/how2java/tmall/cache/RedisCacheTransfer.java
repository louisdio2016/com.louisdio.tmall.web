package com.how2java.tmall.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

public class RedisCacheTransfer {
    @Autowired
    public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory){
        MyBatisRedisCache.setJedisConnectionFactory(jedisConnectionFactory);
    }
}
