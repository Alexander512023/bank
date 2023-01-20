package com.goryaninaa.web.Bank.service.account;

import java.util.Optional;

import com.goryaninaa.web.Bank.model.account.Account;
import com.goryaninaa.web.Bank.model.account.AccountOpenRequisites;
import com.goryaninaa.web.Bank.model.operation.OperationRequisites;
import com.goryaninaa.web.Bank.service.account.exception.AccountDepositException;
import com.goryaninaa.web.Bank.service.account.exception.AccountFindException;
import com.goryaninaa.web.Bank.service.account.exception.AccountOpenException;
import com.goryaninaa.web.Bank.service.account.exception.AccountWithdrawException;

public class AccountService {

	private final AccountRepository accountRepository;
	private final OperationServiceAccount operationService;
	private final NumberCapacityRepository numberCapacityRepository;
	private final RequisiteServiceAccount requisiteService;

	//TODO add unit test
	public AccountService(AccountRepository accountRepository, OperationServiceAccount operationService,
			NumberCapacityRepository numberCapacityRepository, RequisiteServiceAccount requisiteService) {
		this.accountRepository = accountRepository;
		this.operationService = operationService;
		this.numberCapacityRepository = numberCapacityRepository;
		this.requisiteService = requisiteService;
	}

	public void open(AccountOpenRequisites requisites) throws AccountOpenException {
		Account account = openAccount(requisites);
		accountRepository.save(account);
		operationService.processAccountOpen(account, requisites);
	}
	
	public void deposit(OperationRequisites requisites) throws AccountFindException, AccountDepositException {
		Account account = findByNumber(requisites.getAccountRecepient().getNumber());
		account.deposit(requisites.getAmount());
		accountRepository.update(account);
		operationService.processDeposit(account, requisites);
	}
	
	public void withdraw(OperationRequisites requisites) throws AccountFindException, AccountWithdrawException {
		Account account = findByNumber(requisites.getAccountFrom().getNumber());
		account.withdraw(requisites.getAmount());
		accountRepository.update(account);
		operationService.processWithdraw(account, requisites);
	}

	public void transfer(OperationRequisites requisites)
			throws AccountFindException, AccountDepositException, AccountWithdrawException  {
		deposit(requisites);
		withdraw(requisites);
	}
	
	public Account findByNumber(int number) throws AccountFindException {
		Account desiredAccount = findAccount(number);
		return desiredAccount;
	}

	private Account openAccount(AccountOpenRequisites requisites) throws AccountOpenException {
		AccountOpenRequisites completedRequisites = requisiteService.prepareAccountOpenRequisites(requisites);
		int accountNumber = numberCapacityRepository.getNumber();
		return new Account(completedRequisites, accountNumber);
	}

	private Account findAccount(int number) throws AccountFindException {
		Optional<Account> account = accountRepository.findByNumber(number);
		if (account.isPresent()) {
			return account.get();
		} else {
			throw new AccountFindException("There is no account with such number", new IllegalArgumentException());
		}
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
}