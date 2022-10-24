package com.gtw.webflux.repository;

import com.gtw.webflux.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    /**
     * 根据年龄范围查找
     */
    Flux<User> findByAgeBetween(int start, int end);
}
