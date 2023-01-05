package com.goryaninaa.web.Bank.repository;

import java.util.Optional;

import com.goryaninaa.web.Bank.DAOConcurentStub.ClientDAO;
import com.goryaninaa.web.Bank.model.client.Client;
import com.goryaninaa.web.Bank.service.client.ClientRepository;

public class ClientRepositoryPOJO implements ClientRepository {

	private final ClientDAO clientDAO;
	
	public ClientRepositoryPOJO(ClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}
	
	@Override
	public Optional<Client> findByPassport(String passport) {
		Optional<Client> client = clientDAO.findByPassport(passport);
		
		return client;
	}

}
