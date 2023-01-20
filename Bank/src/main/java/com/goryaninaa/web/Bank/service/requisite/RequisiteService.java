package com.goryaninaa.web.Bank.service.requisite;

import java.util.Optional;

import com.goryaninaa.web.Bank.model.account.Account;
import com.goryaninaa.web.Bank.model.account.AccountOpenRequisites;
import com.goryaninaa.web.Bank.model.client.Client;
import com.goryaninaa.web.Bank.model.operation.OperationRequisites;
import com.goryaninaa.web.Bank.model.operation.OperationType;
import com.goryaninaa.web.Bank.service.account.RequisiteServiceAccount;
import com.goryaninaa.web.Bank.service.account.exception.AccountDepositException;
import com.goryaninaa.web.Bank.service.account.exception.AccountOpenException;
import com.goryaninaa.web.Bank.service.account.exception.AccountWithdrawException;
import com.goryaninaa.web.Bank.service.operation.RequisiteServiceOperation;

public class RequisiteService implements RequisiteServiceAccount, RequisiteServiceOperation {

	private final ClientRepositoryRequisite clientRepository;
	
	public RequisiteService(ClientRepositoryRequisite clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public AccountOpenRequisites prepareAccountOpenRequisites(AccountOpenRequisites requisites)
			throws AccountOpenException {
		AccountOpenRequisites enrichedAccountOpenRequisites = enrichOpenAccountRequisites(requisites);
		return enrichedAccountOpenRequisites;
	}
	
	@Override
	public OperationRequisites prepareAccountOpenOperationRequisites(Account account, AccountOpenRequisites requisites)
			throws AccountOpenException {
		OperationRequisites firstOperationRequisites = prepareFirstOperationRequisites(requisites, account);
		return firstOperationRequisites;
	}

	@Override
	public OperationRequisites prepareDepositOperationRequisites(Account account, OperationRequisites requisites)
			throws AccountDepositException {
		OperationRequisites enrichedDepositRequisites = enrichDepositRequisites(requisites, account);
		return enrichedDepositRequisites;
	}

	@Override
	public OperationRequisites prepareWithdrawOperationRequisites(Account account, OperationRequisites requisites)
			throws AccountWithdrawException {
		OperationRequisites enrichedWithdrawRequisites = enrichWithdrawRequisites(requisites, account);
		return enrichedWithdrawRequisites;
	}

	private AccountOpenRequisites enrichOpenAccountRequisites(AccountOpenRequisites requisites) throws AccountOpenException {
		Optional<Client> initiator = clientRepository.findByPassport(requisites.getOperationRequisites().getClient().getPassport());
		if (initiator.isPresent()) {
			requisites.getOperationRequisites().setClient(initiator.get());
			return requisites;
		} else {
			throw new AccountOpenException("There is no such client", new IllegalArgumentException());
		}
	}
	
	private OperationRequisites prepareFirstOperationRequisites(AccountOpenRequisites requisites,
			Account account) throws AccountOpenException {
		AccountOpenRequisites enrichedRequisites = enrichOpenAccountRequisites(requisites);
		OperationRequisites firstOperationRequisites = enrichedRequisites.getOperationRequisites();
		firstOperationRequisites.setAccount(account);
		firstOperationRequisites.setAccountRecepient(account);
		firstOperationRequisites.setOperationType(OperationType.DEPOSIT);
		firstOperationRequisites.setHistoryNumber(1);
		return firstOperationRequisites;
	}
	
	private OperationRequisites enrichDepositRequisites(OperationRequisites requisites, Account account)
			throws AccountDepositException {
		Optional<Client> client = clientRepository.findByPassport(requisites.getClient().getPassport());
		if (client.isPresent()) {
			requisites.enrich(account, client.get(), OperationType.DEPOSIT);
			return requisites;
		} else {
			throw new AccountDepositException("There is no such client", new IllegalArgumentException());
		}
	}

	private OperationRequisites enrichWithdrawRequisites(OperationRequisites requisites, Account account)
			throws AccountWithdrawException {
		Optional<Client> client = clientRepository.findByPassport(requisites.getClient().getPassport());
		if (client.isPresent()) {
			requisites.enrich(account, client.get(), OperationType.WITHDRAW);
			return requisites;
		} else {
			throw new AccountWithdrawException("There is no such client", new IllegalArgumentException());
		}
	}
}
