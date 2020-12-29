package com.online.store.services;

import com.online.store.models.PaymentInfo;

public interface PaymentService {
    boolean validatePaymentInfo(PaymentInfo paymentInfo);
}
