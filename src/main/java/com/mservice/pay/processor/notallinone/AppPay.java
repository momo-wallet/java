package com.mservice.pay.processor.notallinone;

import com.google.gson.Gson;
import com.mservice.pay.models.AppPayRequest;
import com.mservice.pay.models.AppPayResponse;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.sharedmodels.AbstractProcess;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.sharedmodels.HttpResponse;
import com.mservice.shared.utils.Encoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AppPay extends AbstractProcess<AppPayRequest, AppPayResponse> {

    public AppPay(Environment environment) {
        super(environment);
    }

    public static AppPayResponse process(Environment env, String partnerRefId, String partnerTransId, long amount, String partnerName, String storeId, String storeName, String publicKey, String customerNumber, String appData, String description, double version, int payType) throws Exception {
        try {
            AppPay appPay = new AppPay(env);
            AppPayRequest appPayRequest = appPay.createAppPayProcessingRequest(partnerRefId,
                    partnerTransId, amount, partnerName, storeId, storeName, publicKey, customerNumber, appData, description, version, payType);
            AppPayResponse appPayResponse = appPay.execute(appPayRequest);
            return appPayResponse;
        } catch (Exception exception) {
            log.error("[AppPayProcess] ", exception);
        }
        return null;
    }

    public AppPayResponse execute(AppPayRequest request) throws MoMoException {

        try {
            String payload = getGson().toJson(request, AppPayRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), payload);
            if (response.getStatus() != 200) {
                throw new MoMoException("[AppPayResponse] [" + request.getPartnerRefId() + "] -> Error API");
            }

            AppPayResponse appPayResponse = getGson().fromJson(response.getData(), AppPayResponse.class);

            if (appPayResponse.getStatus() == 0) {
                String rawData = Parameter.STATUS + "=" + appPayResponse.getStatus() +
                        "&" + Parameter.MESSAGE + "=" + appPayResponse.getMessage() +
                        "&" + Parameter.AMOUNT + "=" + appPayResponse.getAmount() +
                        "&" + Parameter.PAY_TRANS_ID + "=" + appPayResponse.getTransid();

                String signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());
                log.info("[AppPayResponse] rawData: " + rawData + ", [Signature] -> " + signature + ", [MoMoSignature] -> " + appPayResponse.getSignature());

                if (signature.equals(appPayResponse.getSignature())) {
                    return appPayResponse;
                } else {
                    throw new IllegalArgumentException("Wrong signature from MoMo side - please contact with us");
                }
            } else {
                log.warn("[AppPayResponse] -> Process Failed: " + appPayResponse.getError().toString());
            }
        } catch (Exception e) {
            log.error("[AppPayResponse] ", e);
        }

        return null;
    }

    public AppPayRequest createAppPayProcessingRequest(String partnerRefId, String partnerTransId, long amount, String partnerName,
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

            log.debug("[AppPayRequest] rawData: " + rawData + ", [Signature] -> " + hashRSA);

            return AppPayRequest
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
            log.error("[AppPayRequest] ", e);
        }

        return null;
    }


}
