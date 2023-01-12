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
		AccountRequisites enrichedRequisites = enrichAccountRequisites(requisites);
		int accountNumber = numberCapacityRepository.getNumber();
		Account account = new Account(enrichedRequisites, accountNumber);
		Transaction openTransaction = createOpenTransaction(enrichedRequisites, account);
		accountRepository.save(account);
		transactionRepository.save(openTransaction);
	}
	
	public void deposit(TransactionRequisites requisites) {
		Account account = findByNumber(requisites.getAccountRecepient().getNumber());
		synchronized(account) {
			TransactionRequisites enrichedRequisites = enrichDepositTransaction(requisites, account);
			account.deposit(enrichedRequisites.getAmount());
			TransactionRequisites completedRequisites = completeDepositTransaction(enrichedRequisites, account);
			Transaction deposit = new Transaction(completedRequisites);
			accountRepository.update(account);
			transactionRepository.save(deposit);
		}
	}
	
	public void withdraw(TransactionRequisites requisites) {
		Account account = findByNumber(requisites.getAccountFrom().getNumber());
		synchronized(account) {
			TransactionRequisites enrichedRequisites = enrichWithdrawTransaction(requisites, account);
			account.withdraw(enrichedRequisites.getAmount());
			TransactionRequisites completedRequisites = completeWithdrawTransaction(enrichedRequisites, account);
			Transaction withdraw = new Transaction(completedRequisites);
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

	private AccountRequisites enrichAccountRequisites(AccountRequisites requisites) {
		Optional<Client> initiator = clientRepository.findByPassport(requisites.getTransaction().getClient().getPassport());
		if (initiator.isPresent()) {
			requisites.getTransaction().setClient(initiator.get());
			return requisites;
		} else {
			throw new IllegalArgumentException("There is no such client");
		}
	}
	
	private TransactionRequisites completeDepositTransaction(TransactionRequisites requisites, Account account) {
		requisites.setBalanceAfter(account.getBalance());
		requisites.setHistoryNumber(account.getLastTransactionNumber());
		return requisites;
	}

	private TransactionRequisites enrichDepositTransaction(TransactionRequisites requisites, Account account) {
		Optional<Client> client = clientRepository.findByPassport(requisites.getClient().getPassport());
		if (client.isPresent()) {
			requisites.setAccount(account);
			requisites.setAccountRecepient(account);
			requisites.setBalanceBefore(account.getBalance());
			requisites.setClient(client.get());
			return requisites;
		} else {
			throw new IllegalArgumentException("There is no such client");
		}
	}
	
	private TransactionRequisites completeWithdrawTransaction(TransactionRequisites requisites, Account account) {
		requisites.setBalanceAfter(account.getBalance());
		requisites.setHistoryNumber(account.getLastTransactionNumber());
		return requisites;
	}

	private TransactionRequisites enrichWithdrawTransaction(TransactionRequisites requisites, Account account) {
		Optional<Client> client = clientRepository.findByPassport(requisites.getClient().getPassport());
		if (client.isPresent()) {
			requisites.setAccount(account);
			requisites.setAccountFrom(account);
			requisites.setBalanceBefore(account.getBalance());
			requisites.setClient(client.get());
			return requisites;
		} else {
			throw new IllegalArgumentException("There is no such client");
		}
	}
}