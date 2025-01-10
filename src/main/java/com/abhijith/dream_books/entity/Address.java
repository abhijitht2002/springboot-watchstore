package com.abhijith.dream_books.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int address_id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @Column(name = "name")
    private String name;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "addressline")
    private String addressline;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zipcode")
    private String zipcode;

    public Address(){}

    public Address(Orders order, String name, String mobile, String addressline, String city, String state, String zipcode) {
        this.order = order;
        this.name = name;
        this.mobile = mobile;
        this.addressline = addressline;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddressline() {
        return addressline;
    }

    public void setAddressline(String addressline) {
        this.addressline = addressline;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "address_id=" + address_id +
                ", order=" + order +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", addressline='" + addressline + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}
