package com.mservice.models;

import com.mservice.enums.Language;

public class PaymentTokenRequest extends Request {
    private String partnerName;
    private String partnerClientId;
    private String token;
    private long amount;
    private String storeId;
    private String redirectUrl;
    private String ipnUrl;
    private String orderInfo;
    private String extraData;
    private Boolean autoCapture;
    private Long orderGroupId;
    private String signature;

    public PaymentTokenRequest(String partnerName, String partnerClientId, String token, long amount, String storeId, String redirectUrl, String ipnUrl, String orderInfo, String extraData, Boolean autoCapture, Long orderGroupId, String signature) {
        this.partnerName = partnerName;
        this.partnerClientId = partnerClientId;
        this.token = token;
        this.amount = amount;
        this.storeId = storeId;
        this.redirectUrl = redirectUrl;
        this.ipnUrl = ipnUrl;
        this.orderInfo = orderInfo;
        this.extraData = extraData;
        this.autoCapture = autoCapture;
        this.orderGroupId = orderGroupId;
        this.signature = signature;
    }

    public PaymentTokenRequest(String partnerCode, String orderId, String requestId, Language lang, String partnerName, String partnerClientId, String token, long amount, String storeId, String redirectUrl, String ipnUrl, String orderInfo, String extraData, Boolean autoCapture, Long orderGroupId, String signature) {
        super(partnerCode, orderId, requestId, lang);
        this.partnerName = partnerName;
        this.partnerClientId = partnerClientId;
        this.token = token;
        this.amount = amount;
        this.storeId = storeId;
        this.redirectUrl = redirectUrl;
        this.ipnUrl = ipnUrl;
        this.orderInfo = orderInfo;
        this.extraData = extraData;
        this.autoCapture = autoCapture;
        this.orderGroupId = orderGroupId;
        this.signature = signature;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerClientId() {
        return partnerClientId;
    }

    public void setPartnerClientId(String partnerClientId) {
        this.partnerClientId = partnerClientId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getIpnUrl() {
        return ipnUrl;
    }

    public void setIpnUrl(String ipnUrl) {
        this.ipnUrl = ipnUrl;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public Boolean getAutoCapture() {
        return autoCapture;
    }

    public void setAutoCapture(Boolean autoCapture) {
        this.autoCapture = autoCapture;
    }

    public Long getOrderGroupId() {
        return orderGroupId;
    }

    public void setOrderGroupId(Long orderGroupId) {
        this.orderGroupId = orderGroupId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
