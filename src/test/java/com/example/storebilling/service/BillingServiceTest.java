package com.example.storebilling.service;

import com.example.storebilling.model.BillRequest;
import com.example.storebilling.model.BillResponse;
import com.example.storebilling.model.Item;
import com.example.storebilling.util.BillingUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BillingServiceTest {

    @InjectMocks
    private BillingService billingService;

    @Mock
    private CurrencyExchangeService currencyExchangeService;

    @Test
    public void testCalculateNetPayableAmount() {
        // Mocking request
        BillRequest billRequest = new BillRequest();
        billRequest.setOriginalCurrency("USD");
        billRequest.setTargetCurrency("INR");
        billRequest.setTotalAmount(200.0);
        billRequest.setItems(List.of(
                new Item("Laptop", "electronics", 150.0),
                new Item("Milk", "grocery", 50.0)
        ));

        // Mocking currency conversion
        Mockito.when(currencyExchangeService.convertCurrency("USD", "INR", 190.0)).thenReturn(15600.0);

        // Execute the method
        BillResponse response = billingService.calculateNetPayableAmount(billRequest);

        // Assertions
        Assertions.assertEquals(15600.0, response.getNetPayableAmount());
        Assertions.assertEquals("INR", response.getTargetCurrency());

        // Verify that currency conversion was called once
        Mockito.verify(currencyExchangeService, Mockito.times(1)).convertCurrency("USD", "INR", 190.0);
    }

    @Test
    public void testCalculateFinalAmount_WithDiscounts() {
        // Mocking request
        BillRequest billRequest = new BillRequest();
        billRequest.setTotalAmount(300.0);
        billRequest.setItems(List.of(
                new Item("Phone", "electronics", 200.0),
                new Item("Rice", "grocery", 100.0)
        ));

        // Mocking BillingUtil responses
        try (MockedStatic<BillingUtil> mockedUtil = Mockito.mockStatic(BillingUtil.class)) {
            mockedUtil.when(() -> BillingUtil.isEmployee(Mockito.any())).thenReturn(true);
            mockedUtil.when(() -> BillingUtil.isAffiliate(Mockito.any())).thenReturn(false);
            mockedUtil.when(() -> BillingUtil.isMembershipMoreThanTwoYears(Mockito.any())).thenReturn(false);

            // Execute private method via reflection
            double finalAmount = ReflectionTestUtils.invokeMethod(billingService, "calculateFinalAmount", billRequest);


            Assertions.assertEquals(225.0, finalAmount);
        }
    }
}

