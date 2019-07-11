package com.mservice.processor.paygate;

import com.mservice.paygate.models.RefundATMRequest;
import com.mservice.paygate.models.RefundATMResponse;
import com.mservice.paygate.processor.allinone.RefundATM;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.sharedmodels.PartnerInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RefundATMTest {
    PartnerInfo devInfo = new PartnerInfo("MOMOLRJZ20181206", "mTCKt9W3eU1m39TW", "KqBEecvaJf1nULnhPF5htpG3AMtDIOlD");
    Environment env = new Environment("https://test-payment.momo.vn/gw_payment/transactionProcessor", devInfo, "development");

    RefundATM refundATM = new RefundATM(env);
    String requestId = "1560925663228";
    String orderId = "1560925663228";
    String amount = "5000";
    String transId = "2304677193";
    String bankCode = "SML";

    @Test
    void testShouldNotCreateNewRequest() throws Exception {
        RefundATMRequest request = refundATM.createRefundATMRequest(requestId, orderId, amount, transId, bankCode);
        assertEquals("d6938e069ed7c8f98aba7f4780c1714889ffe18a11d718dd910ba443a81bf65a", request.getSignature(), "Wrong Signature -- Problem with Hash");
        assertEquals("refundMoMoATM", request.getRequestType(), "Wrong Request Type -- Must be refundMoMoATM");

        RefundATMResponse response = refundATM.execute(request);
        assertNull(response, "Wrong Processing Result after executing RefundATMRequest");
    }
}