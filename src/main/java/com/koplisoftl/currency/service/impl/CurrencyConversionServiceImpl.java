package com.koplisoftl.currency.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.coindesk.api.dto.CustomCurrencyPriceIndexResponseDto;
import com.koplisoftl.currency.dao.CurrencyConversionRateDao;
import com.koplisoftl.currency.entity.CurrencyConversionRate;
import com.koplisoftl.currency.service.CurrencyConversionService;


@Service
public class CurrencyConversionServiceImpl implements CurrencyConversionService {
	private static final String CUSTOM_CURRENCY_PRICE_INDEX_URL = "https://api.coindesk.com/v1/bpi/currentprice/%s.json";
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private CurrencyConversionRateDao currencyRateDao;

	@Override
	public void storeCurrencyToUsDollarRate(String currency) {
		CustomCurrencyPriceIndexResponseDto response = restTemplate.getForObject(
				String.format(CUSTOM_CURRENCY_PRICE_INDEX_URL, currency), CustomCurrencyPriceIndexResponseDto.class);
		CurrencyConversionRate currencyRate = new CurrencyConversionRate(
				response.findCurrencyToUsDollarExchangeRate(currency), response.extractDateTime());
		currencyRateDao.insert(currencyRate);
	}

	@Override
	public List<CurrencyConversionRate> findRecentCurrencyToUsDollarRates(int size) {
		return currencyRateDao.findRecent(size);
	}
}
