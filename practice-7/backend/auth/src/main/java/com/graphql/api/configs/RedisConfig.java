package com.graphql.api.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * Конфигурационный класс для настройки подключения к Redis.
 */
@Configuration
public class RedisConfig {
    /**
     * Хост Redis, считываемый из конфигурации.
     */
    @Value("${spring.data.redis.host}")
    String host;
    /**
     * Порт Redis, считываемый из конфигурации.
     */
    @Value("${spring.data.redis.port}")
    Integer port;

    /**
     * Создает фабрику подключения к Redis с указанными хостом и портом.
     *
     * @return Фабрика подключения к Redis.
     */
    @Bean
    public JedisConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(
                host,
                port
        );
        return new JedisConnectionFactory(configuration);
    }
}