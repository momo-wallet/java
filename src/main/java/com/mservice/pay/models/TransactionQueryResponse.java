package com.mservice.pay.models;

import com.google.gson.JsonObject;

public class TransactionQueryResponse extends PayResponse {

    private String message;
    private MoMoJson data;

    public TransactionQueryResponse(Integer status, String signature, Long amount, JsonObject error, String message, MoMoJson data) {
        super(status, signature, amount, error);
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MoMoJson getData() {
        return data;
    }

    public void setData(MoMoJson data) {
        this.data = data;
    }
}
