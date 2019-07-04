package com.mservice.pay.models;

import lombok.Builder;

public class AppProcessRequest extends Request {

    private String appData;
    private String hash;
    private double version;
    private Integer payType;

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

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    @Builder
    public AppProcessRequest(String partnerCode, String partnerRefId, String customerNumber, String description, String appData, String hash, double version, Integer payType) {
        super(partnerCode, partnerRefId, customerNumber, description);
        this.appData = appData;
        this.hash = hash;
        this.version = version;
        this.payType = payType;
    }
}
