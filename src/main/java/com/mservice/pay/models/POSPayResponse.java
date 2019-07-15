package com.mservice.pay.models;

import com.google.gson.JsonObject;
import lombok.Builder;

public class POSPayResponse extends PayResponse {
    protected MoMoJson message;

    @Builder
    public POSPayResponse(Integer status, String signature, Long amount, JsonObject error, MoMoJson message) {
        super(status, signature, amount, error);
        this.message = message;
    }

    public MoMoJson getMessage() {
        return message;
    }

    public void setMessage(MoMoJson message) {
        this.message = message;
    }
}
