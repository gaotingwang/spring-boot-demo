package com.gtw.test.dao;

import com.gtw.test.entity.Seckill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SeckillDao {

    int add(Seckill seckill);

    /**
     * 根据id查询秒杀的商品信息
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀商品列表
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 减库存
     */
    int reduceNumber(@Param("seckillId") long seckillId);
}
