package com.gtw.mybatis.repository;

import com.gtw.mybatis.controller.ITransRepository;
import com.gtw.mybatis.domain.AudioTrans;
import com.gtw.mybatis.domain.Trans;
import com.gtw.mybatis.domain.VideoTrans;
import com.gtw.mybatis.repository.mapper.TransDetailsMapper;
import com.gtw.mybatis.repository.mapper.TransMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author 高廷旺
 * 创建时间: 2016-12-28 16:07
 * 创建原因: 转码信息的仓储实现
 */
@Repository
public class TransRepository implements ITransRepository {
    @Autowired
    private TransMapper transMapper;
    @Autowired
    private TransDetailsMapper detailsMapper;

    @Override
    public void add(Trans trans) {
        transMapper.add(trans);
        if(trans instanceof AudioTrans){
            detailsMapper.addAudioTrans(trans);
        }
        if(trans instanceof VideoTrans){
            VideoTrans videoTrans = (VideoTrans)trans;
            if(videoTrans.getVideos() != null && !videoTrans.getVideos().isEmpty()){
                detailsMapper.addVideoTrans(trans);
            }
        }
    }

    @Override
    public Trans query(String key) {
        return transMapper.query(key);
    }

    @Override
    public void update(Trans trans) {
        //新增转码详情信息
        if(trans instanceof AudioTrans){
            detailsMapper.addAudioTrans(trans);
        }
        if(trans instanceof VideoTrans){
            VideoTrans videoTrans = (VideoTrans)trans;
            //更新截图信息
            if(videoTrans.getCoverKey() != null){
                transMapper.update(videoTrans.getRawKey(), videoTrans.getCoverKey());
            }
            if(videoTrans.getVideos() != null && !videoTrans.getVideos().isEmpty()){
                detailsMapper.addVideoTrans(videoTrans);
            }
        }
    }

    @Override
    public void delete(String key) {
        transMapper.delete(key);
    }
}
