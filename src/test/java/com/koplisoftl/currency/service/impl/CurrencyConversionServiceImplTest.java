package com.koplisoftl.currency.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.koplisoftl.currency.dao.CurrencyConversionRateDao;
import com.koplisoftl.currency.dto.CustomCurrencyPriceIndexResponse;
import com.koplisoftl.currency.entity.CurrencyConversionRate;

@ExtendWith(MockitoExtension.class)
class CurrencyConversionServiceImplTest {
	@InjectMocks
	private CurrencyConversionServiceImpl service = new CurrencyConversionServiceImpl();
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private CurrencyConversionRateDao currencyRateDao;


	@Test
	void storeCurrencyToUsDollarRate() {
		BigDecimal gbpToUsd = BigDecimal.valueOf(1.31);
		CustomCurrencyPriceIndexResponse response = mock(CustomCurrencyPriceIndexResponse.class);
		when(response.findCurrencyToUsDollarExchangeRate("GBP")).thenReturn(gbpToUsd);
		Date now = Date.valueOf(LocalDate.now());
		when(response.getDateTime()).thenReturn(now);
		when(restTemplate.getForObject("https://api.coindesk.com/v1/bpi/currentprice/GBP.json",
				CustomCurrencyPriceIndexResponse.class)).thenReturn(response);
		
		service.storeCurrencyToUsDollarRate("GBP");
		
		ArgumentCaptor<CurrencyConversionRate> captor = ArgumentCaptor.forClass(CurrencyConversionRate.class);
		verify(currencyRateDao).insert(captor.capture());
		CurrencyConversionRate rate = captor.getValue();
		assertThat(rate.getRate()).isEqualTo(gbpToUsd);
		assertThat(rate.getDateTime()).isEqualTo(now);
	}

	@Test
	void testFindRecentCurrencyToUsDollarRates() {
		List<CurrencyConversionRate> recent = Lists.newArrayList(new CurrencyConversionRate());
		when(currencyRateDao.findRecent(100)).thenReturn(recent);
		
		assertThat(service.findRecentCurrencyToUsDollarRates(100)).isEqualTo(recent);
	}

}
