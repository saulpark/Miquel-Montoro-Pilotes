package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tui.proof.MainApplication;
import com.tui.proof.config.ApplicationConfig;
import com.tui.proof.model.Client;
import com.tui.proof.model.dto.ClientDTO;
import com.tui.proof.model.mapper.ClientMapper;
import com.tui.proof.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import utils.FileLoader;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest (classes = {ApplicationConfig.class, MainApplication.class, FileLoader.class})
@Log4j2
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FileLoader fileLoader;

    @Test
    public void createClientTest() throws Exception {
        ClientDTO clientDTO = fileLoader.getClient();
        Client client = ClientMapper.INSTANCE.ClientDtoToClient(clientDTO);
        Optional<Client> optionalClient =  Optional.of(client);

        Mockito.when(clientService.createClient(Mockito.any())).thenReturn(optionalClient);

        mockMvc.perform(post("http://localhost:8080/client")
                        .contentType("application/json").content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isOk());
    }


    @Test
    public void createClientBadRequestTest() throws Exception {
        ClientDTO clientDTO = fileLoader.getClient();
        Client client = ClientMapper.INSTANCE.ClientDtoToClient(clientDTO);

        Mockito.when(clientService.createClient(Mockito.any())).thenReturn(Optional.empty());

        mockMvc.perform(post("http://localhost:8080/client")
                        .contentType("application/json").content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().is4xxClientError());
    }



}
