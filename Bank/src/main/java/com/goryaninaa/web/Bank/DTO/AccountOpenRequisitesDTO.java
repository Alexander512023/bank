package com.goryaninaa.web.Bank.DTO;

import com.goryaninaa.web.Bank.model.account.AccountRequisites;
import com.goryaninaa.web.Bank.model.account.AccountType;
import com.goryaninaa.web.Bank.model.client.Client;
import com.goryaninaa.web.Bank.model.transaction.ServiceInitiator;
import com.goryaninaa.web.Bank.model.transaction.Transaction;

public class AccountRequisitesDTO {
	
	private int amount;
	private ClientDTO clientDTO;
	private AccountType accountType;
	private ServiceInitiator service;
	private int term;
	
	public AccountRequisitesDTO() {
		
	}
	
	public AccountRequisites extractAccountRequisites() {
		Client client = new Client(clientDTO.getPassport());
		Transaction transaction = new Transaction(amount, client, service);
		
		return new AccountRequisites(transaction, accountType, term);
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public ClientDTO getClientDTO() {
		return clientDTO;
	}

	public void setClientDTO(ClientDTO clientDTO) {
		this.clientDTO = clientDTO;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public ServiceInitiator getService() {
		return service;
	}

	public void setService(ServiceInitiator service) {
		this.service = service;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}
	
}
