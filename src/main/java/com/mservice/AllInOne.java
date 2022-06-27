package com.mservice;

import com.mservice.enums.*;
import com.mservice.models.*;
import com.mservice.processor.*;
import com.mservice.config.Environment;
import com.mservice.shared.utils.LogUtils;
/**
 * Demo
 */
public class AllInOne {

    /***
     * Select environment
     * You can load config from file
     * MoMo only provide once endpoint for each envs: dev and prod
     * @param args
     * @throws
     */

    public static void main(String... args) throws Exception {
        LogUtils.init();
        String requestId = String.valueOf(System.currentTimeMillis());
        String orderId = String.valueOf(System.currentTimeMillis());
        Long transId = 2L;
        long amount = 50000;

        String orderInfo = "Pay With MoMo";
        String returnURL = "https://google.com.vn";
        String notifyURL = "https://google.com.vn";

        Environment environment = Environment.selectEnv("dev");


//      Remember to change the IDs at enviroment.properties file

        /***
         * create payment with capture momo wallet
         */
//        PaymentResponse captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.CAPTURE_WALLET);
//
//        orderId = String.valueOf(System.currentTimeMillis());
//        requestId = String.valueOf(System.currentTimeMillis());

        /***
         * create payment with Momo's ATM type
         */
//        PaymentResponse captureATMMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_ATM);
//
//        orderId = String.valueOf(System.currentTimeMillis());
//        requestId = String.valueOf(System.currentTimeMillis());
        //        Payment Method- Phương thức thanh toán MoMo's Credit
        /***
         * create payment with Momo's Credit type
         */
//        PaymentResponse captureCreditMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_CREDIT);
//
//        orderId = String.valueOf(System.currentTimeMillis());
//        requestId = String.valueOf(System.currentTimeMillis());
        /***
         * confirm transaction with Momo's ATM type
         */
//        ConfirmResponse confirmResponse = ConfirmTransaction.process(environment, orderId, requestId, Long.toString(amount), ConfirmRequestType.CAPTURE, "");

        /***
         * query transaction
         */
//        QueryStatusTransactionResponse queryStatusTransactionResponse = QueryTransactionStatus.process(environment, orderId, requestId);

        transId = 2L;
        /***
         * refund transaction
         */
//        RefundMoMoResponse refundMoMoResponse = RefundTransaction.process(environment, orderId, requestId, Long.toString(amount), transId, "");


    }

}
