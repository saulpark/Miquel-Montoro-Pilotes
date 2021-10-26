package utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.tui.proof.model.dto.ClientDTO;
import com.tui.proof.model.dto.OrderDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;

@Component
@Log4j2
public class FileLoader {

    Gson gson;

    public FileLoader(){
        gson = new Gson();
    }

    public ClientDTO getClient() {
        try {
            JsonReader reader = new JsonReader(new FileReader("./src/test/resources/clientDTO.json"));
            return gson.fromJson(reader, ClientDTO.class);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public OrderDTO getOrder() {
        try {
            JsonReader reader = new JsonReader(new FileReader("./src/test/resources/orderDTO.json"));
            return gson.fromJson(reader, OrderDTO.class);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
