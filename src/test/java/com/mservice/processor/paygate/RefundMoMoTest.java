package com.mservice.processor.paygate;

import com.mservice.paygate.PayGate;
import com.mservice.paygate.processor.allinone.RefundMoMo;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.paygate.models.RefundMoMoRequest;
import com.mservice.paygate.models.RefundMoMoResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefundMoMoTest {
    Environment env = Environment.selectEnv("dev");
    RefundMoMo refundMoMo = new RefundMoMo(env);

    String exceptionMessage;

    @Test
    void createRefundRequest() throws Exception {
        RefundMoMoRequest request = refundMoMo.createRefundRequest("1527314064527", "1527297954700", "55000", "144492817");
        assertEquals("a8794aded9704a61d115587a6a244d75999dd26f679da4844eb25616b6df2124", request.getSignature(), "Wrong Signature");
    }

    @Test
    @Disabled
    void execute() throws Exception {
        //need actual transId
        String transId = "144492817";
        String orderId = String.valueOf(System.currentTimeMillis());

        RefundMoMoRequest request = refundMoMo.createRefundRequest("1527314064527", orderId, "7500", transId);

        RefundMoMoResponse response = refundMoMo.execute(request);
        assertEquals(58, response.getErrorCode(),
                "Wrong Error Code from Refund MoMo Response");
        //assertDoesNotThrow(() -> { } );

    }


}