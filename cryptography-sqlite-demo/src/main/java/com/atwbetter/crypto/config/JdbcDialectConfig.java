package com.atwbetter.crypto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.dialect.JdbcDialect;

@Configuration
public class JdbcDialectConfig {

    @Bean
    public JdbcDialect jdbcDialect() {
        // 例：PostgreSQL / MySQL / SQLServer 都不同
        return JdbcDialect; // 如果是 PostgreSQL
        // return JdbcDialect.MYSQL; // 如果是 MySQL（示意）
    }
}

