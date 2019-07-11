package com.mservice.pay;

import com.mservice.shared.constants.RequestType;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.sharedmodels.PartnerInfo;

public class NonAIOPay {

    public static void main(String... args) throws Exception {

        PartnerInfo devInfo = new PartnerInfo("MOMOLRJZ20181206", "mTCKt9W3eU1m39TW", "KqBEecvaJf1nULnhPF5htpG3AMtDIOlD");
        //change the endpoint according to the appropriate processes
        Environment env = new Environment("https://test-payment.momo.vn/pay/confirm", devInfo, "development");

        String commit = RequestType.CONFIRM_APP_TRANSACTION;
        String rollback = RequestType.CANCEL_APP_TRANSACTION;
        long amount = 10000;
        String partnerRefId = String.valueOf(System.currentTimeMillis());
        String partnerTransId = "1561046083186";
        String requestId = String.valueOf(System.currentTimeMillis());
        String momoTransId = "147938695";
        String customerNumber = "0963181714";
        String partnerName = "1561046083186";
        String storeId = "1561046083186";
        String storeName = "1561046083186";
        String appData = "1561046083186";
        String description = "Pay POS Barcode";
        String paymentCode = "MM515023896957011876";

        String publicKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAlsL+G4UyFO0UQsQ4cAXuGYcn38d67PluKmeJqS2RcAqnNUFJjQieI5DSCyHVgAmPpUfDZ3CiSw+5NCfnjgChd/p4fq3bnGqSIw2JP78UROQDJYqfAc+WLvT29IgCH4O+P9+lOLUj2EWf8aqHxBwC1YPxtxK+8M+LKdVMAvZd3lXE3MBg9wTDYEcCNODXkNma/SfIKJCvmVWdKeKXd6IwW7yA0oTjdguAeqP8+O8jLjxJH57otRh63iX945vqAX2YAm9qzVoiDcWpv+UubRmbZ9l0moQwkdsDyCtCYPUtcW6kkdxuhlq8rg8RAVcinsz/843CBYHtqaUaAFQU1TO5EXiXT87zx/Oj2Bf4OC+iAJL/UQ4ASeL1vMoOfDSpSE8EnqKPyP+rM/H7oUaJrIin8KkrxmDLGQWKhNcTFO6UNPv3Hh13tEBv0GRy2vktL8+CWhrYHouXF2XwpS8uR/gH/Vl+5HT/HsTv/13gjSoGBQcdfyck9ZyHh5oBrQTds52C2vabCqWCEafRMbpj7lSrDWS2Df+XznR/hGkgewSdSZ/M0VK/DLadJ3x1Yhblv1HVw3jA3xzY1/zlNOZReLuvW6/kdRwJV/Zj5bd9eLJnz9jDPUcB0hAO+JuJYfTVuhZG9Beo1JbQ9+cFx+92ELn/yHDMod6rfrfBjikU9Gkxor0CAwEAAQ==";
        // Uncomment to use the processes you need
        // Remember to change the MoMoEndpoint and IDs accordingly
//        AppProcessResponse appProcessResponse = AppPay.process(env, partnerRefId, partnerTransId, amount, partnerName,
//                storeId, storeName, publicKey, customerNumber, appData, description, Parameter.VERSION, Parameter.APP_PAY_TYPE);
//        POSProcessResponse posProcessResponse = POSPay.process(env, partnerRefId, amount, "", "", publicKey, "", "MM943358184685515708", Parameter.VERSION, Parameter.APP_PAY_TYPE);
//
//        PayConfirmationResponse payConfirmationResponse = PayConfirmation.process(env, "1562299067267", rollback, requestId, "2305062978", "", "");
//        QRNotifyRequest qrNotifyRequest = new QRNotifyRequest(0, "", "", partnerRefId, momoTransId, amount, env.getPartnerInfo().getPartnerCode(), env.getPartnerInfo().getAccessKey(),
//                partnerTransId, RequestType.TRANS_TYPE_MOMO_WALLET, amount, storeId);
//        QRNotifyResponse qrNotifyResponse = QRNotification.process(env, qrNotifyRequest);
//
//        TransactionQueryResponse transactionQueryResponse = TransactionQuery.process(env, "1562298553079", "1562299067267", publicKey, "", Parameter.VERSION);
//        TransactionQuery.process(env, "1562299067267", "1562299177871", publicKey, "", Parameter.VERSION);
//
//        TransactionRefundResponse transactionRefundResponse = TransactionRefund.process(env, "1562298553079", "", publicKey, "2305062760", amount, "", "1562299067267", Parameter.VERSION);
//        TransactionRefund.process(env, "1562299067267", "", publicKey, "2305062978", amount, "", "1562299177871", Parameter.VERSION);
    }


}
