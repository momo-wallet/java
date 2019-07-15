package com.mservice.pay.models;

public class PayRequest {

    protected String partnerCode;
    protected String partnerRefId;
    protected String customerNumber;
    protected String description;
    protected double version;
    protected Integer payType;

    public PayRequest(String partnerCode, String partnerRefId, String customerNumber, String description, double version, Integer payType) {
        this.partnerCode = partnerCode;
        this.partnerRefId = partnerRefId;
        this.customerNumber = customerNumber;
        this.description = description;
        this.version = version;
        this.payType = payType;
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

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPartnerRefId() {
        return partnerRefId;
    }

    public void setPartnerRefId(String partnerRefId) {
        this.partnerRefId = partnerRefId;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}