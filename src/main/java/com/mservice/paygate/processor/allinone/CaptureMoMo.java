package com.mservice.paygate.processor.allinone;

import com.mservice.paygate.models.CaptureMoMoRequest;
import com.mservice.paygate.models.CaptureMoMoResponse;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.constants.RequestType;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.sharedmodels.AbstractProcess;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.sharedmodels.Execute;
import com.mservice.shared.utils.Console;
import com.mservice.shared.utils.Encoder;
import com.mservice.shared.utils.HttpResponse;

/**
 * @author hainguyen
 * Documention: https://developers.momo.vn
 */
public class CaptureMoMo extends AbstractProcess<CaptureMoMoRequest, CaptureMoMoResponse> {

    public CaptureMoMo(Environment environment) {
        super(environment);
    }

    /**
     * Capture MoMo Process on Payment Gateway
     *
     * @param amount
     * @param extraData
     * @param orderInfo
     * @param env       name of the environment (dev or prod)
     * @param orderId   unique order ID in MoMo system
     * @param requestId request ID
     * @param returnURL URL to redirect customer
     * @param notifyURL URL for MoMo to return transaction status to merchant
     * @return
     **/
    public static CaptureMoMoResponse process(Environment env, String orderId, String requestId, String amount, String orderInfo, String returnURL, String notifyURL, String extraData)
            throws Exception {
        Console.log("========================== START CAPTURE MOMO WALLET ==================");

        try {

            CaptureMoMo m2Processor = new CaptureMoMo(env);
            CaptureMoMoRequest captureMoMoRequest = m2Processor.createPaymentCreationRequest(orderId, requestId, amount, orderInfo, returnURL, notifyURL, extraData);
            CaptureMoMoResponse captureMoMoResponse = m2Processor.execute(captureMoMoRequest);

            // Your handler
            if (captureMoMoResponse.getErrorCode() != 0) {
                Console.error("errorCode::", captureMoMoResponse.getErrorCode() + "");
                Console.error("errorMessage::", captureMoMoResponse.getMessage());
                Console.error("localMessage::", captureMoMoResponse.getLocalMessage());
            } else {
                // To do something here ...
                // You can get payUrl to redirect new tab browser or open link on iframe to serve payment
                // Using deeplink to open MoMo App
                // Using qrCodeUrl to generate QrCode with data is this
                Console.debug("payURL::", captureMoMoResponse.getPayUrl() + "");
                Console.debug("deepLink::", captureMoMoResponse.getDeeplink());
                Console.debug("qrCodeURL::", captureMoMoResponse.getQrCodeUrl());
            }

            Console.log("========================== END CAPTURE MOMO WALLET ==================");

            return captureMoMoResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CaptureMoMoResponse execute(CaptureMoMoRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, CaptureMoMoRequest.class);

            HttpResponse response = Execute.sendToMoMo(environment.getMomoEndpoint(), Parameter.PAY_GATE_URI, payload);

            CaptureMoMoResponse captureMoMoResponse = getGson().fromJson(response.getData(), CaptureMoMoResponse.class);

            errorMoMoProcess(captureMoMoResponse.getErrorCode());

            if (captureMoMoResponse.getErrorCode() == 0) {
                String responserawData = Parameter.REQUEST_ID + "=" + captureMoMoResponse.getRequestId() +
                        "&" + Parameter.ORDER_ID + "=" + captureMoMoResponse.getOrderId() +
                        "&" + Parameter.MESSAGE + "=" + captureMoMoResponse.getMessage() +
                        "&" + Parameter.LOCAL_MESSAGE + "=" + captureMoMoResponse.getLocalMessage() +
                        "&" + Parameter.PAY_URL + "=" + captureMoMoResponse.getPayUrl() +
                        "&" + Parameter.ERROR_CODE + "=" + captureMoMoResponse.getErrorCode() +
                        "&" + Parameter.REQUEST_TYPE + "=" + RequestType.CAPTURE_MOMO_WALLET;

                Console.debug("getCaptureMoMoResponse::partnerRawDataBeforeHash::" + responserawData);
                String signResponse = Encoder.signHmacSHA256(responserawData, partnerInfo.getSecretKey());
                Console.debug("getCaptureMoMoResponse::partnerSignature::" + signResponse);
                Console.debug("getCaptureMoMoResponse::momoSignature::" + captureMoMoResponse.getSignature());

                if (signResponse.equals(captureMoMoResponse.getSignature())) {
                    return captureMoMoResponse;
                } else {
                    throw new IllegalArgumentException("Wrong signature from MoMo side - please contact with us");
                }
            }
            return captureMoMoResponse;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }

    /**
     * @param orderId
     * @param requestId
     * @param amount
     * @param orderInfo
     * @param returnUrl
     * @param notifyUrl
     * @param extraData
     * @return
     */
    public CaptureMoMoRequest createPaymentCreationRequest(String orderId, String requestId, String amount, String orderInfo,
                                                           String returnUrl, String notifyUrl, String extraData) {

        try {
            String requestRawData = new StringBuilder()
                    .append(Parameter.PARTNER_CODE).append("=").append(partnerInfo.getPartnerCode()).append("&")
                    .append(Parameter.ACCESS_KEY).append("=").append(partnerInfo.getAccessKey()).append("&")
                    .append(Parameter.REQUEST_ID).append("=").append(requestId).append("&")
                    .append(Parameter.AMOUNT).append("=").append(amount).append("&")
                    .append(Parameter.ORDER_ID).append("=").append(orderId).append("&")
                    .append(Parameter.ORDER_INFO).append("=").append(orderInfo).append("&")
                    .append(Parameter.RETURN_URL).append("=").append(returnUrl).append("&")
                    .append(Parameter.NOTIFY_URL).append("=").append(notifyUrl).append("&")
                    .append(Parameter.EXTRA_DATA).append("=").append(extraData)
                    .toString();

            Console.debug("createCaptureMoMoRequest::rawDataBeforeHash::" + requestRawData);
            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            Console.debug("createCaptureMoMoRequest::signature::" + signRequest);

            return CaptureMoMoRequest
                    .builder()
                    .accessKey(partnerInfo.getAccessKey())
                    .requestId(requestId)
                    .partnerCode(partnerInfo.getPartnerCode())
                    .requestType(RequestType.CAPTURE_MOMO_WALLET)
                    .notifyUrl(notifyUrl)
                    .returnUrl(returnUrl)
                    .orderId(orderId)
                    .amount(amount)
                    .signature(signRequest)
                    .extraData(extraData)
                    .orderInfo(orderInfo)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
