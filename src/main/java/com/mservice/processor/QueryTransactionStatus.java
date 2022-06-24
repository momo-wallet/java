package com.mservice.processor;

import com.mservice.models.PaymentRequest;
import com.mservice.models.PaymentResponse;
import com.mservice.config.Environment;
import com.mservice.shared.exception.MoMoException;

public class QueryTransactionStatus extends AbstractProcess<PaymentRequest, PaymentResponse> {
    public QueryTransactionStatus(Environment environment) {
        super(environment);
    }

    @Override
    public PaymentResponse execute(PaymentRequest request) throws MoMoException {
        return null;
    }
}
