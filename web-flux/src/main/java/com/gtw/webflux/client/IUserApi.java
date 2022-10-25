package com.gtw.webflux.client;


import com.gtw.webflux.client.annotation.ApiService;
import com.gtw.webflux.domain.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ApiService("http://localhost:8080/restUsers")
public interface IUserApi {

    @GetMapping
    Flux<User> getAllUser();

    @PostMapping
    Mono<User> createUser(@RequestBody Mono<User> user);

    @DeleteMapping("/{id}")
    Mono<ResponseEntity<Void>> deleteUser(@PathVariable String id);

}
