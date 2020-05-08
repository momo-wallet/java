package com.mservice.pay.models;

import com.google.gson.JsonObject;

public class PayConfirmationResponse extends PayResponse {

    protected MoMoJson data;
    protected String message;

    public PayConfirmationResponse(Integer status, String signature, Long amount, JsonObject error, MoMoJson data, String message) {
        super(status, signature, amount, error);
        this.data = data;
        this.message = message;
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
