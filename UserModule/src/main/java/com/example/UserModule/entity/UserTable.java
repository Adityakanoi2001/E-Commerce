package com.example.UserModule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserTable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email")
  private String email;

  @Column(name = "usn")
  private String usn;

  @Column(name = "password")
  private String password;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "city")
  private String city;

  @Column(name = "state")
  private String state;

  public UserTable(String firstName, String lastName, String email, String usn, String encryptedPassword, String city,
      String state, String phoneNumber) {
    if (!isValidPhoneNumber(phoneNumber)) {
      throw new IllegalArgumentException("Invalid phone number");
    }
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.usn = usn;
    this.password = encryptedPassword;
    this.city = city;
    this.state = state;
    this.phoneNumber = phoneNumber;
  }

  private boolean isValidPhoneNumber(String phoneNumber) {
    String regex = "\\d{10}";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(phoneNumber);
    return matcher.matches();
  }
}
