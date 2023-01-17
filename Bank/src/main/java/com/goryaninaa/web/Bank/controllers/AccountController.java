package com.goryaninaa.web.Bank.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.goryaninaa.web.Bank.DTO.AccountDTO;
import com.goryaninaa.web.Bank.DTO.AccountOpenRequisitesDTO;
import com.goryaninaa.web.Bank.DTO.ClientDTO;
import com.goryaninaa.web.Bank.DTO.ErrorDTO;
import com.goryaninaa.web.Bank.DTO.OperationDTO;
import com.goryaninaa.web.Bank.model.account.Account;
import com.goryaninaa.web.Bank.model.account.AccountOpenRequisites;
import com.goryaninaa.web.Bank.model.operation.Operation;
import com.goryaninaa.web.Bank.model.operation.OperationRequisites;
import com.goryaninaa.web.Bank.service.account.AccountService;
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
		} catch (Throwable t) {
			t.printStackTrace();
			HttpResponse httpResponse = prepareResponseOnException(t);
			return httpResponse;
		}
	}

	@PostMapping("/deposit")
	public Response deposit(HttpRequest request, OperationDTO operationDTO) {
		OperationRequisites requisites = operationDTO.extractOperationRequisites();
		try {
			accountService.deposit(requisites);
			return new HttpResponse(HttpResponseCode.OK);
		} catch (Throwable t) {
			t.printStackTrace();
			HttpResponse httpResponse = prepareResponseOnException(t);
			return httpResponse;
		}
	}
	
	@PostMapping("/withdraw")
	public Response withdraw(HttpRequest request, OperationDTO operationDTO) {
		OperationRequisites requisites = operationDTO.extractOperationRequisites();
		try {
			accountService.withdraw(requisites);
			return new HttpResponse(HttpResponseCode.OK);
		} catch (Throwable t) {
			t.printStackTrace();
			HttpResponse httpResponse = prepareResponseOnException(t);
			return httpResponse;
		}
	}
	
	@PostMapping("/transfer")
	public Response transfer(HttpRequest request, OperationDTO operationDTO) {
		OperationRequisites requisites = operationDTO.extractOperationRequisites();
		try {
			accountService.transfer(requisites);
			return new HttpResponse(HttpResponseCode.OK);
		} catch (Throwable t) {
			t.printStackTrace();
			HttpResponse httpResponse = prepareResponseOnException(t);
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
		} catch (Throwable t) {
			t.printStackTrace();
			HttpResponse httpResponse = prepareResponseOnException(t);
			return httpResponse;
		}
	}
	
	private HttpResponse prepareResponseOnException(Throwable t) {
		Throwable cause = t.getCause();
		if (cause instanceof IllegalArgumentException) {
			ErrorDTO errorDTO = new ErrorDTO(404, t.getMessage());
			HttpResponse httpResponse = new HttpResponse(HttpResponseCode.NOTFOUND, errorDTO);
			return httpResponse;
		} else {
			ErrorDTO errorDTO = new ErrorDTO(500, t.getMessage());
			HttpResponse httpResponse = new HttpResponse(HttpResponseCode.INTERNALSERVERERROR, errorDTO);
			return httpResponse;
		}
	}
	
	private AccountDTO prepareAccountDTO(Account account) {
		List<Operation> operations = account.getHistory();
		List<OperationDTO> operationsDTO = new ArrayList<>();
		ClientDTO clientDTO = new ClientDTO(account.getOwner());
		for (Operation operation : operations) {
			operationsDTO.add(new OperationDTO(operation, clientDTO));
		}
		operationsDTO.sort(Comparator.comparing(OperationDTO::getHistoryNumber).reversed());
		return new AccountDTO(account, operationsDTO, clientDTO);
	}
}
