package com.mservice.paygate.processor.allinone;

import com.mservice.paygate.models.PaymentRequest;
import com.mservice.paygate.models.PaymentResponse;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.sharedmodels.AbstractProcess;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.utils.Console;
import com.mservice.shared.utils.Encoder;

public class CaptureIPN extends AbstractProcess<PaymentRequest, PaymentResponse> {

    public CaptureIPN(Environment environment) {
        super(environment);
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

    @Override
    public PaymentResponse execute(PaymentRequest request) throws MoMoException {
        return null;
    }
}
