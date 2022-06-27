package com.mservice.models;

import com.mservice.enums.Language;

public class BindingTokenRequest extends Request {
    private String partnerClientId;
    private String callbackToken;
    private String signature;

    public BindingTokenRequest(String partnerClientId, String callbackToken, String signature) {
        this.partnerClientId = partnerClientId;
        this.callbackToken = callbackToken;
        this.signature = signature;
    }

    public BindingTokenRequest(String partnerCode, String orderId, String requestId, Language lang, String partnerClientId, String callbackToken, String signature) {
        super(partnerCode, orderId, requestId, lang);
        this.partnerClientId = partnerClientId;
        this.callbackToken = callbackToken;
        this.signature = signature;
    }

    public String getPartnerClientId() {
        return partnerClientId;
    }

    public void setPartnerClientId(String partnerClientId) {
        this.partnerClientId = partnerClientId;
    }

    public String getCallbackToken() {
        return callbackToken;
    }

    public void setCallbackToken(String callbackToken) {
        this.callbackToken = callbackToken;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
