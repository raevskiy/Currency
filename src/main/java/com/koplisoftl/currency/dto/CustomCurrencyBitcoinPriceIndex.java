package com.koplisoftl.currency.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomCurrencyBitcoinPriceIndex {
	@JsonProperty("rate_float")
	private float rate;
	
	public CustomCurrencyBitcoinPriceIndex() {
		super();
	}
	
	public CustomCurrencyBitcoinPriceIndex(float rate) {
		this.rate = rate;
	}
	
	public float getRate() {
		return rate;
	}
}
