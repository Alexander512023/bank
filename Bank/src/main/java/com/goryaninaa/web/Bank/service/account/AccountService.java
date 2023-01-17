package com.goryaninaa.web.Bank.service.account;

import java.util.Optional;

import com.goryaninaa.web.Bank.model.account.Account;
import com.goryaninaa.web.Bank.model.account.AccountOpenRequisites;
import com.goryaninaa.web.Bank.model.client.Client;
import com.goryaninaa.web.Bank.model.operation.Operation;
import com.goryaninaa.web.Bank.model.operation.OperationRequisites;
import com.goryaninaa.web.Bank.service.account.exception.AccountDepositException;
import com.goryaninaa.web.Bank.service.account.exception.AccountFindException;
import com.goryaninaa.web.Bank.service.account.exception.AccountOpenException;
import com.goryaninaa.web.Bank.service.account.exception.AccountWithdrawException;
import com.goryaninaa.web.Bank.service.client.ClientRepository;

public class AccountService {

	private final AccountRepository accountRepository;
	private final OperationRepository operationRepository;
	private final NumberCapacityRepository numberCapacityRepository;
	private final ClientRepository clientRepository;

	//TODO add unit test
	public AccountService(AccountRepository accountRepository, OperationRepository operationRepository,
			NumberCapacityRepository numberCapacityRepository, ClientRepository clientRepository) {
		this.accountRepository = accountRepository;
		this.operationRepository = operationRepository;
		this.numberCapacityRepository = numberCapacityRepository;
		this.clientRepository = clientRepository;
	}

	public void open(AccountOpenRequisites requisites) throws AccountOpenException {
		Account account = openAccount(requisites);
		Operation openAccountOperation = createOpenAccountOperation(requisites, account);
		accountRepository.save(account);
		operationRepository.save(openAccountOperation);
	}
	
	public void deposit(OperationRequisites requisites) throws AccountFindException, AccountDepositException {
		Account account = findByNumber(requisites.getAccountRecepient().getNumber());
		account.deposit(requisites.getAmount());
		Operation deposit = generateDepositOperation(requisites, account);
		accountRepository.update(account);
		operationRepository.save(deposit);
	}
	
	public void withdraw(OperationRequisites requisites) throws AccountFindException, AccountWithdrawException {
		Account account = findByNumber(requisites.getAccountFrom().getNumber());
		account.withdraw(requisites.getAmount());
		Operation withdraw = generateWithdrawOperation(requisites, account);
		accountRepository.update(account);
		operationRepository.save(withdraw);
	}

	public void transfer(OperationRequisites requisites)
			throws AccountFindException, AccountDepositException, AccountWithdrawException  {
		deposit(requisites);
		withdraw(requisites);
	}
	
	public Account findByNumber(int number) throws AccountFindException {
		Account account = findAccount(number);
		return account;
	}

//	//TODO
//	public void prolongate(Prolongation prolongation) {
//		Optional<Account> account = accountRepository.findByNumber(prolongation.getProduct().getNumber());
//		
//		if (account.isPresent()) {
//			account.get().prolongate();
//			
//			OperationRepository.save(prolongation);
//			accountRepository.save(account.get());
//		} else {
//			//TODO
//		}
//	}

	private Operation generateDepositOperation(OperationRequisites requisites, Account account)
			throws AccountDepositException {
		OperationRequisites enrichedRequisites = enrichDepositOperation(requisites, account);
		return new Operation(enrichedRequisites);
	}
	
	private Account openAccount(AccountOpenRequisites requisites) throws AccountOpenException {
		AccountOpenRequisites enrichedRequisites = enrichAccountOperation(requisites);
		int accountNumber = numberCapacityRepository.getNumber();
		return new Account(enrichedRequisites, accountNumber);
	}

	private Operation createOpenAccountOperation(AccountOpenRequisites requisites, Account account)
			throws AccountOpenException {
		AccountOpenRequisites enrichedRequisites = enrichAccountOperation(requisites);
		Operation openOperation = enrichedRequisites.getOperation();
		openOperation.setAccount(account);
		openOperation.setAccountRecepient(account);
		return openOperation;
	}

	private AccountOpenRequisites enrichAccountOperation(AccountOpenRequisites requisites) throws AccountOpenException {
		Optional<Client> initiator = clientRepository.findByPassport(requisites.getOperation().getClient().getPassport());
		if (initiator.isPresent()) {
			requisites.getOperation().setClient(initiator.get());
			return requisites;
		} else {
			throw new AccountOpenException("There is no such client", new IllegalArgumentException());
		}
	}
	
	private OperationRequisites enrichDepositOperation(OperationRequisites requisites, Account account)
			throws AccountDepositException {
		Optional<Client> client = clientRepository.findByPassport(requisites.getClient().getPassport());
		if (client.isPresent()) {
			requisites.enrich(account, client.get());
			requisites.setBalanceBefore(account.getBalance() - requisites.getAmount());
			return requisites;
		} else {
			throw new AccountDepositException("There is no such client", new IllegalArgumentException());
		}
	}

	private Operation generateWithdrawOperation(OperationRequisites requisites, Account account)
			throws AccountWithdrawException {
		OperationRequisites enrichedRequisites = enrichWithdrawOperation(requisites, account);
		return new Operation(enrichedRequisites);
	}

	private OperationRequisites enrichWithdrawOperation(OperationRequisites requisites, Account account)
			throws AccountWithdrawException {
		Optional<Client> client = clientRepository.findByPassport(requisites.getClient().getPassport());
		if (client.isPresent()) {
			requisites.enrich(account, client.get());
			requisites.setBalanceBefore(account.getBalance() + Math.abs(requisites.getAmount()));
			return requisites;
		} else {
			throw new AccountWithdrawException("There is no such client", new IllegalArgumentException());
		}
	}
	
	private Account findAccount(int number) throws AccountFindException {
		Optional<Account> account = accountRepository.findByNumber(number);
		if (account.isPresent()) {
			return account.get();
		} else {
			throw new AccountFindException("There is no account with such number");
		}
	}
}