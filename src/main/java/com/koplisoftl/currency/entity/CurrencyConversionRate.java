package com.koplisoftl.currency.entity;

import java.math.BigDecimal;
import java.util.Date;

public class CurrencyConversionRate {
	private Long id;
	private BigDecimal rate;
	private Date dateTime;
	
	public CurrencyConversionRate() {
		super();
	}
	
	public CurrencyConversionRate(BigDecimal rate, Date dateTime) {
		this.rate = rate;
		this.dateTime = dateTime;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public BigDecimal getRate() {
		return rate;
	}
	
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	public Date getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
}
