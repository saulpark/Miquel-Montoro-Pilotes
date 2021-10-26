package service;

import com.tui.proof.MainApplication;
import com.tui.proof.config.ApplicationConfig;
import com.tui.proof.model.Client;
import com.tui.proof.model.Order;
import com.tui.proof.model.dto.ClientDTO;
import com.tui.proof.model.dto.OrderDTO;
import com.tui.proof.model.mapper.ClientMapper;
import com.tui.proof.model.mapper.OrderMapper;
import com.tui.proof.model.repository.ClientRepository;
import com.tui.proof.model.repository.OrderRepository;
import com.tui.proof.service.ClientService;
import com.tui.proof.service.OrderService;
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

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationConfig.class, MainApplication.class, FileLoader.class})
@Log4j2
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private FileLoader fileLoader;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ClientService clientService;

    @Test
    public void createOrderTest() {
        OrderDTO orderDTO = fileLoader.getOrder();
        orderDTO.setOrderId(null);
        orderDTO.getDeliveryAddress().setAddressId(new UUID(0L, 0L));
        orderDTO.setClientId(UUID.randomUUID().toString());
        Order order = OrderMapper.INSTANCE.OrderDtoToOrder(orderDTO);

        Client client = ClientMapper.INSTANCE.ClientDtoToClient(fileLoader.getClient());

        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(order);
        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.of(client));

        Optional<Order> optionalOrder = orderService.createOrder(orderDTO);

        Assert.assertTrue(optionalOrder.isPresent());

    }

    @Test
    public void createOrderErrorExistingOrderTest() {
        OrderDTO orderDTO = fileLoader.getOrder();
        orderDTO.setOrderId(UUID.randomUUID());
        orderDTO.getDeliveryAddress().setAddressId(new UUID(0L, 0L));
        orderDTO.setClientId(UUID.randomUUID().toString());
        Order order = OrderMapper.INSTANCE.OrderDtoToOrder(orderDTO);

        Client client = ClientMapper.INSTANCE.ClientDtoToClient(fileLoader.getClient());

        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(order);
        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.of(client));

        Optional<Order> optionalOrder = orderService.createOrder(orderDTO);

        Assert.assertTrue(optionalOrder.isEmpty());

    }

    @Test
    public void createOrderInvalidPilatesSizeTest() {
        OrderDTO orderDTO = fileLoader.getOrder();
        orderDTO.setOrderId(null);
        orderDTO.getDeliveryAddress().setAddressId(new UUID(0L, 0L));
        orderDTO.setClientId(UUID.randomUUID().toString());
        orderDTO.setPilotes(2);
        Order order = OrderMapper.INSTANCE.OrderDtoToOrder(orderDTO);

        Client client = ClientMapper.INSTANCE.ClientDtoToClient(fileLoader.getClient());

        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(order);
        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.of(client));

        Optional<Order> optionalOrder = orderService.createOrder(orderDTO);

        Assert.assertTrue(optionalOrder.isEmpty());
    }

    @Test
    public void createOrderWrongPostalCodeFormatTest() {
        OrderDTO orderDTO = fileLoader.getOrder();
        orderDTO.setOrderId(null);
        orderDTO.getDeliveryAddress().setAddressId(new UUID(0L, 0L));
        orderDTO.setClientId(UUID.randomUUID().toString());
        orderDTO.getDeliveryAddress().setPostcode("1");
        Order order = OrderMapper.INSTANCE.OrderDtoToOrder(orderDTO);

        Client client = ClientMapper.INSTANCE.ClientDtoToClient(fileLoader.getClient());

        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(order);
        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.of(client));

        Optional<Order> optionalOrder = orderService.createOrder(orderDTO);

        Assert.assertTrue(optionalOrder.isEmpty());
    }

    @Test
    public void createOrderInvalidClientTest() {
        OrderDTO orderDTO = fileLoader.getOrder();
        orderDTO.setOrderId(null);
        orderDTO.getDeliveryAddress().setAddressId(new UUID(0L, 0L));
        orderDTO.setClientId(UUID.randomUUID().toString());
        Order order = OrderMapper.INSTANCE.OrderDtoToOrder(orderDTO);

        Client client = ClientMapper.INSTANCE.ClientDtoToClient(fileLoader.getClient());

        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(order);
        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Optional<Order> optionalOrder = orderService.createOrder(orderDTO);

        Assert.assertTrue(optionalOrder.isEmpty());
    }

    @Test
    public void updateOrderTest() {
        OrderDTO orderDTO = fileLoader.getOrder();
        orderDTO.setOrderId(UUID.randomUUID());
        orderDTO.getDeliveryAddress().setAddressId(UUID.randomUUID());
        orderDTO.setClientId(UUID.randomUUID().toString());
        orderDTO.setOrderTimestamp(Timestamp.from(Instant.now()));
        Order order = OrderMapper.INSTANCE.OrderDtoToOrder(orderDTO);

        Client client = ClientMapper.INSTANCE.ClientDtoToClient(fileLoader.getClient());

        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(order);
        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(order));
        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.of(client));

        Optional<Order> optionalOrder = orderService.updateOrder(orderDTO);

        Assert.assertTrue(optionalOrder.isPresent());

    }

    @Test
    public void updateOrderTimestampErrorTest() {
        OrderDTO orderDTO = fileLoader.getOrder();
        orderDTO.setOrderId(UUID.randomUUID());
        orderDTO.getDeliveryAddress().setAddressId(UUID.randomUUID());
        orderDTO.setClientId(UUID.randomUUID().toString());
        Order order = OrderMapper.INSTANCE.OrderDtoToOrder(orderDTO);

        Client client = ClientMapper.INSTANCE.ClientDtoToClient(fileLoader.getClient());

        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(order);
        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(order));
        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.of(client));

        Optional<Order> optionalOrder = orderService.updateOrder(orderDTO);

        Assert.assertTrue(optionalOrder.isEmpty());
    }

    @Test
    public void updateOrderClientNotFoundTest() {
        OrderDTO orderDTO = fileLoader.getOrder();
        orderDTO.setOrderId(UUID.randomUUID());
        orderDTO.getDeliveryAddress().setAddressId(UUID.randomUUID());
        orderDTO.setClientId(UUID.randomUUID().toString());
        Order order = OrderMapper.INSTANCE.OrderDtoToOrder(orderDTO);

        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(order);
        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(order));
        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Optional<Order> optionalOrder = orderService.updateOrder(orderDTO);

        Assert.assertTrue(optionalOrder.isEmpty());
    }

    @Test
    public void updateOrderOrderNotFoundTest() {
        OrderDTO orderDTO = fileLoader.getOrder();
        orderDTO.setOrderId(UUID.randomUUID());
        orderDTO.getDeliveryAddress().setAddressId(UUID.randomUUID());
        orderDTO.setClientId(UUID.randomUUID().toString());
        Order order = OrderMapper.INSTANCE.OrderDtoToOrder(orderDTO);

        Client client = ClientMapper.INSTANCE.ClientDtoToClient(fileLoader.getClient());

        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(order);
        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.of(client));

        Optional<Order> optionalOrder = orderService.updateOrder(orderDTO);

        Assert.assertTrue(optionalOrder.isEmpty());
    }

    @Test
    public void searchOrderTest() {
        OrderDTO orderDTO = fileLoader.getOrder();
        orderDTO.setOrderId(UUID.randomUUID());
        orderDTO.getDeliveryAddress().setAddressId(UUID.randomUUID());
        orderDTO.setClientId(UUID.randomUUID().toString());
        Order order = OrderMapper.INSTANCE.OrderDtoToOrder(orderDTO);

        ClientDTO clientDTO = fileLoader.getClient();

        Mockito.when(orderRepository.findByCustomerData(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(Arrays.asList(order));

        List<Order> orderList = orderService.searchOrderByClient(clientDTO);

        Assert.assertFalse(orderList.isEmpty());
    }

    @Test
    public void searchOrderWithoutResultTest() {
        ClientDTO clientDTO = fileLoader.getClient();

        Mockito.when(orderRepository.findByCustomerData(Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(new ArrayList<>());

        List<Order> orderList = orderService.searchOrderByClient(clientDTO);

        Assert.assertTrue(orderList.isEmpty());
    }

}
