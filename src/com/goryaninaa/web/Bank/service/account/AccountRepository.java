package com.goryaninaa.web.Bank.service.account;

import java.util.Optional;

import com.goryaninaa.web.Bank.model.account.Account;

public interface AccountRepository {

	void save(Account account);

	Optional<Account> findByNumber(int number);

	void update(Account account);

}
