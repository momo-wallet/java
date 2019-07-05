package com.mservice.pay.models;

import lombok.Builder;

public class PayConfirmationRequest extends PayRequest {
    private String momoTransId;
    private String requestType;
    private String requestId;
    private String signature;

    @Builder
    public PayConfirmationRequest(String partnerCode, String partnerRefId, String customerNumber, String description, String momoTransId, String requestType, String requestId, String signature) {
        super(partnerCode, partnerRefId, customerNumber, description);
        this.momoTransId = momoTransId;
        this.requestType = requestType;
        this.requestId = requestId;
        this.signature = signature;
    }

    public String getMomoTransId() {
        return momoTransId;
    }

    public void setMomoTransId(String momoTransId) {
        this.momoTransId = momoTransId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
