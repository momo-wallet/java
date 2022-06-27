package com.mservice.models;

import com.mservice.enums.Language;

public class CbTokenInquiryRequest extends Request {
    private String partnerClientId;
    private String signature;

    public CbTokenInquiryRequest(String partnerClientId, String signature) {
        this.partnerClientId = partnerClientId;
        this.signature = signature;
    }

    public CbTokenInquiryRequest(String partnerCode, String orderId, String requestId, Language lang, String partnerClientId, String signature) {
        super(partnerCode, orderId, requestId, lang);
        this.partnerClientId = partnerClientId;
        this.signature = signature;
    }

    public String getPartnerClientId() {
        return partnerClientId;
    }

    public void setPartnerClientId(String partnerClientId) {
        this.partnerClientId = partnerClientId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
