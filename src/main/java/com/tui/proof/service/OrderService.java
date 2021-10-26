package com.tui.proof.service;

import com.tui.proof.model.Client;
import com.tui.proof.model.Order;
import com.tui.proof.model.dto.ClientDTO;
import com.tui.proof.model.dto.OrderDTO;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    public Optional<Order> createOrder(OrderDTO orderDTO);

    public Optional<Order> updateOrder(OrderDTO orderDTO);

    public List<Order> searchOrderByClient(ClientDTO clientDTO);

}
