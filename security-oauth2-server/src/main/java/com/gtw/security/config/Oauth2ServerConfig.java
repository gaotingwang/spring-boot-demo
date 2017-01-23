package com.gtw.security.config;

import com.gtw.security.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 1.ResourceServerConfigurerAdapter默认的Order为3,此处将WebSecurityConfigurerAdapter的Order设为6
 *   这样Oauth2的校验可以通过不会出现anonymousUser的情况
 *   但是Basic的校验会出现anonymousUser的情况,目前没有找到解决办法
 * 2.authorization_code授权机制依赖basic认证,要么在此内部使用http.httpBasic(),
 *   要么配置文件中指定security.user.name/password
 */
@Order(6)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class Oauth2ServerConfig extends WebSecurityConfigurerAdapter {
    private final IUserService userService;

    @Autowired
    public Oauth2ServerConfig(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 使用Redis作为token的缓存
     */
    @Bean
    public TokenStore tokenStore(RedisConnectionFactory connectionFactory){
        return new RedisTokenStore(connectionFactory);
    }

    /**
     * 使用Md5加密方式
     */
    @Bean
    public Md5PasswordEncoder passwordEncoder(){
        return new Md5PasswordEncoder();//PasswordEncoder
    }

    /**
     * 指定userDetailsService
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER").and()
                .withUser("admin").password("password").roles("USER", "ADMIN").and();
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

//    @Override
//    @Bean // share AuthenticationManager for web and oauth
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http
                .authorizeRequests()
                .antMatchers("/api/**").hasRole("ADMIN")
                .anyRequest().permitAll()
        ;
    }

    /**
     * Oauth2认证服务配置(产生token,对token的存储)
     */
    @Configuration
    @EnableAuthorizationServer
    public static class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

        private final AuthenticationManager authenticationManager;
        private final TokenStore tokenStore;
        private final IUserService userService;

        @Autowired
        public AuthorizationServerConfig(AuthenticationManager authenticationManager, TokenStore tokenStore, IUserService userService) {
            this.authenticationManager = authenticationManager;
            this.tokenStore = tokenStore;
            this.userService = userService;
        }

        /**
         * 配置客户端详细资料
         * 客户端的重要属性
         * clientId：（必需的）客户端编号。
         * secret（需要可信的客户端）客户端密钥，如果有的话。
         * scope：范围到该客户端是有限的。如果范围是未定义或空（默认值），客户端不受范围限制。
         * authorizedGrantTypes：被授权的客户端使用grant类型。默认值是空的。
         * authorities：授权给客户端的权限（Spring普通的安全权限）。
         */
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*clients.withClientDetails(new ClientDetailsService() {
            @Override
            public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
                return null;
            }
        });*/
            clients.inMemory()
                    .withClient("my-trusted-client")
                        .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                        .authorities("ROLE_USER", "ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                        .scopes("read", "write", "trust")
                        .resourceIds("oauth2-resource").and()
                    .withClient("my-client-with-registered-redirect")
                        .authorizedGrantTypes("authorization_code")
                        .authorities("ROLE_CLIENT")
                        .scopes("read", "trust")
                        .resourceIds("oauth2-resource")
    //                    .redirectUris("http://anywhere?key=value")
                        .redirectUris("http://localhost:8081/testClient/myLogin").and()
                    .withClient("my-client-with-secret")
                        .authorizedGrantTypes("client_credentials", "password")
                        .authorities("ROLE_CLIENT")
                        .scopes("read")
                        .resourceIds("oauth2-resource")
                        .secret("secret")
            //Access token is only valid for 2 minutes.
//                .accessTokenValiditySeconds(120)
            //Refresh token is only valid for 10 minutes.
//                .refreshTokenValiditySeconds(600)
            ;

        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .authenticationManager(authenticationManager)//password grants are switched on by injecting an
                    .userDetailsService(userService)
                    .tokenStore(tokenStore)//指定token存储机制
            //定义授权码机制
                /*.authorizationCodeServices(new AuthorizationCodeServices() {
                    @Override
                    public String createAuthorizationCode(OAuth2Authentication oAuth2Authentication) {
                        return "111111111";
                    }

                    @Override
                    public OAuth2Authentication consumeAuthorizationCode(String s) throws InvalidGrantException {
                        return null;
                    }
                })*/
            //配置the Endpoint URLs,该框架提供的URL路径:
            // /oauth/authorize（授权端点），
            // /oauth/token（令牌端点），user posts approval for grants here
            // /oauth/confirm_access（用户帖子的批准这里赠款），
            // /oauth/error（用来呈现在授权服务器错误），
            // /oauth/check_token（使用的资源服务器来访问令牌进行解码）
            // /oauth/token_key（如果使用jwt令牌公开令牌验证公钥）
                /*.pathMapping(defaultPath, customPath)*/
            //配合用户选择是否给予客户端授权Customizing the UI
                /*.approvalStoreDisabled()*/
            ;
        }

    }

    /**
     * 资源服务的配置(1.读取缓存的token进行对比;2.对请求token进行检测是否正确、是否在权限内)
     */
    @Configuration
    @EnableResourceServer
    public static class ResourceServer extends ResourceServerConfigurerAdapter {
        //指定验证时token的获取位置
        private final TokenStore tokenStore;

        @Autowired
        public ResourceServer(TokenStore tokenStore) {
            this.tokenStore = tokenStore;
        }

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.tokenStore(tokenStore);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http
                    .authorizeRequests()
                    /**
                     * 千万不要自己定义/oauth开头的请求,会哭的
                     */
                    .antMatchers("/testOauth/**").authenticated()
//                    .anyRequest().access("#oauth2.hasScope('read')")
                    .anyRequest().permitAll()
            ;
        }
    }
}
