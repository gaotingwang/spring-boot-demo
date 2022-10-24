package com.gtw.webflux.function.handler;

import com.gtw.webflux.domain.User;
import com.gtw.webflux.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * 1. 编写 HandlerFunction，接口定义参考 org.springframework.web.reactive.function.server.HandlerFunction
 * 为了同时兼容Netty和Servlet3.1，HandlerFunction 将 HttpServletRequest -> ServerRequest，response同理
 */
@Component
public class UserHandler {

    @Autowired
    private UserRepository userRepository;

    /**
     * 查询所有用户
     */
    public Mono<ServerResponse> getAllUser(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userRepository.findAll(), User.class);
    }

    /**
     * 创建用户
     */
    public Mono<ServerResponse> createUser(ServerRequest request) {
        Mono<User> user = request.bodyToMono(User.class);

//        return ServerResponse
//                .ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(userRepository.saveAll(user), User.class);

        return user.flatMap(u -> {
            // 如果相对user进行操作，比如参数校验等，通过flatMap方式进行操作
            // 不能直接使用user.block()进行User获取，对齐操作，会阻塞线程，spring报错

            return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userRepository.saveAll(user), User.class);
        });
    }

    /**
     * 删除用户
     */
    public Mono<ServerResponse> deleteUserById(ServerRequest request) {
        String id = request.pathVariable("id");
        return userRepository.findById(id)
                .flatMap(user -> userRepository.delete(user)
                        .then(ServerResponse.ok().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
