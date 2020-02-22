package com.gtw.spring.boot.autoconfigure;

import com.gtw.spring.boot.autoconfigure.service.GreetingService;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GtwProperties.class)
@ConditionalOnProperty(value = "gtw.greeting.enable", havingValue = "true")
@ConditionalOnClass(DefaultSqlSession.class)
public class GtwAutoConfiguration {

    @Bean
    public GreetingService greetingService(GtwProperties gtwProperties){
        return new GreetingService(gtwProperties.getMembers());
    }
}
