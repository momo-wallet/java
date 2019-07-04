package com.mservice.pay.processor.notallinone;

import com.google.gson.Gson;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.exception.MoMoException;
import com.mservice.pay.models.AppProcessRequest;
import com.mservice.pay.models.AppProcessResponse;
import com.mservice.shared.sharedmodels.AbstractProcess;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.utils.Console;
import com.mservice.shared.utils.Encoder;
import com.mservice.shared.utils.HttpResponse;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class AppPay extends AbstractProcess<AppProcessRequest, AppProcessResponse> {

    public AppPay(Environment environment) {
        super(environment);
    }

    public AppProcessResponse execute(AppProcessRequest request) throws MoMoException {

        try {
            String payload = getGson().toJson(request, AppProcessRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), Parameter.PAY_APP_URI, payload);

            AppProcessResponse appProcessResponse = getGson().fromJson(response.getData(), AppProcessResponse.class);

            String rawData =
                    Parameter.STATUS + "=" + appProcessResponse.getStatus() +
                            "&" + Parameter.MESSAGE + "=" + appProcessResponse.getMessage() +
                            "&" + Parameter.AMOUNT + "=" + appProcessResponse.getAmount() +
                            "&" + Parameter.PAY_TRANS_ID + "=" + appProcessResponse.getTransId();

            Console.debug("getAppPaymentResponse::partnerRawDataBeforeHash::" + rawData);

            String signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());

            Console.debug("getAppPaymentResponse::getAppPaymentResponse::partnerSignature::" + signature);

            if (appProcessResponse.getStatus() != 0) {
                Console.error("getAppPaymentResponse::errorCode::", appProcessResponse.getStatus() + "");
                Console.error("getAppPaymentResponse::errorMessage::", appProcessResponse.getMessage());
            }

            Console.debug("getAppPaymentResponse::transid::", appProcessResponse.getTransId());
            Console.debug("getAppPaymentResponse::amount::", appProcessResponse.getAmount().toString());
            Console.debug("getAppPaymentResponse::MoMo Signature::", appProcessResponse.getSignature());

            if (signature.equals(appProcessResponse.getSignature())) {
                return appProcessResponse;
            } else {
                throw new IllegalArgumentException("Wrong signature from MoMo side - please contact with us");
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public AppProcessRequest createAppPayProcessingRequest(String partnerRefId, String partnerTransId, long amount, String partnerName,
                                                           String storeId, String storeName, String publicKey, String customerNumber, String appData,
                                                           String description, double version, int payType) {

        try {

            Map<String, Object> rawData = new HashMap<>();
            rawData.put(Parameter.PARTNER_REF_ID, partnerRefId);
            rawData.put(Parameter.PARTNER_CODE, partnerInfo.getPartnerCode());
            rawData.put(Parameter.AMOUNT, amount);
            rawData.put(Parameter.PARTNER_NAME, partnerName);
            rawData.put(Parameter.PARTNER_TRANS_ID, partnerTransId);
            rawData.put(Parameter.STORE_ID, storeId);
            rawData.put(Parameter.STORE_NAME, storeName);

            Gson gson = new Gson();
            String jsonStr = gson.toJson(rawData);
            byte[] testByte = jsonStr.getBytes(StandardCharsets.UTF_8);
            String hashRSA = Encoder.encryptRSA(testByte, publicKey);

            Console.debug("createAppPayProcessingRequest::rawDataBeforeHash::" + jsonStr);
            Console.debug("createAppPayProcessingRequest::hash::" + hashRSA);

            return AppProcessRequest
                    .builder()
                    .partnerCode(partnerInfo.getPartnerCode())
                    .customerNumber(customerNumber)
                    .partnerRefId(partnerRefId)
                    .appData(appData)
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

    public static AppProcessResponse process(Environment env, String partnerRefId, String partnerTransId, long amount, String partnerName,
                                             String storeId, String storeName, String publicKey, String customerNumber, String appData,
                                             String description, double version, int payType) throws Exception {
        Console.log("========================== START APP PAYMENT PROCESS ==================");

        AppPay appPay = new AppPay(env);
        AppProcessRequest appProcessRequest = appPay.createAppPayProcessingRequest(partnerRefId,
                partnerTransId, amount, partnerName, storeId, storeName, publicKey, customerNumber, appData, description, version, payType);
        AppProcessResponse appProcessResponse = appPay.execute(appProcessRequest);

        Console.log("========================== END APP PAYMENT PROCESS ==================");
        return appProcessResponse;
    }


}
