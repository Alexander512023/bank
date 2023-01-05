package com.goryaninaa.web.Bank.DAOConcurentStub;

import java.util.List;
import java.util.Optional;

import com.goryaninaa.web.Bank.cache.DataAccessStrategy;
import com.goryaninaa.web.Bank.model.account.Account;
import com.goryaninaa.web.Bank.repository.AccountAccessStrategyType;

public class AccountDataAccessByNumberStrategy implements DataAccessStrategy {

	@Override
	public <V> Optional<V> getData(Object key, List<V> data) {
		Optional<V> account = Optional.empty();
		for (V object : data) {
			if (((Integer) key).equals(((Account) object).getNumber())) {
				account = Optional.ofNullable((V) object);
			}
		}
		sleep(5000);
		return account;
	}

	private void sleep(int miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getStrategy() {
		return AccountAccessStrategyType.NUMBER;
	}
	
}
