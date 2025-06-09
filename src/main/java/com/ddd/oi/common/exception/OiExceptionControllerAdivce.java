package com.ddd.oi.common.exception;

import com.ddd.oi.common.response.CustomApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class OiExceptionControllerAdivce {
    @ExceptionHandler(OiException.class)
    public ResponseEntity<CustomApiResponse<?>> handlePlanearException(OiException e) {
        log.warn("OiException", e);
        return ResponseEntity.status(e.getHttpStatusCode())
                .body(CustomApiResponse.fail(e.getErrorCode()));
    }

}
