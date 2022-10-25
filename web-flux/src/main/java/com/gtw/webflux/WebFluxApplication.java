package com.gtw.webflux;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.Flux;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootApplication
@EnableReactiveMongoRepositories
@Slf4j
public class WebFluxApplication {

    public static void main(String[] args) {

//        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
//            private Subscription subscription;
//
//            @Override
//            public void onSubscribe(Subscription subscription) {
//                this.subscription = subscription;
//                this.subscription.request(1L);
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                System.out.println(Thread.currentThread().getName() + " --> 消费内容：" + integer);
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                subscription.request(1L);
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                log.error("消费异常", throwable);
//                subscription.cancel();
//            }
//
//            @Override
//            public void onComplete() {
//                // 数据全部处理完毕，发布者关闭
//                System.out.println(Thread.currentThread().getName() + " --> 消费全部处理完毕");
//            }
//        };
//
//        // reactor = jdk8 stream + jdk9 reactive stream
//        // Mono 0-1个元素
//        // Flux 0-N个元素
//        String[] strs = {"1", "2", "3"};
//
//        System.out.println(Thread.currentThread().getName() + " --> 准备发布数据");
//        Flux.fromArray(strs)
//                // 中间操作，这里就是jdk8的stream
//                .parallel()
//                .map(Integer::parseInt)
//                // 终止操作，这里是jdk9的reactive stream
//                .subscribe(subscriber);
//        System.out.println(Thread.currentThread().getName() + " --> 发布完毕");

        SpringApplication.run(WebFluxApplication.class, args);

//        System.out.println(Thread.currentThread().getName() + " --> 准备发布数据");
//        Stream<String> stream = IntStream.range(1, 5).mapToObj(WebFluxApplication::test);
//        System.out.println(Thread.currentThread().getName() + " --> 结束发布数据");
//        System.out.println(stream.count());
    }

    public static String test(Integer integer) {
        System.out.println(Thread.currentThread().getName() + " --> 消费内容：" + integer);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "生成：" + integer;
    }


}
