package com.online.store.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.store.exceptions.UnsuccessfulPaymentException;
import com.online.store.models.PaymentInfo;
import com.online.store.services.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PaymentController {

    private final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    private PaymentService paymentService;

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    private void validatePaymentInfo(PaymentInfo paymentInfo) {
        if (paymentService.validatePaymentInfo(paymentInfo)) {
            LOGGER.info("Payment has been successful.");
        } else {
            LOGGER.error("Invalid payment info inputted.");
            throw new UnsuccessfulPaymentException();
        }
    }


    @PostMapping("/pay-for-order")
    public ResponseEntity<String> payForOrder(@RequestBody String string) {
        try {

            PaymentInfo paymentInfo = new ObjectMapper().readValue(string, PaymentInfo.class);

            validatePaymentInfo(paymentInfo);

            return ResponseEntity.ok().build();

        } catch (UnsuccessfulPaymentException | JsonProcessingException ex) {
            LOGGER.error("Order payment unsuccessful.");
            return ResponseEntity.badRequest().build();
        }
    }
}
