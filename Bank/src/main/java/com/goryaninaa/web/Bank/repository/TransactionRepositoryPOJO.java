package com.goryaninaa.web.Bank.repository;

import java.util.List;

import com.goryaninaa.web.Bank.DAOConcurentStub.TransactionDAO;
import com.goryaninaa.web.Bank.model.operation.Operation;
import com.goryaninaa.web.Bank.service.operation.OperationRepository;

public class TransactionRepositoryPOJO implements OperationRepository {

	private final TransactionDAO transactionDAO;
	
	public TransactionRepositoryPOJO(TransactionDAO depositDAO) {
		this.transactionDAO = depositDAO;
	}

	@Override
	public void save(Operation transaction) {
		transactionDAO.save(transaction);
	}

	@Override
	public List<Operation> findOperationsOfAccount(int accountId) {
		return transactionDAO.findTransactionsOfAccount(accountId);
	}

}
