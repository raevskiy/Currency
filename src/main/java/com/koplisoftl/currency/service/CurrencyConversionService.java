package com.koplisoftl.currency.service;

import java.util.List;

import com.koplisoftl.currency.entity.CurrencyConversionRate;

public interface CurrencyConversionService {
	/**
	 * Fetches the data from an external service
	 * and uses it to calculate the provided currency exchange rate to US dollar.
	 * The result is stored in the DB.
	 * @param currency - ISO 4217 currency code (3 characters)
	 */
	void storeCurrencyToUsDollarRate(String currency);
	/**
	 * Finds recent exchange rates that are stored in the DB
	 * @param size - how many entries are returned
	 * @return
	 */
	List<CurrencyConversionRate> findRecentCurrencyToUsDollarRates(int size);
}
