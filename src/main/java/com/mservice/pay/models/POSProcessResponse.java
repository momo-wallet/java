package com.mservice.pay.models;

import lombok.Builder;

public class POSProcessResponse {
    private Integer status;
    private Json message;

    public Json getMessage() {
        return message;
    }

    public void setMessage(Json message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Builder
    public POSProcessResponse(Integer status, Json message) {
        this.status = status;
        this.message = message;
    }

    public class Json {
        String description;
        Long transid;
        Long amount;
        String phoneNumber;

        public Json(String description, Long transId, Long amount, String phoneNumber) {
            this.description = description;
            this.transid = transId;
            this.amount = amount;
            this.phoneNumber = phoneNumber;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Long getTransId() {
            return transid;
        }

        public void setTransId(Long transId) {
            this.transid = transId;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }

}
