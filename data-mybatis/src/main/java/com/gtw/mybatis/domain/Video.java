package com.gtw.mybatis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Video {
    /**
     * 视频播放key
     */
    private String key;
    /**
     * 清晰度
     */
    private Quality quality;
    /**
     * 视频清晰度
     */
    public enum Quality {
        /**
         * 低清
         */
        LD("S00000000-200020"),
        /**
         * 低清
         */
        SD("S00000000-200030"),
        /**
         * 高清
         */
        HD("S00000000-200040"),
        /**
         * 全高清
         */
        FHD("S00000000-200050");

        Quality(String templateId) {
            this.templateId = templateId;
        }

        private String templateId;

        public String getTemplateId() {
            return templateId;
        }

        public void setTemplateId(String templateId) {
            this.templateId = templateId;
        }
    }
}
