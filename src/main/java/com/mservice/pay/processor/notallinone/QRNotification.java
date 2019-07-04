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
import com.mservice.shared.utils.HttpResponse;

public class QRNotification extends AbstractProcess<QRNotifyResponse, QRNotifyResponse> {
    public QRNotification(Environment environment) {
        super(environment);
    }

    //send response to confirm with MoMo server
    //execute here
    //return
    @Override
    public QRNotifyResponse execute(QRNotifyResponse qrNotifyRequest) throws MoMoException {

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

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), Parameter.PAY_QR_CODE_URI, payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("Error API");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //check the request from MoMo server
    public QRNotifyRequest handleNotifyRequest(QRNotifyRequest qrNotifyRequest) {
        try {
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

    public static QRNotifyResponse process(Environment env, QRNotifyRequest request) throws Exception {
        Console.log("========================== START QR NOTIFICATION PAYMENT PROCESS ==================");

        QRNotification qrNotification = new QRNotification(env);
        QRNotifyRequest qrNotifyRequest = qrNotification.handleNotifyRequest(request);
        QRNotifyResponse qrNotifyResponse = qrNotification.execute(qrNotifyRequest);

        Console.log("========================== END QR NOTIFICATION PROCESS ==================");
        return qrNotifyResponse;
    }


}
