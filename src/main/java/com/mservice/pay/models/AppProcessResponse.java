package com.mservice.pay.models;

import lombok.Builder;

public class AppProcessResponse extends Response {
    private String transid;
    private Long amount;

    @Builder
    public AppProcessResponse(Integer status, String message, String signature, String transId, Long amount) {
        super(status, message, signature);
        this.transid = transId;
        this.amount = amount;
    }

    public void setTransId(String transId) {
        this.transid = transId;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getTransId() {
        return transid;
    }

    public Long getAmount() {
        return amount;
    }

}
