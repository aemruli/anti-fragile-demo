package org.springframework.cloud.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Agim Emruli
 */
@RestController
public class CurrencyExchangeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyExchangeController.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private Environment environment;

    @Value("${timeout:0}")
    private int timeout;

    private JsonNode jsonNode;

    @PostConstruct
    public void loadExchangeRate() throws IOException {
        jsonNode = this.objectMapper.readTree(resourceLoader.getResource("classpath:latest").getInputStream());
    }

    @RequestMapping("/exchange/{currency}/{value}")
    public BigDecimal exchange(@PathVariable String currency, @PathVariable BigDecimal value) throws InterruptedException {
        LOGGER.trace("Received called with currency {} and value {} ", currency, value);
        Thread.sleep(this.environment.getProperty("timeout", Long.class, 0L));
        double rate = jsonNode.get("rates").findValue(currency).asDouble(0);
        return value.multiply(new BigDecimal(rate)).setScale(2, RoundingMode.HALF_UP);
    }
}