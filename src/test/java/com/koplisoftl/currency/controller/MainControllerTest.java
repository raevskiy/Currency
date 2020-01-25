package com.koplisoftl.currency.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import com.koplisoftl.currency.dto.CurrencyConversionRateResponse;
import com.koplisoftl.currency.mappimg.CurrencyConversionRateMapper;
import com.koplisoftl.currency.service.CurrencyConversionService;

@ExtendWith(MockitoExtension.class)
class MainControllerTest {
	@InjectMocks
	private MainController controller;
	@Mock
	private CurrencyConversionService currencyConversionService;
	@Mock
	private CurrencyConversionRateMapper currencyConversionRateMapper;

	@Test
	void showHint() {
		assertThat(controller.showHint()).isEqualTo("Shows currency exchange rate at /recent");
	}

	@Test
	void findRecentCurrencyRates() throws NoSuchFieldException, SecurityException {
		ReflectionTestUtils.setField(controller, "currencyCode", "GBP");
		ReflectionTestUtils.setField(controller, "recentSize", 5);
		
		CurrencyConversionRateResponse response = controller.findRecentCurrencyRates();
		
		assertThat(response.getDescription()).isEqualTo("GBP to USD");
		verify(currencyConversionService).findRecentCurrencyToUsDollarRates(5);
		verify(currencyConversionRateMapper).mapeEntitiesToDtos(Mockito.anyList());
	}
	
	@Test
	void handlesExceptions() {
		RuntimeException exception = new RuntimeException();
		
		ResponseEntity<Exception> responseEntity = controller.handleAllExceptions(exception);
		
		assertThat(responseEntity.getBody()).isEqualTo(exception);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
