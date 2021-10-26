package com.tui.proof.model.dto;

import com.tui.proof.model.Address;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class OrderDTO {

    private String clientId;

    private UUID orderId;

    private String number;

    private int pilotes;

    private double orderTotal;

    private Timestamp orderTimestamp;

    private Address deliveryAddress;
}
