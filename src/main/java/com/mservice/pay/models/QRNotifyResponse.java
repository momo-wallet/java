package com.mservice.pay.models;

import lombok.Builder;

public class QRNotifyResponse extends PayResponse {

    private String partnerRefId;
    private String momoTransId;
    private Long amount;

    @Builder
    public QRNotifyResponse(Integer status, String message, String signature, String partnerRefId, String momoTransId, Long amount) {
        super(status, message, signature);
        this.partnerRefId = partnerRefId;
        this.momoTransId = momoTransId;
        this.amount = amount;
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
}
