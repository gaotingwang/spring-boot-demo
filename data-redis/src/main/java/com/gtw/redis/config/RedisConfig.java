package com.gtw.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisTemplate cacheRedisTemplate(RedisConnectionFactory cf) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(cf);
        setSerializer(redisTemplate);
        return redisTemplate;
    }

    @Bean
    public RedisTemplate optRedisTemplate(RedisConnectionFactory cf) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(cf);
        setSerializer(redisTemplate);
        return redisTemplate;
    }

    /**
     * spring-data-redis对key和value 都进行了序列化 变成byte[]再调用对应的redis java client进行存储的
     */
    private void setSerializer(RedisTemplate redisTemplate){
        //设置key序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        //设置value序列化方式
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    }

    @Bean
    public CacheManager cacheManager(@Qualifier("cacheRedisTemplate") RedisTemplate redisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        //设置缓存有效时长
        redisCacheManager.setDefaultExpiration(300);
        return redisCacheManager;
    }

    /**
     * 自定义key生成器
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object clazz, Method method, Object... methodArgs) {
                StringBuilder sb = new StringBuilder();
                sb.append(clazz.getClass().getName())
                        .append(".")
                        .append(method.getName());
                for (Object arg : methodArgs) {
                    sb.append(arg.toString());
                }
                return sb.toString();
            }
        };
    }
}
