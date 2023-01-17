package com.goryaninaa.web.Bank.service.account;

import java.util.List;

import com.goryaninaa.web.Bank.model.operation.Operation;

public interface OperationRepository {

	void save(Operation transaction);
	
	List<Operation> findTransactionsOfAccount(int accountId);

}
