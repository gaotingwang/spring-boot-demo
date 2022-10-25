package com.gtw.webflux.client.config;

import com.gtw.webflux.client.IUserApi;
import com.gtw.webflux.client.handler.ProxyCreator;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xxx
 */
@Configuration
public class ApiServiceConfiguration {

    @Bean
    public FactoryBean<IUserApi> userApi(ProxyCreator creator) {
        return new FactoryBean<IUserApi>() {
            /**
             * 返回代理对象
             */
            @Override
            public IUserApi getObject() throws Exception {
                return (IUserApi)creator.createProxy(this.getObjectType());
            }

            @Override
            public Class<?> getObjectType() {
                return IUserApi.class;
            }
        };
    }
}
