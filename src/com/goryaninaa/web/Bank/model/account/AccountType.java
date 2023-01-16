package com.goryaninaa.web.Bank.model.account;

public enum AccountType {
	
	VKLAD100(100),
	VKLAD75(75);
	
	private final int rate;
	
	private AccountType(int rate) {
		this.rate = rate;
	}


	public int getRate() {
		return rate;
	}
}
