package com.gtw.test.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gtw.test.entity.Seckill;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class SeckillControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//        mockMvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
    }

    @Test
    public void list() throws Exception {
        mockMvc.perform(get("/seckill/list").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/list.jsp"));
    }

    @Test
    public void detail() throws Exception {
        Long seckillId = 1L;
        String name = "5000元秒杀MacBook";
        int number = 100;

        MvcResult result = mockMvc.perform(get("/seckill/{seckillId}/detail", seckillId).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/detail.jsp"))
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        Seckill seckill = (Seckill) modelAndView.getModel().get("seckill");

        assertEquals(seckillId.longValue(), seckill.getSeckillId());
        assertEquals(number, seckill.getNumber());
        assertEquals(name, seckill.getName());
    }

    @Test
    public void exposer() throws Exception {
        long seckillId = 1L;
        String name = "5000元秒杀MacBook";
        int number = 99;// 100 - 1
        Date date1 = new Date(1489255174000L);
        Date date2 = new Date(1488643200000L);
        Date date3 = new Date(1488729600000L);
        Seckill seckill = new Seckill(seckillId, name, number, date2, date3, date1);

        // 方法一：执行完进行事务回滚
        mockMvc.perform(post("/seckill/{seckillId}/exposer", seckillId).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(obj2Str(seckill)));

        // 方法二：执行完事务未回滚(他妹的，写法应该没有问题吧)
//        ResponseEntity<Seckill> entity = this.restTemplate.postForEntity("/seckill/{seckillId}/exposer", null, Seckill.class, seckillId);
//        assertEquals(HttpStatus.OK, entity.getStatusCode());
//        assertEquals(number, entity.getBody().getNumber());
//        assertEquals(name, entity.getBody().getName());
    }

    /**
     * json序列化
     */
    private String obj2Str(Object value) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(value);
    }
}