package com.gtw.webflux.controller;

import com.gtw.webflux.domain.User;
import com.gtw.webflux.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/restUsers")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * 以数组形式一次性返回数据
     */
    @GetMapping
    public Flux<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * 以SSE形式多次返回数据
     */
    @GetMapping(value = "/stream/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamGetAll() {
        return userRepository.findAll();
    }

    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    /**
     * Mono和Flux都是一个Publisher, 使用时不要去调用其subscribe(), 应该交由spring框架去执行终止操作
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable String id) {
        return userRepository.findById(id)
                // 当要操作数据，并返回一个Mono，使用flatMap
                // 不操作数据，只是数据转换，使用map
                .flatMap(user -> userRepository.delete(user)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable String id, @RequestBody User user) {
        return userRepository.findById(id)
                // flatMap 操作数据
                .flatMap(u -> {
                    u.setName(user.getName());
                    u.setAge(user.getAge());
                    return userRepository.save(u);
                })
                // map 转换数据
                .map(u -> new ResponseEntity<User>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
