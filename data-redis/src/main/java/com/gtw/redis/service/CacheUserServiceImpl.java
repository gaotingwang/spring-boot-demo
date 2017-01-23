package com.gtw.redis.service;

import com.gtw.redis.annotation.UserSaveCache;
import com.gtw.redis.domain.User;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CacheUserServiceImpl implements ICacheUserService {

    //@CachePut(value = "user", key = "'id_'+#user.getId()")
    /*@Caching(put = {
            @CachePut(value = "user", key = "'user_id_'+#user.id"),
            @CachePut(value = "user", key = "'user_username_'+#user.username"),
            @CachePut(value = "user", key = "'user_email_'+#user.email")
    })*/

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
}
