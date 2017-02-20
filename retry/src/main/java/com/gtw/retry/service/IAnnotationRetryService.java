package com.gtw.retry.service;

import com.gtw.retry.exception.SelfException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

/**
 * ps:
 * 1.@Recover需要加在@Retryable对应的接口上，否则提示Recover找不到
 * 2.@Retryable可以加在实现类上
 */
public interface IAnnotationRetryService {
    /**
     * 遇到SelfException进行Retry,最大重试次数为2，延迟重试，第一次重试为2秒后，第二次为2*1.5=3秒，第三次为3*1.5类推
     */
    @Retryable(value = SelfException.class, maxAttempts = 2, backoff = @Backoff(delay = 2000, multiplier=1.5))
    double getCurrentRate();

    /**
     * 最大重试失败后调用此方法
     */
    @Recover
    double recover(SelfException e);

    /**
     * 若无recover(SelfException e)方法，执行此方法。ps:SelfException extends RuntimeException
     */
    @Recover
    double recover(RuntimeException e);
}
