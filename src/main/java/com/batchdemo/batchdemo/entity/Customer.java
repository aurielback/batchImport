package com.batchdemo.batchdemo.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Table(name = "CUSTOMERS_INFO")
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstname;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "CONTACT")
    private String contactNo;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "DOB")
    private String dob;

    public Customer(Long id, String firstname, String lastName, String email, String gender, String contactNo, String country, String dob) {
        this.id = id;
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.contactNo = contactNo;
        this.country = country;
        this.dob = dob;
    }

    public Customer(String firstname, String lastName, String email, String gender, String contactNo, String country, String dob) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.contactNo = contactNo;
        this.country = country;
        this.dob = dob;
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(firstname, customer.firstname) && Objects.equals(lastName, customer.lastName) && Objects.equals(email, customer.email) && Objects.equals(gender, customer.gender) && Objects.equals(contactNo, customer.contactNo) && Objects.equals(country, customer.country) && Objects.equals(dob, customer.dob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastName, email, gender, contactNo, country, dob);
    }
}
