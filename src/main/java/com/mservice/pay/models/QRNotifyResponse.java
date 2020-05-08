package com.mservice.pay.models;


public class QRNotifyResponse extends PayResponse {

    protected String partnerRefId;
    protected String momoTransId;
    protected String message;

    public QRNotifyResponse(Integer status, String signature, Long amount, String partnerRefId, String momoTransId, String message) {
        super(status, signature, amount, null);
        this.partnerRefId = partnerRefId;
        this.momoTransId = momoTransId;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
