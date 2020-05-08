package com.mservice.pay.processor.notallinone;

import com.mservice.pay.models.QRNotifyRequest;
import com.mservice.pay.models.QRNotifyResponse;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.constants.RequestType;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.sharedmodels.AbstractProcess;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.utils.Encoder;
import com.mservice.shared.utils.LogUtils;

public class QRNotification extends AbstractProcess<QRNotifyRequest, QRNotifyResponse> {
    public QRNotification(Environment environment) {
        super(environment);
    }

    public static QRNotifyResponse process(Environment env, String rawPostData) throws Exception {
        try {
            QRNotification qrNotification = new QRNotification(env);

            QRNotifyRequest qrNotifyRequest = qrNotification.validateQRNotifyRequest(rawPostData);

            if (qrNotifyRequest != null) {
                return qrNotification.execute(qrNotifyRequest);
            }

        } catch (Exception exception) {
            LogUtils.error("[QRNotifyProcess] :"+ exception);
        }
        return null;
    }

    @Override
    public QRNotifyResponse execute(QRNotifyRequest qrNotifyRequest) throws MoMoException {
        try {
            String rawData = new StringBuilder()
                    .append(Parameter.AMOUNT).append("=").append(qrNotifyRequest.getAmount()).append("&")
                    .append(Parameter.MESSAGE).append("=").append(qrNotifyRequest.getMessage()).append("&")
                    .append(Parameter.MOMO_TRANS_ID).append("=").append(qrNotifyRequest.getMomoTransId()).append("&")
                    .append(Parameter.PARTNER_REF_ID).append("=").append(qrNotifyRequest.getPartnerRefId()).append("&")
                    .append(Parameter.STATUS).append("=").append(qrNotifyRequest.getStatus())
                    .toString();

            String signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());
            LogUtils.debug("[QRNotifyResponseToMoMo] rawData: " + rawData + ", [Signature] -> " + signature);

            QRNotifyResponse qrNotifyResponse = new QRNotifyResponse(qrNotifyRequest.getStatus(), signature, qrNotifyRequest.getAmount(),qrNotifyRequest.getMomoTransId(),qrNotifyRequest.getPartnerRefId(), qrNotifyRequest.getMessage());

            return qrNotifyResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //check the request from MoMo server
    public QRNotifyRequest validateQRNotifyRequest(String rawPostData) {
        try {
            QRNotifyRequest qrNotifyRequest = getGson().fromJson(rawPostData, QRNotifyRequest.class);

            if (!qrNotifyRequest.getPartnerCode().equals(partnerInfo.getPartnerCode())) {
                throw new IllegalArgumentException("[ValidateQRNotifyRequest] [" + qrNotifyRequest.getMomoTransId() + "] Wrong PartnerCode");
            }
            if (!qrNotifyRequest.getAccessKey().equals(partnerInfo.getAccessKey())) {
                throw new IllegalArgumentException("[ValidateQRNotifyRequest] [" + qrNotifyRequest.getMomoTransId() + "] Wrong Access Key");
            }
            if (!qrNotifyRequest.getTransType().equals(RequestType.TRANS_TYPE_MOMO_WALLET)) {
                throw new IllegalArgumentException("[ValidateQRNotifyRequest] [" + qrNotifyRequest.getMomoTransId() + "] Wrong TransType -- must always be momo_wallet");
            }

            String requestRawData = new StringBuilder()
                    .append(Parameter.ACCESS_KEY).append("=").append(qrNotifyRequest.getAccessKey()).append("&")
                    .append(Parameter.AMOUNT).append("=").append(qrNotifyRequest.getAmount()).append("&")
                    .append(Parameter.MESSAGE).append("=").append(qrNotifyRequest.getMessage()).append("&")
                    .append(Parameter.MOMO_TRANS_ID).append("=").append(qrNotifyRequest.getMomoTransId()).append("&")
                    .append(Parameter.PARTNER_CODE).append("=").append(qrNotifyRequest.getPartnerCode()).append("&")
                    .append(Parameter.PARTNER_REF_ID).append("=").append(qrNotifyRequest.getPartnerRefId()).append("&")
                    .append(Parameter.PARTNER_TRANS_ID).append("=").append(qrNotifyRequest.getPartnerTransId()).append("&")
                    .append(Parameter.DATE).append("=").append(qrNotifyRequest.getResponseTime()).append("&")
                    .append(Parameter.STATUS).append("=").append(qrNotifyRequest.getStatus()).append("&")
                    .append(Parameter.STORE_ID).append("=").append(qrNotifyRequest.getStoreId()).append("&")
                    .append(Parameter.TRANS_TYPE).append("=").append(RequestType.TRANS_TYPE_MOMO_WALLET)
                    .toString();

            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            LogUtils.info("[ValidateQRNotifyRequest] rawData: " + requestRawData + ", [Signature] -> " + signRequest + ", [MoMoSignature] -> " + qrNotifyRequest.getSignature());

            if (signRequest.equals(qrNotifyRequest.getSignature())) {
                return qrNotifyRequest;
            } else {
                throw new IllegalArgumentException("Wrong signature from MoMo side - please contact with us");
            }
        } catch (Exception e) {
            LogUtils.error("[ValidateQRNotifyRequest] " + e);
        }

        return null;
    }


}
