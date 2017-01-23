package com.gtw.mybatis.controller;

import com.gtw.mybatis.domain.Trans;

/**
 * @author 高廷旺
 * 创建时间: 2016-12-28 16:13
 * 创建原因: 转码信息仓储接口
 */
public interface ITransRepository {

    /**
     * 插入转码信息
     * @param trans 转码信息
     */
    void add(Trans trans);

    /**
     * 查询转码信息
     * @param key 转码文件key
     * @return 转码文件信息
     */
    Trans query(String key);

    /**
     * 更新转码信息
     * @param trans 转码信息
     */
    void update(Trans trans);

    /**
     * 删除指定文件的转码信息
     * @param key 文件key
     */
    void delete(String key);
}
