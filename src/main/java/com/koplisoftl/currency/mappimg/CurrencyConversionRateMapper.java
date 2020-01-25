package com.koplisoftl.currency.mappimg;

import java.util.List;

import org.mapstruct.Mapper;

import com.koplisoftl.currency.dto.CurrencyConversionRate;

@Mapper(componentModel = "spring")
public interface CurrencyConversionRateMapper {
	CurrencyConversionRate mapEntityToDto(com.koplisoftl.currency.entity.CurrencyConversionRate currencyConversionRate);
	List<CurrencyConversionRate> mapeEntitiesToDtos(List<com.koplisoftl.currency.entity.CurrencyConversionRate> currencyConversionRate);

}
