package com.ddd.oi.common.controller;

import com.ddd.oi.common.config.CategoryColorMapping;
import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.common.service.NaverSearchService;
import com.ddd.oi.schedule_detail.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Slf4j
public class SearchController {
    private final NaverSearchService naverSearchService;
    private final CategoryColorMapping categoryColorMapping;

    @GetMapping("/places")
    public CustomApiResponse<SearchResponse> searchPlaces(
            @RequestParam String query,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "5") int display,
            @RequestParam(defaultValue = "1") int start,
            @RequestParam(defaultValue = "random") String sort) {
        SearchRequest request = SearchRequest.builder()
                .query(query)
                .category(category)
                .display(display)
                .start(start)
                .sort(sort)
                .build();
        SearchResponse response = naverSearchService.searchPlaces(request);
        return CustomApiResponse.success(response, 200, "장소 검색 성공");
    }

   /* @GetMapping("/autocomplete")
    public CustomApiResponse<AutoCompleteResponse> getAutoComplete(
            @RequestParam String query,
            @RequestParam(required = false) String category) {
        if (query.length() < 2) {
            return CustomApiResponse.success(new AutoCompleteResponse(List.of(), category), 200, "자동완성 결과 없음");
        }
        AutoCompleteResponse response = naverSearchService.getAutoComplete(query, category);
        return CustomApiResponse.success(response, 200, "자동완성 성공");
    }
*/
    @GetMapping("/categories")
    public CustomApiResponse<List<CategoryDto>> getCategories() {
        List<String> categories = Arrays.asList("음식점", "카페", "관광명소", "숙박시설", "편의시설", "기타");
        List<CategoryDto> result = categories.stream()
                .map(cat -> new CategoryDto(cat, categoryColorMapping.getColor(cat)))
                .collect(Collectors.toList());
        return CustomApiResponse.success(result, 200, "카테고리 목록 조회 성공");
    }
}
