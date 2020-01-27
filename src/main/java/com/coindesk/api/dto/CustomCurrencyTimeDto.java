package com.coindesk.api.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomCurrencyTimeDto {
	@JsonProperty("updatedISO")
	private Date dateTime;
	
	public CustomCurrencyTimeDto() {
		super();
	}
	
	public CustomCurrencyTimeDto(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	public Date getDateTime() {
		return dateTime;
	}
}
