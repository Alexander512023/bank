package com.goryaninaa.web.Bank.model.account;

import com.goryaninaa.web.Bank.model.transaction.Transaction;

public class AccountOpenRequisites {
	private Transaction transaction;
	private AccountType accountType;
	private int term;
	
	public AccountOpenRequisites() {
	}

	public AccountOpenRequisites(Transaction transaction, AccountType accountType, int term) {
		super();
		this.transaction = transaction;
		this.accountType = accountType;
		this.term = term;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
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
