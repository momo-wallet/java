package com.mservice.models;

import com.mservice.enums.Language;
import com.mservice.enums.RequestType;

public class PaymentRequest extends Request {
    private String orderInfo;

    private long amount;
    private String partnerName;
    private String subPartnerCode;
    private RequestType requestType;
    private String redirectUrl;
    private String ipnUrl;
    private String storeId;
    private String extraData;
    private String partnerClientId;
    private Boolean autoCapture = true;
    private Long orderGroupId;
    private String signature;

    public PaymentRequest(String partnerCode, String orderId, String requestId, Language lang, String orderInfo, long amount, String partnerName, String subPartnerCode, RequestType requestType, String redirectUrl, String ipnUrl, String storeId, String extraData, String partnerClientId, Boolean autoCapture, Long orderGroupId, String signature) {
        super(partnerCode, orderId, requestId, lang);
        this.orderInfo = orderInfo;
        this.amount = amount;
        this.partnerName = partnerName;
        this.subPartnerCode = subPartnerCode;
        this.requestType = requestType;
        this.redirectUrl = redirectUrl;
        this.ipnUrl = ipnUrl;
        this.storeId = storeId;
        this.extraData = extraData;
        this.partnerClientId = partnerClientId;
        this.autoCapture = autoCapture;
        this.orderGroupId = orderGroupId;
        this.signature = signature;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getSubPartnerCode() {
        return subPartnerCode;
    }

    public void setSubPartnerCode(String subPartnerCode) {
        this.subPartnerCode = subPartnerCode;
    }

    public String getRequestType() {
        return requestType.getRequestType();
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public String getPartnerClientId() {
        return partnerClientId;
    }

    public void setPartnerClientId(String partnerClientId) {
        this.partnerClientId = partnerClientId;
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
