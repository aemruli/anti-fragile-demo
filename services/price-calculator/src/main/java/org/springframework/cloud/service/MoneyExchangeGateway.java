package org.springframework.cloud.service;

/**
 * @author Agim Emruli
 */
public interface MoneyExchangeGateway {

    Double exchangeMoney(String currency, long amount);
}
