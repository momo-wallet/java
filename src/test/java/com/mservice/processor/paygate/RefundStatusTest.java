package com.mservice.processor.paygate;

import com.mservice.paygate.PayGate;
import com.mservice.paygate.models.RefundStatusRequest;
import com.mservice.paygate.processor.allinone.RefundStatus;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.paygate.models.RefundStatusResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RefundStatusTest {
    Environment env = Environment.selectEnv("dev");
    String requestId = "1560997093046";
    String orderId = "1560997093046";

    @Test
    @Disabled
    void refundStatusTest() throws Exception {
        assertDoesNotThrow(() -> {

            RefundStatus refundStatus = new RefundStatus(env);

            RefundStatusRequest request = refundStatus.createRefundStatusRequest(requestId, orderId);
            assertEquals("52e4beeb09bb9eaae8add532898e623b52e06d1317daa216289461d3a3f6d096", request.getSignature(),
                    "Wrong Signature from Refund Status Request");

            List<RefundStatusResponse> response = refundStatus.execute(request);
            assertEquals(5, response.size(), "Wrong retrieval of Refund Transactions -- Not enough transactions");
        });

    }
}