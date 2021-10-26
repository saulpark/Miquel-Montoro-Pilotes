package com.tui.proof.service.impl;

import com.tui.proof.model.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishOrderEvent(final String message) {
        OrderEvent customSpringEvent = new OrderEvent(this, message);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }

}
