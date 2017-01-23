package com.gtw.mybatis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Audio {
    /**
     * 音频key
     */
    private String key;
    /**
     * 清晰度
     */
    private Quality quality;
    /**
     * 清晰度
     */
    public enum Quality {
        /**
         * 320kbps
         */
        MP3_320("S00000001-300050"),
        /**
         * 192kbps
         */
        MP3_192("S00000001-300040"),
        /**
         * 160kbps
         */
        MP3_160("S00000001-300030"),
        /**
         * 128kbps
         */
        MP3_128("S00000001-300020"),
        /**
         * 64kbps
         */
        MP3_64("S00000001-300010");

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
