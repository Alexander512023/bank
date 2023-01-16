package com.goryaninaa.web.Bank.DAOConcurentStub;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.goryaninaa.web.Bank.model.client.Client;

public class ClientDAO {

	private static int idCounter = 1;
	private final List<Client> clients;

	public ClientDAO() {
		this.clients = new ArrayList<>();
	}
	
	public void save(Client client) {
		for (Client savedEarlierClient : clients) {
			if (savedEarlierClient.equals(client)) {
				throw new RuntimeException("This client already exists");
			}
		}
		
		client.setId(idCounter++);
		
		clients.add(client);
	}

	public Optional<Client> findByPassport(String passport) {
		Optional<Client> desiredClient = Optional.empty();

		for (Client client : clients) {
			if (client.getPassport().equals(passport)) {
				desiredClient = Optional.ofNullable(client);
				break;
			}
		}

		return desiredClient;
	}

}
