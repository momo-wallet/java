package com.mservice.models;

public class HttpRequest {
    private String method;
    private String endpoint;
    private String payload;
    private String contentType;

    public HttpRequest(String method, String endpoint, String payload, String contentType) {
        this.method = method;
        this.endpoint = endpoint;
        this.payload = payload;
        this.contentType = contentType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
