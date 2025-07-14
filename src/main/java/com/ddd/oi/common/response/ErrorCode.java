package com.ddd.oi.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 글로벌 에러
    PARAMETER_INVALID("잘못된 파라미터 입니다.", HttpStatus.BAD_REQUEST),
    METHOD_INVALID("잘못된 METHOD 요청입니다.", HttpStatus.METHOD_NOT_ALLOWED),
    INTERNAL_SERVER_ERROR("서버 내부 오류입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ENTITY_NOT_FOUND("객체를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ENTITY_TYPE_INVALID("유효하지 않은 엔터티 타입입니다.", HttpStatus.BAD_REQUEST),
    BAD_REQUEST("잘못된 요청입니다", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST),

    // 스케줄 관련 에러
    END_DATE_BEFORE_START_DATE("종료 날짜는 시작 날짜 이후로 설정해야 합니다.", HttpStatus.BAD_REQUEST),
    DUPLICATE_GROUP_NAME("중복된 일행 이름이 포함되어 있습니다.", HttpStatus.BAD_REQUEST),
    SCHEDULE_LIMIT_EXCEEDED("하루에 최대 3개의 일정만 등록할 수 있습니다.", HttpStatus.BAD_REQUEST),
    SCHEDULE_DETAIL_CREATE_LIMIT_EXCEEDED("세부일정은 한 번에 최대 5개까지 생성할 수 있습니다.", HttpStatus.BAD_REQUEST),

    // 스케줄 상세 관련 세어
    INVALID_LATITUDE("유효하지 않은 위도입니다. -90에서 90 사이의 값을 입력해주세요.", HttpStatus.BAD_REQUEST),
    INVALID_LONGITUDE("유효하지 않은 경도입니다. -180에서 180 사이의 값을 입력해주세요.", HttpStatus.BAD_REQUEST),
    INVALID_TARGET_DATE("시작 시간은 현재 시간 이후로 설정해야 합니다.", HttpStatus.BAD_REQUEST),

    // 이미지 관련 에러
    IMAGE_NOT_FOUND("이미지를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    IMAGE_LOADING_ERROR("이미지 로딩에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus httpStatus;

    public int getStatusCode() {
        return httpStatus.value();
    }
}
