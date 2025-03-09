package com.example.storebilling.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillRequest {
    private List<Item> items;
    @NonNull
    private double totalAmount;
    private String userType;
    private int customerTenure;
    @NonNull
    private String originalCurrency;
    @NonNull
    private String targetCurrency;

}
