package com.example.storebilling.controller;

import com.example.storebilling.model.BillRequest;
import com.example.storebilling.model.BillResponse;
import com.example.storebilling.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @PostMapping("/calculate")
    public BillResponse calculateBill(@RequestBody BillRequest billRequest) {
        return billingService.calculateNetPayableAmount(billRequest);
    }
}