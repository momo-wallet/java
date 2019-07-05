package com.mservice.paygate.processor.allinone;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mservice.paygate.models.RefundStatusRequest;
import com.mservice.paygate.models.RefundStatusResponse;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.constants.RequestType;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.sharedmodels.AbstractProcess;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.sharedmodels.Execute;
import com.mservice.shared.utils.Console;
import com.mservice.shared.utils.Encoder;
import com.mservice.shared.utils.HttpResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hainguyen
 * Documention: https://developers.momo.vn
 */
public class RefundStatus extends AbstractProcess<RefundStatusRequest, List<RefundStatusResponse>> {

    public RefundStatus(Environment environment) {
        super(environment);
    }

    public static List<RefundStatusResponse> process(Environment env, String requestId, String orderId) throws Exception {
        Console.log("========================== START MOMO REFUND STATUS ==================");

        RefundStatus refundStatus = new RefundStatus(env);
        RefundStatusRequest request = refundStatus.createRefundStatusRequest(requestId, orderId);
        List<RefundStatusResponse> response = refundStatus.execute(request);

        Console.log("========================== END MOMO REFUND STATUS ==================");

        return response;
    }

    @Override
    public List<RefundStatusResponse> execute(RefundStatusRequest RefundStatusRequest) throws MoMoException {

        List<RefundStatusResponse> responseList = new ArrayList<RefundStatusResponse>();

        try {
            String payload = getGson().toJson(RefundStatusRequest, RefundStatusRequest.class);
            HttpResponse response = Execute.sendToMoMo(environment.getMomoEndpoint(), Parameter.PAY_GATE_URI, payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("Error API");
            }

            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = (JsonArray) jsonParser.parse(response.getData());

            Console.debug("===========================");
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonElement jsonElement = jsonArray.get(i);
                JsonElement obj = jsonElement.getAsJsonObject();

                RefundStatusResponse refundMoMoResponse = getGson().fromJson(obj, RefundStatusResponse.class);

                errorMoMoProcess(refundMoMoResponse.getErrorCode());

                String rawData = Parameter.PARTNER_CODE + "=" + refundMoMoResponse.getPartnerCode() +
                        "&" + Parameter.ACCESS_KEY + "=" + refundMoMoResponse.getAccessKey() +
                        "&" + Parameter.REQUEST_ID + "=" + refundMoMoResponse.getRequestId() +
                        "&" + Parameter.ORDER_ID + "=" + refundMoMoResponse.getOrderId() +
                        "&" + Parameter.ERROR_CODE + "=" + refundMoMoResponse.getErrorCode() +
                        "&" + Parameter.TRANS_ID + "=" + refundMoMoResponse.getTransId() +
                        "&" + Parameter.AMOUNT + "=" + refundMoMoResponse.getAmount() +
                        "&" + Parameter.MESSAGE + "=" + refundMoMoResponse.getMessage() +
                        "&" + Parameter.LOCAL_MESSAGE + "=" + refundMoMoResponse.getLocalMessage() +
                        "&" + Parameter.REQUEST_TYPE + "=" + RequestType.QUERY_REFUND;

                Console.debug("getrefundStatusResponse::transId::" + refundMoMoResponse.getTransId());
                Console.debug("getrefundStatusResponse::rawDataBeforeHash::" + rawData);
                String signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());
                Console.debug("getrefundStatusResponse::signature::" + signature);
                Console.debug("===========================");

                if (refundMoMoResponse.getErrorCode() != 0) {
                    Console.error("errorCode::", refundMoMoResponse.getErrorCode() + "");
                    Console.error("errorMessage::", refundMoMoResponse.getMessage());
                    Console.error("localMessage::", refundMoMoResponse.getLocalMessage());
                }

                if (signature.equals(refundMoMoResponse.getSignature())) {
                    responseList.add(refundMoMoResponse);
                } else {
                    throw new MoMoException("Wrong signature from MoMo side - please contact with us");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseList;
    }

    public RefundStatusRequest createRefundStatusRequest(String requestId, String orderId) {
        String rawData =
                Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() +
                        "&" + Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() +
                        "&" + Parameter.REQUEST_ID + "=" + requestId +
                        "&" + Parameter.ORDER_ID + "=" + orderId +
                        "&" + Parameter.REQUEST_TYPE + "=" + RequestType.QUERY_REFUND;
        String signature = "";

        try {
            Console.debug("createQueryStatusRefundStatusRequest::rawDataBeforeHash::" + rawData);
            signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());
            Console.debug("createQueryStatusRefundStatusRequest::signature::" + signature);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RefundStatusRequest refundStatusRequest = RefundStatusRequest
                .builder()
                .accessKey(partnerInfo.getAccessKey())
                .partnerCode(partnerInfo.getPartnerCode())
                .orderId(orderId)
                .requestId(requestId)
                .requestType(RequestType.QUERY_REFUND)
                .signature(signature)
                .build();
        return refundStatusRequest;
    }

}

