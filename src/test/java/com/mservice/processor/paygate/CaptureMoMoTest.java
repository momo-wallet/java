package com.mservice.processor.paygate;

import com.mservice.paygate.PayGate;
import com.mservice.paygate.models.CaptureMoMoRequest;
import com.mservice.paygate.models.CaptureMoMoResponse;
import com.mservice.paygate.processor.allinone.CaptureMoMo;
import com.mservice.shared.sharedmodels.Environment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CaptureMoMoTest {

    Environment environment = Environment.selectEnv("dev");
    String amount = "30000";
    String orderInfo = "Pay With MoMo";
    String returnURL = "https://google.com.vn";
    String notifyURL = "https://google.com.vn";
    String extraData = "";

    CaptureMoMo m2Processor = new CaptureMoMo(environment);

    @Test
    @DisplayName("CaptureMoMoWallet should NOT create new order")
    void shouldNotCreateNewOrder() throws Exception {
        CaptureMoMoRequest req = m2Processor.createPaymentCreationRequest("1560763203689", "1560760777994", amount, orderInfo, returnURL, notifyURL, extraData);
        assertEquals("7e8ceb252f950056494b75d348b0b52effcb360916fb0f35fa8b792e33cdc9b3",
                req.getSignature(), "Incorrect Signature from MoMo Request");

        CaptureMoMoResponse response = m2Processor.execute(req);
        assertEquals("237072c6d1a9a41c53cdd58940a3f4f8a1c4e44dcf47b28a4e47f5bf440400cd",
                response.getSignature(), "Incorrect Signature from MoMo Response");

        assertEquals(6, response.getErrorCode(),
                "Wrong Error Code -- Incorrect Response Data");

    }

    @Test
    @DisplayName("CaptureMoMoWallet should SUCCESSFULLY create new order")
    void shouldCreateNewCapture() throws Exception {
        String requestId = String.valueOf(System.currentTimeMillis());
        String orderId = String.valueOf(System.currentTimeMillis());

        assertDoesNotThrow(() -> {
            CaptureMoMoRequest req = m2Processor.createPaymentCreationRequest(orderId, requestId, amount, orderInfo, returnURL, notifyURL, extraData);

            CaptureMoMoResponse response = m2Processor.execute(req);
            assertEquals(0, response.getErrorCode(), "Wrong Response from MoMo Server");
            assertNotNull(response.getPayUrl(), "Wrong Response from MoMo Server");

        });

    }



}