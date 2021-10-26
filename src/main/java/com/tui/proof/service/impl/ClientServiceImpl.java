package com.tui.proof.service.impl;

import com.tui.proof.config.ApplicationConfig;
import com.tui.proof.model.Client;
import com.tui.proof.model.dto.ClientDTO;
import com.tui.proof.model.mapper.ClientMapper;
import com.tui.proof.model.repository.ClientRepository;
import com.tui.proof.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Log4j2
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Override
    public Optional<Client> createClient(ClientDTO clientDTO) {
        Client client = ClientMapper.INSTANCE.ClientDtoToClient(clientDTO);
        if(Objects.isNull(client.getClientId()) && validatePhoneFormat(client.getTelephone())){
            return Optional.of(clientRepository.save(client));
        }
        return Optional.empty();
    }

    private boolean validatePhoneFormat(String telephone){
        Pattern pattern = Pattern.compile(applicationConfig.getPhoneFormat());
        Matcher matcher = pattern.matcher(telephone);
        return matcher.matches();
    }
}
