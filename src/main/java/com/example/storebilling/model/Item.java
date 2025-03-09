package com.example.storebilling.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Item {
    @NonNull
    private String name;
    @NonNull
    private String category;
    @NonNull
    private double price;
}
