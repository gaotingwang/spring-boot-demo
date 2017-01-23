package com.gtw.security.service;

public interface ITokenService {

    /**
     * 从redis token store中移除用户名对应的token
     * @param username 用户名
     */
    void revokeTokenByUserName(String username);
}
