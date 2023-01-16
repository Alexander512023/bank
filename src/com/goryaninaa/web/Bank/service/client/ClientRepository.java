package com.goryaninaa.web.Bank.service.client;

import java.util.Optional;

import com.goryaninaa.web.Bank.model.client.Client;

public interface ClientRepository {

	Optional<Client> findByPassport(String passport);

}
