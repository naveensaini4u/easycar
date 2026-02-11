package com.naveen.easycar.utility.paymentProcessor;

import com.naveen.easycar.enums.PaymentMethod;
import com.naveen.easycar.enums.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class CashPaymentProcessor implements PaymentProcessor {

    @Override
    public PaymentMethod getSupportedMethod() {
        return PaymentMethod.CASH;
    }

    @Override
    public void validate(String details) {
        // no validation needed
    }

    @Override
    public String mask(String details) {
        return "CASH_COUNTER";
    }

    @Override
    public PaymentStatus process() {
        return PaymentStatus.SUCCESS;
    }
}

