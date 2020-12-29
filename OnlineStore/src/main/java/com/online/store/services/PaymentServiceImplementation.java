package com.online.store.services;

import com.online.store.exceptions.UnsuccessfulPaymentException;
import com.online.store.models.PaymentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PaymentServiceImplementation implements PaymentService {

    private final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImplementation.class);

    @Override
    public boolean validatePaymentInfo(PaymentInfo paymentInfo) {
        if (!validateFirstName(paymentInfo.getFirstName())) {
            throwInvalidPaymentInfoException("first name", paymentInfo.getFirstName());
            return false;
        }

        if (!validateLastName(paymentInfo.getLastName())) {
            throwInvalidPaymentInfoException("last name", paymentInfo.getLastName());
            return false;
        }

        if (!validateAddress(paymentInfo.getAddress())) {
            throwInvalidPaymentInfoException("address", Arrays.toString(paymentInfo.getAddress()));
            return false;
        }

        if (!validateCardNumber(paymentInfo.getCardNumber())) {
            throwInvalidPaymentInfoException("card number", paymentInfo.getCardNumber());
            return false;
        }

        if (!validateCardCVV(paymentInfo.getCardCVV())) {
            throwInvalidPaymentInfoException("card CVV", paymentInfo.getCardCVV());
            return false;
        }

        return true;
    }

    private boolean validateFirstName(String firstName) {
        String regex = "[A-Z][a-z]*";

        return checkPaymentInfoRegex(regex, firstName);
    }

    private boolean validateLastName(String lastName) {
        String regex = "[A-Z][a-z]*";

        return checkPaymentInfoRegex(regex, lastName);
    }

    private boolean validateAddress(String[] address) {
        if (address.length > 5) {

            return false;
        } else {
            String regex = "^[a-zA-Z0-9 ,]+$";

            for (String s : address) {
                if (!checkPaymentInfoRegex(regex, s)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean validateCardNumber(String cardNumber) {
        String regex = "^[0-9]{12}$";

        String validatingCardStringNumber = cardNumber.replaceAll("-", "");

        return checkPaymentInfoRegex(regex, validatingCardStringNumber);
    }

    private boolean validateCardCVV(String cardCVV) {
        String regex = "^[0-9]{3}$";

        return checkPaymentInfoRegex(regex, cardCVV);
    }

    private void throwInvalidPaymentInfoException(String paymentInfo, String invalidData) {
        LOGGER.error("Invalid {} : {}.", paymentInfo, invalidData);
        throw new UnsuccessfulPaymentException();
    }

    private boolean checkPaymentInfoRegex(String regex, String validatingData) {
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(validatingData);

        return matcher.matches();
    }
}
