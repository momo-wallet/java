package com.mservice.allinone.models;


public class RefundMoMoRequest extends PayGateRequest {
    private String transId;

    public RefundMoMoRequest(String partnerCode, String orderId, String accessKey, String amount, String signature, String requestId, String requestType, String transId) {
        super(partnerCode, orderId, "", accessKey, amount, signature, "", requestId, "", "", requestType);
        this.transId = transId;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }
}
