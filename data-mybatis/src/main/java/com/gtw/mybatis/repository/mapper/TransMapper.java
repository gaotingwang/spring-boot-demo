package com.gtw.mybatis.repository.mapper;

import com.gtw.mybatis.domain.Trans;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 高廷旺
 * 创建时间: 2016-12-28 16:05
 * 创建原因: 转码信息Mapper
 */
@Mapper
public interface TransMapper {
    /**
     * 根据文件key查询转码信息
     * @param key 转码文件key
     * @return 转码信息
     */
    Trans query(String key);

    /**
     * 插入转码信息
     * @param trans 转码信息
     */
    void add(Trans trans);

    /**
     * 更新指定文件的快照key
     * @param key 文件key
     * @param snapShotKey 快照key
     */
    void update(@Param("rawKey") String key, @Param("coverKey") String snapShotKey);

    /**
     * 删除指定文件的转码信息
     * @param key 文件key
     */
    void delete(String key);
}
