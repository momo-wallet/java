package com.mservice.allinone.processor.allinone;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mservice.allinone.models.RefundStatusRequest;
import com.mservice.allinone.models.RefundStatusResponse;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.constants.RequestType;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.sharedmodels.AbstractProcess;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.sharedmodels.HttpResponse;
import com.mservice.shared.utils.Encoder;
import com.mservice.shared.utils.LogUtils;

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

        try {
            RefundStatus refundStatus = new RefundStatus(env);

            RefundStatusRequest request = refundStatus.createRefundStatusRequest(requestId, orderId);
            List<RefundStatusResponse> response = refundStatus.execute(request);
            return response;
        } catch (Exception exception) {
            LogUtils.error("[RefundStatusProcess] " + exception);
        }
        return new ArrayList<>();
    }

    @Override
    public List<RefundStatusResponse> execute(RefundStatusRequest refundStatusRequest) throws MoMoException {

        List<RefundStatusResponse> responseList = new ArrayList<RefundStatusResponse>();

        try {
            String payload = getGson().toJson(refundStatusRequest, RefundStatusRequest.class);
            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[RefundStatusResponse] [" + refundStatusRequest.getOrderId() + "] -> Error API");
            }

            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = (JsonArray) jsonParser.parse(response.getData());

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonElement jsonElement = jsonArray.get(i);
                JsonElement obj = jsonElement.getAsJsonObject();

                RefundStatusResponse refundMoMoResponse = getGson().fromJson(obj, RefundStatusResponse.class);

                //errorMoMoProcess(refundMoMoResponse.getErrorCode());

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

                String signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());
                LogUtils.info("[RefundStatusTransaction] rawData: " + rawData + ", [Signature] -> " + signature + ", [MoMoSignature] -> " + refundMoMoResponse.getSignature());

                if (signature.equals(refundMoMoResponse.getSignature())) {
                    responseList.add(refundMoMoResponse);
                } else {
                    throw new MoMoException("Wrong signature from MoMo side - please contact with us");
                }
            }
        } catch (Exception e) {
            LogUtils.error("[RefundStatusResponse] " + e);
        }

        return responseList;
    }

    public RefundStatusRequest createRefundStatusRequest(String requestId, String orderId) {
        String signature = "";

        try {
            String rawData =
                    Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() +
                            "&" + Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() +
                            "&" + Parameter.REQUEST_ID + "=" + requestId +
                            "&" + Parameter.ORDER_ID + "=" + orderId +
                            "&" + Parameter.REQUEST_TYPE + "=" + RequestType.QUERY_REFUND;
            signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());
            LogUtils.debug("[RefundStatusRequest] rawData: " + rawData + ", [Signature] -> " + signature);
        } catch (Exception e) {
            LogUtils.error("[RefundStatusRequest] " + e);
        }

        RefundStatusRequest refundStatusRequest = new RefundStatusRequest(partnerInfo.getPartnerCode(), orderId, partnerInfo.getAccessKey(),signature,requestId,RequestType.QUERY_REFUND);
        return refundStatusRequest;
    }

}

