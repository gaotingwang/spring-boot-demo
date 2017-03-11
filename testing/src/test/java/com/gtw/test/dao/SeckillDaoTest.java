package com.gtw.test.dao;

import com.gtw.test.entity.Seckill;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional// 开启事务，方法执行完，默认事务会回滚
@Slf4j
public class SeckillDaoTest {
    @Autowired
    private SeckillDao seckillDao;

    /**
     * 测试类开始前加载
     */
    @BeforeClass
    public static void start() {
        log.info("本测试Class开始前");
    }

    @AfterClass
    public static void end() {
        log.info("本测试Class结束后");
    }

    /**
     * 在每个事务之前进行，如果有对表数据修改的操作，由于不在事务中，不会回滚，会对表中数据产生影响
     * 在@Before之前执行
     */
    @BeforeTransaction
    public void beforeTransaction(){
        log.info("事务开启前");
    }

    @AfterTransaction
    public void afterTransaction(){
        log.info("事务关闭后");
    }

    /**
     * 每个测试方法开始前加载，会与方法放在同一个事务中(即二者属于同一事务，若方法的事务提交，则此内的操作自然也提交了）
     * 在@BeforeTransaction之后执行
     */
    @Before
    public void setUp() throws Exception {
        log.info("每个方法开始前：开始测试用例");
    }

    @After
    public void tearDown() throws Exception {
        log.info("每个方法结束后：测试用例结束");
    }

    @Test
    @Commit// 进行事务提交，对应的@Before中事务也会一起提交
    public void add() throws Exception {
        log.info("测试用例1进行中");
        long seckillId = 5L;
        Date current = new Date();
        Seckill seckill = new Seckill(seckillId, "测试产品", 111, current, current, current);
        int count = seckillDao.add(seckill);
        assertEquals(1, count);
    }

    @Test
    public void queryById() throws Exception {
        log.info("测试用例2进行中");
        long seckillId = 1L;
        Seckill seckill = seckillDao.queryById(seckillId);
        assertNotNull(seckill);
        assertEquals(seckillId, seckill.getSeckillId());
    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill> seckills = seckillDao.queryAll(0, 4);
        assertNotNull(seckills);
        assertEquals(4, seckills.size());
    }

    @Test
    public void reduceNumber() throws Exception {
        long seckillId = 4L;
        int count = seckillDao.reduceNumber(seckillId);
        assertEquals(1, count);
    }
}