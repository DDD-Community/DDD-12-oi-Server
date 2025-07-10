package com.ddd.oi.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "naver.search")
@Data
public class NaverSearchConfig {
    private String clientId;
    private String clientSecret;
    private String baseUrl = "https://openapi.naver.com/v1/search/local.json";
    private int defaultDisplay = 10;
    private int maxDisplay = 100;
    private int connectTimeout = 5000;
    private int readTimeout = 10000;
}