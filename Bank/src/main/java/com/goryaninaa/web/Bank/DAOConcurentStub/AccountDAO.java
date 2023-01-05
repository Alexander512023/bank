package com.goryaninaa.web.Bank.DAOConcurentStub;

import java.util.Map;
import java.util.stream.Collectors;

import com.goryaninaa.web.Bank.cache.DAO;
import com.goryaninaa.web.Bank.cache.DataAccessStrategy;
import com.goryaninaa.web.Bank.model.account.Account;

public class AccountDAO extends DAO<Account> {
	
	private static int idCounter = 1;
	
	public AccountDAO(Map<String, DataAccessStrategy> handlers) {
		super(handlers);
	}

	public void save(Account account) {
		for (Account savedEarlierAccount : super.getDataList()) {
			if (savedEarlierAccount.equals(account)) {
				throw new RuntimeException("This account already exists");
			}
		}
		
		account.setId(idCounter++);
		
		super.getDataList().add(account);
	}

//	public Optional<Account> findByNumber(int number) {
//		Optional<Account> desiredAccount = Optional.empty();
//		for (Account account : super.getDataList()) {
//			if (account.getNumber() == number) {
//				desiredAccount = Optional.ofNullable(account);
//				break;
//			}
//		}
//		return desiredAccount;
//	}

	public void update(Account account) {
		super.getDataList().stream().filter(a -> a.getId() != account.getId()).collect(Collectors.toList());
		super.getDataList().add(account);
	}

}
