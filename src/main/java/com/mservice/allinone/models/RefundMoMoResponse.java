package com.mservice.allinone.models;

import java.util.Date;

public class RefundMoMoResponse extends PayGateResponse {
    public RefundMoMoResponse(String partnerCode, String orderId, String orderInfo, String accessKey, String amount, String signature, String extraData, String requestId, String notifyUrl, String returnUrl, String requestType, int errorCode, String message, String localMessage, String transId, String orderType, String payType, Date responseDate) {
        super(partnerCode, orderId, orderInfo, accessKey, amount, signature, extraData, requestId, notifyUrl, returnUrl, requestType, errorCode, message, localMessage, transId, orderType, payType, responseDate);
    }

}
