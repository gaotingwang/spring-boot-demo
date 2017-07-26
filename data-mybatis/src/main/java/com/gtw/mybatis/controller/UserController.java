package com.gtw.mybatis.controller;

import com.gtw.mybatis.domain.User;
import com.gtw.mybatis.repository.mapper.master.TestMasterMapper;
import com.gtw.mybatis.repository.mapper.slave.TestSlaveMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by gaotingwang on 2017/7/14.
 */
@RestController
public class UserController {

    @Autowired
    private TestMasterMapper userMapper1;

    @Autowired
    private TestSlaveMapper userMapper2;

    @GetMapping("/users")
    public List<User> getUsers() {
        List<User> users = userMapper1.getAll(new RowBounds(0,2));//采用逻辑分页查询
        return users;
    }

    @GetMapping(value = "/users/{userId}")
    public User getUser(@PathVariable("userId") Long id) {
        User user = userMapper2.getOne(id);
        return user;
    }

    @PostMapping("/users")
    public void save(User user) {
        userMapper2.insert(user);
    }

    @PutMapping(value="/users/{userId}")
    public void update(User user) {
        userMapper2.update(user);
    }

    @DeleteMapping(value="/users/{userId}")
    public void delete(@PathVariable("userId") Long id) {
        userMapper1.delete(id);
    }

}
