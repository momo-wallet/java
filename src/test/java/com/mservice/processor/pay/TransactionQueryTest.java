package com.mservice.processor.pay;

import com.mservice.pay.models.TransactionQueryResponse;
import com.mservice.pay.processor.notallinone.TransactionQuery;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.sharedmodels.PartnerInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TransactionQueryTest {

    PartnerInfo devInfo = new PartnerInfo("MOMOLRJZ20181206", "mTCKt9W3eU1m39TW", "KqBEecvaJf1nULnhPF5htpG3AMtDIOlD");
    Environment env = new Environment("https://test-payment.momo.vn/pay/query-status", devInfo, "development");

    String publicKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAlsL+G4UyFO0UQsQ4cAXuGYcn38d67PluKmeJqS2RcAqnNUFJjQieI5DSCyHVgAmPpUfDZ3CiSw+5NCfnjgChd/p4fq3bnGqSIw2JP78UROQDJYqfAc+WLvT29IgCH4O+P9+lOLUj2EWf8aqHxBwC1YPxtxK+8M+LKdVMAvZd3lXE3MBg9wTDYEcCNODXkNma/SfIKJCvmVWdKeKXd6IwW7yA0oTjdguAeqP8+O8jLjxJH57otRh63iX945vqAX2YAm9qzVoiDcWpv+UubRmbZ9l0moQwkdsDyCtCYPUtcW6kkdxuhlq8rg8RAVcinsz/843CBYHtqaUaAFQU1TO5EXiXT87zx/Oj2Bf4OC+iAJL/UQ4ASeL1vMoOfDSpSE8EnqKPyP+rM/H7oUaJrIin8KkrxmDLGQWKhNcTFO6UNPv3Hh13tEBv0GRy2vktL8+CWhrYHouXF2XwpS8uR/gH/Vl+5HT/HsTv/13gjSoGBQcdfyck9ZyHh5oBrQTds52C2vabCqWCEafRMbpj7lSrDWS2Df+XznR/hGkgewSdSZ/M0VK/DLadJ3x1Yhblv1HVw3jA3xzY1/zlNOZReLuvW6/kdRwJV/Zj5bd9eLJnz9jDPUcB0hAO+JuJYfTVuhZG9Beo1JbQ9+cFx+92ELn/yHDMod6rfrfBjikU9Gkxor0CAwEAAQ==";

    @Test
    void testSuccess() throws Exception {
        TransactionQueryResponse response = TransactionQuery.process(env, "1562298553079", "1562299067267", publicKey, "", Parameter.VERSION);
        assertEquals(0, response.getStatus(), "Wrong Response Body from MoMo Server");

        assertEquals("1562298553079", response.getData().getBillId(), "Wrong Response Body from MoMo Server");
        assertEquals(0, response.getData().getStatus(), "Wrong Response Body from MoMo Server");

    }

    @Test
    void testFail() throws Exception {
        TransactionQueryResponse response = TransactionQuery.process(env, "dfrbx", "1562299067267", publicKey, "", Parameter.VERSION);
        assertEquals(0, response.getStatus(), "Wrong Response Body from MoMo Server");

        assertNull(response.getData().getStatus(), "Wrong Response");
    }


}