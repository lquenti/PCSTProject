package com.softwaretesting.testing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
@Table(name = "CUSTOMER")
@JsonIgnoreProperties(allowGetters = true)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @NotBlank
    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @NotBlank
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "PHONE_NUMBER", nullable = false, unique = true)
    private String phoneNumber;

    public Customer(Long id, String userName, String name, String phoneNumber) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {return userName;}

    public void setUserName(String userName) {this.userName = userName;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id.equals(customer.id) && userName.equals(customer.userName) && name.equals(customer.name) && phoneNumber.equals(customer.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, name, phoneNumber);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
