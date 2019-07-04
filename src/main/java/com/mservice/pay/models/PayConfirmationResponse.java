package com.mservice.pay.models;

import lombok.Builder;

public class PayConfirmationResponse extends Response {

    private Json data;

    @Builder
    public PayConfirmationResponse(Integer status, String message, String signature, Json data) {
        super(status, message, signature);
        this.data = data;
    }

    public Json getData() {
        return data;
    }

    public void setData(Json data) {
        this.data = data;
    }

    public class Json {
        private String partnerCode;
        private String partnerRefId;
        private String momoTransId;
        private Long amount;

        public Json(String partnerCode, String partnerRefId, String momoTransId, Long amount) {
            this.partnerCode = partnerCode;
            this.partnerRefId = partnerRefId;
            this.momoTransId = momoTransId;
            this.amount = amount;
        }

        public String getParnterCode() {
            return partnerCode;
        }

        public void setParnterCode(String parnterCode) {
            this.partnerCode = parnterCode;
        }

        public String getPartnerRefId() {
            return partnerRefId;
        }

        public void setPartnerRefId(String partnerRefId) {
            this.partnerRefId = partnerRefId;
        }

        public String getMomoTransId() {
            return momoTransId;
        }

        public void setMomoTransId(String momoTransId) {
            this.momoTransId = momoTransId;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }
    }
}
