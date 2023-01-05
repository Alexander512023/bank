package com.goryaninaa.web.Bank.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.goryaninaa.web.Bank.DTO.AccountDTO;
import com.goryaninaa.web.Bank.DTO.AccountRequisitesDTO;
import com.goryaninaa.web.Bank.DTO.ClientDTO;
import com.goryaninaa.web.Bank.DTO.TransactionDTO;
import com.goryaninaa.web.Bank.model.account.Account;
import com.goryaninaa.web.Bank.model.account.AccountRequisites;
import com.goryaninaa.web.Bank.model.transaction.Transaction;
import com.goryaninaa.web.Bank.model.transaction.TransactionRequisites;
import com.goryaninaa.web.Bank.service.account.AccountService;
import com.goryaninaa.web.HttpServer.entity.HttpRequest;
import com.goryaninaa.web.HttpServer.entity.HttpResponse;
import com.goryaninaa.web.HttpServer.requesthandler.Controller;
import com.goryaninaa.web.HttpServer.requesthandler.HttpResponseCode;
import com.goryaninaa.web.HttpServer.requesthandler.Response;
import com.goryaninaa.web.HttpServer.requesthandler.annotation.GetMapping;
import com.goryaninaa.web.HttpServer.requesthandler.annotation.PostMapping;
import com.goryaninaa.web.HttpServer.requesthandler.annotation.RequestMapping;

@RequestMapping("/balance")
public class AccountController implements Controller {

	private final AccountService accountService;
	
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@PostMapping("/open")
	public Response open(HttpRequest request, AccountRequisitesDTO requisitesDTO) {
		AccountRequisites accountRequisites = requisitesDTO.extractAccountRequisites();
		accountService.open(accountRequisites);
		return new HttpResponse(HttpResponseCode.OK);
	}
	
	@PostMapping("/deposit")
	public Response deposit(HttpRequest request, TransactionDTO transactionDTO) {
		TransactionRequisites requisites = transactionDTO.extractTransactionRequisites();
		accountService.deposit(requisites);
		return new HttpResponse(HttpResponseCode.OK);
	}
	
	@PostMapping("/withdraw")
	public Response withdraw(HttpRequest request, TransactionDTO transactionDTO) {
		TransactionRequisites requisites = transactionDTO.extractTransactionRequisites();
		accountService.withdraw(requisites);
		return new HttpResponse(HttpResponseCode.OK);
	}
	
	@PostMapping("/transfer")
	public Response transfer(HttpRequest request, TransactionDTO transactionDTO) {
		TransactionRequisites requisites = transactionDTO.extractTransactionRequisites();
		accountService.transfer(requisites);
		return new HttpResponse(HttpResponseCode.OK);
	}
	
	@GetMapping("/view")
	public Response view(HttpRequest request) {
		Optional<String> accountNumberString = request.getParameterByName("number");
		try {
			Account account = accountService.findByNumber(Integer.valueOf(accountNumberString.get()));
			AccountDTO responseAccountDTO = prepareAccountDTO(account);
			return new HttpResponse(HttpResponseCode.OK, responseAccountDTO);
		} catch (IllegalArgumentException e) {
			return new HttpResponse(HttpResponseCode.NOTFOUND);
		}
	}
	
	private AccountDTO prepareAccountDTO(Account account) {
		List<Transaction> transactions = account.getHistory();
		List<TransactionDTO> transactionsDTO = new ArrayList<>();
		ClientDTO clientDTO = new ClientDTO(account.getOwner());
		for (Transaction transaction : transactions) {
			transactionsDTO.add(new TransactionDTO(transaction, clientDTO));
		}
		transactionsDTO.sort(Comparator.comparing(TransactionDTO::getHistoryNumber).reversed());
		return new AccountDTO(account, transactionsDTO, clientDTO);
	}
}
