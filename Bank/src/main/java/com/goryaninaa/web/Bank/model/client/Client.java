package com.goryaninaa.web.Bank.model.client;

import java.util.List;
import java.util.Objects;

import com.goryaninaa.web.Bank.model.account.Account;

public class Client {
	
	private int id;
	private String passport;
	private String firstName;
	private String secondName;
	private String dateOfBirth;
	private List<Account> products;
	
	public Client() {
	}
	
	public Client(String passport) {
		this.passport = passport;
	}
	
	public Client(int id, String passport, String firstName, String secondName, String dateOfBirth) {
		super();
		this.id = id;
		this.passport = passport;
		this.firstName = firstName;
		this.secondName = secondName;
		this.dateOfBirth = dateOfBirth;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public List<Account> getProducts() {
		return products;
	}

	public void setProducts(List<Account> products) {
		this.products = products;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateOfBirth, firstName, passport, secondName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(passport, other.passport) && Objects.equals(secondName, other.secondName);
	}
	
	
}
