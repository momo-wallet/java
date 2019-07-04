package com.mservice.paygate.processor.allinone;

import com.mservice.shared.constants.Parameter;
import com.mservice.shared.constants.RequestType;
import com.mservice.shared.exception.MoMoException;
import com.mservice.paygate.models.*;
import com.mservice.shared.sharedmodels.AbstractProcess;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.utils.Console;
import com.mservice.shared.utils.Encoder;
import com.mservice.shared.utils.HttpResponse;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class RefundATM extends AbstractProcess<RefundATMRequest, RefundATMResponse> {

    public RefundATM(Environment environment) {
        super(environment);
    }

    public RefundATMResponse execute(RefundATMRequest request) throws MoMoException {
        try {
            String payload = getGson().toJson(request, RefundATMRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), Parameter.PAY_GATE_URI, payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("Error API");
            }
            
            RefundATMResponse refundATMResponse = getGson().fromJson(response.getData(), RefundATMResponse.class);

            errorMoMoProcess(refundATMResponse.getErrorCode());
            
            String rawData = Parameter.PARTNER_CODE + "=" + refundATMResponse.getPartnerCode() +
                    "&" + Parameter.ACCESS_KEY + "=" + refundATMResponse.getAccessKey() +
                    "&" + Parameter.REQUEST_ID + "=" + refundATMResponse.getRequestId() +
                    "&" + Parameter.ORDER_ID + "=" + refundATMResponse.getOrderId() +
                    "&" + Parameter.ERROR_CODE + "=" + refundATMResponse.getErrorCode() +
                    "&" + Parameter.TRANS_ID + "=" + refundATMResponse.getTransId() +
                    "&" + Parameter.MESSAGE + "=" + refundATMResponse.getMessage() +
                    "&" + Parameter.LOCAL_MESSAGE + "=" + refundATMResponse.getLocalMessage() +
                    "&" + Parameter.REQUEST_TYPE + "=" + RequestType.REFUND_ATM;

            Console.debug("getrefundATMResponse::rawDataBeforeHash::" + rawData);
            String signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());
            Console.debug("getrefundATMResponse::signature::" + signature);

            if (refundATMResponse.getErrorCode() != 0) {
                Console.error("errorCode::", refundATMResponse.getErrorCode() + "");
                Console.error("errorMessage::", refundATMResponse.getMessage());
                Console.error("localMessage::", refundATMResponse.getLocalMessage());
            }

            if (signature.equals(refundATMResponse.getSignature())) {
                return refundATMResponse;
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

    public RefundATMRequest createRefundATMRequest(String requestId, String orderId, String amount, String transId, String bankCode) {
        String rawData =
                Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() +
                        "&" + Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() +
                        "&" + Parameter.REQUEST_ID + "=" + requestId +
                        "&" + Parameter.BANK_CODE + "=" + bankCode +
                        "&" + Parameter.AMOUNT + "=" + amount +
                        "&" + Parameter.ORDER_ID + "=" + orderId +
                        "&" + Parameter.TRANS_ID + "=" + transId +
                        "&" + Parameter.REQUEST_TYPE + "=" + RequestType.REFUND_ATM;
        String signature = "";

        try {
            Console.debug("createRefundATMRequest::rawDataBeforeHash::" + rawData);
            signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());
            Console.debug("createRefundATMRequest::signature::" + signature);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RefundATMRequest request = RefundATMRequest
                .builder()
                .accessKey(partnerInfo.getAccessKey())
                .partnerCode(partnerInfo.getPartnerCode())
                .amount(amount)
                .bankCode(bankCode)
                .orderId(orderId)
                .requestId(requestId)
                .transId(transId)
                .requestType(RequestType.REFUND_ATM)
                .signature(signature)
                .build();
        return request;
    }

    public static RefundATMResponse process(Environment env, String orderId, String requestId, String amount, String transId, String bankCode)
            throws Exception {
        Console.log("========================== START ATM REFUND PROCESS  ==================");
        RefundATM refundATM = new RefundATM(env);
        RefundATMRequest request = refundATM.createRefundATMRequest(requestId, orderId, amount, transId, bankCode);
        RefundATMResponse response = refundATM.execute(request);

        Console.log("========================== END ATM REFUND PROCESS ==================");

        return response;
    }


}
