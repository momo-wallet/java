package com.mservice.paygate.processor.allinone;

import com.mservice.paygate.models.PayATMRequest;
import com.mservice.paygate.models.PayATMResponse;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.constants.RequestType;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.sharedmodels.AbstractProcess;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.sharedmodels.HttpResponse;
import com.mservice.shared.sharedmodels.PartnerInfo;
import com.mservice.shared.utils.Encoder;

/**
 * @author hainguyen
 * Documention: https://developers.momo.vn
 */
public class PayATM extends AbstractProcess<PayATMRequest, PayATMResponse> {

    public PayATM(Environment environment) {
        super(environment);
    }

    public static PayATMResponse process(Environment env, String requestId, String orderId, String bankCode, String amount, String orderInfo, String returnUrl, String notifyUrl, String extra) throws Exception {
        PayATM atmProcess = new PayATM(env);

        try {
            PayATMRequest payATMRequest = atmProcess.createPayWithATMRequest(requestId, orderId, bankCode, amount, orderInfo, returnUrl, notifyUrl, extra, env.getPartnerInfo());
            PayATMResponse payATMResponse = atmProcess.execute(payATMRequest);
            return payATMResponse;

        } catch (Exception e) {
            atmProcess.logger.error("[PayATMProcess] ", e);
        }
        return null;
    }

    @Override
    public PayATMResponse execute(PayATMRequest request) throws MoMoException {
        try {
            String payload = getGson().toJson(request, PayATMRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[PayATMResponse] [" + request.getOrderId() + "] -> Error API");
            }

            PayATMResponse payATMResponse = getGson().fromJson(response.getData(), PayATMResponse.class);
//            errorMoMoProcess(payATMResponse.getErrorCode());

            String rawData =
                    Parameter.PARTNER_CODE + "=" + request.getPartnerCode() +
                            "&" + Parameter.ACCESS_KEY + "=" + request.getAccessKey() +
                            "&" + Parameter.REQUEST_ID + "=" + request.getRequestId() +
                            "&" + Parameter.PAY_URL + "=" + payATMResponse.getPayUrl() +
                            "&" + Parameter.ERROR_CODE + "=" + payATMResponse.getErrorCode() +
                            "&" + Parameter.ORDER_ID + "=" + payATMResponse.getOrderId() +
                            "&" + Parameter.MESSAGE + "=" + payATMResponse.getMessage() +
                            "&" + Parameter.LOCAL_MESSAGE + "=" + payATMResponse.getLocalMessage() +
                            "&" + Parameter.REQUEST_TYPE + "=" + RequestType.PAY_WITH_ATM;

            String signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());

            logger.info("[PayATMResponse] rawData: " + rawData + ", [Signature] -> " + signature + ", [MoMoSignature] -> " + request.getSignature());

            if (signature.equals(payATMResponse.getSignature())) {
                return payATMResponse;
            } else {
                throw new IllegalArgumentException("Wrong signature from MoMo side - please contact with us");
            }

        } catch (Exception e) {
            logger.error("[PayATMResponse] ", e);
        }
        return null;
    }

    public PayATMRequest createPayWithATMRequest(String requestId, String orderId, String bankCode, String amount, String orderInfo, String returnUrl,
                                                 String notifyUrl, String extra, PartnerInfo partnerInfo) {

        try {
            String dataCryption
                    = Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() + "&"
                    + Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() + "&"
                    + Parameter.REQUEST_ID + "=" + requestId + "&"
                    + Parameter.BANK_CODE + "=" + bankCode + "&"
                    + Parameter.AMOUNT + "=" + amount + "&"
                    + Parameter.ORDER_ID + "=" + orderId + "&"
                    + Parameter.ORDER_INFO + "=" + orderInfo + "&"
                    + Parameter.RETURN_URL + "=" + returnUrl + "&"
                    + Parameter.NOTIFY_URL + "=" + notifyUrl + "&"
                    + Parameter.EXTRA_DATA + "=" + extra + "&"
                    + Parameter.REQUEST_TYPE + "=" + RequestType.PAY_WITH_ATM;
            String signature = Encoder.signHmacSHA256(dataCryption, partnerInfo.getSecretKey());

            logger.debug("[PayATMRequest] rawData: " + dataCryption + ", [Signature] -> " + signature);

            PayATMRequest payATMRequest = PayATMRequest
                    .builder()
                    .partnerCode(partnerInfo.getPartnerCode())
                    .accessKey(partnerInfo.getAccessKey())
                    .amount(amount)
                    .requestId(requestId)
                    .orderId(orderId)
                    .returnUrl(returnUrl)
                    .notifyUrl(notifyUrl)
                    .orderInfo(orderInfo)
                    .bankCode(bankCode)
                    .requestType(RequestType.PAY_WITH_ATM)
                    .signature(signature)
                    .build();

            return payATMRequest;
        } catch (Exception e) {
            logger.error("[PayATMRequest] ", e);
            throw new IllegalArgumentException("Invalid params ATM Request");
        }
    }

}
