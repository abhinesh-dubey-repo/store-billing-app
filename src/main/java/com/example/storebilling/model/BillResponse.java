package com.example.storebilling.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BillResponse {
    private double netPayableAmount;
    private String targetCurrency;
}
