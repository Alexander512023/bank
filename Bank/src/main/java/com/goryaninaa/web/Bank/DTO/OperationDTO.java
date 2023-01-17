package com.goryaninaa.web.Bank.DTO;

import java.time.LocalDateTime;
import java.util.Optional;

import com.goryaninaa.web.Bank.model.account.Account;
import com.goryaninaa.web.Bank.model.client.Client;
import com.goryaninaa.web.Bank.model.operation.Operation;
import com.goryaninaa.web.Bank.model.operation.OperationRequisites;
import com.goryaninaa.web.Bank.model.operation.ServiceInitiator;
import com.goryaninaa.web.Bank.model.operation.OperationType;

public class OperationDTO implements Comparable<OperationDTO> {
	
	private int amount;
	private int balanceBefore;
	private int balanceAfter;
	private LocalDateTime performedAt;
	private Integer accountFromNumber;
	private Integer accountRecepientNumber;
	private ClientDTO clientDTO;
	private ServiceInitiator service;
	private OperationType operationType;
	private int historyNumber;
	
	public OperationDTO() {
	}
	
	public OperationDTO(Operation operation, ClientDTO clientDTO) {
		this.amount = operation.getAmount();
		this.performedAt = operation.getPerformedAt();
		this.historyNumber = operation.getHistoryNumber();
		this.operationType = operation.getOperationType();
		this.clientDTO = clientDTO;
		this.service = operation.getService();
		this.balanceBefore = operation.getBalanceBefore();
		this.balanceAfter = operation.getBalanceAfter();
		Optional<Account> accountFrom = Optional.ofNullable(operation.getAccountFrom());
		if (accountFrom.isPresent()) {
			this.accountFromNumber = operation.getAccountFrom().getNumber();
		}
		Optional<Account> accountRecepient = Optional.ofNullable(operation.getAccountRecepient());
		if (accountRecepient.isPresent()) {
			this.accountRecepientNumber = operation.getAccountRecepient().getNumber();
		}
	}
	
	public OperationRequisites extractOperationRequisites() {
		OperationRequisites requisites = new OperationRequisites();
		
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
		requisites.setOperationType(operationType);
		requisites.setHistoryNumber(historyNumber);
		
		return requisites;
	}

	@Override
	public int compareTo(OperationDTO that) {
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

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
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
