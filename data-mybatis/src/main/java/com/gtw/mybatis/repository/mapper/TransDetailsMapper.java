package com.gtw.mybatis.repository.mapper;

import com.gtw.mybatis.domain.Trans;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 高廷旺
 * 创建时间: 2016-12-28 16:04
 * 创建原因: 转码详情Mapper
 */
@Mapper
public interface TransDetailsMapper {
    /**
     * 插入音频转码详情
     * @param trans 音频转码详情
     */
    void addAudioTrans(Trans trans);

    /**
     * 插入视频转码详情
     * @param trans 视频转码详情
     */
    void addVideoTrans(Trans trans);
}
