package com.goryaninaa.web.Bank.model.account;

import com.goryaninaa.web.Bank.model.operation.Operation;

public class AccountOpenRequisites {
	private Operation transaction;
	private AccountType accountType;
	private int term;
	
	public AccountOpenRequisites() {
	}

	public AccountOpenRequisites(Operation transaction, AccountType accountType, int term) {
		super();
		this.transaction = transaction;
		this.accountType = accountType;
		this.term = term;
	}

	public Operation getOperation() {
		return transaction;
	}

	public void setTransaction(Operation transaction) {
		this.transaction = transaction;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}
	
}
