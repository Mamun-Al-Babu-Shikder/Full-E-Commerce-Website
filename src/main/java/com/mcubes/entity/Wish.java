package com.mcubes.entity;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 2/21/2020.
 */
@Entity
public class Wish {

    @Id
    @SequenceGenerator(name = "wish_sequence", sequenceName = "wish_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wish_sequence")
    private Long wish_id;
    private String userEmail;
    private Long product_id;

    @Transient
    private String image_url;
    @Transient
    private String name;
    @Transient
    private double price;
    @Transient
    private double old_price;
    @Transient
    private boolean hasOption;


    public Wish() {
    }

    public Long getWish_id() {
        return wish_id;
    }

    public void setWish_id(Long wish_id) {
        this.wish_id = wish_id;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }


    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOld_price() {
        return old_price;
    }

    public void setOld_price(double old_price) {
        this.old_price = old_price;
    }

    public boolean isHasOption() {
        return hasOption;
    }

    public void setHasOption(boolean hasOption) {
        this.hasOption = hasOption;
    }

    @Override
    public String toString() {
        return "Wish{" +
                "wish_id=" + wish_id +
                ", userEmail='" + userEmail + '\'' +
                ", product_id=" + product_id +
                ", image_url='" + image_url + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", old_price=" + old_price +
                ", hasOption=" + hasOption +
                '}';
    }
}
