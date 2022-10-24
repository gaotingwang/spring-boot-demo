package com.gtw.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.IntStream;

@RestController
@Slf4j
public class TestController {

    /**
     * 常规请求，会阻塞请求线程
     */
    @GetMapping("/test/1")
    public String getString1() {
        // 会阻塞请求线程，
        log.info("start1");
        String result = buildString();
        log.info("end1");
        return result;
    }

    /**
     * Mono：返回 0-1 个元素
     * @return 后台请求线程不会产生阻塞，会继续向下执行
     */
    @GetMapping("/test/2")
    public Mono<String> getString2() {
        log.info("start2");
        // 惰性求值，不会阻塞请求线程
        Mono<String> result = Mono.fromSupplier(this::buildString);
        log.info("end2");
        return result;
    }

    /**
     * Flux：返回 0-n 个元素
     * 设置Content-Type="text/event-stream"，会以SSE的形式返回数据：data:${dataContent}
     * @return 浏览器就可以像流一样接收到多次数据
     */
    @GetMapping(value = "/test/3", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getString3() {
        Flux<String> result = Flux
                .fromStream(IntStream.range(1, 5).mapToObj(i -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return "Flux Data: " + i;
                }));
        return result;
    }

    private String buildString() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // ??? 为什么执行者还是请求的线程，不是其他线程，如何达到非阻塞效果的
        log.info(Thread.currentThread().getName() + " : build String");
        return "test string";
    }

}
