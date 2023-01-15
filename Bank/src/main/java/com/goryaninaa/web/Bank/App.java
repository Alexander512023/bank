package com.goryaninaa.web.Bank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.goryaninaa.logger.LoggingMech.LoggingMech;
import com.goryaninaa.web.Bank.DAOConcurentStub.AccountDAO;
import com.goryaninaa.web.Bank.DAOConcurentStub.AccountDataAccessByNumberStrategy;
import com.goryaninaa.web.Bank.DAOConcurentStub.ClientDAO;
import com.goryaninaa.web.Bank.DAOConcurentStub.NumberCapacity;
import com.goryaninaa.web.Bank.DAOConcurentStub.TransactionDAO;
import com.goryaninaa.web.Bank.controllers.AccountController;
import com.goryaninaa.web.Bank.model.account.Account;
import com.goryaninaa.web.Bank.model.client.Client;
import com.goryaninaa.web.Bank.repository.AccountNumberExtractStrategy;
import com.goryaninaa.web.Bank.repository.AccountRepositoryCached;
import com.goryaninaa.web.Bank.repository.ClientRepositoryPOJO;
import com.goryaninaa.web.Bank.repository.NumberCapacityRepositoryPOJO;
import com.goryaninaa.web.Bank.repository.TransactionRepositoryPOJO;
import com.goryaninaa.web.Bank.service.account.AccountRepository;
import com.goryaninaa.web.Bank.service.account.AccountService;
import com.goryaninaa.web.Cache.Cache;
import com.goryaninaa.web.Cache.CacheKeyFactory;
import com.goryaninaa.web.Cache.DataAccessStrategy;
import com.goryaninaa.web.Cache.KeyExtractStrategy;
import com.goryaninaa.web.HttpServer.HttpServer;
import com.goryaninaa.web.HttpServer.requesthandler.Controller;

public class App {

	public static void main(String[] args) {
		Client client = new Client(1, "36 10 000001", "Alex", "Goryanin", "30.10.1989");
		try {
			Properties properties = new Properties();
			properties.load(App.class.getResourceAsStream("/config.properties"));
			
			LoggingMech.getInstance().apply(properties);
			LoggingMech.getInstance().startLogging();
					
			DataAccessStrategy accountDataAccessByNumber = new AccountDataAccessByNumberStrategy();
			Map<String, DataAccessStrategy> accountDataAccesses = new ConcurrentHashMap<>();
			accountDataAccesses.put(accountDataAccessByNumber.getStrategy(), accountDataAccessByNumber);
			AccountDAO accountDAO = new AccountDAO(accountDataAccesses);
			Cache<Account> accountCache = new Cache<>(accountDAO, properties);
			ClientDAO clientDAO = new ClientDAO();
			NumberCapacity numberCapacity = new NumberCapacity();
			TransactionDAO depositDAO = new TransactionDAO();
			TransactionRepositoryPOJO transactionRepository = new TransactionRepositoryPOJO(depositDAO);
			KeyExtractStrategy accountNumberExtractStrategy = new AccountNumberExtractStrategy();
			Map<String, KeyExtractStrategy> accountKeyExtractStrategies = new ConcurrentHashMap<>();
			accountKeyExtractStrategies.put(accountNumberExtractStrategy.getStrategy(), accountNumberExtractStrategy);
			CacheKeyFactory accountCacheKeyFactory = new CacheKeyFactory(accountKeyExtractStrategies);
			AccountRepository accountRepository = new AccountRepositoryCached(accountCache, accountDAO,
					transactionRepository, accountCacheKeyFactory);
			
			clientDAO.save(client);
			
			ClientRepositoryPOJO clientRepository = new ClientRepositoryPOJO(clientDAO);
			NumberCapacityRepositoryPOJO numberCapacityRepository = new NumberCapacityRepositoryPOJO(numberCapacity);
			
			AccountService accountService = new AccountService(accountRepository, transactionRepository,
					numberCapacityRepository, clientRepository);
			Controller accountController = new AccountController(accountService);
	
			List<Controller> controllers = new ArrayList<>();
			controllers.add(accountController);
			
			HttpServer httpServer = new HttpServer(properties, controllers);
			httpServer.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
