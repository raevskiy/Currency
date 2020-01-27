package com.koplisoftl.currency.mappimg;

import java.util.List;

import org.mapstruct.Mapper;

import com.koplisoftl.currency.dto.CurrencyConversionRateDto;
import com.koplisoftl.currency.entity.CurrencyConversionRate;

@Mapper(componentModel = "spring")
public interface CurrencyConversionRateMapper {
	CurrencyConversionRateDto mapEntityToDto(CurrencyConversionRate currencyConversionRate);
	List<CurrencyConversionRateDto> mapEntitiesToDtos(List<CurrencyConversionRate> currencyConversionRates);
}
