package com.mcubes.entity;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 2/5/2020.
 */
@Entity
public class Brand {

    @Id
    @SequenceGenerator(name = "brand_sequence", sequenceName = "brand_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_sequence")
    private Integer brand_id;
    private String name;
    private String image;

    public Brand() {
    }

    public Brand(int brand_id, String name){
        this.brand_id = brand_id;
        this.name = name;
    }

    public Brand(String name){
        this.name = name;
    }

    public Integer getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(Integer brand_id) {
        this.brand_id = brand_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "brand_id=" + brand_id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
