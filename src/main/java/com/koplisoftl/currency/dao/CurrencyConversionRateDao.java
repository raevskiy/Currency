package com.koplisoftl.currency.dao;

import java.util.List;

import com.koplisoftl.currency.entity.CurrencyConversionRate;

public interface CurrencyConversionRateDao {
	List<CurrencyConversionRate> findRecent(int size);
	void insert(CurrencyConversionRate currencyRate);
}
