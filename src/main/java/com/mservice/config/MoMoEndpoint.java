package com.mservice.config;

public class MoMoEndpoint {
    private String endpoint;
    private String create;
    private String refund;
    private String query;
    private String tokenPay;
    private String tokenBind;
    private String tokenQueryCb;
    private String tokenDelete;

    public MoMoEndpoint(String endpoint, String create, String refund, String query, String tokenPay, String tokenBind, String tokenQueryCb, String tokenDelete) {
        this.endpoint = endpoint;
        this.create = create;
        this.refund = refund;
        this.query = query;
        this.tokenPay = tokenPay;
        this.tokenBind = tokenBind;
        this.tokenQueryCb = tokenQueryCb;
        this.tokenDelete = tokenDelete;
    }

    public String getCreateUrl() {
        return endpoint + create;
    }

    public String getRefundUrl() {
        return endpoint + refund;
    }

    public String getQueryUrl() {
        return endpoint + query;
    }

    public String getTokenPayUrl() {
        return endpoint + tokenPay;
    }

    public String getTokenBindUrl() {
        return endpoint + tokenBind;
    }

    public String getTokenQueryCb() {
        return tokenQueryCb;
    }

    public String getTokenDelete() {
        return tokenDelete;
    }
}
