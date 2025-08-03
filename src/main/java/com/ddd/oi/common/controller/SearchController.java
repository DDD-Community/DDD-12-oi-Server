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
@RequestMapping("/api/v1/search")
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

}
