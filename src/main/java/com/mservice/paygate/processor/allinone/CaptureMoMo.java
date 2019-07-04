package com.mservice.paygate.processor.allinone;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.constants.RequestType;
import com.mservice.shared.exception.MoMoException;
import com.mservice.paygate.models.CaptureMoMoRequest;
import com.mservice.paygate.models.CaptureMoMoResponse;
import com.mservice.shared.sharedmodels.AbstractProcess;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.paygate.models.PaymentResponse;
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

    @Override
    public CaptureMoMoResponse execute(CaptureMoMoRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, CaptureMoMoRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), Parameter.PAY_GATE_URI, payload);

            CaptureMoMoResponse captureMoMoResponse = getGson().fromJson(response.getData(), CaptureMoMoResponse.class);

            errorMoMoProcess(captureMoMoResponse.getErrorCode());

            if (captureMoMoResponse.getErrorCode() == 0) {
                String responserawData =
                        Parameter.REQUEST_ID + "=" + captureMoMoResponse.getRequestId() +
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

    /**
     * After end-user do notallinonepay order, MoMo will return result to partner by two ways:
     * - 1. On browser, MoMo website will be redirected to Partner website by returnUrl field which passed to api captureMoMoWallet
     * - 2. MoMo uses notifyUrl field to send http request with POST method to partner endpoint
     * <p>
     * For returnUrl: we will attach and fill data to params: refer PAYLOAD
     * For notifyUrl: we will build request with information:
     * METHOD: POST
     * HEADER: CONTENT-TYPE: X-WWW-FORM-URLENCODED
     * CHARSET : UTF-8
     * <p>
     * PAYLOAD: partnerCode=$partnerCode&amp;accessKey=$accessKey&amp;requestId=$requestId&amp;amount=$amount&amp;orderId=$oderId&amp;orderInfo=$orderInfo&amp;orderType=$orderType&amp;transId=$transId&amp;message=$message&amp;localMessage=$localMessage&amp;responseTime=$responseTime&amp;errorCode=$errorCode&amp;payType=$payType&amp;extraData=$extraData;
     *
     *
     * <p>
     * You can use this function to get and validate result
     * Using for two commands: captureMoMoWallet and payWithATM
     * ErrorCode is key to detect transaction is success or fail
     * <p>
     * if ErrorCode = 0 mean transaction is payment success else fail
     *
     * @param paymentResponse
     * @return
     * @throws Exception
     */

    //Redirect
    public PaymentResponse resultCaptureMoMoWallet(PaymentResponse paymentResponse) throws Exception {
        String rawData
                = Parameter.PARTNER_CODE + "=" + paymentResponse.getPartnerCode() + "&"
                + Parameter.ACCESS_KEY + "=" + paymentResponse.getAccessKey() + "&"
                + Parameter.REQUEST_ID + "=" + paymentResponse.getRequestId() + "&"
                + Parameter.AMOUNT + "=" + paymentResponse.getAmount() + "&"
                + Parameter.ORDER_ID + "=" + paymentResponse.getOrderId() + "&"
                + Parameter.ORDER_INFO + "=" + paymentResponse.getOrderInfo() + "&"
                + Parameter.ORDER_TYPE + "=" + paymentResponse.getOrderType() + "&"
                + Parameter.TRANS_ID + "=" + paymentResponse.getTransId() + "&"
                + Parameter.MESSAGE + "=" + paymentResponse.getMessage() + "&"
                + Parameter.LOCAL_MESSAGE + "=" + paymentResponse.getLocalMessage() + "&"
                + Parameter.DATE + "=" + paymentResponse.getResponseDate() + "&"
                + Parameter.ERROR_CODE + "=" + paymentResponse.getErrorCode() + "&"
                + Parameter.PAY_TYPE + "=" + paymentResponse.getPayType() + "&"
                + Parameter.EXTRA_DATA + "=" + paymentResponse.getExtraData();

        Console.debug("resultCaptureMoMoWallet::partnerRawDataBeforeHash::" + rawData);
        String signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());
        Console.debug("resultCaptureMoMoWallet::partnerSignature::" + signature);
        Console.debug("resultCaptureMoMoWallet::momoSignature::" + paymentResponse.getSignature());

        if (signature.equals(paymentResponse.getSignature())) {
            return paymentResponse;
        } else {
            throw new IllegalArgumentException("Wrong signature from MoMo side - please contact with us");
        }
    }

    /**Capture MoMo Process on Payment Gateway
     * @param amount
     * @param extraData
     * @param orderInfo
     * @param env name of the environment (dev or prod)
     * @param orderId unique order ID in MoMo system
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

}
