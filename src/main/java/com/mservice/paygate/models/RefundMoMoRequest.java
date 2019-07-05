package com.mservice.paygate.models;

import lombok.Builder;

public class RefundMoMoRequest extends PayGateRequest {
    private String transId;

    @Builder
    public RefundMoMoRequest(String partnerCode, String orderId, String orderInfo, String accessKey, String amount, String signature, String extraData, String requestId, String notifyUrl, String returnUrl, String requestType, String transId) {
        super(partnerCode, orderId, orderInfo, accessKey, amount, signature, extraData, requestId, notifyUrl, returnUrl, requestType);
        this.transId = transId;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }
}
