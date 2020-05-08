package com.mservice.allinone.models;

import java.util.Date;

public class PaymentResponse extends PayGateResponse {

    String payUrl;
    String deeplink;
    String deeplinkWebInApp;
    String qrCodeUrl;

    public PaymentResponse(String partnerCode, String orderId, String orderInfo, String accessKey, String amount, String signature, String extraData, String requestId, String requestType, int errorCode, String message, String localMessage, String transId, String orderType, String payType, Date responseDate, String payUrl, String deeplink, String deeplinkWebInApp, String qrCodeUrl) {
        super(partnerCode, orderId, orderInfo, accessKey, amount, signature, extraData, requestId, requestType, errorCode, message, localMessage, transId, orderType, payType, responseDate);
        this.payUrl = payUrl;
        this.deeplink = deeplink;
        this.deeplinkWebInApp = deeplinkWebInApp;
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getDeeplink() {
        return deeplink;
    }

    public void setDeeplink(String deeplink) {
        this.deeplink = deeplink;
    }

    public String getDeeplinkWebInApp() {
        return deeplinkWebInApp;
    }

    public void setDeeplinkWebInApp(String deeplinkWebInApp) {
        this.deeplinkWebInApp = deeplinkWebInApp;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }
}
