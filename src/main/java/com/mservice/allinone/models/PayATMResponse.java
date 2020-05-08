package com.mservice.allinone.models;

import java.util.Date;

public class PayATMResponse extends CaptureMoMoResponse {

    private String bankCode;

    public PayATMResponse(String partnerCode, String orderId, String orderInfo, String accessKey, String amount, String signature, String extraData, String requestId, String requestType, int errorCode, String message, String localMessage, String transId, String orderType, String payType, Date responseDate, String payUrl, String deeplink, String qrCodeUrl, String deeplinkWebInApp, String bankCode) {
        super(partnerCode, orderId, orderInfo, accessKey, amount, signature, extraData, requestId, requestType, errorCode, message, localMessage, transId, orderType, payType, responseDate, payUrl, deeplink, qrCodeUrl, deeplinkWebInApp);
        this.bankCode = bankCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
