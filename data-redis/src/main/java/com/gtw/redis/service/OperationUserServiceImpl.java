package com.gtw.redis.service;

import com.gtw.redis.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

@Service
public class OperationUserServiceImpl implements IOperationUserService{

    /**
     * StringRedisTemplate就相当于RedisTemplate<String, String>的实现
     */
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, User> redisTemplate;

    @Autowired
    public OperationUserServiceImpl(@Qualifier("optRedisTemplate") RedisTemplate<String, User> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 获取 RedisSerializer
     */
    private RedisSerializer<User> getObjRedisSerializer() {
        return (RedisSerializer<User>)redisTemplate.getValueSerializer();
    }
    private RedisSerializer<String> getStringRedisSerializer() {
        return redisTemplate.getStringSerializer();
    }

    @Override
    public void addString(){
        // 保存字符串
        stringRedisTemplate.opsForValue().set("name", "");
        stringRedisTemplate.opsForValue().set("age", 20 + "");
    }

    @Override
    public int addUser(User user){
        // 保存对象
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        operations.set(user.getId() + "", user);
        /*HashOperations operations = redisTemplate.opsForHash();
        HashMap<String, User> hashMap = new LinkedHashMap<>();
        hashMap.put(user.getId() + "", user);
        operations.putAll("hashUser", hashMap);*/

        return user.getId();
    }

    @Override
    public boolean executeAdd(User user) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            byte[] key  = getStringRedisSerializer().serialize(user.getId() + "");
            byte[] value = getObjRedisSerializer().serialize(user);
            return connection.setNX(key, value);
        });
    }

    /**
     * 使用pipeline批量新增
     */
    @Override
    public boolean pipeLineAdd(List<User> list) {
        Assert.notEmpty(list);
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getStringRedisSerializer();
                for (User user : list) {
                    byte[] key  = serializer.serialize(user.getId() + "");
                    byte[] value = serializer.serialize(user.getUsername());
                    connection.setNX(key, value);
                }
                return true;
            }
        }, false, true);
    }

    @Override
    public void delete(String key) {
        List<String> keys = Collections.singletonList(key);
        redisTemplate.delete(keys);// 可以删除多个
    }

    @Override
    public User getUser(final String key) {
//        return redisTemplate.opsForValue().get(key);
        return redisTemplate.execute(new RedisCallback<User>() {
            public User doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getStringRedisSerializer();
                byte[] keyContent = serializer.serialize(key);
                byte[] value = connection.get(keyContent);
                if (value == null) {
                    return null;
                }else {
                    return getObjRedisSerializer().deserialize(value);
                }
            }
        });
    }

    @Override
    public boolean updateUser(String key) {
        User user = getUser(key);
        if (user == null) {
            throw new NullPointerException("数据行不存在, key = " + key);
        }
        user.setUsername("王麻子");

        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getStringRedisSerializer();
                byte[] key = serializer.serialize(user.getId() + "");
                byte[] value = getObjRedisSerializer().serialize(user);
                connection.set(key, value);
                return true;
            }
        });
    }

}
