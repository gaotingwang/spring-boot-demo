package com.gtw.redis.service;

import com.gtw.redis.domain.User;

import java.util.List;

public interface IOperationUserService {

    /**
     * 新增
     */
    boolean add(User user);

    /**
     * 使用RedisTemplate插入
     */
    void valueAddOperations(User value);

    /**
     * 批量新增 使用pipeline方式
     */
    boolean add(List<User> list);

    /**
     * 删除
     */
    void delete(String key);

    /**
     * 删除多个
     */
    void delete(List<String> keys);

    /**
     * 修改
     */
    boolean update(User user);

    /**
     * 通过key获取
     */
    User get(String keyId);
}
