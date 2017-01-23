package com.gtw.jackson.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class ConvertUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 讲json字符串转换成具体对象
     * @param json 输入的json字符串
     * @param tClass 目标类型
     * @param <T> 目标类型
     * @return pojo对象
     * @throws IOException jackson处理的异常
     */
    public static <T> T Json2Obj(String json, Class<T> tClass) throws IOException {
        return mapper.readValue(json,tClass);
    }

    /**
     * 将map转换为具体的对象
     * @param map 源map
     * @param tClass 目标类型
     * @param <T> 目标类型
     * @return 目标对象
     */
    public static <T> T map2obj(Map map, Class<T> tClass){
        return mapper.convertValue(map,tClass);
    }

    /**
     * 将具体对象转为json字符串
     * @param value 需要转换的对象
     * @return 对象对应的json字符串
     * @throws JsonProcessingException jackson处理的异常
     */
    public static String obj2json(Object value) throws JsonProcessingException {
        return mapper.writeValueAsString(value);
    }
}
