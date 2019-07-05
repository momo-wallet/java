package com.mservice.paygate.models;

import lombok.Builder;

public class RefundStatusRequest extends PayGateRequest {
    @Builder
    public RefundStatusRequest(String partnerCode, String orderId, String orderInfo, String accessKey, String amount, String signature, String extraData, String requestId, String notifyUrl, String returnUrl, String requestType) {
        super(partnerCode, orderId, orderInfo, accessKey, amount, signature, extraData, requestId, notifyUrl, returnUrl, requestType);
    }
}
