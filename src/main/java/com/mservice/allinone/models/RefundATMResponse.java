package com.mservice.allinone.models;

import java.util.Date;

public class RefundATMResponse extends RefundMoMoResponse {
    private String bankCode;

    public RefundATMResponse(String partnerCode, String orderId, String orderInfo, String accessKey, String amount, String signature, String extraData, String requestId, String requestType, int errorCode, String message, String localMessage, String transId, String orderType, String payType, Date responseDate, String bankCode) {
        super(partnerCode, orderId, orderInfo, accessKey, amount, signature, extraData, requestId, requestType, errorCode, message, localMessage, transId, orderType, payType, responseDate);
        this.bankCode = bankCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }


}
