package com.tui.proof.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "client")
public class Client {

  @Id
  @Column(name = "client_id")
  @GeneratedValue
  private UUID clientId;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "telephone")
  //@Pattern(regexp="^\\d{9}$")
  private String telephone;

}
