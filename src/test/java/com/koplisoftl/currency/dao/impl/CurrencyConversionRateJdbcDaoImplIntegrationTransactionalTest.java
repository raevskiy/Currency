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
import org.springframework.transaction.annotation.Transactional;

import com.koplisoftl.currency.entity.CurrencyConversionRate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class CurrencyConversionRateJdbcDaoImplIntegrationTransactionalTest {
	@Autowired
	private CurrencyConversionRateJdbcDaoImpl dao;
	@MockBean
	private SchedulerFactoryBean schedulerFactoryBean;

	@Test
	@Transactional
	void insertsNothingfindsEmptyList() {
		assertThat(dao.findRecent(5)).isEmpty();
	}

	@Test
	@Transactional
	void inserts1finds1CurrencyRate() {
		Date now = Timestamp.valueOf(LocalDateTime.now());
		
		dao.insert(new CurrencyConversionRate(BigDecimal.TEN, now));
		List<CurrencyConversionRate> result = dao.findRecent(5);
		
		assertThat(result).hasSize(1);
		CurrencyConversionRate theOnlyRecentRate = result.get(0); 
		assertThat(theOnlyRecentRate.getId()).isNotNull();
		assertThat(theOnlyRecentRate.getRate()).isEqualByComparingTo(BigDecimal.TEN);
		assertThat(theOnlyRecentRate.getDateTime()).isEqualTo(now);
	}
}
