package com.mservice.models;

import com.mservice.enums.Language;

public class DeleteTokenRequest extends Request {
    private String partnerClientId;
    private String token;
    private String signature;

    public DeleteTokenRequest(String partnerClientId, String token, String signature) {
        this.partnerClientId = partnerClientId;
        this.token = token;
        this.signature = signature;
    }

    public DeleteTokenRequest(String partnerCode, String orderId, String requestId, Language lang, String partnerClientId, String token, String signature) {
        super(partnerCode, orderId, requestId, lang);
        this.partnerClientId = partnerClientId;
        this.token = token;
        this.signature = signature;
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
