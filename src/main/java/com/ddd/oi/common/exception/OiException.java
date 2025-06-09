package com.ddd.oi.common.exception;

import com.ddd.oi.common.response.ErrorCode;
import lombok.Getter;

@Getter
public class OiException extends RuntimeException {
    private final ErrorCode errorCode;

    public OiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public int getHttpStatusCode() {
        return this.errorCode.getStatusCode();
    }

}
