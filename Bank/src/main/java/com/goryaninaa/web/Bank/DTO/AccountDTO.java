package com.goryaninaa.web.Bank.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.goryaninaa.web.Bank.model.account.Account;
import com.goryaninaa.web.Bank.model.account.AccountType;
import com.goryaninaa.web.Bank.model.account.State;

public class AccountDTO {

	private int lastTransactionNumber;
	private int balance;
	private int number;
	private State state;
	private LocalDateTime openedAt;
	private LocalDateTime closedAt;
	private ClientDTO owner;
	private List<OperationDTO> historyDTO;
	private AccountType type;
	private int term;
	private LocalDate prolongationDate;
	
	public AccountDTO() {
	}
	
	public AccountDTO(Account account) {
		this.number = account.getNumber();
	}

	public AccountDTO(Account account, List<OperationDTO> historyDTO, ClientDTO owner) {
		this.lastTransactionNumber = account.getLastOperationNumber();
		this.balance = account.getBalance();
		this.number = account.getNumber();
		this.state = account.getState();
		this.openedAt = account.getOpenedAt();
		this.closedAt = account.getClosedAt();
		this.owner = owner;
		this.historyDTO = historyDTO;
		this.type = account.getType();
		this.term = account.getTerm();
		this.prolongationDate = account.getProlongationDate();
	}

	public int getLastTransactionNumber() {
		return lastTransactionNumber;
	}

	public void setLastTransactionNumber(int lastTransactionNumber) {
		this.lastTransactionNumber = lastTransactionNumber;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public LocalDateTime getOpenedAt() {
		return openedAt;
	}

	public void setOpenedAt(LocalDateTime openedAt) {
		this.openedAt = openedAt;
	}

	public LocalDateTime getClosedAt() {
		return closedAt;
	}

	public void setClosedAt(LocalDateTime closedAt) {
		this.closedAt = closedAt;
	}

	public ClientDTO getOwner() {
		return owner;
	}

	public void setOwner(ClientDTO owner) {
		this.owner = owner;
	}

	public List<OperationDTO> getHistoryDTO() {
		return historyDTO;
	}

	public void setHistoryDTO(List<OperationDTO> historyDTO) {
		this.historyDTO = historyDTO;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public LocalDate getProlongationDate() {
		return prolongationDate;
	}

	public void setProlongationDate(LocalDate prolongationDate) {
		this.prolongationDate = prolongationDate;
	}
	
}
