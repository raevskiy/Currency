package com.coindesk.api.dto;

import static java.math.BigDecimal.ROUND_HALF_UP;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomCurrencyPriceIndexResponse {
	static final String US_DOLLAR = "USD";
	@JsonProperty("time")
	private CustomCurrencyTime time;
	@JsonProperty("bpi")
	private Map<String, CustomCurrencyBitcoinPriceIndex> bitcoinToCustomCurrency = new HashMap<>();
	
	public CustomCurrencyPriceIndexResponse() {
		super();
	}
	
	public CustomCurrencyPriceIndexResponse(CustomCurrencyTime time, Map<String, CustomCurrencyBitcoinPriceIndex> bitcoinToCustomCurrency) {
		this.bitcoinToCustomCurrency = bitcoinToCustomCurrency;
		this.time = time;
	}

	public BigDecimal findCurrencyToUsDollarExchangeRate(String currency) {
		BigDecimal bitcoinToUsd = extractExchangeRate(US_DOLLAR);
		BigDecimal bitcoinToCurrency = extractExchangeRate(currency);
		return bitcoinToUsd.divide(bitcoinToCurrency, 4, ROUND_HALF_UP);
	}
	
	private BigDecimal extractExchangeRate(String currency) {
		return Optional.ofNullable(bitcoinToCustomCurrency.get(currency))
				.map(CustomCurrencyBitcoinPriceIndex::getRate)
				.orElseThrow(() -> new NoSuchElementException("No data available"));
	}
	
	public Date extractDateTime() {
		return time.getDateTime();
	}
}
