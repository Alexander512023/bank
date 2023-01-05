package com.goryaninaa.web.Bank.DTO;

import java.time.LocalDateTime;
import java.util.Optional;

import com.goryaninaa.web.Bank.model.account.Account;
import com.goryaninaa.web.Bank.model.client.Client;
import com.goryaninaa.web.Bank.model.transaction.ServiceInitiator;
import com.goryaninaa.web.Bank.model.transaction.Transaction;
import com.goryaninaa.web.Bank.model.transaction.TransactionRequisites;
import com.goryaninaa.web.Bank.model.transaction.TransactionType;

public class TransactionDTO implements Comparable<TransactionDTO> {
	
	private int amount;
	private int balanceBefore;
	private int balanceAfter;
	private LocalDateTime performedAt;
	private Integer accountFromNumber;
	private Integer accountRecepientNumber;
	private ClientDTO clientDTO;
	private ServiceInitiator service;
	private TransactionType transactionType;
	private int historyNumber;
	
	public TransactionDTO() {
	}
	
	public TransactionDTO(Transaction transaction, ClientDTO clientDTO) {
		this.amount = transaction.getAmount();
		this.performedAt = transaction.getPerformedAt();
		this.historyNumber = transaction.getHistoryNumber();
		this.transactionType = transaction.getTransactionType();
		this.clientDTO = clientDTO;
		this.service = transaction.getService();
		this.balanceBefore = transaction.getBalanceBefore();
		this.balanceAfter = transaction.getBalanceAfter();
		Optional<Account> accountFrom = Optional.ofNullable(transaction.getAccountFrom());
		if (accountFrom.isPresent()) {
			this.accountFromNumber = transaction.getAccountFrom().getNumber();
		}
		Optional<Account> accountRecepient = Optional.ofNullable(transaction.getAccountRecepient());
		if (accountRecepient.isPresent()) {
			this.accountRecepientNumber = transaction.getAccountRecepient().getNumber();
		}
	}
	
	public TransactionRequisites extractTransactionRequisites() {
		TransactionRequisites requisites = new TransactionRequisites();
		
		requisites.setAmount(amount);
		Optional<Integer> accountFrom = Optional.ofNullable(accountFromNumber);
		if (accountFrom.isPresent()) {
			requisites.setAccountFrom(new Account(accountFrom.get()));
		}
		Optional<Integer> accountRecepient = Optional.ofNullable(accountRecepientNumber);
		if (accountRecepient.isPresent()) {
			requisites.setAccountRecepient(new Account(accountRecepient.get()));
		}
		requisites.setClient(new Client(clientDTO.getPassport()));
		requisites.setService(service);
		requisites.setTransactionType(transactionType);
		requisites.setHistoryNumber(historyNumber);
		
		return requisites;
	}

	@Override
	public int compareTo(TransactionDTO that) {
		if (this.historyNumber < that.historyNumber) {
			return 1;
		} else if (this.historyNumber == that.historyNumber) {
			return 0;
		} else {
			return -1;
		}
	}
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public LocalDateTime getPerformedAt() {
		return performedAt;
	}

	public void setPerformedAt(LocalDateTime performedAt) {
		this.performedAt = performedAt;
	}


	public Integer getAccountFromNumber() {
		return accountFromNumber;
	}

	public void setAccountFromNumber(Integer accountFromNumber) {
		this.accountFromNumber = accountFromNumber;
	}

	public Integer getAccountRecepientNumber() {
		return accountRecepientNumber;
	}

	public void setAccountRecepientNumber(Integer accountRecepientNumber) {
		this.accountRecepientNumber = accountRecepientNumber;
	}

	public ClientDTO getClientDTO() {
		return clientDTO;
	}

	public void setClientDTO(ClientDTO clientDTO) {
		this.clientDTO = clientDTO;
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

	public int getBalanceBefore() {
		return balanceBefore;
	}

	public int getBalanceAfter() {
		return balanceAfter;
	}
	
}
