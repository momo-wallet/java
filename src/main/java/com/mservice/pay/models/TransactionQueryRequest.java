package com.mservice.pay.models;


public class TransactionQueryRequest extends PayRequest {
    private String hash;
    private String momoTransId;

    public TransactionQueryRequest(String partnerCode, String partnerRefId,double version, String hash, String momoTransId) {
        super(partnerCode, partnerRefId, "", "", version, null);
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
