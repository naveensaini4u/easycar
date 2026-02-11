package com.naveen.easycar.dto;

import com.naveen.easycar.enums.PaymentMethod;
import jakarta.validation.constraints.*;

public class PaymentRequestDto {
    @NotNull(message = "Payment method is required")
    private PaymentMethod method;

    // CARD fields
    @Pattern(regexp = "\\d{12,19}", message = "Invalid card number")
    private String cardNumber;

    @Size(min = 2, message = "Card holder name too short")
    private String cardHolderName;

    @Pattern(regexp = "^(0[1-9]|1[0-2])$", message = "Invalid expiry month")
    private String expiryMonth;

    @Pattern(regexp = "^20\\d{2}$", message = "Invalid expiry year")
    private String expiryYear;

    // UPI field
    @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z]+$",
            message = "Invalid UPI ID")
    private String upiId;

    // getters & setters

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }
}
