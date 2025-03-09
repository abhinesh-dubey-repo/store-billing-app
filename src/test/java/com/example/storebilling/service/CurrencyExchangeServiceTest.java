package com.example.storebilling.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CurrencyExchangeServiceTest {

    @InjectMocks
    private CurrencyExchangeService currencyExchangeService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(currencyExchangeService, "apiUrl", "https://mock-api.com");
        ReflectionTestUtils.setField(currencyExchangeService, "apiKey", "mock-api-key");
    }

    @Test
    public void testConvertCurrency() {
        String fromCurrency = "USD";
        String toCurrency = "INR";
        double amount = 100.0;
        double exchangeRate = 82.5;
        double expectedConvertedAmount = amount * exchangeRate;

        // Mock API response
        CurrencyExchangeService.ExchangeRateResponse mockResponse = new CurrencyExchangeService.ExchangeRateResponse();
        mockResponse.setConversionRate(exchangeRate);

        String mockUrl = String.format("https://mock-api.com/mock-api-key/pair/%s/%s", fromCurrency, toCurrency);
        Mockito.when(restTemplate.getForObject(mockUrl, CurrencyExchangeService.ExchangeRateResponse.class))
                .thenReturn(mockResponse);

        // Execute method and assert the result
        double actualConvertedAmount = currencyExchangeService.convertCurrency(fromCurrency, toCurrency, amount);
        Assertions.assertEquals(expectedConvertedAmount, actualConvertedAmount, 0.01);

        // Verify that RestTemplate was called exactly once
        Mockito.verify(restTemplate, Mockito.times(1)).getForObject(mockUrl, CurrencyExchangeService.ExchangeRateResponse.class);
    }

    @Test
    public void testConvertCurrency_DefaultRateWhenApiFails() {
        String fromCurrency = "USD";
        String toCurrency = "EUR";
        double amount = 100.0;

        // Mock API failure (null response)
        String mockUrl = String.format("https://mock-api.com/mock-api-key/pair/%s/%s", fromCurrency, toCurrency);
        Mockito.when(restTemplate.getForObject(mockUrl, CurrencyExchangeService.ExchangeRateResponse.class))
                .thenReturn(null);

        // Execute method and assert that default exchange rate (1.0) is used
        double actualConvertedAmount = currencyExchangeService.convertCurrency(fromCurrency, toCurrency, amount);
        Assertions.assertEquals(amount, actualConvertedAmount, 0.01);

        // Verify that RestTemplate was called exactly once
        Mockito.verify(restTemplate, Mockito.times(1)).getForObject(mockUrl, CurrencyExchangeService.ExchangeRateResponse.class);
    }
}
