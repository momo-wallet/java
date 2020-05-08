package com.mservice.allinone.models;


public class PayATMRequest extends PayGateRequest {

    private String bankCode;

    public PayATMRequest(String partnerCode, String accessKey, String amount, String requestId, String orderId, String returnUrl, String notifyUrl, String orderInfo, String bankCode,String extraData, String requestType, String signature) {
        super(partnerCode, orderId, orderInfo, accessKey, amount, signature, extraData, requestId, notifyUrl, returnUrl, requestType);
        this.bankCode = bankCode;    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
