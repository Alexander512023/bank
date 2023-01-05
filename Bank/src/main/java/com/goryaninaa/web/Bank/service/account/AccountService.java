package com.goryaninaa.web.Bank.service.account;

import java.util.Optional;

import com.goryaninaa.web.Bank.model.account.Account;
import com.goryaninaa.web.Bank.model.account.AccountRequisites;
import com.goryaninaa.web.Bank.model.client.Client;
import com.goryaninaa.web.Bank.model.transaction.Transaction;
import com.goryaninaa.web.Bank.model.transaction.TransactionRequisites;
import com.goryaninaa.web.Bank.service.client.ClientRepository;

public class AccountService {

	private final AccountRepository accountRepository;
	private final TransactionRepository transactionRepository;
	private final NumberCapacityRepository numberCapacityRepository;
	private final ClientRepository clientRepository;

	//TODO add unit test
	public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository,
			NumberCapacityRepository numberCapacityRepository, ClientRepository clientRepository) {
		this.accountRepository = accountRepository;
		this.transactionRepository = transactionRepository;
		this.numberCapacityRepository = numberCapacityRepository;
		this.clientRepository = clientRepository;
	}

	public void open(AccountRequisites requisites) {
		enrichAccountRequisites(requisites);
		int accountNumber = numberCapacityRepository.getNumber();
		Account account = new Account(requisites, accountNumber);
		Transaction openTransaction = createOpenTransaction(requisites, account);
		accountRepository.save(account);
		transactionRepository.save(openTransaction);
	}
	
	public void deposit(TransactionRequisites requisites) {
		Account account = findByNumber(requisites.getAccountRecepient().getNumber());
		synchronized(account) {
			enrichDepositTransaction(requisites, account);
			account.deposit(requisites.getAmount());
			completeDepositTransaction(requisites, account);
			Transaction deposit = new Transaction(requisites);
			accountRepository.update(account);
			transactionRepository.save(deposit);
		}
	}
	
	public void withdraw(TransactionRequisites requisites) {
		Account account = findByNumber(requisites.getAccountFrom().getNumber());
		synchronized(account) {
			enrichWithdrawTransaction(requisites, account);
			account.withdraw(requisites.getAmount());
			completeWithdrawTransaction(requisites, account);
			Transaction withdraw = new Transaction(requisites);
			transactionRepository.save(withdraw);
			accountRepository.update(account);
		}
	}

	public void transfer(TransactionRequisites requisites) {
		deposit(requisites);
		withdraw(requisites);
	}
	
	public Account findByNumber(int number) {
		Optional<Account> account = accountRepository.findByNumber(number);
		if (account.isPresent()) {
			return account.get();
		} else {
			throw new IllegalArgumentException("There is no account with such number");
		}
	}
	
//	//TODO
//	public void prolongate(Prolongation prolongation) {
//		Optional<Account> account = accountRepository.findByNumber(prolongation.getProduct().getNumber());
//		
//		if (account.isPresent()) {
//			account.get().prolongate();
//			
//			transactionRepository.save(prolongation);
//			accountRepository.save(account.get());
//		} else {
//			//TODO
//		}
//	}

	private Transaction createOpenTransaction(AccountRequisites requisites, Account account) {
		Transaction openTransaction = requisites.getTransaction();
		openTransaction.setAccount(account);
		openTransaction.setAccountRecepient(account);
		return openTransaction;
	}

	private void enrichAccountRequisites(AccountRequisites requisites) {
		Optional<Client> initiator = clientRepository.findByPassport(requisites.getTransaction().getClient().getPassport());
		if (initiator.isPresent()) {
			requisites.getTransaction().setClient(initiator.get());
		} else {
			throw new IllegalArgumentException("There is no such client");
		}
	}
	
	private void completeDepositTransaction(TransactionRequisites requisites, Account account) {
		requisites.setBalanceAfter(account.getBalance());
		requisites.setHistoryNumber(account.getLastTransactionNumber());
	}

	private void enrichDepositTransaction(TransactionRequisites requisites, Account account) {
		Optional<Client> client = clientRepository.findByPassport(requisites.getClient().getPassport());
		if (client.isPresent()) {
			requisites.setAccount(account);
			requisites.setAccountRecepient(account);
			requisites.setBalanceBefore(account.getBalance());
			requisites.setClient(client.get());
		} else {
			throw new IllegalArgumentException("There is no such client");
		}
	}
	
	private void completeWithdrawTransaction(TransactionRequisites requisites, Account account) {
		requisites.setBalanceAfter(account.getBalance());
		requisites.setHistoryNumber(account.getLastTransactionNumber());
	}

	private void enrichWithdrawTransaction(TransactionRequisites requisites, Account account) {
		Optional<Client> client = clientRepository.findByPassport(requisites.getClient().getPassport());
		if (client.isPresent()) {
			requisites.setAccount(account);
			requisites.setAccountFrom(account);
			requisites.setBalanceBefore(account.getBalance());
			requisites.setClient(client.get());
		} else {
			throw new IllegalArgumentException("There is no such client");
		}
	}
}