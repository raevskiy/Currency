package com.koplisoftl.currency.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomCurrencyTime {
	@JsonProperty("updatedISO")
	private Date dateTime;
	
	public Date getDateTime() {
		return dateTime;
	}
}
