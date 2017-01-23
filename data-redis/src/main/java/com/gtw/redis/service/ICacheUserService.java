package com.gtw.redis.service;

import com.gtw.redis.domain.User;

import java.util.List;

public interface ICacheUserService {

    User addUser(User user);

    User getUserByID(Integer id);

    List<User> getUsers(User user);
}
