package com.gtw.test.service;

import com.gtw.test.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillServiceTest {
    @Autowired
    private SeckillService seckillService;

    @Test
    public void getById() throws Exception {
        long seckillId = 1L;
        Seckill seckill = seckillService.getById(seckillId);
        assertNotNull(seckill);
        assertEquals(seckillId, seckill.getSeckillId());
    }

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckills = seckillService.getSeckillList();
        assertNotNull(seckills);
    }

    @Test
    @Transactional// 加入事务，执行完，对事务进行回滚
    public void exportSeckill() throws Exception {
        long seckillId = 4L;
        Seckill seckill = seckillService.exportSeckill(seckillId);
        // TODO: 17/3/12 如何判断seckill数量减一了？
    }

}