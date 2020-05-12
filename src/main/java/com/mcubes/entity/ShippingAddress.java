package com.mcubes.entity;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 4/15/2020.
 */
@Entity
public class ShippingAddress {

    @Id
    @SequenceGenerator(name = "shipping_address_sequence", sequenceName = "shipping_address_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipping_address_sequence")
    private long id;
    private String address;
    private String city;
    private String country;
    private String postalCode;
    private String contact;


    public ShippingAddress() {
    }

    public ShippingAddress(String address, String city, String country, String postalCode, String contact) {
        this.address = address;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.contact = contact;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "ShippingAddress{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
