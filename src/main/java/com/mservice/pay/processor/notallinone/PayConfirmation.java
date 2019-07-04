package com.mservice.pay.processor.notallinone;

import com.mservice.pay.models.PayConfirmationRequest;
import com.mservice.pay.models.PayConfirmationResponse;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.sharedmodels.AbstractProcess;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.utils.Console;
import com.mservice.shared.utils.Encoder;
import com.mservice.shared.utils.HttpResponse;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class PayConfirmation extends AbstractProcess<PayConfirmationRequest, PayConfirmationResponse> {

    public PayConfirmation(Environment environment) {
        super(environment);
    }

    @Override
    public PayConfirmationResponse execute(PayConfirmationRequest request) throws MoMoException {
        try {
            String payload = getGson().toJson(request, PayConfirmationRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), Parameter.PAY_CONFIRMATION_URI, payload);

            PayConfirmationResponse payConfirmationResponse = getGson().fromJson(response.getData(), PayConfirmationResponse.class);

            if (payConfirmationResponse.getStatus() != 0) {
                Console.error("getPayConfirmationResponse::errorCode::", payConfirmationResponse.getStatus() + "");
                Console.error("getPayConfirmationResponse::errorMessage::", payConfirmationResponse.getMessage());
                throw new IllegalArgumentException("Wrong signature from MoMo side - please contact with us");

            } else {
                PayConfirmationResponse.Json data = payConfirmationResponse.getData();
                Console.debug("getPayConfirmationResponse::MoMoTransId::", data.getMomoTransId());
                Console.debug("getPayConfirmationResponse::PartnerRefId::", data.getPartnerRefId());
                Console.debug("getPayConfirmationResponse::amount::", data.getAmount().toString());
                Console.debug("getPayConfirmationResponse::MoMo Signature::", payConfirmationResponse.getSignature());

                String rawData = Parameter.AMOUNT + "=" + data.getAmount() +
                        "&" + Parameter.MOMO_TRANS_ID + "=" + data.getMomoTransId() +
                        "&" + Parameter.PARTNER_CODE + "=" + data.getParnterCode() +
                        "&" + Parameter.PARTNER_REF_ID + "=" + data.getPartnerRefId();

                Console.debug("getPayConfirmationResponse::partnerRawDataBeforeHash::" + rawData);

                String signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());

                Console.debug("getPayConfirmationResponse::partnerSignature::" + signature);

                if (signature.equals(payConfirmationResponse.getSignature())) {
                    return payConfirmationResponse;
                } else {
                    throw new IllegalArgumentException("Wrong signature from MoMo side - please contact with us");
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public PayConfirmationRequest createAppAppConfirmRequest(String partnerRefId, String requestType, String requestId, String momoTransId,
                                                             String customerNumber, String description) {

        try {
            String requestRawData = new StringBuilder()
                    .append(Parameter.PARTNER_CODE).append("=").append(partnerInfo.getPartnerCode()).append("&")
                    .append(Parameter.PARTNER_REF_ID).append("=").append(partnerRefId).append("&")
                    .append(Parameter.REQUEST_TYPE).append("=").append(requestType).append("&")
                    .append(Parameter.REQUEST_ID).append("=").append(requestId).append("&")
                    .append(Parameter.MOMO_TRANS_ID).append("=").append(momoTransId)
                    .toString();

            Console.debug("createPayConfirmRequest::rawDataBeforeHash::" + requestRawData);
            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            Console.debug("createPayConfirmRequest::signature::" + signRequest);

            return PayConfirmationRequest
                    .builder()
                    .partnerCode(partnerInfo.getPartnerCode())
                    .partnerRefId(partnerRefId)
                    .requestType(requestType)
                    .requestId(requestId)
                    .momoTransId(momoTransId)
                    .customerNumber(customerNumber)
                    .description(description)
                    .signature(signRequest)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static PayConfirmationResponse process(Environment env, String partnerRefId, String requestType, String requestId, String momoTransId,
                                                  String customerNumber, String description) throws Exception {
        Console.log("========================== END PAY CONFIRMATION PROCESS ==================");

        PayConfirmation payConfirmation = new PayConfirmation(env);
        PayConfirmationRequest payConfirmationRequest = payConfirmation.createAppAppConfirmRequest(partnerRefId, requestType, requestId,
                momoTransId, customerNumber, description);
        PayConfirmationResponse payConfirmationResponse = payConfirmation.execute(payConfirmationRequest);
        Console.log("========================== END PAY CONFIRMATION PROCESS ==================");

        return payConfirmationResponse;
    }


}
