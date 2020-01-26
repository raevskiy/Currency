package com.coindesk.api.dto;

import static com.coindesk.api.dto.CustomCurrencyPriceIndexResponse.US_DOLLAR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import com.coindesk.api.dto.CustomCurrencyBitcoinPriceIndex;
import com.coindesk.api.dto.CustomCurrencyPriceIndexResponse;
import com.coindesk.api.dto.CustomCurrencyTime;

class CustomCurrencyPriceIndexResponseTest {
	private static final String GB_POUND = "GBP";

	@Test
	void findsCurrencyToUsDollarExchangeRate() {
		Map<String, CustomCurrencyBitcoinPriceIndex> bitcoinToCustomCurrency = new HashMap<>();
		bitcoinToCustomCurrency.put(US_DOLLAR, new CustomCurrencyBitcoinPriceIndex(BigDecimal.TEN));
		bitcoinToCustomCurrency.put(GB_POUND, new CustomCurrencyBitcoinPriceIndex(BigDecimal.valueOf(5)));

		assertThat(createResponse(bitcoinToCustomCurrency).findCurrencyToUsDollarExchangeRate(GB_POUND))
				.isEqualByComparingTo(BigDecimal.valueOf(2));
	}

	@Test
	void throwsExceptionIfUsdIsAbsentInResponse() {
		Throwable throwable = catchThrowable(() -> createInvalidResponse(GB_POUND).findCurrencyToUsDollarExchangeRate(GB_POUND));
		
		assertThat(throwable).isInstanceOf(NoSuchElementException.class).hasMessage("No data available");
	}
	
	@Test
	void throwsExceptionIfCurrencyIsAbsentInResponse() {
		Throwable throwable = catchThrowable(() -> createInvalidResponse(US_DOLLAR).findCurrencyToUsDollarExchangeRate(GB_POUND));
		
		assertThat(throwable).isInstanceOf(NoSuchElementException.class).hasMessage("No data available");
	}
	
	@Test
	void extractsDateTime() {
		Date now = Timestamp.valueOf(LocalDateTime.now());
		CustomCurrencyPriceIndexResponse response = new CustomCurrencyPriceIndexResponse(
				new CustomCurrencyTime(now),
				new HashMap<>());
		
		assertThat(response.extractDateTime()).isEqualTo(now);
	}

	private CustomCurrencyPriceIndexResponse createResponse(
			Map<String, CustomCurrencyBitcoinPriceIndex> bitcoinToCustomCurrency) {
		return new CustomCurrencyPriceIndexResponse(new CustomCurrencyTime(), bitcoinToCustomCurrency);
	}
	
	private CustomCurrencyPriceIndexResponse createInvalidResponse(String currencyCode) {
		Map<String, CustomCurrencyBitcoinPriceIndex> bitcoinToCustomCurrency = new HashMap<>();
		bitcoinToCustomCurrency.put(currencyCode, new CustomCurrencyBitcoinPriceIndex(BigDecimal.valueOf(5)));
		return createResponse(bitcoinToCustomCurrency);
	}
}
