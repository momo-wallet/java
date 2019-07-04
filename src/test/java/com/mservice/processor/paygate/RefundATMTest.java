package com.mservice.processor.paygate;

import com.mservice.paygate.PayGate;
import com.mservice.paygate.processor.allinone.RefundATM;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.paygate.models.RefundATMRequest;
import com.mservice.paygate.models.RefundATMResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefundATMTest {
    Environment env = Environment.selectEnv("dev");
    RefundATM refundATM = new RefundATM(env);
    String requestId = "1560925663228";
    String orderId = "1560925663228";
    String amount = "5000";
    String transId = "2304677193";
    String bankCode = "SML";

    @Test
    @Disabled
    void execute() throws Exception {
        RefundATMRequest request = refundATM.createRefundATMRequest(requestId, orderId, amount, transId, bankCode);
        assertDoesNotThrow(() -> {});

        RefundATMResponse response = refundATM.execute(request);
        assertDoesNotThrow(() -> {});
    }
}