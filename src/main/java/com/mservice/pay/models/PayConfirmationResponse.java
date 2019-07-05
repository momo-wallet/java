package com.mservice.pay.models;

import lombok.Builder;

public class PayConfirmationResponse extends PayResponse {

    private MoMoJson data;

    @Builder
    public PayConfirmationResponse(Integer status, String message, String signature, MoMoJson data) {
        super(status, message, signature);
        this.data = data;
    }

    public MoMoJson getData() {
        return data;
    }

    public void setData(MoMoJson data) {
        this.data = data;
    }

}
