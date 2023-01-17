package com.goryaninaa.web.Bank.model.operation;

import java.time.LocalDateTime;
import java.util.Objects;

import com.goryaninaa.web.Bank.model.account.Account;
import com.goryaninaa.web.Bank.model.client.Client;

public class Operation implements Comparable<Operation> {
	
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
	private OperationType operationType;
	private int historyNumber;
	
	public Operation() {
	}
	
	//Open account with this constructor
	public Operation(int amount, Client client, ServiceInitiator service) {
		setAmount(amount);
		setBalanceBefore(0);
		setBalanceAfter(amount);
		setPerformedAt(LocalDateTime.now());
		setClient(client);
		setService(service);
		setOperationType(OperationType.DEPOSIT);
		setHistoryNumber(1);
	}
	
	//Standard operation constructor
	public Operation(OperationRequisites requisites) {
		setAmount(requisites.getAmount());
		setBalanceBefore(requisites.getBalanceBefore());
		setBalanceAfter(requisites.getBalanceAfter());
		setPerformedAt(LocalDateTime.now());
		setClient(requisites.getClient());
		setService(requisites.getService());
		setOperationType(requisites.getOperationType());
		setHistoryNumber(requisites.getHistoryNumber());
		
		defineAccounts(requisites);
		System.out.println();
	}

	@Override
	public int compareTo(Operation that) {
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

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
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
		Operation other = (Operation) obj;
		return id == other.id && amount == other.amount && historyNumber == other.historyNumber && Objects.equals(account, other.account);
	}
	
	private void defineAccounts(OperationRequisites requisites) {
		setAccount(requisites.getAccount());
		switch (operationType) {
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
