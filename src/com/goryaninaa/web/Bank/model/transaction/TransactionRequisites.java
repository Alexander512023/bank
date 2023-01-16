package com.goryaninaa.web.Bank.model.transaction;

import com.goryaninaa.web.Bank.model.account.Account;
import com.goryaninaa.web.Bank.model.client.Client;

public class TransactionRequisites {
	
	private int amount;
	private int balanceBefore;
	private int balanceAfter;
	private Account account;
	private Account accountFrom;
	private Account accountRecepient;
	private Client client;
	private ServiceInitiator service;
	private TransactionType transactionType;
	private int historyNumber;

	public TransactionRequisites() {
		
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getBalanceBefore() {
		return balanceBefore;
	}

	public void setBalanceBefore(int balanceBefore) {
		this.balanceBefore = balanceBefore;
	}

	public int getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(int balanceAfter) {
		this.balanceAfter = balanceAfter;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Account getAccountFrom() {
		return accountFrom;
	}

	public void setAccountFrom(Account accountFrom) {
		this.accountFrom = accountFrom;
	}

	public Account getAccountRecepient() {
		return accountRecepient;
	}

	public void setAccountRecepient(Account accountRecepient) {
		this.accountRecepient = accountRecepient;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ServiceInitiator getService() {
		return service;
	}

	public void setService(ServiceInitiator service) {
		this.service = service;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public int getHistoryNumber() {
		return historyNumber;
	}

	public void setHistoryNumber(int historyNumber) {
		this.historyNumber = historyNumber;
	}
	
}
