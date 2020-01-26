package com.koplisoftl.currency.controller;

import java.util.Optional;

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
	static final String RECENT_PATH = "/recent";
	static final String HINT = "Shows currency exchange rate at " + RECENT_PATH;
	static final String INTERNAL_ERROR = "Internal Server Error. We are fixing it right now";
	
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
		return HINT;
	}

	@GetMapping(path = RECENT_PATH)
	public @ResponseBody CurrencyConversionRateResponse findRecentCurrencyRates() {
		return new CurrencyConversionRateResponse(
				currencyCode + " to USD",
				currencyConversionRateMapper.mapeEntitiesToDtos(currencyConversionService.findRecentCurrencyToUsDollarRates(recentSize)));
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleAllExceptions(RuntimeException exception) {
	    return new ResponseEntity<String>(
	    		Optional.ofNullable(exception.getMessage()).orElse(INTERNAL_ERROR),
	    		HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
