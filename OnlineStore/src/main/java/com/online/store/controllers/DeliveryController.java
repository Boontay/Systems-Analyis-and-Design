package com.online.store.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.online.store.Constants;
import com.online.store.exceptions.UnsuccessfulDeliveryException;
import com.online.store.models.DeliveryInfo;
import com.online.store.services.DeliveryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class DeliveryController {

    private final Logger LOGGER = LoggerFactory.getLogger(DeliveryController.class);

    private DeliveryService deliveryService;

    @Autowired
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    private void validateDeliveryInfo(DeliveryInfo deliveryInfo) {
        if (deliveryService.validateDeliveryInfo(deliveryInfo)) {
            LOGGER.info("Delivery has been successful.");
        } else {
            LOGGER.error("Invalid delivery info inputted.");
            throw new UnsuccessfulDeliveryException();
        }
    }


    @PostMapping(Constants.deliveryEndpoint)
    public ResponseEntity<String> arrangeDelivery(@RequestBody String string) {
        try {

            DeliveryInfo deliveryInfo = new ObjectMapper().readValue(string, DeliveryInfo.class);

            validateDeliveryInfo(deliveryInfo);

            return ResponseEntity.ok().build();

        } catch (UnsuccessfulDeliveryException | JsonProcessingException ex) {
            LOGGER.error("Order delivery unsuccessful.");
            return ResponseEntity.badRequest().build();
        }
    }
}
