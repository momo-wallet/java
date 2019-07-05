package com.mservice.pay.models;

public class PayRequest {

    private String partnerCode;
    private String partnerRefId;
    private String customerNumber;
    private String description;

    public PayRequest(String partnerCode, String partnerRefId, String customerNumber, String description) {
        this.partnerCode = partnerCode;
        this.partnerRefId = partnerRefId;
        this.customerNumber = customerNumber;
        this.description = description;
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
