package com.gtw.mybatis.domain;

import lombok.Data;

/**
 * Created by gaotingwang on 2017/7/14.
 */
@Data
public class User {
    private long id;
    private String userName;
    private String passWord;
    private String userSex;
    private String nickName;

    public enum Sex{
        MAN,WOMAN;
    }
}
