package com.mservice.pay.models;


public class PayConfirmationRequest extends PayRequest {
    protected String momoTransId;
    protected String requestType;
    protected String requestId;
    protected String signature;

    public PayConfirmationRequest(String partnerCode, String partnerRefId, String customerNumber, String description, String momoTransId, String requestType, String requestId, String signature) {
        super(partnerCode, partnerRefId, customerNumber, description, 0, null);
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

