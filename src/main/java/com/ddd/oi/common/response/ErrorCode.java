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

    // 인증/인가 에러
    CREDENTIALS_INVALID("아이디 또는 비밀번호가 잘못되었습니다.", HttpStatus.UNAUTHORIZED),
    REQUEST_FORMAT_INVALID("입력 형식이 잘못되었습니다.", HttpStatus.BAD_REQUEST),

    COOKIE_NOT_FOUND("쿠키가 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_NOT_FOUND("Refresh 토큰이 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_EXPIRED("Refresh 토큰이 만료되었습니다.", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_INVALID_OR_EXPIRED("Refresh 토큰이 유효하지 않거나 만료되었습니다.", HttpStatus.NOT_FOUND),
    ACCESS_TOKEN_EXPIRED("Access 토큰이 만료되었습니다.", HttpStatus.REQUEST_TIMEOUT),
    TOKEN_INVALID("유효하지 않은 토큰입니다.", HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRED("만료된 토큰입니다.", HttpStatus.BAD_REQUEST),
    OAUTH_PROVIDER_MISMATCH("다른 플랫폼으로 가입된 계정입니다. 해당 플랫폼으로 로그인하세요.",HttpStatus.BAD_REQUEST),

    // Redis 관련
    REDIS_DATA_NOT_FOUND("해당 키에 대한 값이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    REDIS_SERVER_ERROR("Redis 서버에서 데이터를 처리하는 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    REDIS_DELETE_FAILED("삭제 요청 실패: 키가 존재하지 않거나 삭제되지 않았습니다.", HttpStatus.NOT_FOUND),

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
