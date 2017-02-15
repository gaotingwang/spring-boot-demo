package com.gtw.redis.controller;

import com.gtw.redis.domain.User;
import com.gtw.redis.service.ICacheUserService;
import com.gtw.redis.service.IOperationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private ICacheUserService userService;
    @Autowired
    private IOperationUserService operationUserService;

    //////////////////////@Cache缓存操作/////////////////////////

    @RequestMapping(value = "/cacheAddUser", method = RequestMethod.GET)
    public User cacheUser(){
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

    @RequestMapping(value = "/cacheUserByCondition", method = RequestMethod.GET)
    public User getUserByCondition(@RequestParam boolean flag){
        return userService.getUserByCondition(flag);
    }


    ///////////////////////RedisTemplate操作////////////////////////

    @RequestMapping(value = "/testAddString", method = RequestMethod.GET)
    public void addString(){
        operationUserService.addString();
    }

    @RequestMapping(value = "/testAddUser", method = RequestMethod.GET)
    public int addUser(){
        User user = new User((int)(Math.random()*100),"张三","abc",20,"123@qq.com");
        return operationUserService.addUser(user);
    }

    @RequestMapping(value = "/testExecuteAdd", method = RequestMethod.GET)
    public void executeAdd(){
        User user = new User(1,"张三","abc",20,"123@qq.com");
        operationUserService.executeAdd(user);
    }

    @RequestMapping(value = "/testPipeLineAdd", method = RequestMethod.GET)
    public void pipeLineAdd(){
        User user1 = new User(1,"张三","abc",20,"123@qq.com");
        User user2 = new User(2,"张三","abc",20,"123@qq.com");
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        operationUserService.pipeLineAdd(users);
    }

    @RequestMapping(value = "/testDelete", method = RequestMethod.DELETE)
    public void pipeLineAdd(@RequestParam String key){
        operationUserService.delete(key);
    }

    @RequestMapping(value = "/testGetUser", method = RequestMethod.GET)
    public User operationGetUser(@RequestParam String key){
        return operationUserService.getUser(key);
    }

    @RequestMapping(value = "/testUpdateUser", method = RequestMethod.GET)
    public void operationUpdateUser(@RequestParam String key){
        operationUserService.updateUser(key);
    }
}
