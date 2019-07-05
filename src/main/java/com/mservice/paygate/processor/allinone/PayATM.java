package com.mservice.paygate.processor.allinone;

import com.mservice.paygate.models.PayATMRequest;
import com.mservice.paygate.models.PayATMResponse;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.constants.RequestType;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.sharedmodels.AbstractProcess;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.sharedmodels.Execute;
import com.mservice.shared.sharedmodels.PartnerInfo;
import com.mservice.shared.utils.Console;
import com.mservice.shared.utils.Encoder;
import com.mservice.shared.utils.HttpResponse;

/**
 * @author hainguyen
 * Documention: https://developers.momo.vn
 */
public class PayATM extends AbstractProcess<PayATMRequest, PayATMResponse> {

    public PayATM(Environment environment) {
        super(environment);
    }

    public static PayATMResponse process(Environment env, String requestId, String orderId, String bankCode, String amount, String orderInfo, String returnUrl,
                                         String notifyUrl, String extra) throws Exception {
        Console.log("========================== START ATM PAYMENT =====================");
        try {

            PayATM atmProcess = new PayATM(env);
            PayATMRequest payATMRequest = atmProcess.createPayWithATMRequest(requestId, orderId, bankCode, amount, orderInfo, returnUrl, notifyUrl, extra, env.getPartnerInfo());
            PayATMResponse payATMResponse = atmProcess.execute(payATMRequest);

            // Your handler
            if (payATMResponse.getErrorCode() != 0) {
                Console.error("errorCode::", payATMResponse.getErrorCode() + "");
                Console.error("errorMessage::", payATMResponse.getMessage());
                Console.error("localMessage::", payATMResponse.getLocalMessage());
            } else {
                // To do something here ...
                // You can get payUrl to redirect new tab browser or open link on iframe to serve payment
                // Using deeplink to open MoMo App
                // Using qrCodeUrl to generate QrCode with data is this
                Console.debug("ATMpayURL::", payATMResponse.getPayUrl() + "");
            }

            Console.log("========================== END ATM PAYMENT =====================");
            return payATMResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PayATMResponse execute(PayATMRequest request) throws MoMoException {
        try {
            String payload = getGson().toJson(request, PayATMRequest.class);

            HttpResponse response = Execute.sendToMoMo(environment.getMomoEndpoint(), Parameter.PAY_GATE_URI, payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("Error API");
            }

            PayATMResponse payATMResponse = getGson().fromJson(response.getData(), PayATMResponse.class);

            errorMoMoProcess(payATMResponse.getErrorCode());

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

            Console.debug("getPayATMMoMoResponse::partnerRawDataBeforeHash::" + rawData);

            String signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());

            Console.debug("getPayATMMoMoResponse::partnerSignature::" + signature);
            Console.debug("getPayATMMoMoResponse::momoSignature::" + payATMResponse.getSignature());

            if (payATMResponse.getErrorCode() != 0) {
                Console.error("errorCode::", payATMResponse.getErrorCode() + "");
                Console.error("errorMessage::", payATMResponse.getMessage());
                Console.error("localMessage::", payATMResponse.getLocalMessage());
            }

            if (signature.equals(payATMResponse.getSignature())) {
                return payATMResponse;
            } else {
                throw new IllegalArgumentException("Wrong signature from MoMo side - please contact with us");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PayATMRequest createPayWithATMRequest(String requestId, String orderId, String bankCode, String amount, String orderInfo, String returnUrl,
                                                 String notifyUrl, String extra, PartnerInfo partnerInfo) {
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
        String signature = "";
        try {
            Console.debug("createPayWithATMRequest::rawDataBeforeHash::" + dataCryption);
            signature = Encoder.signHmacSHA256(dataCryption, partnerInfo.getSecretKey());
            Console.debug("createPayWithATMRequest::signature::" + signature);

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
            e.printStackTrace();
            throw new IllegalArgumentException("Invalid params notallinonepay ATM Request");
        }
    }

}
