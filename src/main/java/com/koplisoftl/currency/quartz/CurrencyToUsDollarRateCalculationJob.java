package com.koplisoftl.currency.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.koplisoftl.currency.service.CurrencyConversionService;

public class CurrencyToUsDollarRateCalculationJob implements Job {
	@Autowired
	private CurrencyConversionService currencyConversionService;
	@Value("${currencyToUsdRateCalculationJob.currency}")
	private String currencyCode;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		currencyConversionService.storeCurrencyToUsDollarRate(currencyCode);
	}

}
