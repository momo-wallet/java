package com.mservice.paygate.models;

import lombok.Builder;

/**
 * Created by Hai.Nguyen Date: 19-01-2018
 */
public class QueryStatusTransactionRequest extends PayGateRequest {

    @Builder
    public QueryStatusTransactionRequest(String partnerCode, String orderId, String orderInfo, String accessKey, String amount, String signature, String extraData, String requestId, String notifyUrl, String returnUrl, String requestType) {
        super(partnerCode, orderId, orderInfo, accessKey, amount, signature, extraData, requestId, notifyUrl, returnUrl, requestType);
    }
}
