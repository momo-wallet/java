package com.mservice.pay.models;

import lombok.Builder;

public class TransactionQueryRequest extends PayRequest {
    private String hash;
    private String momoTransId;

    @Builder
    public TransactionQueryRequest(String partnerCode, String partnerRefId, String customerNumber, String description, double version, Integer payType, String hash, String momoTransId) {
        super(partnerCode, partnerRefId, customerNumber, description, version, payType);
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
}
