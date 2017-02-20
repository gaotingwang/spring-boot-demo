package com.gtw.retry.service;

import com.gtw.retry.exception.SelfException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class TemplateRetryService implements ITemplateRetryService {
    @Override
    public void templateRetry() {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(getRetryPolicy());
        retryTemplate.setBackOffPolicy(getBackOffPolicy());

        // 设置监听器，可以在重试操作的某些位置(重试开始、重试抛出异常、重试结束)嵌入调用者自定义的一些操作，可以在对应场景触发。
        retryTemplate.registerListener(new MyRetryListener());

        // 重试回调函数
        final RetryCallback<Object, Exception> retryCallback = new RetryCallback<Object, Exception>() {
            public Object doWithRetry(RetryContext context) throws Exception {
                System.out.println("do retry operation" + "\t" + context.getRetryCount());

                // 设置context一些属性,给RecoveryCallback传递一些属性
                context.setAttribute("key1", "value1");
                throw new SelfException("exception");
            }
        };

        // 如果RetryCallback执行出现指定异常, 并且超过最大重试次数依旧出现指定异常的话,就执行RecoveryCallback动作
        final RecoveryCallback<Object> recoveryCallback = new RecoveryCallback<Object>() {
            public Object recover(RetryContext context) throws Exception {
                System.out.println("do recory operation");
                System.out.println(context.getAttribute("key1"));

                log.error("Retry Fail");
                return null;
            }
        };

        try {
            retryTemplate.execute(retryCallback, recoveryCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重试策略
     */
    private RetryPolicy getRetryPolicy(){
        // TimeoutRetryPolicy策略，TimeoutRetryPolicy超时时间默认是1秒。TimeoutRetryPolicy超时是指在execute方法内部，
        // 从open操作开始到调用TimeoutRetryPolicy的canRetry方法这之间所经过的时间。这段时间未超过TimeoutRetryPolicy定义的超时时间，那么执行操作，否则抛出异常

        // 根据产生的异常选择重试策略
        /*ExceptionClassifierRetryPolicy policy = new ExceptionClassifierRetryPolicy();
        Map<Class<? extends Throwable>, RetryPolicy> policyMap = new HashMap<Class<? extends Throwable>, RetryPolicy>();
        policyMap.put(TimeoutException.class, new AlwaysRetryPolicy());// TimeoutException执行Retry
        policyMap.put(NullPointerException.class, new NeverRetryPolicy());// NullPointerException不执行Retry
        policy.setPolicyMap(policyMap);*/
        // setPolicyMap与setExceptionClassifier使用一个即可
        /*Classifier<Throwable, RetryPolicy> exceptionClassifier = new Classifier<Throwable, RetryPolicy>(){
            public RetryPolicy classify(Throwable classifiable) {
                if(classifiable instanceof TimeoutException)
                    return new SimpleRetryPolicy();
                return new NeverRetryPolicy();
            }

        };
        policy.setExceptionClassifier(exceptionClassifier);*/

        // 指定需要重试的异常
        Map<Class<? extends Throwable>, Boolean> doExceptionMaps = new HashMap<>();
        doExceptionMaps.put(SelfException.class, true);// 为false，那么执行方法，随后抛出异常不进行重试
        // 遇到SelfException进行重试，最大重试次数3次
        return new SimpleRetryPolicy(3, doExceptionMaps);
    }

    /**
     * 再次重试的时间间隔
     */
    private BackOffPolicy getBackOffPolicy(){
        // NoBackOffPolicy 因此采用次策略，重试不会等待。这也是RetryTemplate采用的默认退避(backOff)策略
        // SleepingbackOffPolicy 指数退避策略 ，每次等待时间为 等待时间 = 等待时间 * N ，即每次等待时间为上一次的N倍。如果等待时间超过最大等待时间，那么以后的等待时间为最大等待时间
        // ExponentialBackOffPolicy方法，返回等待时间，随后next*(Multiplier – 1)最为等待时间。 即 [next,next* Multiplier) 之间的一个随机数。

        // 设定再次重试的时间间隔
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();// 默认为1秒
        fixedBackOffPolicy.setBackOffPeriod(5000);// 重试执行周期5s
        return fixedBackOffPolicy;
    }

    /**
     * 在RetryTemplate中doOpenInterceptors,doCloseInterceptors,doOnErrorInterceptors会调用监听器对应的open,close,onError 方法
     * doOpenInterceptors 方法在第一次重试之前会被调用，如果该方法返回true，则会继续向下直接，如果返回false，则抛出异常，停止重试。
     * doCloseInterceptors 会在重试操作执行完毕后调用。
     * doOnErrorInterceptors 在抛出异常后执行。
     * 当注册多个Listener时，open方法按会按Listener的注册顺序调用，而onError和close则按Listener注册的顺序逆序调用。
     */
    class MyRetryListener implements RetryListener {
        public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
            System.out.println("open");
            return true;
        }

        public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback,
                                                     Throwable throwable) {

            System.out.println("onError");
        }

        public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback,
                                                   Throwable throwable) {
            System.out.println("close");
        }
    }
}
