package com.gtw.mybatis.service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public abstract class HistoryBigDataDealService<T> {

    private final int executeThreadCount = 16;
    private static final int RESULT_SIZE = 2000;
    private static final int PARTITION_SIZE = 10;
//    private static final int PERIOD = 5;

    /**
     * step1: 起调度线程, 按恒定频率获取数据
     */
    public void start() {
        for (int i = 0; i < PARTITION_SIZE; i++) {
            schedule(i);
        }
    }

    /**
     * step2: 每个调度线程筛选待处理数据，为选待处的数据加锁后执行消费
     */
    private void schedule(int currentPartition) {
        List<T> sourceData = this.selectExecuteDataList(PARTITION_SIZE, currentPartition, RESULT_SIZE);
        if (sourceData.isEmpty()) {
            return;
        }
//        this.lockExecuteData(sourceData);
        this.threadExecute(sourceData, executeThreadCount);
//        this.afterExecute(sourceData);
    }

    /**
     * step3: 分割要执行的数据，然后对分割后的数据采用多线程方式执行
     * @param sourceData 要执行的数据
     * @param maxThreadCount 执行的最大线程数
     */
    private void threadExecute(List<T> sourceData, int maxThreadCount) {
        List<List<T>> splitData = Lists.partition(sourceData, maxThreadCount);

        // 起线程池
        ExecutorService executor = Executors.newFixedThreadPool(maxThreadCount);

        try {
            for (List<T> subData : splitData) {
                // 线程任务执行
                executor.execute(() -> {
                    dealWithData(subData);
                });
            }
        } catch (Exception e) {
            log.error("程序执行错误：", e);
        }

        executor.shutdown();
    }

    /**
     * 分片式获取数据
     * @param partitionSize 分片大小
     * @param currentPartition 当前分片
     * @param resultSize 每个分片数据量大小
     * @return 要执行的数据
     */
    protected abstract List<T> selectExecuteDataList(int partitionSize, int currentPartition, int resultSize);

//    /**
//     * 为执行的数据加锁
//     * @param executeData 要执行的数据
//     */
//    protected void lockExecuteData(List<T> executeData) {
//        //todo 主从形式下延迟问题，需要考虑分布式锁
//    }

    /**
     * step4: 每个线程内部消费处理集合数据
     * @param spiltData 分割到每个线程内的数据
     */
    protected abstract void dealWithData(List<T> spiltData);

//    /**
//     * 处理完数据的后置处理
//     * @param executeData 执行完的数据
//     */
//    protected void afterExecute(List<T> executeData) {
//    }

}
