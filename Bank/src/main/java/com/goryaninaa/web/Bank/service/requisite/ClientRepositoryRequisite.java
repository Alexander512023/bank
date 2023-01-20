package com.goryaninaa.web.Bank.service.requisite;

import java.util.Optional;

import com.goryaninaa.web.Bank.model.client.Client;

public interface ClientRepositoryRequisite {

	Optional<Client> findByPassport(String passport);

}
