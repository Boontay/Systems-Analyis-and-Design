package com.online.store.services;

import com.online.store.exceptions.UnsuccessfulDeliveryException;
import com.online.store.models.DeliveryInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DeliveryServiceImplementation implements DeliveryService {

    private final Logger LOGGER = LoggerFactory.getLogger(DeliveryServiceImplementation.class);

    @Override
    public boolean validateDeliveryInfo(DeliveryInfo deliveryInfo) {
        if (!validateFirstName(deliveryInfo.getFirstName())) {
            throwInvalidDeliveryInfoException("first name", deliveryInfo.getFirstName());
            return false;
        }

        if (!validateLastName(deliveryInfo.getLastName())) {
            throwInvalidDeliveryInfoException("last name", deliveryInfo.getLastName());
            return false;
        }

        if (!validateAddress(deliveryInfo.getAddress())) {
            throwInvalidDeliveryInfoException("address", Arrays.toString(deliveryInfo.getAddress()));
            return false;
        }

        return true;
    }

    private boolean validateFirstName(String firstName) {
        String regex = "[A-Z][a-z]*";

        return checkDeliveryInfoRegex(regex, firstName);
    }

    private boolean validateLastName(String lastName) {
        String regex = "[A-Z][a-z]*";

        return checkDeliveryInfoRegex(regex, lastName);
    }

    private boolean validateAddress(String[] address) {
        if (address.length > 5) {

            return false;
        } else {
            String regex = "^[a-zA-Z0-9 ,]+$";

            for (String s : address) {
                if (!checkDeliveryInfoRegex(regex, s)) {
                    return false;
                }
            }
        }

        return true;
    }

    private void throwInvalidDeliveryInfoException(String deliveryInfo, String invalidData) {
        LOGGER.error("Invalid {} : {}.", deliveryInfo, invalidData);
        throw new UnsuccessfulDeliveryException();
    }

    private boolean checkDeliveryInfoRegex(String regex, String validatingData) {
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(validatingData);

        if (matcher.matches()) {
            return true;
        } else {
            System.out.println(validatingData);
            return false;
        }
    }
}
