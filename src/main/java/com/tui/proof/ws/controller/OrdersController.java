package com.tui.proof.ws.controller;

import com.tui.proof.config.ApplicationConfig;
import com.tui.proof.model.Order;
import com.tui.proof.model.dto.ClientDTO;
import com.tui.proof.model.dto.OrderDTO;
import com.tui.proof.service.OrderService;
import com.tui.proof.service.impl.EventPublisher;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private EventPublisher eventPublisher;


    @GetMapping("/")
    public void test() {
        log.info("Foo controller");
    }

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO){
        Optional<Order> optionalOrder = orderService.createOrder(orderDTO);
        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            eventPublisher.publishOrderEvent(applicationConfig.getCreated()+order.getOrderId().toString());
            return new ResponseEntity<>(order, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/order")
    public ResponseEntity<Order> updateOrder(@RequestBody OrderDTO orderDTO){
        Optional<Order> optionalOrder = orderService.updateOrder(orderDTO);
        if(optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            eventPublisher.publishOrderEvent(applicationConfig.getUpdated()+order.getOrderId().toString());
            return new ResponseEntity<>(order, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/orderByClient")
    public ResponseEntity<List<Order>> searchOrder(@RequestBody ClientDTO clientDTO){
        List<Order> orders = orderService.searchOrderByClient(clientDTO);
        if(orders.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
    }
}
