package com.sappadev.simplewebangular.controllers.vo;

/**
 * Generic JSON that is sent from server to client and contains information
 * about if it was a successful or not.
 */
public class ResponseJson {
    /**
     * Specifies if this is a successful response or not
     */
    public final Response response;

    public ResponseJson(Response response) {
        this.response = response;
    }
}
