package com.koplisoftl.currency.dto;

import java.util.Collection;

public class CurrencyConversionRateResponseDto {
	private String description;
	private Collection<CurrencyConversionRateDto> conversionRates;
	
	public CurrencyConversionRateResponseDto() {
		super();
	}
	
	public CurrencyConversionRateResponseDto(String description, Collection<CurrencyConversionRateDto> conversionRates) {
		this.description = description;
		this.conversionRates = conversionRates;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Collection<CurrencyConversionRateDto> getConversionRates() {
		return conversionRates;
	}
}
