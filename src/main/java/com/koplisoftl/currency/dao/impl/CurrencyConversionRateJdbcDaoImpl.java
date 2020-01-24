package com.koplisoftl.currency.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.koplisoftl.currency.dao.CurrencyConversionRateDao;
import com.koplisoftl.currency.entity.CurrencyConversionRate;

@Repository
public class CurrencyConversionRateJdbcDaoImpl implements CurrencyConversionRateDao {
	class CurrencyRateRowMapper implements RowMapper<CurrencyConversionRate> {
        @Override
        public CurrencyConversionRate mapRow(ResultSet rs, int rowNum) throws SQLException {
        	CurrencyConversionRate currencyRate = new CurrencyConversionRate();
            currencyRate.setId(rs.getLong("id"));
            currencyRate.setRate(rs.getBigDecimal("rate"));
            currencyRate.setDateTime(rs.getTimestamp("time"));
            return currencyRate;
        }
    }
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
    public List<CurrencyConversionRate> findRecent(int size) {
        return jdbcTemplate.query("select * from currency_rate", new CurrencyRateRowMapper());
    }
    
	@Override
    public void insert(CurrencyConversionRate currencyRate) {
        jdbcTemplate.update("insert into currency_rate (id, rate, time) values(?, ?, ?)",
            new Object[] {
            		currencyRate.getId(), currencyRate.getRate(), currencyRate.getDateTime()
            });
    }

}
