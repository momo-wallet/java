package com.mservice.pay.models;


public class AppPayRequest extends PayRequest {

    protected String appData;
    protected String hash;

    public AppPayRequest(String partnerCode, String partnerRefId, String customerNumber, String description, double version, Integer payType, String appData, String hash) {
        super(partnerCode, partnerRefId, customerNumber, description, version, payType);
        this.appData = appData;
        this.hash = hash;
    }

    public String getAppData() {
        return appData;
    }

    public void setAppData(String appData) {
        this.appData = appData;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

}
