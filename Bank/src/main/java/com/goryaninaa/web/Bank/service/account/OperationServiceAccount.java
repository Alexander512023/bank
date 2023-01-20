package com.goryaninaa.web.Bank.service.account;

import com.goryaninaa.web.Bank.model.account.Account;
import com.goryaninaa.web.Bank.model.account.AccountOpenRequisites;
import com.goryaninaa.web.Bank.model.operation.OperationRequisites;
import com.goryaninaa.web.Bank.service.account.exception.AccountDepositException;
import com.goryaninaa.web.Bank.service.account.exception.AccountOpenException;
import com.goryaninaa.web.Bank.service.account.exception.AccountWithdrawException;

public interface OperationServiceAccount {

	void processAccountOpen(Account account, AccountOpenRequisites requisites) throws AccountOpenException;

	void processDeposit(Account account, OperationRequisites requisites) throws AccountDepositException;

	void processWithdraw(Account account, OperationRequisites requisites) throws AccountWithdrawException;

}
