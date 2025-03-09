package com.example.storebilling.service;

import com.example.storebilling.enums.DiscountType;
import com.example.storebilling.model.BillRequest;
import com.example.storebilling.model.BillResponse;
import com.example.storebilling.model.Item;
import com.example.storebilling.util.BillingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillingService {
    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    public BillResponse calculateNetPayableAmount(BillRequest billRequest) {
        double netAmount = calculateFinalAmount(billRequest);
        double convertedAmount = currencyExchangeService.convertCurrency(
                billRequest.getOriginalCurrency(), billRequest.getTargetCurrency(), netAmount);

        return new BillResponse(convertedAmount, billRequest.getTargetCurrency());
    }

    private double calculateFinalAmount(BillRequest billRequest) {

        List<Item> items = billRequest.getItems();

        double totalBill = billRequest.getTotalAmount();

        double percentageDiscount = getBestPercentageDiscount(billRequest);
        // Apply percentage discount (only on non-grocery items)
        double nonGroceryTotal = items.stream()
                .filter(item -> !item.getCategory().equalsIgnoreCase("grocery"))
                .mapToDouble(Item::getPrice)
                .sum();

        double discountAmount = nonGroceryTotal * percentageDiscount;

        // Apply fixed discount
        double fixedDiscount = calculateFixedDiscount(totalBill);

        // Final amount after all discounts
        return totalBill - discountAmount - fixedDiscount;
    }

    // Calculate fixed discount ($5 for every $100 spent)
    private double calculateFixedDiscount(double totalBill) {
        return (totalBill / 100) * DiscountType.FIXED_DISCOUNT.getDiscountValue();
    }

    private double getBestPercentageDiscount(BillRequest billRequest) {
        double discount = 0;

        if (BillingUtil.isEmployee(billRequest)) {
            discount = Math.max(discount, DiscountType.EMPLOYEE.getDiscountValue());
        }

        if (BillingUtil.isAffiliate(billRequest)) {
            discount = Math.max(discount, DiscountType.AFFILIATE.getDiscountValue());
        }

        if (BillingUtil.isMembershipMoreThanTwoYears(billRequest)) {
            discount = Math.max(discount, DiscountType.CUSTOMER_2_YEARS.getDiscountValue());
        }

        return discount;
    }
}