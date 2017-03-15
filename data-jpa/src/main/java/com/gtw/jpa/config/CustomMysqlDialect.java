package com.gtw.jpa.config;

import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomMysqlDialect extends MySQL5InnoDBDialect {
    /**
     * 数据库不指定字符集默认插入的字符串的字符集是 latin1。
     * 在没有指定数据库字符集时若要设定编码为UTF-8
     */
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
