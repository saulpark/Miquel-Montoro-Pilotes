package com.tui.proof.model;

import org.springframework.context.ApplicationEvent;

public class OrderEvent extends ApplicationEvent {

    private String message;

    public OrderEvent(Object source, String eventMessage) {
        super(source);
        this.message = eventMessage;
    }

    public String getMessage(){
        return this.message;
    }
}
