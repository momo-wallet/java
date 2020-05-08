package com.mservice.allinone.models;


public class RefundStatusRequest extends PayGateRequest {
    public RefundStatusRequest(String partnerCode, String orderId, String accessKey, String signature, String requestId, String requestType) {
        super(partnerCode, orderId, "", accessKey, "", signature, "", requestId, "", "", requestType);
    }
}
