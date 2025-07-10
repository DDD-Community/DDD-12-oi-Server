package com.ddd.oi.common.config;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class CategoryColorMapping {
    private final Map<String, String> colorMap = new HashMap<>();

    public CategoryColorMapping() {
        colorMap.put("음식점", "#FF6B3D"); // 오렌지
        colorMap.put("카페", "#19C89A"); // 민트
        colorMap.put("관광명소", "#FFB800"); // 노랑
        colorMap.put("숙박시설", "#A259FF"); // 보라
        colorMap.put("편의시설", "#3D6BFF"); // 파랑
        colorMap.put("기타", "#666666"); // 회색
    }

    public String getColor(String category) {
        return colorMap.getOrDefault(category, "#666666");
    }

    public Map<String, String> getAll() {
        return colorMap;
    }
}