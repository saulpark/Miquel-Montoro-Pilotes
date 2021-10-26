package com.tui.proof.model;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import java.util.UUID;

@Data
@Entity
@Table(name = "address")
public class Address {

  @Id
  @Column(name = "address_id")
  @GeneratedValue
  private UUID addressId;

  @Column(name = "street")
  private String street;

  @Column(name = "postcode")
  private String postcode;

  @Column(name = "city")
  private String city;

  @Column(name = "country")
  private String country;
}
