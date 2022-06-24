package com.mservice.models;

public class RefundMoMoResponse extends Response {
    private String requestId;
    private Long amount;
    private Long transId;

    public RefundMoMoResponse(String requestId, Long amount, Long transId) {
        this.requestId = requestId;
        this.amount = amount;
        this.transId = transId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getTransId() {
        return transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }
}
