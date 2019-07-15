package com.mservice.pay.models;

import lombok.Builder;

public class POSPayRequest extends PayRequest {
    protected String hash;

    @Builder
    public POSPayRequest(String partnerCode, String partnerRefId, String customerNumber, String description, double version, Integer payType, String hash) {
        super(partnerCode, partnerRefId, customerNumber, description, version, payType);
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

}
