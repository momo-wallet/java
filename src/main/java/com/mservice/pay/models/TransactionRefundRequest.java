package com.mservice.pay.models;

import lombok.Builder;

public class TransactionRefundRequest extends TransactionQueryRequest {
    @Builder(builderMethodName = "refundBuilder")
    public TransactionRefundRequest(String partnerCode, String partnerRefId, String customerNumber, String description, String hash, Double version, String momoTransId, Long amount, String storeId, String requestId) {
        super(partnerCode, partnerRefId, customerNumber, description, hash, version, momoTransId);
        this.amount = amount;
        this.storeId = storeId;
        this.requestId = requestId;
    }

    private Long amount;
    private String storeId;
    private String requestId;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
