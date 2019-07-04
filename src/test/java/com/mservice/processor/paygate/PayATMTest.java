package com.mservice.processor.paygate;

import com.mservice.paygate.PayGate;
import com.mservice.paygate.processor.allinone.PayATM;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.paygate.models.PayATMRequest;
import com.mservice.paygate.models.PayATMResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PayATMTest {
    Environment env = Environment.selectEnv("dev");

    String amount = "50000";

    String orderInfo = "Pay With MoMo";
    String returnURL = "https://google.com.vn";
    String notifyURL = "https://google.com.vn";
    String extraData = "";
    String bankCode = "SML";

    @Test
    @DisplayName("PayATMProcess should NOT create new order")
    @Disabled
    void shouldNotCreatePayATM() throws Exception {
        assertDoesNotThrow(() -> {
            PayATM atmProcess = new PayATM(env);
            PayATMRequest payATMRequest = atmProcess.createPayWithATMRequest("1561046083186", "1561046083186", bankCode, amount, orderInfo, returnURL, notifyURL, extraData, env.getPartnerInfo());
            assertEquals("bab73e154828ec9fcdd1fb7aace617fc13f6c51f0b7ab365c21116e4342c5dfe", payATMRequest.getSignature(),
                    "Wrong Request Signature");

            assertThrows(
                    IllegalArgumentException.class,
                    () -> atmProcess.execute(payATMRequest)
            );


        });

    }

    @Test
    @DisplayName("PayATMProcess should SUCCESSFULLY create new order")
    void shouldCreateNewPayATM() throws Exception {
        String requestId = String.valueOf(System.currentTimeMillis());
        String orderId = String.valueOf(System.currentTimeMillis());

        assertDoesNotThrow(() -> {
            PayATM atmProcess = new PayATM(env);

            PayATMRequest payATMRequest = atmProcess.createPayWithATMRequest(requestId, orderId, bankCode, amount, orderInfo, returnURL, notifyURL, extraData, env.getPartnerInfo());

            PayATMResponse payATMResponse = atmProcess.execute(payATMRequest);
            assertEquals(0, payATMResponse.getErrorCode(), "Wrong Response from MoMo Server");
            assertNotNull(payATMResponse.getPayUrl(), "Wrong Response from MoMo Server");

        });

    }

}
