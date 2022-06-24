package com.mservice.models;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hai.Nguyen Date: 19-01-2018
 */
public class QueryStatusTransactionResponse extends Response {

    String requestId;
    String metaData;
    List<RefundOfQueryStatusTransaction> refundTrans = new ArrayList<>();
    private Long amount;
    private String partnerUserId;
    private Long transId;
    private String extraData;
    private String payType;

    public QueryStatusTransactionResponse(String requestId, String metaData, List<RefundOfQueryStatusTransaction> refundTrans, Long amount, String partnerUserId, Long transId, String extraData, String payType) {
        this.requestId = requestId;
        this.metaData = metaData;
        this.refundTrans = refundTrans;
        this.amount = amount;
        this.partnerUserId = partnerUserId;
        this.transId = transId;
        this.extraData = extraData;
        this.payType = payType;
    }

    public void setTransId(Long transId) {
        this.transId = (transId != null) ? transId : 0;
    }

    public void setExtraData(String extraData) {
        this.extraData = (extraData != null) ? extraData : "";
    }

    public void setPayType(String payType) {
        this.payType = (payType != null) ? payType : "";
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public List<RefundOfQueryStatusTransaction> getRefundTrans() {
        return refundTrans;
    }

    public void setRefundTrans(List<RefundOfQueryStatusTransaction> refundTrans) {
        this.refundTrans = refundTrans;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getPartnerUserId() {
        return partnerUserId;
    }

    public void setPartnerUserId(String partnerUserId) {
        this.partnerUserId = partnerUserId;
    }

    public Long getTransId() {
        return transId;
    }

    public String getExtraData() {
        return extraData;
    }

    public String getPayType() {
        return payType;
    }
}
