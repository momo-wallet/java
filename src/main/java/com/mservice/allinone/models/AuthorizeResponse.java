package com.mservice.allinone.models;

import java.util.Date;

public class AuthorizeResponse extends PaymentResponse {

    private String hash;

    public AuthorizeResponse(String partnerCode, String orderId, String orderInfo, String accessKey, String amount, String signature, String extraData, String requestId, String requestType, int errorCode, String message, String localMessage, String transId, String orderType, String payType, Date responseDate, String payUrl, String deeplink, String deeplinkWebInApp, String qrCodeUrl, String hash) {
        super(partnerCode, orderId, orderInfo, accessKey, amount, signature, extraData, requestId, requestType, errorCode, message, localMessage, transId, orderType, payType, responseDate, payUrl, deeplink, deeplinkWebInApp, qrCodeUrl);
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
