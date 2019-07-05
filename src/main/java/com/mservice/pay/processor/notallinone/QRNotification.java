package com.mservice.pay.processor.notallinone;

import com.mservice.pay.models.QRNotifyRequest;
import com.mservice.pay.models.QRNotifyResponse;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.constants.RequestType;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.sharedmodels.AbstractProcess;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.utils.Console;
import com.mservice.shared.utils.Encoder;

public class QRNotification extends AbstractProcess<QRNotifyRequest, String> {
    public QRNotification(Environment environment) {
        super(environment);
    }

    public static String process(Environment env, QRNotifyRequest request) throws Exception {
        Console.log("========================== START QR NOTIFICATION PAYMENT PROCESS ==================");

        QRNotification qrNotification = new QRNotification(env);
        QRNotifyRequest qrNotifyRequest = qrNotification.handleNotifyRequest(request);
        String qrNotifyResponse;

        if (qrNotifyRequest == null) {
            qrNotifyResponse = "";
        } else {
            qrNotifyResponse = qrNotification.execute(qrNotifyRequest);
        }

        Console.log("========================== END QR NOTIFICATION PROCESS ==================");
        return qrNotifyResponse;
    }

    @Override
    public String execute(QRNotifyRequest qrNotifyRequest) throws MoMoException {
        try {
            String requestRawData = new StringBuilder()
                    .append(Parameter.AMOUNT).append("=").append(qrNotifyRequest.getAmount()).append("&")
                    .append(Parameter.MESSAGE).append("=").append(qrNotifyRequest.getMessage()).append("&")
                    .append(Parameter.MOMO_TRANS_ID).append("=").append(qrNotifyRequest.getMomoTransId()).append("&")
                    .append(Parameter.PARTNER_REF_ID).append("=").append(qrNotifyRequest.getPartnerRefId()).append("&")
                    .append(Parameter.STATUS).append("=").append(qrNotifyRequest.getStatus())
                    .toString();

            Console.debug("QRPayResponseToMoMoServer::rawDataBeforeHash::" + requestRawData);
            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            Console.debug("QRPayResponseToMoMoServer::signature::" + signRequest);

            QRNotifyResponse qrNotifyResponse = QRNotifyResponse
                    .builder()
                    .status(qrNotifyRequest.getStatus())
                    .message(qrNotifyRequest.getMessage())
                    .partnerRefId(qrNotifyRequest.getPartnerRefId())
                    .momoTransId(qrNotifyRequest.getMomoTransId())
                    .amount(qrNotifyRequest.getAmount())
                    .signature(signRequest)
                    .build();

            String payload = getGson().toJson(qrNotifyResponse, QRNotifyResponse.class);
            return payload;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //check the request from MoMo server
    public QRNotifyRequest handleNotifyRequest(QRNotifyRequest qrNotifyRequest) {
        try {

            if (!qrNotifyRequest.getPartnerCode().equals(partnerInfo.getPartnerCode())) {
                throw new IllegalArgumentException("Wrong PartnerCode");
            }
            if (!qrNotifyRequest.getAccessKey().equals(partnerInfo.getAccessKey())) {
                throw new IllegalArgumentException("Wrong Access Key");
            }
            if (!qrNotifyRequest.getTransType().equals(RequestType.TRANS_TYPE_MOMO_WALLET)) {
                throw new IllegalArgumentException("Wrong TransType -- must always be momo_wallet");
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

            Console.debug("QRPayResponseToMoMoServer::rawDataBeforeHash::" + requestRawData);
            String signRequest = Encoder.signHmacSHA256(requestRawData, partnerInfo.getSecretKey());
            Console.debug("QRPayResponseToMoMoServer::signature::" + signRequest);
            Console.debug("QRPayResponseToMoMoServer::MoMo Signature::" + qrNotifyRequest.getSignature());

            if (signRequest.equals(qrNotifyRequest.getSignature())) {
                return qrNotifyRequest;
            } else {
                throw new IllegalArgumentException("Wrong signature from MoMo side - please contact with us");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
