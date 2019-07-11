package com.mservice.pay.models;

import lombok.Builder;

public class POSPayResponse {
    private Integer status;
    private MoMoJson message;

    @Builder
    public POSPayResponse(Integer status, MoMoJson message) {
        this.status = status;
        this.message = message;
    }

    public MoMoJson getMessage() {
        return message;
    }

    public void setMessage(MoMoJson message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
