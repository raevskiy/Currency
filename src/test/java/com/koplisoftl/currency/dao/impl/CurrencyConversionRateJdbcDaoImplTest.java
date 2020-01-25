package com.koplisoftl.currency.dao.impl;

import static com.koplisoftl.currency.dao.impl.CurrencyConversionRateJdbcDaoImpl.INSERT;
import static com.koplisoftl.currency.dao.impl.CurrencyConversionRateJdbcDaoImpl.SELECT_RECENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.koplisoftl.currency.dao.impl.CurrencyConversionRateJdbcDaoImpl.CurrencyRateRowMapper;
import com.koplisoftl.currency.entity.CurrencyConversionRate;

@ExtendWith(MockitoExtension.class)
class CurrencyConversionRateJdbcDaoImplTest {
	@InjectMocks
	private CurrencyConversionRateJdbcDaoImpl dao = new CurrencyConversionRateJdbcDaoImpl();
	@Mock
    private JdbcTemplate jdbcTemplate;

	@Test
	void insertsCurrencyRate() {
		LocalDateTime now = LocalDateTime.now(); 
		CurrencyConversionRate currencyRate = new CurrencyConversionRate(BigDecimal.TEN, Timestamp.valueOf(now));
		
		dao.insert(currencyRate);
		
		verify(jdbcTemplate).update(INSERT,
	            new Object[] {currencyRate.getRate(), currencyRate.getDateTime()});
	}
	
	@Test
	void findsRecentCurrencyRates() {
		List<CurrencyConversionRate> recent =  Lists.newArrayList(new CurrencyConversionRate());
		when(jdbcTemplate.query(
				Mockito.eq(SELECT_RECENT),
				Mockito.eq(new Object[] {100}),
				Mockito.any(CurrencyRateRowMapper.class)))
		.thenReturn(recent);
		
		assertThat(dao.findRecent(100)).isEqualTo(recent);
	}

}
