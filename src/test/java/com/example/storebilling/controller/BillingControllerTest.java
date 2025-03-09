package com.example.storebilling.controller;

import com.example.storebilling.model.BillRequest;
import com.example.storebilling.model.BillResponse;
import com.example.storebilling.model.Item;
import com.example.storebilling.service.BillingService;
import com.example.storebilling.util.BillingUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BillingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BillingService billingService;

    @MockBean
    private BillingUtil billingUtil;

    @Test
    public void testCalculateBill() throws Exception {
        double totalAmount = 640.0;
        String userType = "Employee";
        int customerTenure = 1;
        String originalCurrency = "EUR";
        String targetCurrency =  "USD";

        double netPayableAmount = 452.94479999999993;

        List<Item> items = Arrays.asList(
                new Item("Laptop", "Appliances", 320),
                new Item("Headphones", "Electronics", 120),
                new Item("Sauces", "Grocery", 200.0)
        );
        BillRequest billRequest = new BillRequest(items, totalAmount, userType, customerTenure, originalCurrency, targetCurrency);

        BillResponse billResponse = new BillResponse(netPayableAmount, targetCurrency);

        Mockito.when(billingService.calculateNetPayableAmount(billRequest)).thenReturn(billResponse);

        mockMvc.perform(post("/api/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(billRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.netPayableAmount").value(452.94479999999993))
                .andExpect(jsonPath("$.targetCurrency").value("USD")
        );

        Mockito.verify(billingService, Mockito.times(1)).calculateNetPayableAmount(Mockito.any(BillRequest.class));
    }


}
