package com.tui.proof.service;

import com.tui.proof.model.Client;
import com.tui.proof.model.dto.ClientDTO;

import java.util.Optional;

public interface ClientService {
    public Optional<Client> createClient(ClientDTO client);
}
