package com.gtw.mybatis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Trans {
    /**
     * 源文件key
     */
    @NonNull
    protected String rawKey;
    /**
     * 转码类型
     */
    protected Type type;
    /**
     * Fail状态会包含错误信息
     */
    protected String message;
    /**
     * 转码状态
     */
    protected State state;
    /**
     * 转码类型
     */
    public enum Type{
        /**
         * mp3
         */
        MP3,
        /**
         * mp4
         */
        MP4
    }
    /**
     * 转码状态
     */
    public enum State {
        /**
         * 转码失败
         */
        FAIL,
        /**
         * 转码成功
         */
        SUCCESS,
        /**
         * 转码等待
         */
        WAITING
    }

    public Trans(String rawKey, Type type) {
        this.rawKey = rawKey;
        this.type = type;
    }
}
