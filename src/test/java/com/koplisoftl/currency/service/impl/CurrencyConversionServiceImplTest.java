package com.koplisoftl.currency.service.impl;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.koplisoftl.currency.dao.CurrencyConversionRateDao;

@ExtendWith(MockitoExtension.class)
class CurrencyConversionServiceImplTest {
	@InjectMocks
	private CurrencyConversionServiceImpl service = new CurrencyConversionServiceImpl();
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private CurrencyConversionRateDao currencyRateDao;


	@Test
	void testStoreCurrencyToUsDollarRate() {
		fail("Not yet implemented");
	}

	@Test
	void testFindRecentCurrencyToUsDollarRates() {
		fail("Not yet implemented");
	}

}
