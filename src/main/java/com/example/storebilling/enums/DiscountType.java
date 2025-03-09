package com.example.storebilling.enums;

public enum DiscountType {
    EMPLOYEE(0.30),
    AFFILIATE(0.10),
    CUSTOMER_2_YEARS(0.05),
    FIXED_DISCOUNT(5);

    private final double discountValue;
    DiscountType(double discountValue) {
        this.discountValue = discountValue;
    }

    public double getDiscountValue() {
        return discountValue;
    }
}
