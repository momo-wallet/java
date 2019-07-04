package com.mservice.paygate.processor.allinone;

import com.mservice.shared.constants.Parameter;
import com.mservice.shared.constants.RequestType;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.sharedmodels.AbstractProcess;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.paygate.models.RefundMoMoRequest;
import com.mservice.paygate.models.RefundMoMoResponse;
import com.mservice.shared.utils.Console;
import com.mservice.shared.utils.Encoder;
import com.mservice.shared.utils.HttpResponse;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class RefundMoMo extends AbstractProcess<RefundMoMoRequest, RefundMoMoResponse> {

    public RefundMoMo(Environment environment) {
        super(environment);
    }

    public RefundMoMoResponse execute(RefundMoMoRequest request) throws MoMoException {
        try {
            String payload = getGson().toJson(request, RefundMoMoRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), Parameter.PAY_GATE_URI, payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("Error API");
            }

            RefundMoMoResponse refundMoMoResponse = getGson().fromJson(response.getData(), RefundMoMoResponse.class);

            errorMoMoProcess(refundMoMoResponse.getErrorCode());

            String rawData = Parameter.PARTNER_CODE + "=" + refundMoMoResponse.getPartnerCode() +
                    "&" + Parameter.ACCESS_KEY + "=" + refundMoMoResponse.getAccessKey() +
                    "&" + Parameter.REQUEST_ID + "=" + refundMoMoResponse.getRequestId() +
                    "&" + Parameter.ORDER_ID + "=" + refundMoMoResponse.getOrderId() +
                    "&" + Parameter.ERROR_CODE + "=" + refundMoMoResponse.getErrorCode() +
                    "&" + Parameter.TRANS_ID + "=" + refundMoMoResponse.getTransId() +
                    "&" + Parameter.MESSAGE + "=" + refundMoMoResponse.getMessage() +
                    "&" + Parameter.LOCAL_MESSAGE + "=" + refundMoMoResponse.getLocalMessage() +
                    "&" + Parameter.REQUEST_TYPE + "=" + RequestType.REFUND_MOMO_WALLET;

            Console.debug("getrefundMoMoResponse::rawDataBeforeHash::" + rawData);
            String signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());
            Console.debug("getrefundMoMoResponse::signature::" + signature);

            if (refundMoMoResponse.getErrorCode() != 0) {
                Console.error("errorCode::", refundMoMoResponse.getErrorCode() + "");
                Console.error("errorMessage::", refundMoMoResponse.getMessage());
                Console.error("localMessage::", refundMoMoResponse.getLocalMessage());
            }

            if (signature.equals(refundMoMoResponse.getSignature())) {
                return refundMoMoResponse;
            } else {
                throw new MoMoException("Wrong signature from MoMo side - please contact with us");
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public RefundMoMoRequest createRefundRequest(String requestId, String orderId, String amount, String transId) {
        String rawData =
                Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() +
                        "&" + Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() +
                        "&" + Parameter.REQUEST_ID + "=" + requestId +
                        "&" + Parameter.AMOUNT + "=" + amount +
                        "&" + Parameter.ORDER_ID + "=" + orderId +
                        "&" + Parameter.TRANS_ID + "=" + transId +
                        "&" + Parameter.REQUEST_TYPE + "=" + RequestType.REFUND_MOMO_WALLET;
        String signature = "";

        try {
            Console.debug("createRefundRequest::rawDataBeforeHash::" + rawData);
            signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());
            Console.debug("createRefundRequest::signature::" + signature);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RefundMoMoRequest request = RefundMoMoRequest
                .builder()
                .accessKey(partnerInfo.getAccessKey())
                .partnerCode(partnerInfo.getPartnerCode())
                .amount(amount)
                .orderId(orderId)
                .requestId(requestId)
                .transId(transId)
                .requestType(RequestType.REFUND_MOMO_WALLET)
                .signature(signature)
                .build();
        return request;
    }

    public static RefundMoMoResponse process(Environment env, String requestId, String orderId, String amount, String transId)
            throws Exception {
        Console.log("========================== START MOMO REFUND PROCESS  ==================");

        RefundMoMo refundMoMo = new RefundMoMo(env);
        RefundMoMoRequest request = refundMoMo.createRefundRequest(requestId, orderId, amount, transId);
        RefundMoMoResponse response = refundMoMo.execute(request);

        Console.log("========================== END MOMO REFUND PROCESS ==================");

        return response;

    }


}
