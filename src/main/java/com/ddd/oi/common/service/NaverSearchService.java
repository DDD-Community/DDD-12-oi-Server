package com.ddd.oi.common.service;

import com.ddd.oi.common.config.CategoryMapping;
import com.ddd.oi.common.config.NaverSearchConfig;
import com.ddd.oi.common.config.CategoryColorMapping;
import com.ddd.oi.common.config.NaverMapConfig;
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
    private final NaverMapConfig naverMapConfig;
    private final CategoryMapping categoryMapping;
    private final CategoryColorMapping categoryColorMapping;
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
                for (PlaceItem item : searchResponse.getItems()) {
                    String mainCategory = mapToMainCategory(item.getCategory());
                    item.setMainCategory(mainCategory);
                    item.setCategoryColor(categoryColorMapping.getColor(mainCategory));

                    String addr = item.getRoadAddress() != null ? item.getRoadAddress() : item.getAddress();

                    if (addr != null && !addr.isBlank()) {
                        try {
                            double[] latLng = geocodeAddress(addr);
                            item.setLongitude(latLng[0]);
                            item.setLatitude(latLng[1]);
                            item.setMapx(null);
                            item.setMapy(null);
                        } catch (Exception e) {
                            log.warn("주소 좌표 변환 실패: address={}", addr, e);
                        }
                    }
                }
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
            List<String> suggestions = suggestionSet.stream().limit(5).collect(Collectors.toList());
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

    private String mapToMainCategory(String naverCategory) {
        if (naverCategory == null)
            return "기타";
        for (String main : categoryMapping.getMappings().keySet()) {
            for (String keyword : categoryMapping.getNaverCategories(main)) {
                if (naverCategory.contains(keyword)) {
                    return main;
                }
            }
        }
        return "기타";
    }

    // 주소 → 좌표 변환 (geocode API)
    private double[] geocodeAddress(String address) {
        String url = "https://maps.apigw.ntruss.com/map-geocode/v2/geocode";
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", naverMapConfig.getApiKeyId());
        headers.set("X-NCP-APIGW-API-KEY", naverMapConfig.getApiKey());
        headers.set("Accept", "application/json");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("query", address);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                entity,
                Map.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List addresses = (List) response.getBody().get("addresses");
            if (addresses != null && !addresses.isEmpty()) {
                Map addr = (Map) addresses.get(0);
                double longitude = Double.parseDouble(addr.get("x").toString());
                double latitude = Double.parseDouble(addr.get("y").toString());
                return new double[] { longitude, latitude };
            }
        }
        throw new OiException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
