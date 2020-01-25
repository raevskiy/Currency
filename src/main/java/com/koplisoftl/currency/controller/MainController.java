package com.koplisoftl.currency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koplisoftl.currency.dto.CurrencyConversionRateResponse;
import com.koplisoftl.currency.mappimg.CurrencyConversionRateMapper;
import com.koplisoftl.currency.service.CurrencyConversionService;

@Controller
public class MainController {
	@Autowired
	private CurrencyConversionService currencyConversionService;
	@Autowired
	private CurrencyConversionRateMapper currencyConversionRateMapper;
	@Value("${currencyToUsdRateCalculationJob.currency}")
	private String currencyCode;
	
	@GetMapping
	public @ResponseBody String showHint() {
		return "Shows currency exchange rate at /recent";
	}

	@GetMapping(path = "/recent")
	public @ResponseBody CurrencyConversionRateResponse findRecentCurrencyRates() {
		return new CurrencyConversionRateResponse(
				currencyCode + " to USD",
				currencyConversionRateMapper.mapeEntitiesToDtos(currencyConversionService.findRecentCurrencyToUsDollarRates(10)));
	}

}
