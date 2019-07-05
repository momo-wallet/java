package com.mservice.paygate.models;

import java.util.Date;

public class PayATMResponse extends CaptureMoMoResponse {

    private String bankCode;

    public PayATMResponse(String partnerCode, String orderId, String orderInfo, String accessKey, String amount, String signature, String extraData, String requestId, String notifyUrl, String returnUrl, String requestType, int errorCode, String message, String localMessage, String transId, String orderType, String payType, Date responseDate, String payUrl, String deeplink, String qrCodeUrl, String deeplinkWebInApp, String bankCode) {
        super(partnerCode, orderId, orderInfo, accessKey, amount, signature, extraData, requestId, notifyUrl, returnUrl, requestType, errorCode, message, localMessage, transId, orderType, payType, responseDate, payUrl, deeplink, qrCodeUrl, deeplinkWebInApp);
        this.bankCode = bankCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
