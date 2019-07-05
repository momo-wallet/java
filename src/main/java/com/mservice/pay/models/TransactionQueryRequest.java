package com.mservice.pay.models;

import lombok.Builder;

public class TransactionQueryRequest extends PayRequest {
    private String hash;
    private Double version;
    private String momoTransId;

    @Builder
    public TransactionQueryRequest(String partnerCode, String partnerRefId, String customerNumber, String description, String hash, Double version, String momoTransId) {
        super(partnerCode, partnerRefId, customerNumber, description);
        this.hash = hash;
        this.version = version;
        this.momoTransId = momoTransId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public String getMomoTransId() {
        return momoTransId;
    }

    public void setMomoTransId(String momoTransId) {
        this.momoTransId = momoTransId;
    }
}
