package com.mservice.pay.models;


public class TransactionRefundRequest extends PayRequest {
    private Long amount;
    private String storeId;
    private String requestId;
    private String hash;
    private String momoTransId;

    public TransactionRefundRequest(String partnerCode,  double version,String requestId, String hash) {
        super(partnerCode, "", "", "", version, null);
        this.requestId = requestId;
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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
