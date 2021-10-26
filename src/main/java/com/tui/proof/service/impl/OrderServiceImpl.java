package com.tui.proof.service.impl;

import com.tui.proof.config.ApplicationConfig;
import com.tui.proof.model.Client;
import com.tui.proof.model.Order;
import com.tui.proof.model.OrderEvent;
import com.tui.proof.model.dto.ClientDTO;
import com.tui.proof.model.dto.OrderDTO;
import com.tui.proof.model.mapper.ClientMapper;
import com.tui.proof.model.mapper.OrderMapper;
import com.tui.proof.model.repository.ClientRepository;
import com.tui.proof.model.repository.OrderRepository;
import com.tui.proof.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService, ApplicationListener<OrderEvent> {

    private static final String EVENT_MESSAGE = "order event: ";
    private static final int POSTAL_CODE_SIZE = 5;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<Order> createOrder(OrderDTO newOrderDTO) {
        Optional<Client> optionalClient = clientRepository.findById(UUID.fromString(newOrderDTO.getClientId()));
        if(optionalClient.isPresent()) {
            Client client = optionalClient.get();
            Order newOrder = getOrderFromDTO(newOrderDTO);
            if (Objects.isNull(newOrder.getOrderId()) && validateOrderSize(newOrder) && validatePostalCode(newOrder.getDeliveryAddress().getPostcode())) {
                Timestamp startTimestamp = Timestamp.from(Instant.now());
                newOrder.setOrderTimestamp(startTimestamp);
                newOrder.setClientId(client);
                return Optional.of(orderRepository.save(newOrder));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Order> updateOrder(OrderDTO updateOrderDTO) {
        Optional<Client> optionalClient = clientRepository.findById(UUID.fromString(updateOrderDTO.getClientId()));
        if(optionalClient.isPresent()) {
            Order updateOrder = getOrderFromDTO(updateOrderDTO);
            Timestamp finalTimestamp = Timestamp.from(Instant.now());
            Optional<Order> optionalOrder = orderRepository.findById(updateOrder.getOrderId());
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                Long timeToBlock = applicationConfig.getOrderTimeToBlock();
                if (checkTimestamp(order, finalTimestamp, timeToBlock) && validateOrderSize(updateOrder)) {
                    updateOrder.setClientId(optionalClient.get());
                    return Optional.of(orderRepository.save(updateOrder));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Order> searchOrderByClient(ClientDTO clientDTO) {
        Client client =  ClientMapper.INSTANCE.ClientDtoToClient(clientDTO);

        return orderRepository.findByCustomerData(client.getFirstName().isEmpty()?null: client.getFirstName(),
                client.getLastName().isEmpty()?null: client.getLastName(),
                client.getTelephone().isEmpty()?null:client.getTelephone());
    }

    private boolean checkTimestamp(Order order, Timestamp finalTimestamp, Long timeToBlock) {
        Timestamp blockTimeStamp = new Timestamp(order.getOrderTimestamp().getTime() + TimeUnit.MINUTES.toMillis(timeToBlock));
        if(finalTimestamp.after(blockTimeStamp)){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private boolean validateOrderSize(Order order) {
        if(!applicationConfig.getOrderSize().contains(order.getPilotes())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private Order getOrderFromDTO(OrderDTO orderDTO) {
        return OrderMapper.INSTANCE.OrderDtoToOrder(orderDTO);
    }

    private boolean validatePostalCode(String postcode) {
        try {
            Integer.parseInt(postcode);
            if(postcode.length()==applicationConfig.getPostalCodeSize()){
                return true;
            }
        }catch (NumberFormatException e){
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public void onApplicationEvent(OrderEvent orderEvent) {
        log.info(EVENT_MESSAGE + orderEvent.getMessage());
    }
}
