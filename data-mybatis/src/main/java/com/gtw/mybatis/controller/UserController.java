package com.gtw.mybatis.controller;

import com.gtw.mybatis.domain.User;
import com.gtw.mybatis.repository.mapper.master.TestMasterMapper;
import com.gtw.mybatis.repository.mapper.slave.TestSlaveMapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value="获取用户列表", notes="所有用户列表")
    @GetMapping("/users")
    public List<User> getUsers() {
        List<User> users = userMapper1.getAll(new RowBounds(0,2));//采用逻辑分页查询
        return users;
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @GetMapping(value = "/users/{userId}")
    public User getUser(@PathVariable("userId") Long id) {
        User user = userMapper2.getOne(id);
        return user;
    }

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @PostMapping("/users")
    public void save(User user) {
        userMapper2.insert(user);
    }

    @ApiOperation(value="更新用户详细信息", notes="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    @PutMapping(value="/users/{userId}")
    public void update(User user) {
        userMapper2.update(user);
    }

    @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @DeleteMapping(value="/users/{userId}")
    public void delete(@PathVariable("userId") Long id) {
        userMapper1.delete(id);
    }

}
