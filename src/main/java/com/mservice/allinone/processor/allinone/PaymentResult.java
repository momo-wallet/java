package com.mservice.allinone.processor.allinone;

import com.mservice.allinone.models.PayGateResponse;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.sharedmodels.AbstractProcess;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.utils.Encoder;
import com.mservice.shared.utils.LogUtils;

/***
 * @author uyen.tran
 */
public class PaymentResult extends AbstractProcess<PayGateResponse, PayGateResponse> {

    public PaymentResult(Environment environment) {
        super(environment);
    }

    /***
     *
     * @param env
     * @param momoPaymentResult : After user confirms payment, we will announce payment result to you
     * @return
     * @throws Exception
     */
    public static PayGateResponse process(Environment env, PayGateResponse momoPaymentResult) {
        try {
            PaymentResult paymentResult = new PaymentResult(env);
            if(momoPaymentResult !=null){
                PayGateResponse responseMoMo = paymentResult.execute(momoPaymentResult);
                return responseMoMo;
            }
        } catch (Exception exception) {
            LogUtils.error("[Process Payment Result] "+ exception);
        }
        return null;
    }

    @Override
    public PayGateResponse execute(PayGateResponse payGateResponse) {
        try {
            String rawData = Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() +
                    "&" + Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() +

                    "&" + Parameter.REQUEST_ID + "=" + payGateResponse.getRequestId() +
                    "&" + Parameter.AMOUNT + "=" + payGateResponse.getAmount() +
                    "&" + Parameter.ORDER_ID + "=" + payGateResponse.getOrderId() +
                    "&" + Parameter.ORDER_INFO + "=" + payGateResponse.getOrderInfo() +
                    "&" + Parameter.ORDER_TYPE + "=" + payGateResponse.getOrderType() +
                    "&" + Parameter.TRANS_ID + "=" + payGateResponse.getTransId() +
                    "&" + Parameter.MESSAGE + "=" + payGateResponse.getMessage() +
                    "&" + Parameter.LOCAL_MESSAGE + "=" + payGateResponse.getLocalMessage() +
                    "&" + Parameter.DATE + "=" + payGateResponse.getResponseDate() +
                    "&" + Parameter.ERROR_CODE + "=" + payGateResponse.getErrorCode() +
                    "&" + Parameter.PAY_TYPE + "=" + payGateResponse.getPayType() +
                    "&" + Parameter.EXTRA_DATA + "=" + payGateResponse.getExtraData();

            String signatureClient = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());

            LogUtils.debug("[Payment Result] rawData: " + rawData + ", [Signature] -> " + signatureClient);
            if (signatureClient.equals(payGateResponse.getSignature())) {
                int errorCode = 0;
                String message = "Success";
                String localMessage = "Thành công";
//                Xử lý kết quả thanh toán - Process Payment Result

                PayGateResponse responseIPNMoMo = new PayGateResponse(payGateResponse.getPartnerCode(), payGateResponse.getAccessKey(), payGateResponse.getOrderId(), payGateResponse.getOrderInfo(), payGateResponse.getAmount(), payGateResponse.getSignature(), payGateResponse.getExtraData(), payGateResponse.getRequestId(),payGateResponse.getRequestType(),errorCode, message, localMessage, payGateResponse.getTransId());

                return responseIPNMoMo;
            }
            PayGateResponse responseIPNMoMo = new PayGateResponse(payGateResponse.getPartnerCode(), payGateResponse.getAccessKey(), payGateResponse.getOrderId(), payGateResponse.getOrderInfo(), payGateResponse.getAmount(), payGateResponse.getSignature(), payGateResponse.getExtraData(), payGateResponse.getRequestId(),payGateResponse.getRequestType(),101, "error", "Lỗi", payGateResponse.getTransId());
//          200 - Everything will be 200 Oke
            return responseIPNMoMo;
        } catch (Exception exception) {
            LogUtils.error("[Payment Result] " + exception);
            throw new IllegalArgumentException("Invalid params capture MoMo Request");
        }
    }
}