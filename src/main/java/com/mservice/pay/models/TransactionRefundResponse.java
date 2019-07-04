package com.mservice.pay.models;

import lombok.Builder;

public class TransactionRefundResponse extends Response {
    @Builder(builderMethodName = "refundBuilder")
    public TransactionRefundResponse(Integer status, String message, String signature, String partnerRefId, Long transid, Long amount) {
        super(status, message, signature);
        this.partnerRefId = partnerRefId;
        this.transid = transid;
        this.amount = amount;
    }

    private String partnerRefId;
    private Long transid;
    private Long amount;

    public String getPartnerRefId() {
        return partnerRefId;
    }

    public void setPartnerRefId(String partnerRefId) {
        this.partnerRefId = partnerRefId;
    }

    public Long getTransid() {
        return transid;
    }

    public void setTransid(Long transid) {
        this.transid = transid;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
