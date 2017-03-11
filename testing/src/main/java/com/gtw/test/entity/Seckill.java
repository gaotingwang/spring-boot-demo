package com.gtw.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 高廷旺
 * 创建原因: 秒杀库存对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seckill {
    private long seckillId;
    private String name;
    private int number;
    private Date startTime;
    private Date endTime;
    private Date createTime;
}
