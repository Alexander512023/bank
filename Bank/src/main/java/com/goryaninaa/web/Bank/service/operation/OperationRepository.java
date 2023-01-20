package com.goryaninaa.web.Bank.service.operation;

import java.util.List;

import com.goryaninaa.web.Bank.model.operation.Operation;

public interface OperationRepository {

	void save(Operation operation);
	
	List<Operation> findOperationsOfAccount(int accountId);

}
