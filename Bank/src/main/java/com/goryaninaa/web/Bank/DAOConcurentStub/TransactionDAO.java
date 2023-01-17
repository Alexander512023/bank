package com.goryaninaa.web.Bank.DAOConcurentStub;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.goryaninaa.web.Bank.model.operation.Operation;

public class TransactionDAO {
	
	private static int idCounter = 1;
	private final List<Operation> transactions;

	public TransactionDAO() {
		this.transactions = new ArrayList<>();
	}
	
	public void save(Operation transaction) {
		for (Operation savedEarlierTransaction : transactions) {
			if (savedEarlierTransaction.equals(transaction)) {
				throw new RuntimeException("This transaction already exists");
			}
		}
		
		transaction.setId(idCounter++);
		
		transactions.add(transaction);
	}

	public List<Operation> findTransactionsOfAccount(int accountId) {
		return transactions.stream().filter(t -> t.getAccount().getId() == accountId).collect(Collectors.toList());
	}
}
