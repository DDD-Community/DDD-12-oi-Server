package com.ddd.oi.contents_spot.service;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import com.ddd.oi.contents.domain.Contents;
import com.ddd.oi.contents.repository.ContentsRepository;
import com.ddd.oi.contents_spot.domain.ContentsSpot;
import com.ddd.oi.contents_spot.dto.ContentsSpotRequest;
import com.ddd.oi.contents_spot.dto.ContentsSpotResponse;
import com.ddd.oi.contents_spot.repository.ContentsSpotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class ContentsSpotServiceTest {
    @InjectMocks
    ContentsSpotService service;
    @Mock
    ContentsSpotRepository spotRepository;
    @Mock
    ContentsRepository contentsRepository;
    Contents contents;
    ContentsSpot spot;

    @BeforeEach
    void setup() {
        contents = Contents.builder().id(1L).title("코스").contentsTag(null).build();
        spot = ContentsSpot.builder().spotName("장소").build();
    }

    @Test
    @DisplayName("장소 생성 성공")
    void create_success() {
        when(contentsRepository.findById(1L)).thenReturn(Optional.of(contents));
        ContentsSpotRequest req = new ContentsSpotRequest("spot", "addr", "desc", "img", 37.5, 127.0);
        ContentsSpotResponse res = service.createSpot(1L, req);
        assertThat(res.spotName()).isEqualTo("spot");
        verify(spotRepository).save(any(ContentsSpot.class));
    }

    @Test
    @DisplayName("존재하지 않는 코스에 장소 생성시 예외")
    void create_no_contents() {
        when(contentsRepository.findById(1L)).thenReturn(Optional.empty());
        ContentsSpotRequest req = new ContentsSpotRequest("spot", "addr", "desc", "img", 37.5, 127.0);
        OiException ex = assertThrows(OiException.class, () -> service.createSpot(1L, req));
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.ENTITY_NOT_FOUND);
    }

    @Test
    @DisplayName("장소 단건 조회 성공")
    void get_success() {
        when(spotRepository.findById(2L)).thenReturn(Optional.of(spot));
        ContentsSpotResponse res = service.getSpot(2L);
        assertThat(res.spotName()).isEqualTo("장소");
    }

    @Test
    @DisplayName("장소 단건 조회시 없으면 예외")
    void get_not_found() {
        when(spotRepository.findById(2L)).thenReturn(Optional.empty());
        OiException ex = assertThrows(OiException.class, () -> service.getSpot(2L));
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.ENTITY_NOT_FOUND);
    }

    @Test
    @DisplayName("장소 수정 성공")
    void update_success() {
        when(spotRepository.findById(2L)).thenReturn(Optional.of(spot));
        ContentsSpotRequest req = new ContentsSpotRequest("수정", "a", "b", "c", 1.0, 2.0);
        ContentsSpotResponse res = service.updateSpot(2L, req);
        assertThat(res.spotName()).isEqualTo("수정");
    }

    @Test
    @DisplayName("장소 수정시 없으면 예외")
    void update_not_found() {
        when(spotRepository.findById(2L)).thenReturn(Optional.empty());
        ContentsSpotRequest req = new ContentsSpotRequest("수정", "a", "b", "c", 1.0, 2.0);
        OiException ex = assertThrows(OiException.class, () -> service.updateSpot(2L, req));
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.ENTITY_NOT_FOUND);
    }

    @Test
    @DisplayName("장소 삭제 성공")
    void delete_success() {
        when(spotRepository.existsById(2L)).thenReturn(true);
        service.deleteSpot(2L);
        verify(spotRepository).deleteById(2L);
    }

    @Test
    @DisplayName("장소 삭제시 없으면 예외")
    void delete_not_found() {
        when(spotRepository.existsById(2L)).thenReturn(false);
        OiException ex = assertThrows(OiException.class, () -> service.deleteSpot(2L));
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.ENTITY_NOT_FOUND);
    }

    @Test
    @DisplayName("코스별 장소 목록 조회")
    void list_success() {
        when(spotRepository.findAll()).thenReturn(List.of(spot));
        List<ContentsSpotResponse> list = service.getSpotsByContentsId(1L);
        assertThat(list).hasSize(1);
    }
}