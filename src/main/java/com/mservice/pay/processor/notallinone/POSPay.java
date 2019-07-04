package com.mservice.pay.processor.notallinone;

import com.google.gson.Gson;
import com.mservice.pay.models.POSProcessRequest;
import com.mservice.pay.models.POSProcessResponse;
import com.mservice.shared.sharedmodels.AbstractProcess;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.utils.Console;
import com.mservice.shared.utils.Encoder;
import com.mservice.shared.utils.HttpResponse;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class POSPay extends AbstractProcess<POSProcessRequest, POSProcessResponse> {
    public POSPay(Environment environment) {
        super(environment);
    }

    public POSProcessResponse execute(POSProcessRequest request) throws MoMoException {
        try {
            String payload = getGson().toJson(request, POSProcessRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), Parameter.PAY_POS_URI, payload);

            POSProcessResponse posProcessResponse = getGson().fromJson(response.getData(), POSProcessResponse.class);

            if (posProcessResponse.getStatus() != 0) {
                Console.error("getPOSPayProcessingRequest::errorCode::", posProcessResponse.getStatus() + "");
                Console.error("getPOSPayProcessingRequest::errorMessage::", posProcessResponse.getMessage().getDescription());
            } else {
                POSProcessResponse.Json message = posProcessResponse.getMessage();
                Console.debug("getPOSPayProcessingRequest::transId::", message.getTransId().toString());
                Console.debug("getPOSPayProcessingRequest::amount::", message.getAmount().toString());
                Console.debug("getPOSPayProcessingRequest::Phone Number::", message.getPhoneNumber());
            }
            return posProcessResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public POSProcessRequest createPOSPayProcessingRequest(String partnerRefId, long amount, String storeId, String storeName, String publicKey,
                                                           String paymentCode, String description, double version, int payType) {

        try {

            Map<String, Object> rawData = new HashMap<>();
            rawData.put(Parameter.PARTNER_REF_ID, partnerRefId);
            rawData.put(Parameter.PARTNER_CODE, partnerInfo.getPartnerCode());
            rawData.put(Parameter.AMOUNT, amount);
            rawData.put(Parameter.PAYMENT_CODE, paymentCode);
            rawData.put(Parameter.STORE_ID, storeId);
            rawData.put(Parameter.STORE_NAME, storeName);

            Gson gson = new Gson();
            String jsonStr = gson.toJson(rawData);
            byte[] testByte = jsonStr.getBytes(StandardCharsets.UTF_8);
            String hashRSA = Encoder.encryptRSA(testByte, publicKey);

            Console.debug("createPOSPayProcessingRequest::rawDataBeforeHash::" + jsonStr);
            Console.debug("createPOSPayProcessingRequest::hash::" + hashRSA);

            return POSProcessRequest
                    .builder()
                    .partnerCode(partnerInfo.getPartnerCode())
                    .partnerRefId(partnerRefId)
                    .hash(hashRSA)
                    .description(description)
                    .version(version)
                    .payType(payType)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static POSProcessResponse process(Environment env, String partnerRefId, long amount, String storeId, String storeName,
                                             String publicKey, String description, String paymentCode, double version, int payType) throws Exception {
        Console.log("========================== START POS PAYMENT PROCESS ==================");

        POSPay posPay = new POSPay(env);
        POSProcessRequest posPayProcessingRequest = posPay.createPOSPayProcessingRequest(partnerRefId, amount, storeId, storeName, publicKey,
                paymentCode, description, version, payType);

        POSProcessResponse posProcessResponse = posPay.execute(posPayProcessingRequest);

        Console.log("========================== END POS PAYMENT PROCESS ==================");
        return posProcessResponse;
    }


}
