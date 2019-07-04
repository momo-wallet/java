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

public class TransactionQuery extends AbstractProcess<TransactionQueryRequest, TransactionQueryResponse> {

    public TransactionQuery(Environment environment) {
        super(environment);
    }

    @Override
    public TransactionQueryResponse execute(TransactionQueryRequest request) throws MoMoException {
        try {
            String payload = getGson().toJson(request, TransactionQueryRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), Parameter.PAY_STATUS_URI, payload);

            TransactionQueryResponse transactionQueryResponse = getGson().fromJson(response.getData(), TransactionQueryResponse.class);

            if (transactionQueryResponse.getStatus() != 0) {
                Console.error("getTransactionQueryRequest::errorCode::", transactionQueryResponse.getStatus() + "");
                Console.error("getTransactionQueryRequest::errorMessage::", transactionQueryResponse.getMessage());

            } else {
                TransactionQueryResponse.Json data = transactionQueryResponse.getData();

                Console.debug("getTransactionQueryRequest::billId::", data.getBillId());
                Console.debug("getTransactionQueryRequest::amount::", data.getAmount().toString());
                Console.debug("getTransactionQueryRequest::Phone Number::", data.getPhoneNumber());
            }
            return transactionQueryResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public TransactionQueryRequest createTransactionQueryRequest(String partnerRefId, String requestId, String publicKey, String momoTransId, double version) {

        try {

            Map<String, Object> rawData = new HashMap<>();
            rawData.put(Parameter.PARTNER_REF_ID, partnerRefId);
            rawData.put(Parameter.PARTNER_CODE, partnerInfo.getPartnerCode());
            rawData.put(Parameter.REQUEST_ID, requestId);
            rawData.put(Parameter.MOMO_TRANS_ID, momoTransId);

            Gson gson = new Gson();
            String jsonStr = gson.toJson(rawData);
            byte[] testByte = jsonStr.getBytes(StandardCharsets.UTF_8);
            String hashRSA = Encoder.encryptRSA(testByte, publicKey);

            Console.debug("createTransactionQueryRequest::rawDataBeforeHash::" + jsonStr);
            Console.debug("createTransactionQueryRequest::hash::" + hashRSA);

            return TransactionQueryRequest
                    .builder()
                    .partnerCode(partnerInfo.getPartnerCode())
                    .partnerRefId(partnerRefId)
                    .hash(hashRSA)
                    .version(version)
                    .momoTransId(momoTransId)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static TransactionQueryResponse process(Environment env, String partnerRefId, String requestId, String publicKey, String momoTransId, double version) throws Exception {
        Console.log("========================== START TRANSACTION QUERY STATUS ==================");

        TransactionQuery transactionQuery = new TransactionQuery(env);
        TransactionQueryRequest transactionQueryRequest = transactionQuery.createTransactionQueryRequest(partnerRefId, requestId, publicKey, momoTransId, version);
        TransactionQueryResponse transactionQueryResponse = transactionQuery.execute(transactionQueryRequest);

        // Your handler

        Console.log("========================== END TRANSACTION QUERY STATUS ==================");

        return transactionQueryResponse;
    }


}
