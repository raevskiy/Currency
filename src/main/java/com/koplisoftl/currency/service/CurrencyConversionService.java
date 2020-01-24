package com.koplisoftl.currency.service;

import java.util.List;

import com.koplisoftl.currency.entity.CurrencyConversionRate;

public interface CurrencyConversionService {
	void storeCurrencyToUsDollarRate(String currency);
	List<CurrencyConversionRate> findRecentCurrencyToUsDollarRates(int size);
}
