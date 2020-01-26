package com.coindesk.api.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomCurrencyBitcoinPriceIndex {
	@JsonProperty("rate_float")
	private BigDecimal rate;
	
	public CustomCurrencyBitcoinPriceIndex() {
		super();
	}
	
	public CustomCurrencyBitcoinPriceIndex(BigDecimal rate) {
		this.rate = rate;
	}
	
	public BigDecimal getRate() {
		return rate;
	}
}
