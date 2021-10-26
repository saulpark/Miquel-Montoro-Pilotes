package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tui.proof.MainApplication;
import com.tui.proof.config.ApplicationConfig;
import com.tui.proof.model.Order;
import com.tui.proof.model.dto.ClientDTO;
import com.tui.proof.model.dto.OrderDTO;
import com.tui.proof.model.mapper.OrderMapper;
import com.tui.proof.service.OrderService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {ApplicationConfig.class, MainApplication.class, FileLoader.class})
@Log4j2
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FileLoader fileLoader;

    @Test
    public void createOrderTest() throws Exception {
        OrderDTO orderDto = fileLoader.getOrder();
        orderDto.setOrderId(new UUID(0L, 0L));
        orderDto.getDeliveryAddress().setAddressId(new UUID(0L, 0L));
        Order order = OrderMapper.INSTANCE.OrderDtoToOrder(orderDto);
        Optional<Order> optionalOrder = Optional.of(order);

        Mockito.when(orderService.createOrder(Mockito.any())).thenReturn(optionalOrder);

        mockMvc.perform(post("http://localhost:8080/order")
                        .contentType("application/json").content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void createOrderBadRequestTest() throws Exception {
        OrderDTO orderDto = fileLoader.getOrder();
        orderDto.setOrderId(new UUID(0L, 0L));
        orderDto.getDeliveryAddress().setAddressId(new UUID(0L, 0L));
        Order order = OrderMapper.INSTANCE.OrderDtoToOrder(orderDto);

        Mockito.when(orderService.createOrder(Mockito.any())).thenReturn(Optional.empty());

        mockMvc.perform(post("http://localhost:8080/order")
                        .contentType("application/json").content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void updateOrderTest() throws Exception {
        OrderDTO orderDto = fileLoader.getOrder();
        orderDto.setOrderId(new UUID(0L, 0L));
        orderDto.getDeliveryAddress().setAddressId(new UUID(0L, 0L));
        Order order = OrderMapper.INSTANCE.OrderDtoToOrder(orderDto);
        Optional<Order> optionalOrder = Optional.of(order);

        Mockito.when(orderService.updateOrder(Mockito.any())).thenReturn(optionalOrder);

        mockMvc.perform(put("http://localhost:8080/order")
                        .contentType("application/json").content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateOrderBadRequestTest() throws Exception {
        OrderDTO orderDto = fileLoader.getOrder();
        orderDto.setOrderId(new UUID(0L, 0L));
        orderDto.getDeliveryAddress().setAddressId(new UUID(0L, 0L));
        Order order = OrderMapper.INSTANCE.OrderDtoToOrder(orderDto);
        Optional<Order> optionalOrder = Optional.of(order);

        Mockito.when(orderService.updateOrder(Mockito.any())).thenReturn(Optional.empty());

        mockMvc.perform(put("http://localhost:8080/order")
                        .contentType("application/json").content(objectMapper.writeValueAsString(orderDto)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void searchOrderTest() throws Exception {
        ClientDTO clientDTO = fileLoader.getClient();
        OrderDTO orderDTO = fileLoader.getOrder();
        Order order = OrderMapper.INSTANCE.OrderDtoToOrder(orderDTO);

        Mockito.when(orderService.searchOrderByClient(Mockito.any())).thenReturn(Arrays.asList(order));

        mockMvc.perform(post("http://localhost:8080/orderByClient")
                        .contentType("application/json").content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void searchOrderBadRequestTest() throws Exception {
        ClientDTO clientDTO = fileLoader.getClient();

        Mockito.when(orderService.searchOrderByClient(Mockito.any())).thenReturn(new ArrayList<>());

        mockMvc.perform(post("http://localhost:8080/orderByClient")
                        .contentType("application/json").content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().is4xxClientError());
    }


}
