package com.mservice.models;


import com.mservice.enums.Language;

public class RefundMoMoRequest extends Request {
    private Long amount;
    private Long transId;
    private String signature;
    private String description;

    public RefundMoMoRequest(Long amount, Long transId, String signature, String description) {
        this.amount = amount;
        this.transId = transId;
        this.signature = signature;
        this.description = description;
    }

    public RefundMoMoRequest(String partnerCode, String orderId, String requestId, Language lang, Long amount, Long transId, String signature, String description) {
        super(partnerCode, orderId, requestId, lang);
        this.amount = amount;
        this.transId = transId;
        this.signature = signature;
        this.description = description;
    }

    public Long getTransId() {
        return transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
