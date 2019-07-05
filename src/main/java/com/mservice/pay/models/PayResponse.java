package com.mservice.pay.models;

public class PayResponse {

    private Integer status;
    private String message;
    private String signature;

    public PayResponse(Integer status, String message, String signature) {
        this.status = status;
        this.message = message;
        this.signature = signature;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
