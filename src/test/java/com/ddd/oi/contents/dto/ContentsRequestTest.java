package com.ddd.oi.contents.dto;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.contents.domain.enumType.ContentsTag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContentsRequestTest {
    @Test
    @DisplayName("ContentsCreateRequest - 필수값 누락/음수 등 예외처리")
    void 생성요청_예외처리() {
        // title null
        OiException 예외1 = assertThrows(OiException.class, () -> new ContentsCreateRequest(null, "desc", 100, "추천", 1,
                ContentsTag.TRAVEL, "짧은제목", "짧은설명", List.of()));
        assertThat(예외1.getErrorCode()).isEqualTo(ErrorCode.PARAMETER_INVALID);
        // title blank
        OiException 예외2 = assertThrows(OiException.class, () -> new ContentsCreateRequest("   ", "desc", 100, "추천", 1,
                ContentsTag.TRAVEL, "짧은제목", "짧은설명", List.of()));
        assertThat(예외2.getErrorCode()).isEqualTo(ErrorCode.PARAMETER_INVALID);
        // contentsTag null
        OiException 예외3 = assertThrows(OiException.class,
                () -> new ContentsCreateRequest("제목", "desc", 100, "추천", 1, null, "짧은제목", "짧은설명", List.of()));
        assertThat(예외3.getErrorCode()).isEqualTo(ErrorCode.PARAMETER_INVALID);
        // cost 음수
        OiException 예외4 = assertThrows(OiException.class, () -> new ContentsCreateRequest("제목", "desc", -1, "추천", 1,
                ContentsTag.TRAVEL, "짧은제목", "짧은설명", List.of()));
        assertThat(예외4.getErrorCode()).isEqualTo(ErrorCode.PARAMETER_INVALID);
        // duration 음수
        OiException 예외5 = assertThrows(OiException.class, () -> new ContentsCreateRequest("제목", "desc", 100, "추천", -1,
                ContentsTag.TRAVEL, "짧은제목", "짧은설명", List.of()));
        assertThat(예외5.getErrorCode()).isEqualTo(ErrorCode.PARAMETER_INVALID);
    }

    @Test
    @DisplayName("ContentsUpdateRequest - 필수값 누락/음수 등 예외처리")
    void 수정요청_예외처리() {
        // title null
        OiException 예외1 = assertThrows(OiException.class, () -> new ContentsUpdateRequest(null, "desc", 100, "추천", 1,
                ContentsTag.TRAVEL, "짧은제목", "짧은설명", List.of()));
        assertThat(예외1.getErrorCode()).isEqualTo(ErrorCode.PARAMETER_INVALID);
        // title blank
        OiException 예외2 = assertThrows(OiException.class, () -> new ContentsUpdateRequest("   ", "desc", 100, "추천", 1,
                ContentsTag.TRAVEL, "짧은제목", "짧은설명", List.of()));
        assertThat(예외2.getErrorCode()).isEqualTo(ErrorCode.PARAMETER_INVALID);
        // contentsTag null
        OiException 예외3 = assertThrows(OiException.class,
                () -> new ContentsUpdateRequest("제목", "desc", 100, "추천", 1, null, "짧은제목", "짧은설명", List.of()));
        assertThat(예외3.getErrorCode()).isEqualTo(ErrorCode.PARAMETER_INVALID);
        // cost 음수
        OiException 예외4 = assertThrows(OiException.class, () -> new ContentsUpdateRequest("제목", "desc", -1, "추천", 1,
                ContentsTag.TRAVEL, "짧은제목", "짧은설명", List.of()));
        assertThat(예외4.getErrorCode()).isEqualTo(ErrorCode.PARAMETER_INVALID);
        // duration 음수
        OiException 예외5 = assertThrows(OiException.class, () -> new ContentsUpdateRequest("제목", "desc", 100, "추천", -1,
                ContentsTag.TRAVEL, "짧은제목", "짧은설명", List.of()));
        assertThat(예외5.getErrorCode()).isEqualTo(ErrorCode.PARAMETER_INVALID);
    }
}