package com.gtw.redis.service;

import com.gtw.redis.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperationUserServiceImpl implements IOperationUserService{

    @Autowired
    @Qualifier("optRedisTemplate")
    private RedisTemplate<String, User> redisTemplate;

    /**
     * 获取 RedisSerializer
     */
    private RedisSerializer getObjRedisSerializer() {
        return redisTemplate.getValueSerializer();
    }

    private RedisSerializer<String> getStringRedisSerializer() {
        return redisTemplate.getStringSerializer();
    }

    /**
     * 新增
     */
    public boolean add(final User user) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] key  = getStringRedisSerializer().serialize(user.getId()+"");
                byte[] name = getObjRedisSerializer().serialize(user);
                return connection.setNX(key, name);
            }
        });
    }

    public void valueAddOperations(User user) {
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        operations.set(user.getId()+"", user);
    }

    /**
     * 批量新增 使用pipeline方式
     */
    public boolean add(final List<User> list) {
        Assert.notEmpty(list);
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getStringRedisSerializer();
                for (User user : list) {
                    byte[] key  = serializer.serialize(user.getId()+"");
                    byte[] name = serializer.serialize(user.getUsername());
                    connection.setNX(key, name);
                }
                return true;
            }
        }, false, true);
    }

    /**
     * 删除
     */
    public void delete(String key) {
        List<String> list = new ArrayList<String>();
        list.add(key);
        delete(list);
    }

    /**
     * 删除多个
     */
    public void delete(List<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 修改
     */
    public boolean update(final User user) {
        String key = user.getId()+"";
        if (get(key) == null) {
            throw new NullPointerException("数据行不存在, key = " + key);
        }
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getStringRedisSerializer();
                byte[] key1 = serializer.serialize(user.getId()+"");
                byte[] name = serializer.serialize(user.getUsername());
                connection.set(key1, name);
                return true;
            }
        });
    }

    /**
     * 通过key获取
     */
    public User get(final String keyId) {
        return redisTemplate.execute(new RedisCallback<User>() {
            public User doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getStringRedisSerializer();
                byte[] key = serializer.serialize(keyId);
                byte[] value = connection.get(key);
                if (value == null) {
                    return null;
                }else {
                    return (User) getObjRedisSerializer().deserialize(value);
                }
            }
        });

    }
}
