package com.naveen.easycar.utility.paymentProcessor;

import com.naveen.easycar.enums.PaymentMethod;
import com.naveen.easycar.enums.PaymentStatus;

public interface PaymentProcessor {

    PaymentMethod getSupportedMethod();

    void validate(String details);

    String mask(String details);

    PaymentStatus process();
}

