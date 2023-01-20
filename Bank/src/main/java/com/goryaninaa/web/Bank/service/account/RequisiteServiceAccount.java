package com.goryaninaa.web.Bank.service.account;

import com.goryaninaa.web.Bank.model.account.AccountOpenRequisites;
import com.goryaninaa.web.Bank.service.account.exception.AccountOpenException;

public interface RequisiteServiceAccount {

	AccountOpenRequisites prepareAccountOpenRequisites(AccountOpenRequisites requisites) throws AccountOpenException;

}
