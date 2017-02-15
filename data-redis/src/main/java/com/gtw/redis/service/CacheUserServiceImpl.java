package com.gtw.redis.service;

import com.gtw.redis.annotation.UserSaveCache;
import com.gtw.redis.domain.User;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CacheUserServiceImpl implements ICacheUserService {

    /*@Caching(put = {
            @CachePut(value = "user", key = "'user_id_'+#user.id"),
            @CachePut(value = "user", key = "'user_username_'+#user.username"),
            @CachePut(value = "user", key = "'user_email_'+#user.email")
    })*/
    /**
     * 使用CachePut每次都会执行方法体，并将结果写入到缓存中
     * 1.会生成user~key(zset类型)的key的值，其内保存value = "user"的所有key值
     * 2.生成以指定key值的内容
     */
    @UserSaveCache
    public User addUser(User user) {
        System.out.println("addUser,@CachePut注解方法被调用啦。。。。。");
        return user;
    }

    //key与keyGenerator互斥
    @Cacheable(value = "users", keyGenerator = "keyGenerator")
    public User getUserByID(Integer id) {
        System.out.println("@Cacheable注解方法被调用啦。。。。。");
        return new User(3,"王五","aaa",25,"123@126.com");
    }

    //如果不指定key或keyGenerator,使用默认的key生成器,使用默认生成器的序列化不能使用StringRedisSerializer
    @CachePut(value = "users")
    public List<User> getUsers(User user) {
        System.out.println("@CachePut注解方法被调用啦。。。。。");
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User temp = new User(111,"李四"+i,"1231",221,"123@gmail.com1");
            users.add(temp);
        }
        return users;
    }


    /**
     * 实现功能：
     * 1.当flag为true时，执行方法体查询，并更新数据到缓存中
     * 2.为false时，执行缓存查询，不执行方法体中内容
     */
    @Caching(
            // flag为false时，从缓存中读;为true时，执行方法体
            cacheable = @Cacheable(value = "cacheUser", key = "targetClass + '.' + methodName", condition = "!#flag"),
            // flag为true时，更新缓存;为false不更新
            put = @CachePut(value = "cacheUser", key = "targetClass + '.' + methodName", condition = "#flag")
    )
    public User getUserByCondition(boolean flag){
        System.out.println("@Cacheable注解方法被调用啦。。。。。");
        int id  = (int)(Math.random()*100);
        return new User(id,"李四" + id,"1231",221,"123@gmail.com1");
    }
}
