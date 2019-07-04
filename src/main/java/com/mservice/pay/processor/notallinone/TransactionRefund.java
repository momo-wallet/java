package com.mservice.pay.processor.notallinone;

import com.google.gson.Gson;
import com.mservice.pay.models.*;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.sharedmodels.AbstractProcess;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.utils.Console;
import com.mservice.shared.utils.Encoder;
import com.mservice.shared.utils.HttpResponse;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TransactionRefund extends AbstractProcess<TransactionRefundRequest, TransactionRefundResponse> {
    public TransactionRefund(Environment environment) {
        super(environment);
    }

    @Override
    public TransactionRefundResponse execute(TransactionRefundRequest request) throws MoMoException {
        try {
            String payload = getGson().toJson(request, TransactionRefundRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), Parameter.PAY_REFUND_URI, payload);

            TransactionRefundResponse transactionRefundResponse = getGson().fromJson(response.getData(), TransactionRefundResponse.class);

            if (transactionRefundResponse.getStatus() != 0) {
                Console.error("getTransactionRefundRequest::errorCode::", transactionRefundResponse.getStatus() + "");
                Console.error("getTransactionRefundRequest::errorMessage::", transactionRefundResponse.getMessage());

            } else {
                Console.debug("getTransactionRefundRequest::transid::", transactionRefundResponse.getTransid().toString());
                Console.debug("getTransactionRefundRequest::amount::", transactionRefundResponse.getAmount().toString());
                Console.debug("getTransactionRefundRequest::partnerRefId::", transactionRefundResponse.getPartnerRefId());
            }

            return transactionRefundResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public TransactionRefundRequest createTransactionRefundRequest(String partnerRefId, String storeId, String publicKey, String momoTransId,
                                                                   Long amount, String description, String requestId, double version) {

        try {

            Map<String, Object> rawData = new HashMap<>();
            rawData.put(Parameter.PARTNER_REF_ID, partnerRefId);
            rawData.put(Parameter.PARTNER_CODE, partnerInfo.getPartnerCode());
            rawData.put(Parameter.AMOUNT, amount);
            rawData.put(Parameter.MOMO_TRANS_ID, momoTransId);
            rawData.put(Parameter.STORE_ID, storeId);
            rawData.put(Parameter.DESCRIPTION, description);

            Gson gson = new Gson();
            String jsonStr = gson.toJson(rawData);
            byte[] testByte = jsonStr.getBytes(StandardCharsets.UTF_8);
            String hashRSA = Encoder.encryptRSA(testByte, publicKey);

            Console.debug("createTransactionRefundRequest::rawDataBeforeHash::" + jsonStr);
            Console.debug("createTransactionRefundRequest::hash::" + hashRSA);

            return TransactionRefundRequest
                    .refundBuilder()
                    .partnerCode(partnerInfo.getPartnerCode())
                    .requestId(requestId)
                    .hash(hashRSA)
                    .version(version)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static TransactionRefundResponse process(Environment env, String partnerRefId, String storeId, String publicKey, String momoTransId,
                                                    Long amount, String description, String requestId, double version) throws Exception {
        Console.log("========================== START TRANSACTION REFUND PROCESS  ==================");

        TransactionRefund transactionRefund = new TransactionRefund(env);
        TransactionRefundRequest transactionRefundRequest = transactionRefund.createTransactionRefundRequest(partnerRefId, storeId, publicKey, momoTransId, amount, description, requestId, version);
        TransactionRefundResponse transactionRefundResponse = transactionRefund.execute(transactionRefundRequest);

        // Your handler

        Console.log("========================== END TRANSACTION REFUND PROCESS ==================");

        return transactionRefundResponse;
    }

}
