package com.goryaninaa.web.Bank.DTO;

import java.util.List;

import com.goryaninaa.web.Bank.model.account.Account;
import com.goryaninaa.web.Bank.model.client.Client;

public class ClientDTO {

	private String passport;
	private String firstName;
	private String secondName;
	private String dateOfBirth;
	private List<Account> products;
	
	public ClientDTO() {
		
	}
	
	public ClientDTO(Client client) {
		this.passport = client.getPassport();
		this.firstName = client.getFirstName();
		this.secondName = client.getSecondName();
		this.dateOfBirth = client.getDateOfBirth();
		this.products = client.getProducts();
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

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<Account> getProducts() {
		return products;
	}

	public void setProducts(List<Account> products) {
		this.products = products;
	}

}
