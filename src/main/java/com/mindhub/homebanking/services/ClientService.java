package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {

    public List<Client> getAllClients();
    public Client getClientsById (long id);
    public Client getClientsByEmail(String email);
    public void saveClient(Client client);
}
