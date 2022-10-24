package com.gtw.webflux.function.router;

import com.gtw.webflux.function.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class AllRouters {

    /**
     * 2. 定义RouterFunction，相当于SpringMVC的dispatcherServlet
     */
    @Bean
    public RouterFunction<ServerResponse> userRoute(UserHandler userHandler) {
        return RouterFunctions
                .nest(
                        RequestPredicates.path("/routerUsers"),
                        RouterFunctions.route(RequestPredicates.GET("/"), userHandler::getAllUser)
                                .andRoute(RequestPredicates.POST("/").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), userHandler::createUser)
                                .andRoute(RequestPredicates.DELETE("/{id}"), userHandler::deleteUserById)
                );
    }
}
