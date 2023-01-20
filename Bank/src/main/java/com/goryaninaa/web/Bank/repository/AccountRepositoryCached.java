package com.goryaninaa.web.Bank.repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.goryaninaa.web.Bank.DAOConcurentStub.AccountDAO;
import com.goryaninaa.web.Bank.model.account.Account;
import com.goryaninaa.web.Bank.model.operation.Operation;
import com.goryaninaa.web.Bank.service.account.AccountRepository;
import com.goryaninaa.web.Bank.service.operation.OperationRepository;
import com.goryaninaa.web.Cache.Cache;
import com.goryaninaa.web.Cache.CacheKey;
import com.goryaninaa.web.Cache.CacheKeyFactory;

public class AccountRepositoryCached implements AccountRepository {

	private final Cache<Account> cache;
	private final CacheKeyFactory cacheKeyFactory;
	private final AccountDAO accountDAO;
	private final OperationRepository transactionRepository;
	
	public AccountRepositoryCached(Cache<Account> cache, AccountDAO accountDAO,
			OperationRepository transactionRepository, CacheKeyFactory cacheKeyFactory) {
		this.cache = cache;
		this.cacheKeyFactory = cacheKeyFactory;
		this.accountDAO = accountDAO;
		this.transactionRepository = transactionRepository;
	}

	@Override
	public void save(Account account) {
		List<CacheKey> cacheKeys = cacheKeyFactory.generateAllCacheKeys(account);
		cache.remove(cacheKeys);
		accountDAO.save(account);
	}

	@Override
	public Optional<Account> findByNumber(int number) {
		CacheKey cacheKey = cacheKeyFactory.generateCacheKey(number, AccountAccessStrategyType.NUMBER);
		Optional<Account> account = cache.getData(cacheKey);
		if (account.isPresent()) {
			List<Operation> transactions = transactionRepository.findOperationsOfAccount(account.get().getId());
			transactions.sort(Comparator.comparing(Operation::getHistoryNumber));
			account.get().setHistory(transactions);
		}
		return account;
	}

	@Override
	public void update(Account account) {
		List<CacheKey> cacheKeys = cacheKeyFactory.generateAllCacheKeys(account);
		cache.remove(cacheKeys);
		accountDAO.update(account);
	}
}
