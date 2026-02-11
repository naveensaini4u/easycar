package com.naveen.easycar.utility.paymentProcessor;

import com.naveen.easycar.enums.PaymentMethod;
import com.naveen.easycar.enums.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class CardPaymentProcessor implements PaymentProcessor {

    @Override
    public PaymentMethod getSupportedMethod() {
        return PaymentMethod.CARD;
    }

    @Override
    public void validate(String cardNumber) {

        if (cardNumber == null || cardNumber.length() < 12) {
            throw new IllegalArgumentException("Invalid card number");
        }
    }

    @Override
    public String mask(String cardNumber) {

        return "**** **** **** " +
                cardNumber.substring(cardNumber.length() - 4);
    }

    @Override
    public PaymentStatus process() {
        return PaymentStatus.SUCCESS;
    }
}


