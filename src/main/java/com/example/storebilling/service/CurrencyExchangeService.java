package com.example.storebilling.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyExchangeService {

    @Value("${currency.exchange.api.url}")
    private String apiUrl;

    @Value("${currency.exchange.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    public double convertCurrency(String originalCurrency, String targetCurrency, double amount) {
        double exchangeRate = getExchangeRate(originalCurrency, targetCurrency);
        return amount * exchangeRate;
    }

    // Method to fetch the exchange rate from the external API
    private double getExchangeRate(String fromCurrency, String toCurrency) {
        String url = String.format("%s/%s/pair/%s/%s", apiUrl, apiKey, fromCurrency, toCurrency);
        ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);

        if (response != null) {
            return response.getConversionRate();
        }
        return 1.0;
    }

    @Data
    static class ExchangeRateResponse {
        private String result;
        @JsonProperty("conversion_rate")
        private double conversionRate;

    }
}
