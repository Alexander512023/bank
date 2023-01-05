package com.goryaninaa.web.Bank.model.transaction;

import java.time.LocalDateTime;
import java.util.Objects;

import com.goryaninaa.web.Bank.model.account.Account;
import com.goryaninaa.web.Bank.model.client.Client;

public class Transaction implements Comparable<Transaction> {
	
	private int id;
	private int amount;
	private int balanceBefore;
	private int balanceAfter;
	private LocalDateTime performedAt;
	private Account account;
	private Account accountFrom;
	private Account accountRecepient;
	private Client client;
	private ServiceInitiator service;
	private TransactionType transactionType;
	private int historyNumber;
	
	public Transaction() {
	}
	
	//Open account with this constructor
	public Transaction(int amount, Client client, ServiceInitiator service) {
		setAmount(amount);
		setBalanceBefore(0);
		setBalanceAfter(amount);
		setPerformedAt(LocalDateTime.now());
		setClient(client);
		setService(service);
		setTransactionType(TransactionType.DEPOSIT);
		setHistoryNumber(1);
	}
	
	//Standard transaction constructor
	public Transaction(TransactionRequisites requisites) {
		setAmount(requisites.getAmount());
		setBalanceBefore(requisites.getBalanceBefore());
		setBalanceAfter(requisites.getBalanceAfter());
		setPerformedAt(LocalDateTime.now());
		setClient(requisites.getClient());
		setService(requisites.getService());
		setTransactionType(requisites.getTransactionType());
		setHistoryNumber(requisites.getHistoryNumber());
		
		defineAccounts(requisites);
		System.out.println();
	}

	@Override
	public int compareTo(Transaction that) {
		if (this.historyNumber < that.historyNumber) {
			return 1;
		} else if (this.historyNumber == that.historyNumber) {
			return 0;
		} else {
			return -1;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public LocalDateTime getPerformedAt() {
		return performedAt;
	}

	public void setPerformedAt(LocalDateTime performedAt) {
		this.performedAt = performedAt;
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

	@Override
	public int hashCode() {
		return Objects.hash(id, amount, historyNumber, account);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		return id == other.id && amount == other.amount && historyNumber == other.historyNumber && Objects.equals(account, other.account);
	}
	
	private void defineAccounts(TransactionRequisites requisites) {
		setAccount(requisites.getAccount());
		switch (transactionType) {
		case DEPOSIT:
			setAccountRecepient(requisites.getAccountRecepient());
			break;
		case WITHDRAW:
			setAccountFrom(requisites.getAccountFrom());
			break;
		case TRANSFER:
			setAccountFrom(requisites.getAccountFrom());
			setAccountRecepient(requisites.getAccountRecepient());
			break;
		default:
			break;
		}
	}
}
