package com.ddd.oi.common.controller;

import com.ddd.oi.common.config.CategoryColorMapping;
import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.common.service.NaverSearchService;
import com.ddd.oi.schedule_detail.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SearchController.class)
@MockBean(JpaMetamodelMappingContext.class)
class SearchControllerTest {
        @Autowired
        MockMvc mockMvc;

        @MockBean
        NaverSearchService naverSearchService;
        @MockBean
        CategoryColorMapping categoryColorMapping;

        @Test
        @DisplayName("장소 검색 성공")
        void 장소_검색_성공() throws Exception {
                SearchResponse mockResponse = new SearchResponse();
                mockResponse.setItems(List
                                .of(new PlaceItem("카페", null, null, null, null, null, null, null, null, null, null)));
                mockResponse.setCategory("카페");
                mockResponse.setHasMore(false);
                mockResponse.setDisplay(1);
                mockResponse.setStart(1);
                mockResponse.setTotal(1);
                Mockito.when(naverSearchService.searchPlaces(any())).thenReturn(mockResponse);
                Mockito.when(categoryColorMapping.getColor(anyString())).thenReturn("#FF6B3D");

                mockMvc.perform(get("/api/search/places")
                                .param("query", "카페"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.data.items[0].title").value("카페"))
                                .andExpect(jsonPath("$.message").value("장소 검색 성공"));
        }

        @Test
        @DisplayName("장소 검색 파라미터 누락(500)")
        void 장소_검색_파라미터_누락() throws Exception {
                Mockito.when(categoryColorMapping.getColor(anyString())).thenReturn("#FF6B3D");
                mockMvc.perform(get("/api/search/places"))
                                .andExpect(status().isInternalServerError());
        }

        @Test
        @DisplayName("장소 검색 서비스 예외(500)")
        void 장소_검색_서비스_예외() throws Exception {
                Mockito.when(naverSearchService.searchPlaces(any()))
                                .thenThrow(new OiException(ErrorCode.INTERNAL_SERVER_ERROR));
                Mockito.when(categoryColorMapping.getColor(anyString())).thenReturn("#FF6B3D");
                mockMvc.perform(get("/api/search/places").param("query", "카페"))
                                .andExpect(status().isInternalServerError())
                                .andExpect(jsonPath("$.resultType").value("FAIL"));
        }

        /*@Test
        @DisplayName("자동완성 성공")
        void 자동완성_성공() throws Exception {
                // given
                AutoCompleteResponse mockResponse = new AutoCompleteResponse(List.of("카페", "카페베네"), "카페");
                Mockito.when(naverSearchService.getAutoComplete(anyString(), anyString()))
                    .thenReturn(mockResponse);

                // when & then
                mockMvc.perform(get("/api/search/autocomplete")
                        .param("query", "카페"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.statusCode").value(200))
                    .andExpect(jsonPath("$.resultType").value("SUCCESS"))
                    .andExpect(jsonPath("$.data.suggestions").isArray())
                    .andExpect(jsonPath("$.data.suggestions[0]").value("카페"))
                    .andExpect(jsonPath("$.data.category").value("카페"))
                    .andExpect(jsonPath("$.message").value("자동완성 성공"));
        }*/

        @Test
        @DisplayName("자동완성 파라미터 누락(500)")
        void 자동완성_파라미터_누락() throws Exception {
                Mockito.when(categoryColorMapping.getColor(anyString())).thenReturn("#FF6B3D");
                mockMvc.perform(get("/api/search/autocomplete"))
                                .andExpect(status().isInternalServerError());
        }

       /* @Test
        @DisplayName("자동완성 서비스 예외(500)")
        void 자동완성_서비스_예외() throws Exception {
                Mockito.when(naverSearchService.getAutoComplete(anyString(), anyString()))
                                .thenThrow(new OiException(ErrorCode.INTERNAL_SERVER_ERROR));
                Mockito.when(categoryColorMapping.getColor(anyString())).thenReturn("#FF6B3D");
                mockMvc.perform(get("/api/search/autocomplete").param("query", "카페"))
                                .andExpect(status().isInternalServerError())
                                .andExpect(jsonPath("$.resultType").value("FAIL"));
        }*/

        @Test
        @DisplayName("카테고리 목록 성공")
        void 카테고리_목록_성공() throws Exception {
                Mockito.when(categoryColorMapping.getColor(anyString())).thenReturn("#FF6B3D");

                mockMvc.perform(get("/api/search/categories"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.data[0].name").value("음식점"))
                                .andExpect(jsonPath("$.data[0].color").value("#FF6B3D"));
        }
}
