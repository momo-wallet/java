package com.mservice.paygate.processor.allinone;

import com.mservice.paygate.models.QueryStatusTransactionRequest;
import com.mservice.paygate.models.QueryStatusTransactionResponse;
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
        Console.log("========================== START QUERY QUERY STATUS ==================");
        try {
            QueryStatusTransaction queryStatusTransaction = new QueryStatusTransaction(env);
            QueryStatusTransactionRequest queryStatusRequest = queryStatusTransaction.createQueryRequest(orderId, requestId);
            QueryStatusTransactionResponse queryStatusResponse = queryStatusTransaction.execute(queryStatusRequest);

            // Your handler
            if (queryStatusResponse.getErrorCode() == 0) {
                Console.debug("payType::", queryStatusResponse.getPayType());
                Console.debug("transId::", queryStatusResponse.getTransId());
                Console.debug("orderId::", queryStatusResponse.getOrderId());
            }
            Console.log("========================== END QUERY QUERY STATUS ==================");

            return queryStatusResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public QueryStatusTransactionResponse execute(QueryStatusTransactionRequest request) throws MoMoException {

        try {
            String payload = getGson().toJson(request, QueryStatusTransactionRequest.class);

            HttpResponse response = Execute.sendToMoMo(environment.getMomoEndpoint(), Parameter.PAY_GATE_URI, payload);

            if (response.getStatus() != 200) {
                throw new MoMoException("Error API");
            }

            QueryStatusTransactionResponse queryStatusResponse = getGson().fromJson(response.getData(), QueryStatusTransactionResponse.class);

            errorMoMoProcess(queryStatusResponse.getErrorCode());

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

            Console.debug("getQueryStatusResponse::rawDataBeforeHash::" + rawData);
            String signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());
            Console.debug("getQueryStatusResponse::signature::" + signature);

            if (queryStatusResponse.getErrorCode() != 0) {
                Console.error("errorCode::", queryStatusResponse.getErrorCode() + "");
                Console.error("errorMessage::", queryStatusResponse.getMessage());
                Console.error("localMessage::", queryStatusResponse.getLocalMessage());
            }

            if (signature.equals(queryStatusResponse.getSignature())) {
                return queryStatusResponse;
            } else {
                throw new MoMoException("Wrong signature from MoMo side - please contact with us");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public QueryStatusTransactionRequest createQueryRequest(String orderId, String requestId) {
        String rawData =
                Parameter.PARTNER_CODE + "=" + partnerInfo.getPartnerCode() +
                        "&" + Parameter.ACCESS_KEY + "=" + partnerInfo.getAccessKey() +
                        "&" + Parameter.REQUEST_ID + "=" + requestId +
                        "&" + Parameter.ORDER_ID + "=" + orderId +
                        "&" + Parameter.REQUEST_TYPE + "=" + RequestType.TRANSACTION_STATUS;
        String signature = "";

        try {
            Console.debug("createQueryStatusRequest::rawDataBeforeHash::" + rawData);
            signature = Encoder.signHmacSHA256(rawData, partnerInfo.getSecretKey());
            Console.debug("createQueryStatusRequest::signature::" + signature);
        } catch (Exception e) {
            e.printStackTrace();
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
