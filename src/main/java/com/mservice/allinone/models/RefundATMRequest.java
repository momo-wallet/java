package com.mservice.allinone.models;


public class RefundATMRequest extends PayGateRequest {

    private String transId;
    private String bankCode;

    public RefundATMRequest(String partnerCode, String orderId, String accessKey, String amount, String signature, String requestId, String requestType, String transId, String bankCode) {
        super(partnerCode, orderId, "", accessKey, amount, signature, "", requestId, "", "", requestType);
        this.transId = transId;
        this.bankCode = bankCode;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

}
