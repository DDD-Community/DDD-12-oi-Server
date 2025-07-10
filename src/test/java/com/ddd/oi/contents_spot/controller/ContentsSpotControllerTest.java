package com.ddd.oi.contents_spot.controller;

import com.ddd.oi.contents_spot.dto.ContentsSpotRequest;
import com.ddd.oi.contents_spot.dto.ContentsSpotResponse;
import com.ddd.oi.contents_spot.service.ContentsSpotService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

@WebMvcTest(ContentsSpotController.class)
@MockBean(JpaMetamodelMappingContext.class)

class ContentsSpotControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ContentsSpotService contentsSpotService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("장소 생성 성공")
    void 장소_생성_성공() throws Exception {
        ContentsSpotRequest req = new ContentsSpotRequest("spot", "addr", "desc", "img", 37.5, 127.0);
        ContentsSpotResponse mockResponse = new ContentsSpotResponse(1L, "spot", "addr", "desc", "img", 37.5, 127.0);

        Mockito.when(contentsSpotService.createSpot(eq(1L), any())).thenReturn(mockResponse);

        mockMvc.perform(post("/api/contents/1/spots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.spotName").value("spot"));
    }

    @Test
    @DisplayName("장소 단건 조회 404 예외")
    void 장소_조회_예외() throws Exception {
        Mockito.when(contentsSpotService.getSpot(eq(1L))).thenThrow(new RuntimeException());
        mockMvc.perform(get("/api/contents/1/spots/1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("장소 단건 조회 성공")
    void 장소_조회_성공() throws Exception {
        ContentsSpotResponse res = new ContentsSpotResponse(1L, "spot", "addr", "desc", "img", 37.5, 127.0);
        Mockito.when(contentsSpotService.getSpot(eq(1L))).thenReturn(res);
        mockMvc.perform(get("/api/contents/1/spots/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.spotName").value("spot"));
    }

    @Test
    @DisplayName("장소 수정 성공")
    void 장소_수정_성공() throws Exception {
        ContentsSpotRequest req = new ContentsSpotRequest("spot2", "addr2", "desc2", "img2", 38.0, 128.0);
        ContentsSpotResponse res = new ContentsSpotResponse(1L, "spot2", "addr2", "desc2", "img2", 38.0, 128.0);
        Mockito.when(contentsSpotService.updateSpot(eq(1L), any())).thenReturn(res);
        mockMvc.perform(put("/api/contents/1/spots/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.spotName").value("spot2"));
    }

    @Test
    @DisplayName("장소 수정 실패(404)")
    void 장소_수정_실패() throws Exception {
        ContentsSpotRequest req = new ContentsSpotRequest("spot2", "addr2", "desc2", "img2", 38.0, 128.0);
        Mockito.when(contentsSpotService.updateSpot(eq(1L), any())).thenThrow(
                new com.ddd.oi.common.exception.OiException(com.ddd.oi.common.response.ErrorCode.ENTITY_NOT_FOUND));
        mockMvc.perform(put("/api/contents/1/spots/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.resultType").value("FAIL"));
    }

    @Test
    @DisplayName("장소 삭제 성공")
    void 장소_삭제_성공() throws Exception {
        Mockito.doNothing().when(contentsSpotService).deleteSpot(eq(1L));
        mockMvc.perform(delete("/api/contents/1/spots/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("장소 삭제 성공"));
    }

    @Test
    @DisplayName("장소 삭제 실패(404)")
    void 장소_삭제_실패() throws Exception {
        Mockito.doThrow(
                new com.ddd.oi.common.exception.OiException(com.ddd.oi.common.response.ErrorCode.ENTITY_NOT_FOUND))
                .when(contentsSpotService).deleteSpot(eq(1L));
        mockMvc.perform(delete("/api/contents/1/spots/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.resultType").value("FAIL"));
    }

    @Test
    @DisplayName("장소 목록 조회 성공")
    void 장소_목록_조회_성공() throws Exception {
        List<ContentsSpotResponse> list = List.of(
                new ContentsSpotResponse(1L, "spot", "addr", "desc", "img", 37.5, 127.0),
                new ContentsSpotResponse(2L, "spot2", "addr2", "desc2", "img2", 38.0, 128.0));
        Mockito.when(contentsSpotService.getSpotsByContentsId(eq(1L))).thenReturn(list);
        mockMvc.perform(get("/api/contents/1/spots"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].spotName").value("spot"));
    }

    @Test
    @DisplayName("장소 생성 실패(404)")
    void 장소_생성_실패() throws Exception {
        ContentsSpotRequest req = new ContentsSpotRequest("spot", "addr", "desc", "img", 37.5, 127.0);
        Mockito.when(contentsSpotService.createSpot(eq(1L), any())).thenThrow(
                new com.ddd.oi.common.exception.OiException(com.ddd.oi.common.response.ErrorCode.ENTITY_NOT_FOUND));
        mockMvc.perform(post("/api/contents/1/spots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.resultType").value("FAIL"));
    }
}
