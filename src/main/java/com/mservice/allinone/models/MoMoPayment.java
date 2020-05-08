package com.mservice.allinone.models;

public class MoMoPayment {
    private String partnerCode;
    private String accessKey;
    private String orderId;
    private String requestId;
    private String amount;
    private String orderInfo;
    private String requestType;
    private String extraData;
    private String signature;

    public MoMoPayment() {
    }

    public MoMoPayment(String partnerCode, String accessKey, String orderId, String requestId, String amount, String orderInfo, String requestType, String extraData, String signature) {
        this.partnerCode = partnerCode;
        this.accessKey = accessKey;
        this.orderId = orderId;
        this.requestId = requestId;
        this.amount = amount;
        this.orderInfo = orderInfo;
        this.requestType = requestType;
        this.extraData = extraData;
        this.signature = signature;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
