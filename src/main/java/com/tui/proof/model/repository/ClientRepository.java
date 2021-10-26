package com.tui.proof.model.repository;

import com.tui.proof.model.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ClientRepository extends CrudRepository<Client, UUID> {
}
