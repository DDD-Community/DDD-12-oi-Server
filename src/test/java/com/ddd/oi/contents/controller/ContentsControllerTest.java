/*
package com.ddd.oi.contents.controller;

import com.ddd.oi.contents.domain.enumType.ContentsTag;
import com.ddd.oi.contents.dto.ContentsCreateRequest;
import com.ddd.oi.contents.dto.ContentsResponse;
import com.ddd.oi.contents.dto.ContentsUpdateRequest;
import com.ddd.oi.contents.service.ContentsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@WebMvcTest(ContentsController.class)
@MockBean(JpaMetamodelMappingContext.class)
class ContentsControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ContentsService contentsService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("컨텐츠 생성 성공")
    void 컨텐츠_생성_성공() throws Exception {
        ContentsCreateRequest req = new ContentsCreateRequest("제목", "설명", 10000, "추천일정", 3, ContentsTag.TRAVEL, "짧은제목",
                "짧은설명", List.of());
        ContentsResponse res = new ContentsResponse(1L, "제목", "설명", 10000, "추천일정", 3, ContentsTag.TRAVEL, "짧은제목",
                "짧은설명", List.of(), List.of());
        Mockito.when(contentsService.createContents(any())).thenReturn(res);
        mockMvc.perform(post("/api/contents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("제목"));
    }

    @Test
    @DisplayName("컨텐츠 단건 조회 성공")
    void 컨텐츠_조회_성공() throws Exception {
        ContentsResponse res = new ContentsResponse(1L, "제목", "설명", 10000, "추천일정", 3, ContentsTag.TRAVEL, "짧은제목",
                "짧은설명", List.of(), List.of());
        Mockito.when(contentsService.getContents(eq(1L))).thenReturn(res);
        mockMvc.perform(get("/api/contents/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("제목"));
    }

    @Test
    @DisplayName("컨텐츠 단건 조회 예외")
    void 컨텐츠_조회_예외() throws Exception {
        Mockito.when(contentsService.getContents(eq(1L))).thenThrow(new RuntimeException());
        mockMvc.perform(get("/api/contents/1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("컨텐츠 수정 성공")
    void 컨텐츠_수정_성공() throws Exception {
        ContentsUpdateRequest req = new ContentsUpdateRequest("수정제목", "수정설명", 20000, "수정일정", 5, ContentsTag.DATE,
                "수정짧은제목", "수정짧은설명", List.of());
        ContentsResponse res = new ContentsResponse(1L, "수정제목", "수정설명", 20000, "수정일정", 5, ContentsTag.DATE, "수정짧은제목",
                "수정짧은설명", List.of(), List.of());
        Mockito.when(contentsService.updateContents(eq(1L), any())).thenReturn(res);
        mockMvc.perform(put("/api/contents/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("수정제목"));
    }

    @Test
    @DisplayName("컨텐츠 수정 예외")
    void 컨텐츠_수정_예외() throws Exception {
        ContentsUpdateRequest req = new ContentsUpdateRequest("수정제목", "수정설명", 20000, "수정일정", 5, ContentsTag.DATE,
                "수정짧은제목", "수정짧은설명", List.of());
        Mockito.when(contentsService.updateContents(eq(1L), any())).thenThrow(new RuntimeException());
        mockMvc.perform(put("/api/contents/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("컨텐츠 삭제 성공")
    void 컨텐츠_삭제_성공() throws Exception {
        Mockito.doNothing().when(contentsService).deleteContents(eq(1L));
        mockMvc.perform(delete("/api/contents/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("컨텐츠 삭제 예외")
    void 컨텐츠_삭제_예외() throws Exception {
        Mockito.doThrow(new RuntimeException()).when(contentsService).deleteContents(eq(1L));
        mockMvc.perform(delete("/api/contents/1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("컨텐츠 페이징 조회 성공")
    void 컨텐츠_페이징_조회_성공() throws Exception {
        ContentsResponse res = new ContentsResponse(1L, "제목", "설명", 10000, "추천일정", 3, ContentsTag.TRAVEL, "짧은제목",
                "짧은설명", List.of(), List.of());
        Mockito.when(contentsService.getContentsPage(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(res), PageRequest.of(0, 10), 1));
        mockMvc.perform(get("/api/contents?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].title").value("제목"));
    }
}*/
