package com.online.store.models;

public class PaymentInfo extends Info {

    private String cardNumber;

    private String cardCVV;

    public PaymentInfo(String firstName, String lastName, String[] address, String cardNumber, String cardCVV) {
        super(firstName, lastName, address);

        this.cardNumber = cardNumber;
        this.cardCVV = cardCVV;
    }

    public PaymentInfo() {
        super();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }
}
