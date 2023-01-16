package com.goryaninaa.web.Bank.repository;

import java.util.List;

import com.goryaninaa.web.Bank.DAOConcurentStub.TransactionDAO;
import com.goryaninaa.web.Bank.model.transaction.Transaction;
import com.goryaninaa.web.Bank.service.account.TransactionRepository;

public class TransactionRepositoryPOJO implements TransactionRepository {

	private final TransactionDAO transactionDAO;
	
	public TransactionRepositoryPOJO(TransactionDAO depositDAO) {
		this.transactionDAO = depositDAO;
	}

	@Override
	public void save(Transaction transaction) {
		transactionDAO.save(transaction);
	}

	@Override
	public List<Transaction> findTransactionsOfAccount(int accountId) {
		return transactionDAO.findTransactionsOfAccount(accountId);
	}

}
