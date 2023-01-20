package com.goryaninaa.web.Bank.model.account;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.goryaninaa.web.Bank.model.client.Client;
import com.goryaninaa.web.Bank.model.operation.Operation;

//TODO add unit test
public class Account implements Comparable<Account> {

	private int id;
	private int lastOperationNumber;
	private int balance;
	private int number;
	private State state;
	private LocalDateTime openedAt;
	private LocalDateTime closedAt;
	private Client owner;
	private List<Operation> history = new ArrayList<>();
	private AccountType type;
	private int term;
	private LocalDate prolongationDate; 
	
	public Account() {
	}
	
	public Account(int number) {
		this.number = number;
	}
	
	public Account(AccountOpenRequisites requisites, int number) {
		setLastTransactionNumber(1);
		setBalance(requisites.getOperationRequisites().getAmount());
		setNumber(number);
		setState(State.OPENED);
		setOpenedAt(LocalDateTime.now());
		setOwner(requisites.getOperationRequisites().getClient());
		setType(requisites.getAccountType());
		setTerm(requisites.getTerm());
		setProlongationDate(openedAt.toLocalDate().plusMonths(term));
	}
	
	public void deposit(int amount) {
		balance += Math.abs(amount);
		lastOperationNumber++;
	}

	public void withdraw(int amount) {
		if (balance - Math.abs(amount) > 0) {
			balance -= Math.abs(amount);
			lastOperationNumber++;
		} else {
			throw new RuntimeException("Insufficient funds");
		}
	}
	
	@Override
	public int compareTo(Account that) {
		if (
				(this.state.equals(that.state) && this.openedAt.compareTo(that.openedAt) > 0)
				|| (this.state.equals(State.OPENED) && that.state.equals(State.CLOSED))
				) {
			return 1;
		} else {
			return -1;
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLastOperationNumber() {
		return lastOperationNumber;
	}

	public void setLastTransactionNumber(int lastTransactionNumber) {
		this.lastOperationNumber = lastTransactionNumber;
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

	public Client getOwner() {
		return owner;
	}

	public void setOwner(Client owner) {
		this.owner = owner;
	}

	public List<Operation> getHistory() {
		return history;
	}

	public void setHistory(List<Operation> history) {
		this.history = history;
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

	public void addTransaction(Operation transaction) {
		history.add(transaction);
		history.sort(Comparator.comparing(Operation::getHistoryNumber));
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", transactionNumber=" + lastOperationNumber + ", balance=" + balance + ", number="
				+ number + ", state=" + state + ", openedAt=" + openedAt + ", closedAt=" + closedAt
				+ ", client=" + owner;
	}
}
