package com.mservice.allinone.processor.allinone;

import com.mservice.allinone.models.CaptureMoMoRequest;
import com.mservice.allinone.models.CaptureMoMoResponse;
import com.mservice.allinone.models.PaymentResponse;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.constants.RequestType;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.sharedmodels.AbstractProcess;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.sharedmodels.HttpResponse;
import com.mservice.shared.utils.Encoder;
import com.mservice.shared.utils.LogUtils;

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
     * @return CaptureMoMoResponse
     **/

    public static CaptureMoMoResponse process(Environment env, String orderId, String requestId, String amount, String orderInfo, String returnURL, String notifyURL, String extraData) throws Exception {
        try {
            CaptureMoMo m2Processor = new CaptureMoMo(env);

            CaptureMoMoRequest captureMoMoRequest = m2Processor.createPaymentCreationRequest(orderId, requestId, amount, orderInfo, returnURL, notifyURL, extraData);
            CaptureMoMoResponse captureMoMoResponse = m2Processor.execute(captureMoMoRequest);

            return captureMoMoResponse;
        } catch (Exception exception) {
            LogUtils.error("[CaptureMoMoProcess] "+ exception);
        }
        return null;
    }

    @Override
    public CaptureMoMoResponse execute(CaptureMoMoRequest request) throws MoMoException {
        try {

            String payload = getGson().toJson(request, CaptureMoMoRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[CaptureMoMoResponse] [" + request.getOrderId() + "] -> Error API");
            }

            System.out.println("uweryei7rye8wyreow8: "+ response.getData());

            CaptureMoMoResponse captureMoMoResponse = getGson().fromJson(response.getData(), CaptureMoMoResponse.class);

//            errorMoMoProcess(captureMoMoResponse.getErrorCode());

            String responserawData = Parameter.REQUEST_ID + "=" + captureMoMoResponse.getRequestId() +
                    "&" + Parameter.ORDER_ID + "=" + captureMoMoResponse.getOrderId() +
                    "&" + Parameter.MESSAGE + "=" + captureMoMoResponse.getMessage() +
                    "&" + Parameter.LOCAL_MESSAGE + "=" + captureMoMoResponse.getLocalMessage() +
                    "&" + Parameter.PAY_URL + "=" + captureMoMoResponse.getPayUrl() +
                    "&" + Parameter.ERROR_CODE + "=" + captureMoMoResponse.getErrorCode() +
                    "&" + Parameter.REQUEST_TYPE + "=" + RequestType.CAPTURE_MOMO_WALLET;

            String signResponse = Encoder.signHmacSHA256(responserawData, partnerInfo.getSecretKey());
            LogUtils.info("[CaptureMoMoResponse] rawData: " + responserawData + ", [Signature] -> " + signResponse + ", [MoMoSignature] -> " + captureMoMoResponse.getSignature());

            if (signResponse.equals(captureMoMoResponse.getSignature())) {
                return captureMoMoResponse;
            } else {
                throw new IllegalArgumentException("Wrong signature from MoMo side - please contact with us");
            }

        } catch (Exception exception) {
            LogUtils.error("[CaptureMoMoResponse] "+ exception);
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

            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            LogUtils.debug("[CaptureMoMoRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest);

            return new CaptureMoMoRequest(partnerInfo.getPartnerCode(),orderId, orderInfo,partnerInfo.getAccessKey(),amount,signRequest,extraData,requestId,notifyUrl,returnUrl,RequestType.CAPTURE_MOMO_WALLET);
        } catch (Exception e) {
            LogUtils.error("[CaptureMoMoRequest] "+ e);
        }

        return null;
    }

    /**
     * After end-user do allinonepay order, MoMo will return result to partner by two ways:
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
     * Used for two commands: captureMoMoWallet and payWithATM
     * ErrorCode is key to detect transaction is success or fail
     * <p>
     * if ErrorCode = 0 mean transaction is payment success
     * else fail
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

        LogUtils.debug("resultCaptureMoMoWallet::partnerRawDataBeforeHash::" + rawData);
        String signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());
        LogUtils.debug("resultCaptureMoMoWallet::partnerSignature::" + signature);
        LogUtils.debug("resultCaptureMoMoWallet::momoSignature::" + paymentResponse.getSignature());

        if (signature.equals(paymentResponse.getSignature())) {
            return paymentResponse;
        } else {
            throw new IllegalArgumentException("Wrong signature from MoMo side - please contact with us");
        }
    }

}
