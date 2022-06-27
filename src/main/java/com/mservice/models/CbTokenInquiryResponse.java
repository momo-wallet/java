package com.mservice.models;

public class CbTokenInquiryResponse extends Response {
    private String requestId;
    private String callbackToken;

    public CbTokenInquiryResponse(String requestId, String callbackToken) {
        this.requestId = requestId;
        this.callbackToken = callbackToken;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCallbackToken() {
        return callbackToken;
    }

    public void setCallbackToken(String callbackToken) {
        this.callbackToken = callbackToken;
    }
}
