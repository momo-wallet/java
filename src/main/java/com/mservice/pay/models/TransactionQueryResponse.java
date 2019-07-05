package com.mservice.pay.models;

public class TransactionQueryResponse {

    private Integer status;
    private String message;
    private MoMoJson data;

    public TransactionQueryResponse(Integer status, String message, MoMoJson data) {
        this.status = status;
        this.message = message;
        this.data = data;
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

    public MoMoJson getData() {
        return data;
    }

    public void setData(MoMoJson data) {
        this.data = data;
    }

}
