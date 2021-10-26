package com.tui.proof.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
public class Order {

  @Id
  @Column(name ="order_id")
  @GeneratedValue
  private UUID orderId;

  @Column(name = "order_number")
  private String number;

  @Column(name = "pilotes")
  private int pilotes;

  @Column(name = "order_total")
  @PositiveOrZero
  private double orderTotal;

  @Column(name = "order_timestamp")
  private Timestamp orderTimestamp;

  @ManyToOne
  @JoinColumn(name = "client_id")
  private Client clientId;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "address_id")
  private Address deliveryAddress;

}
