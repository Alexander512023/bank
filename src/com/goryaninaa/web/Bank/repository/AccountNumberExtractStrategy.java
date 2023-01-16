package com.goryaninaa.web.Bank.repository;

import com.goryaninaa.web.Bank.model.account.Account;
import com.goryaninaa.web.Cache.KeyExtractStrategy;

public class AccountNumberExtractStrategy implements KeyExtractStrategy {

	@Override
	public Object extractKey(Object entity) {
		Account account = (Account) entity;
		return account.getNumber();
	}

	@Override
	public String getStrategy() {
		return AccountAccessStrategyType.NUMBER;
	}

}
