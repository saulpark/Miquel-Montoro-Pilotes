package com.tui.proof.ws.controller;

import com.tui.proof.model.Client;
import com.tui.proof.model.dto.ClientDTO;
import com.tui.proof.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Log4j2
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/client")
    public ResponseEntity<Client> createClient(@RequestBody ClientDTO client) {
        Optional<Client> optionalClient = clientService.createClient(client);
        return optionalClient.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
    }
}
