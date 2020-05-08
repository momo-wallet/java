package com.mservice.pay.models;

public class MoMoJson {

    protected String partnerCode;
    protected String partnerRefId;
    protected String momoTransId;
    protected Long amount;
    protected String description;
    protected Long transid;
    protected String phoneNumber;
    protected Integer status;
    protected String message;
    protected String billId;
    protected Long discountAmount;
    protected Long fee;
    protected String customerName;
    protected String storeId;
    protected String requestDate;
    protected String responseDate;

    public MoMoJson(String partnerCode, String partnerRefId, String momoTransId, Long amount, String description, Long transid, String phoneNumber, Integer status, String message, String billId, Long transId, Long discountAmount, Long fee, String customerName, String storeId, String requestDate, String responseDate) {
        this.partnerCode = partnerCode;
        this.partnerRefId = partnerRefId;
        this.momoTransId = momoTransId;
        this.amount = amount;
        this.description = description;
        this.transid = transid;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.message = message;
        this.billId = billId;
        this.discountAmount = discountAmount;
        this.fee = fee;
        this.customerName = customerName;
        this.storeId = storeId;
        this.requestDate = requestDate;
        this.responseDate = responseDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTransid() {
        return transid;
    }

    public void setTransid(Long transid) {
        this.transid = transid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public Long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(String responseDate) {
        this.responseDate = responseDate;
    }
}
