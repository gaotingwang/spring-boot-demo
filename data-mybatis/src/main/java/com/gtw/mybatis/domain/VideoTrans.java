package com.gtw.mybatis.domain;

import lombok.Data;

import java.util.List;

@Data
public class VideoTrans extends Trans {
    /**
     * 转码成功视频列表
     */
    private List<Video> videos;
    /**
     * 封面key
     */
    private String coverKey;

    public VideoTrans(String rawKey, String coverKey) {
        super(rawKey, Type.MP4);
        this.coverKey=coverKey;
    }

    public VideoTrans(List<Video> videos, String rawKey, String coverKey) {
        super(rawKey, Type.MP4);
        this.coverKey=coverKey;
        this.videos=videos;
    }
}
