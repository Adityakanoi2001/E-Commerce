package com.example.UserModule.entity;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


import com.example.UserModule.helper.Constants;
import com.example.UserModule.helper.UserActivity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "insights")
public class UserInsights {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @OneToOne(targetEntity = UserTable.class, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "user_id")
  private UserTable user;

  @Column(name = "account_creation_date")
  private Date accountCreationDate;

  @Column(name = "first_login_date")
  private Date firstLoginDate;

  @Column(name = "last_login_date")
  private Date lastLoginDate;

  @Column(name = "location")
  private String location;

  @Column(name = "device")
  private String device;

  @Column(name = "password_reset_attempts")
  private int passwordResetAttempts;

  @Column(name = "live_user_flag")
  private boolean userLiveFlag;

  @Column(name = "user_activity")
  private String userActivity;


  public UserInsights(UserTable user) {
    this.user = user;
    this.accountCreationDate = new Date();
    this.passwordResetAttempts = Constants.PASSWORD_ATTEMPTS;
    this.firstLoginDate = null;
    this.userActivity = UserActivity.FRESH.getName();
  }
}
