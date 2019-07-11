package com.mservice.processor.paygate;

import com.mservice.allinone.PayGate;
import com.mservice.allinone.models.CaptureMoMoResponse;
import com.mservice.allinone.models.PayATMResponse;
import com.mservice.allinone.models.QueryStatusTransactionResponse;
import com.mservice.allinone.models.RefundStatusResponse;
import com.mservice.allinone.processor.allinone.CaptureMoMo;
import com.mservice.allinone.processor.allinone.PayATM;
import com.mservice.allinone.processor.allinone.QueryStatusTransaction;
import com.mservice.allinone.processor.allinone.RefundStatus;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.sharedmodels.PartnerInfo;
import com.mservice.shared.utils.Encoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PayGateTest {
    PartnerInfo devInfo = new PartnerInfo("MOMOLRJZ20181206", "mTCKt9W3eU1m39TW", "KqBEecvaJf1nULnhPF5htpG3AMtDIOlD");
    Environment environment = new Environment("https://test-payment.momo.vn/gw_payment/transactionProcessor", devInfo, "development");

    long amount = 30000;
    String requestId = "1560760777994";
    String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkpa+qMXS6O11x7jBGo9W3yxeHEsAdyDE" +
            "40UoXhoQf9K6attSIclTZMEGfq6gmJm2BogVJtPkjvri5/j9mBntA8qKMzzanSQaBEbr8FyByHnf226dsL" +
            "t1RbJSMLjCd3UC1n0Yq8KKvfHhvmvVbGcWfpgfo7iQTVmL0r1eQxzgnSq31EL1yYNMuaZjpHmQuT2" +
            "4Hmxl9W9enRtJyVTUhwKhtjOSOsR03sMnsckpFT9pn1/V9BE2Kf3rFGqc6JukXkqK6ZW9mtmGLSq3" +
            "K+JRRq2w8PVmcbcvTr/adW4EL2yc1qk9Ec4HtiDhtSYd6/ov8xLVkKAQjLVt7Ex3/agRPfPrNwIDAQAB";
    String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCSlr6oxdLo7XXHuMEaj1bfLF4cSwB3IMTjRSheGhB/0rpq21IhyVNkwQZ+rqCYmbYGiBUm0+SO+uLn+P2YGe0DyoozPNqdJBoERuvwXIHIed/bbp2wu3VFslIwuMJ3dQLWfRirwoq98eG+a9VsZxZ+mB+juJBNWYvSvV5DHOCdKrfUQvXJg0y5pmOkeZC5PbgebGX1b16dG0nJVNSHAqG2M5I6xHTewyexySkVP2mfX9X0ETYp/esUapzom6ReSorplb2a2YYtKrcr4lFGrbDw9WZxty9Ov9p1bgQvbJzWqT0Rzge2IOG1Jh3r+i/zEtWQoBCMtW3sTHf9qBE98+s3AgMBAAECggEAQxBiU9aFgnk5HFGDTwJrDRlASRNrOBUu3odCS6MDD2e6T67daYWw+HRy4zxDTu1r4JsbijMA6wUPEG/SnWanD8f26DAcGC5vFKvZv5Ki8bQIXVzDGhr5MRS/E3lDxuEqljSPN+1+Ch6CV9r/vmN/YBV6zC1hH3IrTRPD71Jj1KMITCDQlKcDbZqgFTY0wq2ONrzQ5lF0u1sSrdnHLny2kayIAocWqSVbfcSE/9iKN4jkc2/zBQOAFgBQVPuZOdLL+rf1PTKus75aJm/TzaCcoxF496kTw/mRJ77rOxB8mNDEhGULTopG0Bk12upA+QXzxsWJKm8pgv/iXV+0Hi27oQKBgQDCMAydxOCybtOnTkRQ66typlRJQDVgBCD4yhNchOd6jWk34GRY64MuNbyyrD8A5P/ioI4OvRs00S28Sb/G/w3ldciR0j7lm9FgbjkTDCrVVbp4P8gczgL+z5mPdCua1KQD+2C5RA2tMRJlAfczIVekoxCriuCQSO9RltsGT7LmEQKBgQDBP/bzTD+PKWmxeBOTLeNGH8IM63DeccWtowxRgeF1xohFK1ipi5RKxoKOVLxku0U3tKOe6thE2IhpaqYFcCRs2TFZidChyytEjD4LVlECfe9OvCqfVL8IvDUzw8B3850HYrGUh8y4Mmry3JJYLOKoAPBqEg9NLe9c8yI9rI3UxwKBgGVQjnSOMLHH8vPaePhDTUtfDqC9OFvlK5LCU8G0sdUWDKyTjad7ERE+BjqudZyw3fTO0e9MqPIwpQ0U6VMY5ZYvkrrKF/jSCDaoq2yNr5doyAZPOMgWkCeEBtl6wflhMkXFlNx0bjJLZQ6ALQpnPgPu9BacObft5bcK3zF2yZ8RAoGBAIgkYfuBKf3XdQh7yX6Ug1qxoOmtLHTpvhPXnCQH1ig811+za+D13mDXfL5838QvUlIuRl78n6PQ0DlD0vZdzKuKT4P+3SY+lZrTGhqukp+ozOCxG23oLDUhMnHnZD6dN3EujGBRU14o1sOFtOu9o2gsUTLIylLbG5hmCSdd2wWdAoGBAIvddYHkS1b8B8TCv1+CVObe5WCUvqpZgbHo3oztD0KxlgWvl+f6y8DUToK3KU9sp512Ivk43mn1Xv2QftBx8E4vyhWeltdiKOJOhMsk6djjoyb8AOuyPVumXTQBuue1yRrTKLAl1SaZnzdrKzpXsI8OBpnI0bjFxA2SNnU/iD0R";
    String partnerCode = "MOMOIQA420180417";
    String phoneNumber = "0963181714";
    String username = "nhat.nguyen";
    String bankCode = "SML";
    String orderId = "1560760777994";
    String orderInfo = "Pay With MoMo";
    String returnURL = "https://google.com.vn";
    String notifyURL = "https://google.com.vn";
    String extraData = "";

    @Test
    @DisplayName("Check RSA encoding and decoding")
    void encodeRSATest() throws Exception {
        // this is test, use your order_id
        String expectedStr = "{\"amount\":30000,\"partnerCode\":\"MOMOIQA420180417\",\"partnerTransId\":\"1560760777994\",\"customerNumber\":\"0963181714\",\"userName\":\"nhat.nguyen\",\"partnerRefId\":\"1560760777994\"}";

        String hashRSA = PayGate.generateRSA(phoneNumber, requestId, requestId, username, partnerCode, amount, publicKey);
        String decrypt = Encoder.decryptRSA(hashRSA, privateKey);
        assertEquals(expectedStr, decrypt, "Incorrect RSA Encryption/Decryption");
    }

    @Test
    @DisplayName("Capture MoMo Wallet Process")
    void captureProcess() throws Exception {

        CaptureMoMoResponse pg = CaptureMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, extraData);

        assertEquals("509bfc51cf9da67f3600eda14e93ee1663b7c518e3d2d7d383f346842d82f113",
                pg.getSignature(), "Incorrect Capture MoMo Process");
    }

    @Test
    @DisplayName("Query Transaction Status")
    void queryProcess() throws Exception {
        QueryStatusTransactionResponse queryResponse = QueryStatusTransaction.process(environment, orderId, requestId);
        assertEquals("d1ec709f123231071ab31ee05d98cc245876a0e9b2ffc342aa4501a26ad705b0",
                queryResponse.getSignature(), "Incorrect Query Signature");

    }


    @Test
    @DisplayName("Pay With ATM Process")
    void payATM() throws Exception {
        String requestId = String.valueOf(System.currentTimeMillis());
        String orderId = String.valueOf(System.currentTimeMillis());

        PayATMResponse response = PayATM.process(environment, requestId, orderId, bankCode, Long.toString(amount), orderInfo, returnURL, notifyURL, extraData);
        assertEquals(0, response.getErrorCode(), "Wrong ATM Payment Signature");
    }

    @Test
    @DisplayName("Refund Status Process")
    void refundStatus() throws Exception {

        List<RefundStatusResponse> response = RefundStatus.process(environment, "1560997093046", "1560997093046");
        assertEquals(5, response.size(), "Wrong retrieval of Refund Transactions -- Not enough transactions");

    }


}