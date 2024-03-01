package com.example.ecommerce.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Data
@Getter
@Setter
@Entity
@Table(name = "users")
public class UserTable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    public UserTable(String firstName, String lastName, String email, String password ,String city, String state,String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.city=city;
        this.state=state;
        this.phoneNumber=phoneNumber;
    }
    public UserTable(){

}
}
