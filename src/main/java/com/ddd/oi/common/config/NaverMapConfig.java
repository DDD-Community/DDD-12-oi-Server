package com.ddd.oi.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "naver.map")
@Data
public class NaverMapConfig {
    private String apiKeyId;
    private String apiKey;
}