package com.koplisoftl.currency.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koplisoftl.currency.dto.CurrencyConversionRate;

@Controller
public class MainController {
	@GetMapping
	public @ResponseBody String showHint() {
		return "Shows currency exchange rate at /recent";
	}

	@GetMapping(path = "/recent")
	public @ResponseBody Collection<CurrencyConversionRate> findEuroToUsDollarRecentRates() {
		Collection<CurrencyConversionRate> result = new ArrayList<>();
		result.add(new CurrencyConversionRate());
		return result;
	}

}
