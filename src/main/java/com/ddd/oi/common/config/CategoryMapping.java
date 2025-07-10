package com.ddd.oi.common.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@ConfigurationProperties(prefix = "search.category")
@Data
public class CategoryMapping {
    private Map<String, List<String>> mappings = new HashMap<>();

    @PostConstruct
    public void init() {
        if (mappings.isEmpty()) {
            mappings.put("음식점", Arrays.asList("음식점", "한식", "중식", "일식", "양식", "분식", "치킨", "피자", "카페"));
            mappings.put("카페", Arrays.asList("카페", "디저트", "베이커리"));
            mappings.put("관광명소", Arrays.asList("관광명소", "문화시설", "레저시설", "공원"));
            mappings.put("숙박시설", Arrays.asList("숙박", "호텔", "모텔", "펜션", "게스트하우스"));
            mappings.put("편의시설", Arrays.asList("편의점", "마트", "병원", "약국", "주유소", "은행", "ATM"));
            mappings.put("기타", Arrays.asList("기타"));
        }
    }

    public List<String> getNaverCategories(String userCategory) {
        return mappings.getOrDefault(userCategory, Arrays.asList("기타"));
    }
}