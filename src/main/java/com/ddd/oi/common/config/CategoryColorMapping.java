package com.ddd.oi.common.config;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class CategoryColorMapping {
    private final Map<String, String> colorMap = new HashMap<>();

    public CategoryColorMapping() {
        colorMap.put("음식점", "#F76945");
        colorMap.put("카페", "#09B596");
        colorMap.put("관광명소", "#F7A61F");
        colorMap.put("숙박시설", "#A052FF");
        colorMap.put("편의시설", "#5F77FF");
        colorMap.put("기타", "#676767");
    }

    public String getColor(String category) {
        return colorMap.getOrDefault(category, "#676767");
    }

    public Map<String, String> getAll() {
        return colorMap;
    }
}
