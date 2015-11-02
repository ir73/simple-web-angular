package com.sappadev.simplewebangular.controllers.vo;

/**
 * Created by serge_000 on 31.10.2015.
 */
public enum ErrorCode {
    /**
     * Unknown error
     */
    UNKNOWN(0),

    /**
     * Unauthorized error
     */
    UNAUTHORIZED(1),

    /**
     * Invalid input for controller
     */
    INVALID_INPUT(2);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.valueOf(code);
    }
}
