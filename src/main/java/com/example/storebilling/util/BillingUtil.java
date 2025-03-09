package com.example.storebilling.util;

import com.example.storebilling.enums.UserType;
import com.example.storebilling.model.BillRequest;

public class BillingUtil {

    public static boolean isEmployee(BillRequest billRequest) {
        return UserType.EMPLOYEE.toString().equalsIgnoreCase(billRequest.getUserType());
    }

    public static boolean isAffiliate(BillRequest billRequest) {
        return UserType.AFFILIATE.toString().equalsIgnoreCase(billRequest.getUserType());
    }

    public static boolean isMembershipMoreThanTwoYears(BillRequest billRequest) {
        return billRequest.getCustomerTenure() > 2;
    }
}
