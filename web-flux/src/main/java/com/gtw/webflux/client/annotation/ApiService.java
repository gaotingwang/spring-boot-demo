package com.gtw.webflux.client.annotation;

import java.lang.annotation.*;

/**
 * @author xxx
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiService {
    String value() default "";
}
