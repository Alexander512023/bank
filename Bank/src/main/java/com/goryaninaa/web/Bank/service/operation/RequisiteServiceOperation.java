package com.goryaninaa.web.Bank.service.operation;

import com.goryaninaa.web.Bank.model.account.Account;
import com.goryaninaa.web.Bank.model.account.AccountOpenRequisites;
import com.goryaninaa.web.Bank.model.operation.OperationRequisites;
import com.goryaninaa.web.Bank.service.account.exception.AccountDepositException;
import com.goryaninaa.web.Bank.service.account.exception.AccountOpenException;
import com.goryaninaa.web.Bank.service.account.exception.AccountWithdrawException;

public interface RequisiteServiceOperation {

	OperationRequisites prepareAccountOpenOperationRequisites(Account account, AccountOpenRequisites requisites) throws AccountOpenException;

	OperationRequisites prepareDepositOperationRequisites(Account account, OperationRequisites requisites) throws AccountDepositException;

	OperationRequisites prepareWithdrawOperationRequisites(Account account, OperationRequisites requisites) throws AccountWithdrawException;

}
