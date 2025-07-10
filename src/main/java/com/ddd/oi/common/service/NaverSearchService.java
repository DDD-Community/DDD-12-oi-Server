package com.ddd.oi.common.service;

import com.ddd.oi.common.config.CategoryMapping;
import com.ddd.oi.common.config.NaverSearchConfig;
import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.schedule_detail.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaverSearchService {
    private final NaverSearchConfig naverConfig;
    private final CategoryMapping categoryMapping;
    private final RestTemplate restTemplate;

    public SearchResponse searchPlaces(SearchRequest request) {
        try {
            String query = buildSearchQuery(request);
            URI uri = buildSearchUri(query, request);

            HttpHeaders headers = createNaverHeaders();
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<SearchResponse> response = restTemplate.exchange(
                uri, HttpMethod.GET, entity, SearchResponse.class);
            SearchResponse searchResponse = response.getBody();
            if (searchResponse != null) {
                searchResponse.setCategory(request.getCategory());
                searchResponse.setHasMore(
                    searchResponse.getStart() + searchResponse.getDisplay() < searchResponse.getTotal());
            }
            return searchResponse != null ? searchResponse : new SearchResponse();
        } catch (Exception e) {
            log.error("Error searching places for query: {}", request.getQuery(), e);
            throw new OiException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public AutoCompleteResponse getAutoComplete(String query, String category) {
        try {
            SearchRequest searchRequest = SearchRequest.builder()
                    .query(query)
                    .category(category)
                    .display(5)
                    .build();

            SearchResponse searchResponse = searchPlaces(searchRequest);

            Set<String> suggestionSet = new HashSet<>();
            for (PlaceItem item : searchResponse.getItems()) {
                String title = item.getTitle();
                if (title != null && title.toLowerCase().contains(query.toLowerCase())) {
                    suggestionSet.add(title);
                }
                String itemCategory = item.getCategory();
                if (itemCategory != null) {
                    String[] categories = itemCategory.split(",");
                    for (String cat : categories) {
                        cat = cat.trim();
                        if (cat.toLowerCase().contains(query.toLowerCase())) {
                            suggestionSet.add(cat);
                        }
                    }
                }
            }
            List<String> suggestions = suggestionSet.stream().limit(10).collect(Collectors.toList());
            return new AutoCompleteResponse(suggestions, category);
        } catch (Exception e) {
            log.error("Error getting autocomplete for query: {}", query, e);
            throw new OiException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private String buildSearchQuery(SearchRequest request) {
        StringBuilder queryBuilder = new StringBuilder(request.getQuery());
        if (request.getCategory() != null && !request.getCategory().equals("기타")) {
            List<String> naverCategories = categoryMapping.getNaverCategories(request.getCategory());
            if (!naverCategories.isEmpty()) {
                queryBuilder.append(" ").append(naverCategories.get(0));
            }
        }
        return queryBuilder.toString();
    }

    private URI buildSearchUri(String query, SearchRequest request) {
        return UriComponentsBuilder.fromHttpUrl(naverConfig.getBaseUrl())
            .queryParam("query", query)
            .queryParam("display", Math.min(request.getDisplay(), naverConfig.getMaxDisplay()))
            .queryParam("start", request.getStart())
            .queryParam("sort", request.getSort())
            .build()
            .encode()
            .toUri();
    }

    private HttpHeaders createNaverHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverConfig.getClientId());
        headers.set("X-Naver-Client-Secret", naverConfig.getClientSecret());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
