package com.mservice.pay.models;

public class TransactionQueryResponse {

    private Integer status;
    private String message;
    private Json data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Json getData() {
        return data;
    }

    public void setData(Json data) {
        this.data = data;
    }

    public TransactionQueryResponse(Integer status, String message, Json data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public class Json {
        private Integer status;
        private String message;
        private String partnerCode;
        private String billId;
        private Long transId;
        private Long amount;
        private Long discountAmount;
        private Long fee;
        private String phoneNumber;
        private String customerName;
        private String storeId;
        private String requestDate;
        private String responseDate;

        public Json(Integer status, String message, String partnerCode, String billId, Long transId, Long amount, Long discountAmount, Long fee, String phoneNumber, String customerName, String storeId, String requestDate, String responseDate) {
            this.status = status;
            this.message = message;
            this.partnerCode = partnerCode;
            this.billId = billId;
            this.transId = transId;
            this.amount = amount;
            this.discountAmount = discountAmount;
            this.fee = fee;
            this.phoneNumber = phoneNumber;
            this.customerName = customerName;
            this.storeId = storeId;
            this.requestDate = requestDate;
            this.responseDate = responseDate;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPartnerCode() {
            return partnerCode;
        }

        public void setPartnerCode(String partnerCode) {
            this.partnerCode = partnerCode;
        }

        public String getBillId() {
            return billId;
        }

        public void setBillId(String billId) {
            this.billId = billId;
        }

        public Long getTransId() {
            return transId;
        }

        public void setTransId(Long transId) {
            this.transId = transId;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }

        public Long getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(Long discountAmount) {
            this.discountAmount = discountAmount;
        }

        public Long getFee() {
            return fee;
        }

        public void setFee(Long fee) {
            this.fee = fee;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getRequestDate() {
            return requestDate;
        }

        public void setRequestDate(String requestDate) {
            this.requestDate = requestDate;
        }

        public String getResponseDate() {
            return responseDate;
        }

        public void setResponseDate(String responseDate) {
            this.responseDate = responseDate;
        }

    }
}
