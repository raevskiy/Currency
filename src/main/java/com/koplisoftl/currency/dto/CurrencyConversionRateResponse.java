package com.koplisoftl.currency.dto;

import java.util.Collection;

public class CurrencyConversionRateResponse {
	private String description;
	private Collection<CurrencyConversionRate> conversionRates;
	
	public CurrencyConversionRateResponse() {
		super();
	}
	
	public CurrencyConversionRateResponse(String description, Collection<CurrencyConversionRate> conversionRates) {
		this.description = description;
		this.conversionRates = conversionRates;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Collection<CurrencyConversionRate> getConversionRates() {
		return conversionRates;
	}
}
