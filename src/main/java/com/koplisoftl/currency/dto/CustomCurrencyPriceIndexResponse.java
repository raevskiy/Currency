package com.koplisoftl.currency.dto;

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
	@JsonProperty("time")
	private CustomCurrencyTime time;
	@JsonProperty("bpi")
	private Map<String, CustomCurrencyBitcoinPriceIndex> bitcoinToCustomCurrency = new HashMap<>();
	
	public void setBitcoinToCustomCurrency(Map<String, CustomCurrencyBitcoinPriceIndex> bitcoinToCustomCurrency) {
		this.bitcoinToCustomCurrency = bitcoinToCustomCurrency;
	}

	public BigDecimal findCurrencyToUsDollarExchangeRate(String currency) {
		return Optional.ofNullable(bitcoinToCustomCurrency.get("USD"))
				.map(CustomCurrencyBitcoinPriceIndex::getRate)
				.map(rate -> BigDecimal.valueOf(rate / bitcoinToCustomCurrency.get(currency).getRate()))
				.orElseThrow(() -> new NoSuchElementException("No data available"));
	}
	
	public Date getDateTime() {
		return time.getDateTime();
	}
}
