package com.mservice.pay.models;

import lombok.Builder;

public class QRNotifyRequest extends PayResponse {
    //MoMo sends request to partner's server
    private String partnerRefId;
    private String momoTransId;
    private Long amount;
    private String partnerCode;
    private String accessKey;
    private String partnerTransId;
    private String transType;
    private Long responseTime;
    private String storeId;

    @Builder
    public QRNotifyRequest(Integer status, String message, String signature, String partnerRefId, String momoTransId,
                           Long amount, String partnerCode, String accessKey, String partnerTransId, String transType,
                           Long responseTime, String storeId) {
        super(status, message, signature);
        this.partnerRefId = partnerRefId;
        this.momoTransId = momoTransId;
        this.amount = amount;
        this.partnerCode = partnerCode;
        this.accessKey = accessKey;
        this.partnerTransId = partnerTransId;
        this.transType = transType;
        this.responseTime = responseTime;
        this.storeId = storeId;
    }

    public String getPartnerRefId() {
        return partnerRefId;
    }

    public void setPartnerRefId(String partnerRefId) {
        this.partnerRefId = partnerRefId;
    }

    public String getMomoTransId() {
        return momoTransId;
    }

    public void setMomoTransId(String momoTransId) {
        this.momoTransId = momoTransId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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

    public String getPartnerTransId() {
        return partnerTransId;
    }

    public void setPartnerTransId(String partnerTransId) {
        this.partnerTransId = partnerTransId;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
