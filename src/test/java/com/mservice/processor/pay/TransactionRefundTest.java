package com.mservice.processor.pay;

import com.mservice.pay.models.TransactionRefundResponse;
import com.mservice.pay.processor.notallinone.TransactionRefund;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.sharedmodels.PartnerInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionRefundTest {

    PartnerInfo devInfo = new PartnerInfo("MOMOLRJZ20181206", "mTCKt9W3eU1m39TW", "KqBEecvaJf1nULnhPF5htpG3AMtDIOlD");
    Environment env = new Environment("https://test-payment.momo.vn/pay/refund", devInfo, "development");

    String publicKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAlsL+G4UyFO0UQsQ4cAXuGYcn38d67PluKmeJqS2RcAqnNUFJjQieI5DSCyHVgAmPpUfDZ3CiSw+5NCfnjgChd/p4fq3bnGqSIw2JP78UROQDJYqfAc+WLvT29IgCH4O+P9+lOLUj2EWf8aqHxBwC1YPxtxK+8M+LKdVMAvZd3lXE3MBg9wTDYEcCNODXkNma/SfIKJCvmVWdKeKXd6IwW7yA0oTjdguAeqP8+O8jLjxJH57otRh63iX945vqAX2YAm9qzVoiDcWpv+UubRmbZ9l0moQwkdsDyCtCYPUtcW6kkdxuhlq8rg8RAVcinsz/843CBYHtqaUaAFQU1TO5EXiXT87zx/Oj2Bf4OC+iAJL/UQ4ASeL1vMoOfDSpSE8EnqKPyP+rM/H7oUaJrIin8KkrxmDLGQWKhNcTFO6UNPv3Hh13tEBv0GRy2vktL8+CWhrYHouXF2XwpS8uR/gH/Vl+5HT/HsTv/13gjSoGBQcdfyck9ZyHh5oBrQTds52C2vabCqWCEafRMbpj7lSrDWS2Df+XznR/hGkgewSdSZ/M0VK/DLadJ3x1Yhblv1HVw3jA3xzY1/zlNOZReLuvW6/kdRwJV/Zj5bd9eLJnz9jDPUcB0hAO+JuJYfTVuhZG9Beo1JbQ9+cFx+92ELn/yHDMod6rfrfBjikU9Gkxor0CAwEAAQ==";
    long amount = 10000;

    @Test
    void testRepeatOrder() throws Exception {
        TransactionRefundResponse transactionRefundResponse = TransactionRefund.process(env, "1562298553079", "", publicKey, "2305062760", amount, "", "1562299067267", Parameter.VERSION);

        assertEquals(2128, transactionRefundResponse.getStatus(), "Should have problem with the requestId");
        assertEquals("1562298553079", transactionRefundResponse.getPartnerRefId(), "Wrong partnerRefId");
    }

    @Test
    void testShouldFail() throws Exception {
        String requestId = String.valueOf(System.currentTimeMillis());
        TransactionRefundResponse transactionRefundResponse = TransactionRefund.process(env, "1562298553079", "", publicKey, "2305062760", amount, "", requestId, Parameter.VERSION);

        assertEquals(2125, transactionRefundResponse.getStatus(), "Should Fail");
        assertEquals("1562298553079", transactionRefundResponse.getPartnerRefId(), "Wrong partnerRefId");
    }


}