package com.gtw.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

@Service
public class TokenService implements ITokenService {
    private final TokenStore tokenStore;
    private final ConsumerTokenServices tokenService;

    @Autowired
    public TokenService(TokenStore tokenStore, ConsumerTokenServices tokenService) {
        this.tokenStore = tokenStore;
        this.tokenService = tokenService;
    }

    /**
     * 用户修改信息时（修改用户名、密码）需要清除原来的token
     */
    @Override
    public void revokeTokenByUserName(String username) {
        tokenStore.findTokensByClientIdAndUserName("my-trusted-client",username).forEach(token -> tokenService.revokeToken(token.getValue()));
    }
}
