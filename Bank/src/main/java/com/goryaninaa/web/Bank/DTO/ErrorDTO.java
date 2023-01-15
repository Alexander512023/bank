package com.goryaninaa.web.Bank.DTO;

public class ErrorDTO {
	
	private final int CODE;
	private final String MESSAGE;
	
	public ErrorDTO(int cODE, String mESSAGE) {
		super();
		CODE = cODE;
		MESSAGE = mESSAGE;
	}

	public int getCODE() {
		return CODE;
	}

	public String getMESSAGE() {
		return MESSAGE;
	}
}
