package com.goryaninaa.web.Bank.service.account;

import java.util.List;

import com.goryaninaa.web.Bank.model.transaction.Transaction;

public interface TransactionRepository {

	void save(Transaction transaction);
	
	List<Transaction> findTransactionsOfAccount(int accountId);

}
