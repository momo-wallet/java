package com.mservice.pay.models;


public class POSPayRequest extends PayRequest {
    protected String hash;

    public POSPayRequest(String partnerCode, String partnerRefId, String description, double version, Integer payType, String hash) {
        super(partnerCode, partnerRefId, "", description, version, payType);
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

}
