package com.goryaninaa.web.Bank.service.operation;

import com.goryaninaa.web.Bank.model.account.Account;
import com.goryaninaa.web.Bank.model.account.AccountOpenRequisites;
import com.goryaninaa.web.Bank.model.operation.Operation;
import com.goryaninaa.web.Bank.model.operation.OperationRequisites;
import com.goryaninaa.web.Bank.service.account.OperationServiceAccount;
import com.goryaninaa.web.Bank.service.account.exception.AccountDepositException;
import com.goryaninaa.web.Bank.service.account.exception.AccountOpenException;
import com.goryaninaa.web.Bank.service.account.exception.AccountWithdrawException;

public class OperationService implements OperationServiceAccount {
	
	private final RequisiteServiceOperation requisiteService;
	private final OperationRepository operationRepository;

	public OperationService(RequisiteServiceOperation requisiteService, OperationRepository operationRepository) {
		this.requisiteService = requisiteService;
		this.operationRepository = operationRepository;
	}

	@Override
	public void processAccountOpen(Account account, AccountOpenRequisites requisites) throws AccountOpenException {
		OperationRequisites completedRequisites = requisiteService.prepareAccountOpenOperationRequisites(account, requisites);
		Operation open = new Operation(completedRequisites);
		operationRepository.save(open);
	}

	@Override
	public void processDeposit(Account account, OperationRequisites requisites) throws AccountDepositException {
		OperationRequisites completedRequisites = requisiteService.prepareDepositOperationRequisites(account, requisites);
		Operation deposit = new Operation(completedRequisites);
		operationRepository.save(deposit);
	}

	@Override
	public void processWithdraw(Account account, OperationRequisites requisites) throws AccountWithdrawException {
		OperationRequisites completedRequisites = requisiteService.prepareWithdrawOperationRequisites(account, requisites);
		Operation withdraw = new Operation(completedRequisites);
		operationRepository.save(withdraw);
	}
}
