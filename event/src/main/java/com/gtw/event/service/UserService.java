package com.gtw.event.service;

import com.gtw.event.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private PublishRegister publishRegister;

    /**
     * 用户注册后，需要做事：
     *      1、加积分
     *      2、发确认邮件
     *      3、如果是游戏帐户，可能赠送游戏大礼包
     *      4、索引用户数据
     * 为了使UserService与其他（发邮件、加积分等）Service解耦
     * 结构图参考resources中user.png
     */
    public void register(String username, String password) {
        System.out.println(username + "注册成功！");

        publishRegister.publishRegisterEvent(new User(username, password));
    }

}
