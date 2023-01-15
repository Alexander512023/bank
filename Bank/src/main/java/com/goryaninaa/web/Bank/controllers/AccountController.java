package com.goryaninaa.web.Bank.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.goryaninaa.web.Bank.DTO.AccountDTO;
import com.goryaninaa.web.Bank.DTO.AccountOpenRequisitesDTO;
import com.goryaninaa.web.Bank.DTO.ClientDTO;
import com.goryaninaa.web.Bank.DTO.ErrorDTO;
import com.goryaninaa.web.Bank.DTO.TransactionDTO;
import com.goryaninaa.web.Bank.model.account.Account;
import com.goryaninaa.web.Bank.model.account.AccountOpenRequisites;
import com.goryaninaa.web.Bank.model.transaction.Transaction;
import com.goryaninaa.web.Bank.model.transaction.TransactionRequisites;
import com.goryaninaa.web.Bank.service.account.AccountService;
import com.goryaninaa.web.Bank.service.account.exception.AccountDepositException;
import com.goryaninaa.web.Bank.service.account.exception.AccountFindException;
import com.goryaninaa.web.Bank.service.account.exception.AccountOpenException;
import com.goryaninaa.web.Bank.service.account.exception.AccountTransferException;
import com.goryaninaa.web.Bank.service.account.exception.AccountWithdrawException;
import com.goryaninaa.web.HttpServer.entity.HttpRequest;
import com.goryaninaa.web.HttpServer.entity.HttpResponse;
import com.goryaninaa.web.HttpServer.requesthandler.Controller;
import com.goryaninaa.web.HttpServer.requesthandler.HttpResponseCode;
import com.goryaninaa.web.HttpServer.requesthandler.Response;
import com.goryaninaa.web.HttpServer.requesthandler.annotation.GetMapping;
import com.goryaninaa.web.HttpServer.requesthandler.annotation.PostMapping;
import com.goryaninaa.web.HttpServer.requesthandler.annotation.RequestMapping;

@RequestMapping("/account")
public class AccountController implements Controller {

	private final AccountService accountService;
	
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@PostMapping("/open")
	public Response open(HttpRequest request, AccountOpenRequisitesDTO requisitesDTO) {
		AccountOpenRequisites accountRequisites = requisitesDTO.extractAccountRequisites();
		try {
			accountService.open(accountRequisites);
			return new HttpResponse(HttpResponseCode.OK);
		} catch (AccountOpenException e) {
			e.printStackTrace();
			HttpResponse httpResponse = prepareResponse(e);
			return httpResponse;
		}
	}

	@PostMapping("/deposit")
	public Response deposit(HttpRequest request, TransactionDTO transactionDTO) {
		TransactionRequisites requisites = transactionDTO.extractTransactionRequisites();
		try {
			accountService.deposit(requisites);
			return new HttpResponse(HttpResponseCode.OK);
		} catch (AccountDepositException e) {
			e.printStackTrace();
			HttpResponse httpResponse = prepareResponse(e);
			return httpResponse;
		}
	}
	
	@PostMapping("/withdraw")
	public Response withdraw(HttpRequest request, TransactionDTO transactionDTO) {
		TransactionRequisites requisites = transactionDTO.extractTransactionRequisites();
		try {
			accountService.withdraw(requisites);
			return new HttpResponse(HttpResponseCode.OK);
		} catch (AccountWithdrawException e) {
			e.printStackTrace();
			HttpResponse httpResponse = prepareResponse(e);
			return httpResponse;
		}
	}
	
	@PostMapping("/transfer")
	public Response transfer(HttpRequest request, TransactionDTO transactionDTO) {
		TransactionRequisites requisites = transactionDTO.extractTransactionRequisites();
		try {
			accountService.transfer(requisites);
			return new HttpResponse(HttpResponseCode.OK);
		} catch (AccountTransferException e) {
			e.printStackTrace();
			HttpResponse httpResponse = prepareResponse(e);
			return httpResponse;
		}
	}
	
	@GetMapping("/view")
	public Response view(HttpRequest request) {
		Optional<String> accountNumberString = request.getParameterByName("number");
		try {
			Account account = accountService.findByNumber(Integer.valueOf(accountNumberString.get()));
			AccountDTO responseAccountDTO = prepareAccountDTO(account);
			return new HttpResponse(HttpResponseCode.OK, responseAccountDTO);
		} catch (AccountFindException e) {
			e.printStackTrace();
			HttpResponse httpResponse = prepareResponse(e);
			return httpResponse;
		}
	}
	
	private HttpResponse prepareResponse(Exception e) {
		Throwable cause = e.getCause();
		if (cause instanceof IllegalArgumentException) {
			ErrorDTO errorDTO = new ErrorDTO(404, e.getMessage());
			HttpResponse httpResponse = new HttpResponse(HttpResponseCode.NOTFOUND, errorDTO);
			return httpResponse;
		} else {
			ErrorDTO errorDTO = new ErrorDTO(500, e.getMessage());
			HttpResponse httpResponse = new HttpResponse(HttpResponseCode.INTERNALSERVERERROR, errorDTO);
			return httpResponse;
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
