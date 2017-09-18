package com.gtw.mybatis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select() // 返回一个ApiSelectorBuilder实例用来控制哪些接口暴露给Swagger来展现
                .apis(RequestHandlerSelectors.basePackage("com.gtw.mybatis.controller")) // 采用指定扫描的包路径来定义，Swagger会扫描该包下所有Controller定义的API，并产生文档内容（除了被@ApiIgnore指定的请求）
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 来创建该Api的基本信息（这些基本信息会展现在文档页面中）
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                .description("更多Spring Boot相关示例请关注：https://github.com/gaotingwang/spring-boot-demo")
                .termsOfServiceUrl("http://gtwlover.oschina.io/")
                .version("1.0")
                .build();
    }
}
