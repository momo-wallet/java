package com.mservice.processor.paygate;

import com.mservice.paygate.PayGate;
import com.mservice.paygate.processor.allinone.QueryStatusTransaction;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.paygate.models.QueryStatusTransactionRequest;
import com.mservice.paygate.models.QueryStatusTransactionResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class QueryStatusTransactionTest {
    Environment env = Environment.selectEnv("dev");
    String orderId = "1560763203689";
    String requestId = "1560760777994";

    String exceptionMessage;

    @ParameterizedTest
    @CsvSource({
            "1560763203689,1560760777994,90dcfd397251f421abce889eaea5d2cec74d0ebc0a1ee71628387106a250de9f,2bd8afd69a16815bbf9103af6e2e2e6a8bf6c3c1d37483af8039334a3b53f631,-1",
            "1561047808782,1561047808782,908349181c8f315d3feeb9c00e2293740caaf8fb33d89864321d6480fd76f9e6,f5547a014813b2292117a63d4c3997b10d7e55d711d5c69210753d079815d9a0,37"
    })
        //add more test if needed
    void queryStatusTest(String orderId, String requestId, String reqSign, String resSign, String errorCode) throws Exception {
        QueryStatusTransaction queryStatusTransaction = new QueryStatusTransaction(env);

        QueryStatusTransactionRequest queryStatusRequest = queryStatusTransaction.createQueryRequest(orderId, requestId);
        assertEquals(reqSign, queryStatusRequest.getSignature(), "Wrong Query Transaction Request Signature");

        QueryStatusTransactionResponse queryStatusResponse = queryStatusTransaction.execute(queryStatusRequest);
        assertEquals(Integer.valueOf(errorCode), queryStatusResponse.getErrorCode(), "Wrong Response");
        assertEquals(resSign, queryStatusResponse.getSignature(), "Wrong Query Transaction Response Signature");

    }
}