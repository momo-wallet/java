package com.mservice.models;

public class PaymentResponse extends Response {

    public PaymentResponse(Integer resultCode, String message) {
        this.resultCode = resultCode;
        this.message = message;
    }

    private String requestId;

    private Long amount;

    private String payUrl;

    private String shortLink;

    private String deeplink;

    private String qrCodeUrl;

    private String deeplinkWebInApp;

    private Long transId;

    private String applink;

    private String partnerClientId;

    private String bindingUrl;

    private String deeplinkMiniApp;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

    public String getDeeplink() {
        return deeplink;
    }

    public void setDeeplink(String deeplink) {
        this.deeplink = deeplink;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getDeeplinkWebInApp() {
        return deeplinkWebInApp;
    }

    public void setDeeplinkWebInApp(String deeplinkWebInApp) {
        this.deeplinkWebInApp = deeplinkWebInApp;
    }

    public Long getTransId() {
        return transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    public String getApplink() {
        return applink;
    }

    public void setApplink(String applink) {
        this.applink = applink;
    }

    public String getPartnerClientId() {
        return partnerClientId;
    }

    public void setPartnerClientId(String partnerClientId) {
        this.partnerClientId = partnerClientId;
    }

    public String getBindingUrl() {
        return bindingUrl;
    }

    public void setBindingUrl(String bindingUrl) {
        this.bindingUrl = bindingUrl;
    }

    public String getDeeplinkMiniApp() {
        return deeplinkMiniApp;
    }

    public void setDeeplinkMiniApp(String deeplinkMiniApp) {
        this.deeplinkMiniApp = deeplinkMiniApp;
    }
}
