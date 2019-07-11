package com.mservice.pay.models;

import lombok.Builder;

public class AppPayResponse extends PayResponse {
    private String transid;
    private Long amount;

    @Builder
    public AppPayResponse(Integer status, String message, String signature, String transId, Long amount) {
        super(status, message, signature);
        this.transid = transId;
        this.amount = amount;
    }

    public String getTransId() {
        return transid;
    }

    public void setTransId(String transId) {
        this.transid = transId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

}
