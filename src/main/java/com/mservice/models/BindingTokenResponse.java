package com.mservice.models;

public class BindingTokenResponse extends Response{
    private String requestId;
    private String partnerClientId;
    private String aesToken;

    public BindingTokenResponse(String requestId, String partnerClientId, String aesToken) {
        this.requestId = requestId;
        this.partnerClientId = partnerClientId;
        this.aesToken = aesToken;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getPartnerClientId() {
        return partnerClientId;
    }

    public void setPartnerClientId(String partnerClientId) {
        this.partnerClientId = partnerClientId;
    }

    public String getAesToken() {
        return aesToken;
    }

    public void setAesToken(String aesToken) {
        this.aesToken = aesToken;
    }
}
