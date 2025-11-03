package com.example.UserModule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class AuthenticationToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  private String token;

  @Column(name = "created_date")
  private Date createdDate;

  @OneToOne(targetEntity = UserTable.class, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "user_id")
  private UserTable user;


  public AuthenticationToken(UserTable user) {
    this.user = user;
    this.createdDate = new Date();
    this.token = UUID.randomUUID().toString();
  }

  public AuthenticationToken() {
  }
}
