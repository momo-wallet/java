package com.mservice.pay.models;

import com.google.gson.JsonObject;

public class PayResponse {

    protected Integer status;
    protected String signature;
    protected Long amount;
    protected JsonObject error;

    public PayResponse(Integer status, String signature, Long amount, JsonObject error) {
        this.status = status;
        this.signature = signature;
        this.amount = amount;
        this.error = error;
    }

    public JsonObject getError() {
        return error;
    }

    public void setError(JsonObject error) {
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
