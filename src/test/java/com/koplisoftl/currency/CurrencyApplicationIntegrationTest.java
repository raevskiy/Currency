package com.koplisoftl.currency;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.koplisoftl.currency.dto.CurrencyConversionRate;
import com.koplisoftl.currency.dto.CurrencyConversionRateResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
class CurrencyApplicationIntegrationTest {
	@LocalServerPort
    private int port;
    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void showsHint() {
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(""), HttpMethod.GET, null, String.class);
        
        assertThat(response.getBody()).isEqualTo("Shows currency exchange rate at /recent");
    }
    
    @Test
    void showsRecent() throws InterruptedException {
    	Thread.sleep(10000);
        ResponseEntity<CurrencyConversionRateResponse> response = restTemplate.exchange(
                createURLWithPort("/recent"), HttpMethod.GET, null, CurrencyConversionRateResponse.class);
        
        CurrencyConversionRateResponse body = response.getBody();
    	assertThat(body.getDescription()).isEqualTo("GBP to USD");
    	assertThat(body.getConversionRates()).hasSize(10);
    	assertThat(body.getConversionRates()).extracting(CurrencyConversionRate::getRate).allMatch(rate -> rate.compareTo(BigDecimal.ZERO) > 0);
    }
    
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
