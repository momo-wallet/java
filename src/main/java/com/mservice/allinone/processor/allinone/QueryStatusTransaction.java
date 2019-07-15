package com.mservice.allinone.processor.allinone;

import com.mservice.allinone.models.QueryStatusTransactionRequest;
import com.mservice.allinone.models.QueryStatusTransactionResponse;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.constants.RequestType;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.sharedmodels.AbstractProcess;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.sharedmodels.HttpResponse;
import com.mservice.shared.utils.Encoder;

/**
 * @author hainguyen
 * Documention: https://developers.momo.vn
 */
public class QueryStatusTransaction extends AbstractProcess<QueryStatusTransactionRequest, QueryStatusTransactionResponse> {

    public QueryStatusTransaction(Environment environment) {
        super(environment);
    }

    /**
     * Check Query Transaction Status on Payment Gateway
     *
     * @param orderId   MoMo's order ID
     * @param requestId request ID
     **/
    public static QueryStatusTransactionResponse process(Environment env, String orderId, String requestId) throws Exception {
        QueryStatusTransaction queryStatusTransaction = new QueryStatusTransaction(env);
        try {
            QueryStatusTransactionRequest queryStatusRequest = queryStatusTransaction.createQueryRequest(orderId, requestId);
            QueryStatusTransactionResponse queryStatusResponse = queryStatusTransaction.execute(queryStatusRequest);

            return queryStatusResponse;
        } catch (Exception e) {
            queryStatusTransaction.logger.error("[QueryStatusProcess] ", e);
        }
        return null;
    }

    @Override
    public QueryStatusTransactionResponse execute(QueryStatusTransactionRequest request) throws MoMoException {

        try {
            String payload = getGson().toJson(request, QueryStatusTransactionRequest.class);

            HttpResponse response = execute.sendToMoMo(environment.getMomoEndpoint(), payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("[QueryStatusTransactionResponse] [" + request.getOrderId() + "] -> Error API");
            }

            QueryStatusTransactionResponse queryStatusResponse = getGson().fromJson(response.getData(), QueryStatusTransactionResponse.class);

//            errorMoMoProcess(queryStatusResponse.getErrorCode());

            String rawData = Parameter.PARTNER_CODE + "=" + queryStatusResponse.getPartnerCode() +
                    "&" + Parameter.ACCESS_KEY + "=" + queryStatusResponse.getAccessKey() +
                    "&" + Parameter.REQUEST_ID + "=" + queryStatusResponse.getRequestId() +
                    "&" + Parameter.ORDER_ID + "=" + queryStatusResponse.getOrderId() +
                    "&" + Parameter.ERROR_CODE + "=" + queryStatusResponse.getErrorCode() +
                    "&" + Parameter.TRANS_ID + "=" + queryStatusResponse.getTransId() +
                    "&" + Parameter.AMOUNT + "=" + queryStatusResponse.getAmount() +
                    "&" + Parameter.MESSAGE + "=" + queryStatusResponse.getMessage() +
                    "&" + Parameter.LOCAL_MESSAGE + "=" + queryStatusResponse.getLocalMessage() +
                    "&" + Parameter.REQUEST_TYPE + "=" + RequestType.TRANSACTION_STATUS +
                    "&" + Parameter.PAY_TYPE + "=" + queryStatusResponse.getPayType() +
                    "&" + Parameter.EXTRA_DATA + "=" + queryStatusResponse.getExtraData();

            String signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());
            logger.info("[QueryStatusTransactionResponse] rawData: " + rawData + ", [Signature] -> " + signature + ", [MoMoSignature] -> " + queryStatusResponse.getSignature());

            if (signature.equals(queryStatusResponse.getSignature())) {
                return queryStatusResponse;
            } else {
                throw new MoMoException("Wrong signature from MoMo side - please contact with us");
            }

        } catch (Exception e) {
            logger.error("[QueryStatusTransactionResponse] ", e);
        }
        return null;

    }

    public QueryStatusTransactionRequest createQueryRequest(String orderId, String requestId) {
        String signature = "";
        try {
            String rawData =
                    Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() +
                            "&" + Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() +
                            "&" + Parameter.REQUEST_ID + "=" + requestId +
                            "&" + Parameter.ORDER_ID + "=" + orderId +
                            "&" + Parameter.REQUEST_TYPE + "=" + RequestType.TRANSACTION_STATUS;
            signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());
            logger.debug("[QueryStatusTransactionRequest] rawData: " + rawData + ", [Signature] -> " + signature);

        } catch (Exception e) {
            logger.error("[QueryStatusTransactionRequest] ", e);
        }

        QueryStatusTransactionRequest request = QueryStatusTransactionRequest
                .builder()
                .accessKey(partnerInfo.getAccessKey())
                .partnerCode(partnerInfo.getPartnerCode())
                .orderId(orderId)
                .requestId(requestId)
                .requestType(RequestType.TRANSACTION_STATUS)
                .signature(signature)
                .build();
        return request;
    }

}
