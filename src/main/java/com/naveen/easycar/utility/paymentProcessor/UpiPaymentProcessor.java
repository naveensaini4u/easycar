package com.naveen.easycar.utility.paymentProcessor;

import com.naveen.easycar.enums.PaymentMethod;
import com.naveen.easycar.enums.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class UpiPaymentProcessor implements PaymentProcessor {

    @Override
    public PaymentMethod getSupportedMethod() {
        return PaymentMethod.UPI;
    }

    @Override
    public void validate(String details) {
        if (details == null || !details.contains("@")) {
            throw new IllegalArgumentException("Invalid UPI ID");
        }
    }

    @Override
    public String mask(String details) {
        return details;
    }

    @Override
    public PaymentStatus process() {
        return PaymentStatus.SUCCESS;
    }
}

