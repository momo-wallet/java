package com.mservice.pay.models;

import com.google.gson.JsonObject;

public class TransactionRefundResponse extends PayResponse {
    protected String partnerRefId;
    protected Long transid;
    protected String message;

    public TransactionRefundResponse(Integer status, String signature, Long amount, JsonObject error, String partnerRefId, Long transid, String message) {
        super(status, signature, amount, error);
        this.partnerRefId = partnerRefId;
        this.transid = transid;
        this.message = message;
    }

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
