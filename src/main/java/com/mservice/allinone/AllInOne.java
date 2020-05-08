package com.mservice.allinone;

import com.mservice.allinone.models.*;
import com.mservice.allinone.processor.allinone.CaptureMoMo;
import com.mservice.allinone.processor.allinone.PayATM;
import com.mservice.allinone.processor.allinone.PaymentResult;
import com.mservice.allinone.processor.allinone.QueryStatusTransaction;
import com.mservice.shared.sharedmodels.Environment;
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

        Environment environment = Environment.selectEnv("dev", Environment.ProcessType.PAY_GATE);


//      Remember to change the IDs at enviroment.properties file

//        Payment Method- Phương thức thanh toán
        CaptureMoMoResponse captureMoMoResponse = CaptureMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "");

//        Transaction Query - Kiểm tra trạng thái giao dịch
        QueryStatusTransactionResponse queryStatusTransactionResponse = QueryStatusTransaction.process(environment, orderId, requestId);

//      Process Payment Result - Xử lý kết quả thanh toán
        PayGateResponse payGateResponse = PaymentResult.process(environment,new PayGateResponse());

    }

}
