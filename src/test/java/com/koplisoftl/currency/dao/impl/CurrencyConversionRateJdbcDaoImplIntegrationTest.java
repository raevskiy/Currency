package com.koplisoftl.currency.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

import com.koplisoftl.currency.entity.CurrencyConversionRate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class CurrencyConversionRateJdbcDaoImplIntegrationTest {
	@Autowired
	private CurrencyConversionRateJdbcDaoImpl dao;
	@MockBean
	private SchedulerFactoryBean schedulerFactoryBean;

	@Test
	void insertsManyfindsLimitedAnountOfCurrencyRates() throws InterruptedException {
		Date now = Timestamp.valueOf(LocalDateTime.now());
		
		for (int i = 0; i < 10; i++) {
			dao.insert(new CurrencyConversionRate(BigDecimal.valueOf(i + 1), now));
			Thread.sleep(100);
		}
		List<CurrencyConversionRate> result = dao.findRecent(5);
		
		assertThat(result).hasSize(5);
		assertThat(result).extracting(CurrencyConversionRate::getRate).containsExactly(
				new BigDecimal("10.0000"),
				new BigDecimal("9.0000"),
				new BigDecimal("8.0000"),
				new BigDecimal("7.0000"),
				new BigDecimal("6.0000"));
	}

}
