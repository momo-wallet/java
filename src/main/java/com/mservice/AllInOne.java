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
        long amount = 50000;

        String orderInfo = "Pay With MoMo";
        String returnURL = "https://google.com.vn";
        String notifyURL = "https://google.com.vn";
        String extraData = "";
        String bankCode = "SML";

        Environment environment = Environment.selectEnv("dev");


//      Remember to change the IDs at enviroment.properties file

//        Payment Method- Phương thức thanh toán
        PaymentResponse captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.CAPTURE_WALLET);

        orderId = String.valueOf(System.currentTimeMillis());
        requestId = String.valueOf(System.currentTimeMillis());
        //        Payment Method- Phương thức thanh toán
        PaymentResponse captureATMMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_ATM);

        orderId = String.valueOf(System.currentTimeMillis());
        requestId = String.valueOf(System.currentTimeMillis());
        //        Payment Method- Phương thức thanh toán
        PaymentResponse captureCreditMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.PAY_WITH_CREDIT);

////        Transaction Query - Kiểm tra trạng thái giao dịch
//        QueryStatusTransactionResponse queryStatusTransactionResponse = QueryStatusTransaction.process(environment, orderId, requestId);

    }

}
