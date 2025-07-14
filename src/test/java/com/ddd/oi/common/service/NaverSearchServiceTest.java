package com.ddd.oi.common.service;

import com.ddd.oi.common.config.CategoryMapping;
import com.ddd.oi.common.config.NaverSearchConfig;
import com.ddd.oi.common.config.CategoryColorMapping;
import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.schedule_detail.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;

class NaverSearchServiceTest {
        @Test
        @DisplayName("장소 검색 성공")
        void 장소_검색_성공() {
                // given
                NaverSearchConfig config = new NaverSearchConfig();
                CategoryMapping mapping = new CategoryMapping();
                CategoryColorMapping colorMapping = Mockito.mock(CategoryColorMapping.class);
                RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
                NaverSearchService service = new NaverSearchService(config, mapping, colorMapping, restTemplate);

                SearchRequest req = SearchRequest.builder().query("카페").build();
                SearchResponse mockRes = new SearchResponse();
                mockRes.setItems(List
                                .of(new PlaceItem("카페", null, null, null, null, null, null, null, null, null, null)));
                ResponseEntity<SearchResponse> entity = new ResponseEntity<>(mockRes, HttpStatus.OK);
                Mockito.when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class),
                                eq(SearchResponse.class))).thenReturn(entity);

                // when
                SearchResponse res = service.searchPlaces(req);

                // then
                assertThat(res.getItems().get(0).getTitle()).isEqualTo("카페");
        }

        @Test
        @DisplayName("장소 검색 예외 발생")
        void 장소_검색_예외() {
                NaverSearchConfig config = new NaverSearchConfig();
                CategoryMapping mapping = new CategoryMapping();
                CategoryColorMapping colorMapping = Mockito.mock(CategoryColorMapping.class);
                RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
                NaverSearchService service = new NaverSearchService(config, mapping, colorMapping, restTemplate);

                SearchRequest req = SearchRequest.builder().query("카페").build();
                Mockito.when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class),
                                eq(SearchResponse.class))).thenThrow(new RuntimeException("API 오류"));

                assertThatThrownBy(() -> service.searchPlaces(req))
                                .isInstanceOf(OiException.class)
                                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.INTERNAL_SERVER_ERROR);
        }

        @Test
        @DisplayName("자동완성 성공")
        void 자동완성_성공() {
                NaverSearchConfig config = new NaverSearchConfig();
                CategoryMapping mapping = new CategoryMapping();
                CategoryColorMapping colorMapping = Mockito.mock(CategoryColorMapping.class);
                RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
                NaverSearchService service = new NaverSearchService(config, mapping, colorMapping, restTemplate);

                SearchRequest req = SearchRequest.builder().query("카페").build();
                SearchResponse mockRes = new SearchResponse();
                mockRes.setItems(List
                                .of(new PlaceItem("카페", null, null, null, null, null, null, null, null, null, null)));
                ResponseEntity<SearchResponse> entity = new ResponseEntity<>(mockRes, HttpStatus.OK);
                Mockito.when(restTemplate.exchange(any(URI.class), eq(HttpMethod.GET), any(HttpEntity.class),

                                eq(SearchResponse.class))).thenReturn(entity);

                AutoCompleteResponse res = service.getAutoComplete("카페", null);
                assertThat(res.getSuggestions()).contains("카페");
        }

        @Test
        @DisplayName("자동완성 예외 발생")
        void 자동완성_예외() {
                NaverSearchConfig config = new NaverSearchConfig();
                CategoryMapping mapping = new CategoryMapping();
                CategoryColorMapping colorMapping = Mockito.mock(CategoryColorMapping.class);
                RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
                NaverSearchService service = new NaverSearchService(config, mapping, colorMapping, restTemplate);

                Mockito.when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class),
                                eq(SearchResponse.class))).thenThrow(new RuntimeException("API 오류"));

                assertThatThrownBy(() -> service.getAutoComplete("카페", null))
                                .isInstanceOf(OiException.class)
                                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.INTERNAL_SERVER_ERROR);
        }
}

