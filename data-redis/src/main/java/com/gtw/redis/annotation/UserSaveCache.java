package com.gtw.redis.annotation;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;

import java.lang.annotation.*;

@Caching(put = {
        @CachePut(value = "users", key = "'user_id_'+#user.id"),
        @CachePut(value = "users", key = "'user_username_'+#user.username"),
        @CachePut(value = "users", key = "'user_email_'+#user.email")
})
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface UserSaveCache {
}
