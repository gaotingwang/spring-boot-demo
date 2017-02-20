package com.gtw.retry.service;

import com.gtw.retry.exception.SelfException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AnnotationRetryService implements IAnnotationRetryService {

    private static final double BASE_EXCHANGE_RATE = 1.09;
    private int attempts = 0;

    @Override
    public double getCurrentRate() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println("Calculating - Attempt " + attempts + " at " + sdf.format(new Date()));
        attempts++;

        throw new SelfException("计算失败！！！");
    }

    @Override
    public double recover(SelfException e){
        attempts = 0;
        System.out.println("SelfException: Recovering - returning safe value");
        return BASE_EXCHANGE_RATE;
    }

    @Override
    public double recover(RuntimeException e){
        attempts = 0;
        System.out.println("RuntimeException: Recovering - returning safe value");
        return BASE_EXCHANGE_RATE;
    }
}
