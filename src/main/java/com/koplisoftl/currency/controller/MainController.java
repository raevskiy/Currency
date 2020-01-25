package com.koplisoftl.currency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koplisoftl.currency.dto.CurrencyConversionRateResponse;
import com.koplisoftl.currency.mappimg.CurrencyConversionRateMapper;
import com.koplisoftl.currency.service.CurrencyConversionService;

@Controller
public class MainController {
	private static final String RECENT_PATH = "/recent";
	@Autowired
	private CurrencyConversionService currencyConversionService;
	@Autowired
	private CurrencyConversionRateMapper currencyConversionRateMapper;
	@Value("${currencyToUsdRateCalculationJob.currency}")
	private String currencyCode;
	@Value("${currencyToUsdRateCalculationJob.recent.size}")
	private int recentSize;
	
	@GetMapping
	public @ResponseBody String showHint() {
		return "Shows currency exchange rate at " + RECENT_PATH;
	}

	@GetMapping(path = RECENT_PATH)
	public @ResponseBody CurrencyConversionRateResponse findRecentCurrencyRates() {
		return new CurrencyConversionRateResponse(
				currencyCode + " to USD",
				currencyConversionRateMapper.mapeEntitiesToDtos(currencyConversionService.findRecentCurrencyToUsDollarRates(recentSize)));
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
	    return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
