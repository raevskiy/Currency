package com.coindesk.api.dto;

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

class CustomCurrencyPriceIndexResponseDtoTest {
	private static final String US_DOLLAR = "USD";
	private static final String GB_POUND = "GBP";

	@Test
	void findsCurrencyToUsDollarExchangeRate() {
		Map<String, CustomCurrencyBitcoinPriceIndexDto> bitcoinToCustomCurrency = new HashMap<>();
		bitcoinToCustomCurrency.put(US_DOLLAR, new CustomCurrencyBitcoinPriceIndexDto(BigDecimal.TEN));
		bitcoinToCustomCurrency.put(GB_POUND, new CustomCurrencyBitcoinPriceIndexDto(BigDecimal.valueOf(5)));

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
		CustomCurrencyPriceIndexResponseDto response = new CustomCurrencyPriceIndexResponseDto(
				new CustomCurrencyTimeDto(now),
				new HashMap<>());
		
		assertThat(response.extractDateTime()).isEqualTo(now);
	}

	private CustomCurrencyPriceIndexResponseDto createResponse(
			Map<String, CustomCurrencyBitcoinPriceIndexDto> bitcoinToCustomCurrency) {
		return new CustomCurrencyPriceIndexResponseDto(new CustomCurrencyTimeDto(), bitcoinToCustomCurrency);
	}
	
	private CustomCurrencyPriceIndexResponseDto createInvalidResponse(String currencyCode) {
		Map<String, CustomCurrencyBitcoinPriceIndexDto> bitcoinToCustomCurrency = new HashMap<>();
		bitcoinToCustomCurrency.put(currencyCode, new CustomCurrencyBitcoinPriceIndexDto(BigDecimal.valueOf(5)));
		return createResponse(bitcoinToCustomCurrency);
	}
}
