package com.mservice.allinone.models;

import com.google.gson.Gson;

import java.util.Date;

public class PayGateResponse extends MoMoPayment {

    private int errorCode;
    private String message;
    private String localMessage;
    private String transId;
    private String orderType;
    private String payType;
    private Date responseTime;

    public PayGateResponse(String partnerCode, String accessKey, String orderId, String orderInfo, String amount, String signature, String extraData, String requestId, String requestType, int errorCode, String message, String localMessage, String transId, String orderType, String payType, Date responseDate) {
        super(partnerCode, accessKey, orderId, requestId, amount, orderInfo, requestType, extraData, signature);
        this.errorCode = errorCode;
        this.message = message;
        this.localMessage = localMessage;
        this.transId = transId;
        this.orderType = orderType;
        this.payType = payType;
        this.responseTime = responseDate;
    }

    public PayGateResponse(String partnerCode, String accessKey, String orderId, String orderInfo, String amount, String signature, String extraData, String requestId, String requestType, int errorCode, String message, String localMessage, String transId) {
        super(partnerCode, accessKey, orderId, requestId, amount, orderInfo, requestType, extraData, signature);
        this.errorCode = errorCode;
        this.message = message;
        this.localMessage = localMessage;
        this.transId = transId;
    }

    public PayGateResponse() {
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocalMessage() {
        return localMessage;
    }

    public void setLocalMessage(String localMessage) {
        this.localMessage = localMessage;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Date getResponseDate() {
        return responseTime;
    }

    public void setResponseDate(Date responseDate) {
        this.responseTime = responseDate;
    }

    public String getJsonObject() {
        return new Gson().toJson(this);
    }
}
