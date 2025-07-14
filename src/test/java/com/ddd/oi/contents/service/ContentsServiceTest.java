/*
package com.ddd.oi.contents.service;

import com.ddd.oi.contents.domain.Contents;
import com.ddd.oi.contents.domain.enumType.ContentsTag;
import com.ddd.oi.contents.dto.ContentsCreateRequest;
import com.ddd.oi.contents.dto.ContentsUpdateRequest;
import com.ddd.oi.contents.repository.ContentsRepository;
import com.ddd.oi.contents.service.ContentsService;
import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import(ContentsService.class)
class ContentsServiceTest {
    @Autowired
    ContentsService contentsService;
    @Autowired
    ContentsRepository contentsRepository;

    @Test
    @DisplayName("컨텐츠 생성 및 단일 조회 성공")
    void 컨텐츠_생성_및_조회_성공() {
        ContentsCreateRequest 요청 = new ContentsCreateRequest(
                "제목", "설명", 10000, "추천일정", 3, ContentsTag.TRAVEL, "짧은제목", "짧은설명", List.of());
        var 저장 = contentsService.createContents(요청);
        var 조회 = contentsService.getContents(저장.id());
        assertThat(조회.title()).isEqualTo("제목");
    }

    @Test
    @DisplayName("존재하지 않는 컨텐츠 조회시 예외 발생")
    void 컨텐츠_조회_예외() {
        OiException 예외 = assertThrows(OiException.class, () -> contentsService.getContents(999L));
        assertThat(예외.getErrorCode()).isEqualTo(ErrorCode.ENTITY_NOT_FOUND);
    }

    @Test
    @DisplayName("컨텐츠 수정 성공")
    void 컨텐츠_수정_성공() {
        ContentsCreateRequest 요청 = new ContentsCreateRequest(
                "제목", "설명", 10000, "추천일정", 3, ContentsTag.TRAVEL, "짧은제목", "짧은설명", List.of());
        var 저장 = contentsService.createContents(요청);
        ContentsUpdateRequest 수정요청 = new ContentsUpdateRequest(
                "수정제목", "수정설명", 20000, "수정일정", 5, ContentsTag.DATE, "수정짧은제목", "수정짧은설명", List.of());
        var 수정 = contentsService.updateContents(저장.id(), 수정요청);
        assertThat(수정.title()).isEqualTo("수정제목");
    }

    @Test
    @DisplayName("존재하지 않는 컨텐츠 수정시 예외 발생")
    void 컨텐츠_수정_예외() {
        ContentsUpdateRequest 수정요청 = new ContentsUpdateRequest(
                "수정제목", "수정설명", 20000, "수정일정", 5, ContentsTag.DATE, "수정짧은제목", "수정짧은설명", List.of());
        OiException 예외 = assertThrows(OiException.class, () -> contentsService.updateContents(999L, 수정요청));
        assertThat(예외.getErrorCode()).isEqualTo(ErrorCode.ENTITY_NOT_FOUND);
    }

    @Test
    @DisplayName("컨텐츠 삭제 성공")
    void 컨텐츠_삭제_성공() {
        ContentsCreateRequest 요청 = new ContentsCreateRequest(
                "제목", "설명", 10000, "추천일정", 3, ContentsTag.TRAVEL, "짧은제목", "짧은설명", List.of());
        var 저장 = contentsService.createContents(요청);
        contentsService.deleteContents(저장.id());
        OiException 예외 = assertThrows(OiException.class, () -> contentsService.getContents(저장.id()));
        assertThat(예외.getErrorCode()).isEqualTo(ErrorCode.ENTITY_NOT_FOUND);
    }

    @Test
    @DisplayName("존재하지 않는 컨텐츠 삭제시 예외 발생")
    void 컨텐츠_삭제_예외() {
        OiException 예외 = assertThrows(OiException.class, () -> contentsService.deleteContents(999L));
        assertThat(예외.getErrorCode()).isEqualTo(ErrorCode.ENTITY_NOT_FOUND);
    }
}*/
