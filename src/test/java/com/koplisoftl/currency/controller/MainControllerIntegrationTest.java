package com.koplisoftl.currency.controller;

import static org.assertj.core.util.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalToObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.koplisoftl.currency.dto.CurrencyConversionRate;
import com.koplisoftl.currency.mappimg.CurrencyConversionRateMapper;
import com.koplisoftl.currency.service.CurrencyConversionService;

@WebMvcTest(MainController.class)
class MainControllerIntegrationTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private CurrencyConversionService currencyConversionService;
	@MockBean
	private CurrencyConversionRateMapper currencyConversionRateMapper;

	@Test
	void showsHint() throws Exception {
		mockMvc.perform(get("")).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(equalToObject(MainController.HINT)));
	}

	@Test
	void findsRecentCurrencyRates() throws Exception {
		when(currencyConversionRateMapper.mapeEntitiesToDtos(Mockito.anyList())).thenReturn(
				newArrayList(new CurrencyConversionRate(BigDecimal.TEN, Timestamp.valueOf("2020-01-26 15:43:00"))));

		mockMvc.perform(get(MainController.RECENT_PATH)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(equalToObject(
						"{\"description\":\"EUR to USD\",\"conversionRates\":[{\"rate\":10,\"dateTime\":\"2020-01-26T13:43:00.000+0000\"}]}")));
	}
	
	@Test
	void handlesInternalError() throws Exception {
		RuntimeException exception = new RuntimeException("Something went terribly wrong");
		when(currencyConversionRateMapper.mapeEntitiesToDtos(Mockito.anyList())).thenThrow(exception);

		mockMvc.perform(get(MainController.RECENT_PATH)).andDo(print())
				.andExpect(status().is5xxServerError())
				.andExpect(content().string(containsString(exception.getMessage())));
	}
}
