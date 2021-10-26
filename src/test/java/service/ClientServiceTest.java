package service;

import com.tui.proof.MainApplication;
import com.tui.proof.config.ApplicationConfig;
import com.tui.proof.model.Client;
import com.tui.proof.model.dto.ClientDTO;
import com.tui.proof.model.mapper.ClientMapper;
import com.tui.proof.model.repository.ClientRepository;
import com.tui.proof.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import utils.FileLoader;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationConfig.class, MainApplication.class, FileLoader.class})
@Log4j2
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private FileLoader fileLoader;

    @MockBean
    private ClientRepository clientRepository;

    @Test
    public void createClientTest() {
        ClientDTO clientDTO = fileLoader.getClient();
        Client client = ClientMapper.INSTANCE.ClientDtoToClient(clientDTO);

        Mockito.when(clientRepository.save(Mockito.any())).thenReturn(client);

        Optional<Client> optionalClient = clientService.createClient(clientDTO);

        Assert.assertTrue(optionalClient.isPresent());

    }

    @Test
    public void createClientErrorPhoneFormatTest() {
        ClientDTO clientDTO = fileLoader.getClient();
        clientDTO.setTelephone("1234567890");
        Client client = ClientMapper.INSTANCE.ClientDtoToClient(clientDTO);

        Mockito.when(clientRepository.save(Mockito.any())).thenReturn(client);

        Optional<Client> optionalClient = clientService.createClient(clientDTO);

        Assert.assertTrue(optionalClient.isEmpty());

    }

}
