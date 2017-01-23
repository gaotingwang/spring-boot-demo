package com.gtw.mybatis.domain;

import lombok.Data;

import java.util.List;

@Data
public class AudioTrans extends Trans {
    /**
     * 转码成功音频列表
     */
    private List<Audio> audios;

    public AudioTrans(String rawKey) {
        super(rawKey, Type.MP3);
    }

    public AudioTrans(List<Audio> audios, String rawKey) {
        super(rawKey, Type.MP3);
        this.audios=audios;
    }
}
