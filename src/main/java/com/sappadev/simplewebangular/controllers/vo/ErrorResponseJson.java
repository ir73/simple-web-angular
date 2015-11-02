package com.sappadev.simplewebangular.controllers.vo;

/**
 * Error response that is sent by REST controllers to client in case of
 * an error
 */
public class ErrorResponseJson extends ResponseJson {
    /**
     * Error code that specifies the cause of an error
     */
    public final ErrorCode errorCode;

    public ErrorResponseJson(ErrorCode errorCode) {
        super(Response.ERROR);
        this.errorCode = errorCode;
    }
}
