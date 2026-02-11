package com.naveen.easycar.utility;

import com.naveen.easycar.dto.PaymentRequestDto;
import com.naveen.easycar.enums.PaymentMethod;

public class PaymentUtility {

    public static String extractDetails(PaymentRequestDto request) {

        if (request.getMethod() == PaymentMethod.CARD) {
            return request.getCardNumber();
        }

        if (request.getMethod() == PaymentMethod.UPI) {
            return request.getUpiId();
        }

        return "";
    }

    public static void validateConditionalFields(
            PaymentRequestDto request
    ) {

        if (request.getMethod() == PaymentMethod.CARD) {

            if (request.getCardNumber() == null ||
                    request.getCardHolderName() == null ||
                    request.getExpiryMonth() == null ||
                    request.getExpiryYear() == null) {

                throw new IllegalArgumentException(
                        "Card details are required"
                );
            }
        }

        if (request.getMethod() == PaymentMethod.UPI) {

            if (request.getUpiId() == null) {
                throw new IllegalArgumentException(
                        "UPI ID is required"
                );
            }
        }
    }

}
