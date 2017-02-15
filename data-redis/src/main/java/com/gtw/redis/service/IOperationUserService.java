package com.gtw.redis.service;

import com.gtw.redis.domain.User;

import java.util.List;

public interface IOperationUserService {

    void addString();

    int addUser(User user);

    boolean executeAdd(User user);

    /**
     * 使用pipeline批量新增
     */
    boolean pipeLineAdd(List<User> list);

    void delete(String key);

    User getUser(String key);

    boolean updateUser(String key);
}
