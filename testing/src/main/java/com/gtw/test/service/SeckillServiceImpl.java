package com.gtw.test.service;

import com.gtw.test.dao.SeckillDao;
import com.gtw.test.entity.Seckill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService {
    private final SeckillDao seckillDao;

    @Autowired
    public SeckillServiceImpl(SeckillDao seckillDao) {
        this.seckillDao = seckillDao;
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    @Transactional
    public Seckill exportSeckill(long seckillId) {
        seckillDao.reduceNumber(seckillId);
        return seckillDao.queryById(seckillId);
    }
}
