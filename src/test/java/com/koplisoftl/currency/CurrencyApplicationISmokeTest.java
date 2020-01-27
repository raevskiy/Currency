package com.koplisoftl.currency;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

import com.koplisoftl.currency.dto.CurrencyConversionRateDto;
import com.koplisoftl.currency.dto.CurrencyConversionRateResponseDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class CurrencyApplicationISmokeTest {
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
    	Thread.sleep(3000);
        ResponseEntity<CurrencyConversionRateResponseDto> response = restTemplate.exchange(
                createURLWithPort("/recent"), HttpMethod.GET, null, CurrencyConversionRateResponseDto.class);
        
        CurrencyConversionRateResponseDto body = response.getBody();
    	assertThat(body.getDescription()).isEqualTo("GBP to USD");
    	assertThat(body.getConversionRates()).hasSize(3);
    	assertThat(body.getConversionRates()).extracting(CurrencyConversionRateDto::getRate).allMatch(rate -> rate.compareTo(BigDecimal.ZERO) > 0);
    }
    
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
