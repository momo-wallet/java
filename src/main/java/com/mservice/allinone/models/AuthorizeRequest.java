package com.mservice.allinone.models;

import com.mservice.shared.sharedmodels.PartnerClientInfo;

public class AuthorizeRequest extends PayGateRequest {
    private PartnerClientInfo partnerClientInfo;

    public AuthorizeRequest(String partnerCode, String orderId, String orderInfo, String accessKey, String amount, String signature, String extraData, String requestId, String notifyUrl, String returnUrl, String requestType, PartnerClientInfo partnerClientInfo) {
        super(partnerCode, orderId, orderInfo, accessKey, amount, signature, extraData, requestId, notifyUrl, returnUrl, requestType);
        this.partnerClientInfo = partnerClientInfo;
    }

    public PartnerClientInfo getPartnerClientInfo() {
        return partnerClientInfo;
    }

    public void setPartnerClientInfo(PartnerClientInfo partnerClientInfo) {
        this.partnerClientInfo = partnerClientInfo;
    }
}
