package com.gtw.security.config;

import com.gtw.security.domain.ClientResources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class Oauth2ClientConfig {
    @Value("${oauth.resource:http://localhost:8080}")
    private String baseUrl;

    @Value("${oauth.authorize:http://localhost:8080/oauth/authorize}")
    private String authorizeUrl;

    @Value("${oauth.token:http://localhost:8080/oauth/token}")
    private String tokenUrl;

//    @Value("${oauth.redirect:http://anywhere?key=value}")
    @Value("${oauth.redirect:http://localhost:8081/testClient/myLogin}")
    private String redirect;


    @Bean
    protected OAuth2ProtectedResourceDetails resource() {
        //grant_type is authorization_code
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setAccessTokenUri(tokenUrl);
        details.setClientId("my-client-with-registered-redirect");
        details.setClientSecret("secret");
        details.setUserAuthorizationUri(authorizeUrl);
        details.setUseCurrentUri(false);
        details.setPreEstablishedRedirectUri(redirect);

        //grant_type is client_credentials
//        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
//        details.setAccessTokenUri(tokenUrl);
//        details.setClientId("my-client-with-secret");
//        details.setClientSecret("secret");

        //grant_type is password
//        ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
//        details.setAccessTokenUri(tokenUrl);
//        details.setClientId("my-trusted-client");
//        details.setUsername("张三");
//        details.setPassword("password");

        return details;
    }

    @Bean
    public OAuth2RestTemplate myRestTemplate(OAuth2ClientContext context) {
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resource(),context);
        RootUriTemplateHandler.addTo(restTemplate,baseUrl);
        return restTemplate;
    }

    /**
     * ConfigurationProperties注解是方便通过配置文件生成所需类
     * 这里因为OAuth配置比较复杂，故将properties文件改用YAML模式
     */
    @Bean
    @ConfigurationProperties("github")
    public ClientResources githubClient() {
        return new ClientResources();
    }

    @Bean
    public OAuth2RestTemplate gitHubRestTemplate(OAuth2ClientContext context) {
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(githubClient().getClient(),context);
        RootUriTemplateHandler.addTo(restTemplate,"https://github.com/login/");
        return restTemplate;
    }
}
