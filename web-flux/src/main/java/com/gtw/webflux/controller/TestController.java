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
        // 惰性求值，不会阻塞请求线程，参考jdk8的Stream流不进行终止操作，流中内容是不会执行的
        // Mono和Flux都是一个Publisher, 使用时不要去调用其subscribe(), 应该交由spring框架去执行终止操作
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
        log.info("start3");
        Flux<String> result = Flux
                .fromStream(IntStream.range(1, 5).mapToObj(i -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    // 这是发布者在调用执行，发布者的执行还是当前的请求线程
                    log.info(Thread.currentThread().getName() + " Flux Data: " + i);
                    return "Flux Data: " + i;
                }));
        log.info("end3");
        return result;
    }

    private String buildString() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 这是发布者在调用执行，发布者的执行还是当前的请求线程
        log.info(Thread.currentThread().getName() + " : build String");
        return "test string";
    }

}
