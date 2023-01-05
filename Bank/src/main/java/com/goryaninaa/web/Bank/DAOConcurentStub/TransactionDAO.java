package com.goryaninaa.web.Bank.DAOConcurentStub;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.goryaninaa.web.Bank.model.transaction.Transaction;

public class TransactionDAO {
	
	private static int idCounter = 1;
	private final List<Transaction> transactions;

	public TransactionDAO() {
		this.transactions = new ArrayList<>();
	}
	
	public void save(Transaction transaction) {
		for (Transaction savedEarlierTransaction : transactions) {
			if (savedEarlierTransaction.equals(transaction)) {
				throw new RuntimeException("This transaction already exists");
			}
		}
		
		transaction.setId(idCounter++);
		
		transactions.add(transaction);
	}

	public List<Transaction> findTransactionsOfAccount(int accountId) {
		return transactions.stream().filter(t -> t.getAccount().getId() == accountId).collect(Collectors.toList());
	}
}
