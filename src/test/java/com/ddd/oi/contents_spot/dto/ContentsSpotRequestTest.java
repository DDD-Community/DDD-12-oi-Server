package com.ddd.oi.contents_spot.dto;

import com.ddd.oi.common.exception.OiException;
import com.ddd.oi.common.response.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContentsSpotRequestTest {
    @Test
    @DisplayName("spotName이 null/blank면 예외 발생")
    void 장소명_null_또는_빈값_예외() {
        OiException 예외1 = assertThrows(OiException.class,
                () -> new ContentsSpotRequest(null, "주소", "설명", "이미지", 37.5, 127.0));
        assertThat(예외1.getErrorCode()).isEqualTo(ErrorCode.PARAMETER_INVALID);
        OiException 예외2 = assertThrows(OiException.class,
                () -> new ContentsSpotRequest("   ", "주소", "설명", "이미지", 37.5, 127.0));
        assertThat(예외2.getErrorCode()).isEqualTo(ErrorCode.PARAMETER_INVALID);
    }

    @Test
    @DisplayName("위도/경도 범위 벗어나면 예외 발생")
    void 위도_경도_범위_예외() {
        OiException 예외1 = assertThrows(OiException.class,
                () -> new ContentsSpotRequest("spot", "주소", "설명", "이미지", 100.0, 127.0));
        assertThat(예외1.getErrorCode()).isEqualTo(ErrorCode.INVALID_LATITUDE);
        OiException 예외2 = assertThrows(OiException.class,
                () -> new ContentsSpotRequest("spot", "주소", "설명", "이미지", 37.5, 200.0));
        assertThat(예외2.getErrorCode()).isEqualTo(ErrorCode.INVALID_LONGITUDE);
    }
}