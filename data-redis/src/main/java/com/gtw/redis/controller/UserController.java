package com.gtw.redis.controller;

import com.gtw.redis.domain.User;
import com.gtw.redis.service.ICacheUserService;
import com.gtw.redis.service.IOperationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private ICacheUserService userService;
    @Autowired
    private IOperationUserService operationUserService;

    @RequestMapping(value = "/cacheAddUser", method = RequestMethod.GET)
    public User addUser(){
        User user = new User(1,"张三","abc",20,"123@qq.com");
        return userService.addUser(user);
    }

    @RequestMapping(value = "/cacheGetUserByID", method = RequestMethod.GET)
    public User getUserByID(@RequestParam int id ){
        return userService.getUserByID(id);
    }

    @RequestMapping(value = "/cacheGetUsers", method = RequestMethod.GET)
    public List<User> getUsers(){
        User user = new User(2,"lisi","123",22,"123@gmail.com");
        return userService.getUsers(user);
    }

    @RequestMapping(value = "/getUserByCondition", method = RequestMethod.GET)
    public User getUserByCondition(@RequestParam boolean flag){
        return userService.getUserByCondition(flag);
    }

    @RequestMapping(value = "/testAddUser", method = RequestMethod.GET)
    public void apiAddUser(){
        User user = new User(1,"张三","abc",20,"123@qq.com");
        operationUserService.add(user);
    }

    @RequestMapping(value = "/testOperationAddUser", method = RequestMethod.GET)
    public void operationAddUser(){
        User user = new User(1,"张三","abc",20,"123@qq.com");
        operationUserService.valueAddOperations(user);
    }

    @RequestMapping(value = "/testGetUser", method = RequestMethod.GET)
    public User operationGetUser(@RequestParam String userId){
        return operationUserService.get(userId);
    }
}
