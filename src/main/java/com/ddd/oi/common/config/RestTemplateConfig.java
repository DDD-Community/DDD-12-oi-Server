package com.ddd.oi.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(NaverSearchConfig config) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(config.getConnectTimeout());
        factory.setConnectionRequestTimeout(config.getReadTimeout());

        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(org.springframework.http.client.ClientHttpResponse response) throws IOException {
                // 네이버 API 오류 처리 (필요시 커스텀)
            }
        });
        return restTemplate;
    }
}