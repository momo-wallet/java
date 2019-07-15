package com.mservice.pay.models;

import lombok.Builder;

public class TransactionRefundRequest extends PayRequest {
    private Long amount;
    private String storeId;
    private String requestId;
    private String hash;
    private String momoTransId;

    @Builder
    public TransactionRefundRequest(String partnerCode, String partnerRefId, String customerNumber, String description, double version, Integer payType, Long amount, String storeId, String requestId, String hash, String momoTransId) {
        super(partnerCode, partnerRefId, customerNumber, description, version, payType);
        this.amount = amount;
        this.storeId = storeId;
        this.requestId = requestId;
        this.hash = hash;
        this.momoTransId = momoTransId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
