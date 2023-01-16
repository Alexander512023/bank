package com.goryaninaa.web.Bank.repository;

import com.goryaninaa.web.Bank.DAOConcurentStub.NumberCapacity;
import com.goryaninaa.web.Bank.service.account.NumberCapacityRepository;

public class NumberCapacityRepositoryPOJO implements NumberCapacityRepository {
	
	private final NumberCapacity numberCapacity;
	
	public NumberCapacityRepositoryPOJO(NumberCapacity numberCapacity) {
		this.numberCapacity = numberCapacity;
	}

	@Override
	public int getNumber() {
		return numberCapacity.getNumber();
	}

}
