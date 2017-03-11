package com.gtw.test.service;


import com.gtw.test.entity.Seckill;

import java.util.List;

public interface SeckillService {

    Seckill getById(long seckillId);

    List<Seckill> getSeckillList();

    Seckill exportSeckill(long seckillId);
}
