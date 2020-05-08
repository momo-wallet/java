package com.mservice.allinone.models;


/**
 * Created by Hai.Nguyen Date: 19-01-2018
 */
public class QueryStatusTransactionRequest extends PayGateRequest {

    public QueryStatusTransactionRequest(String partnerCode, String orderId, String accessKey,  String signature, String requestId, String requestType) {
        super(partnerCode, orderId, "", accessKey, "", signature, "", requestId, "", "", requestType);
    }
}
