package com.mcubes.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 2/3/2020.
 */
@Entity
@ToString
public class ProductImage {

    @Id
    @SequenceGenerator(name = "product_image_sequence", sequenceName = "product_image_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_image_sequence")
    private Long id;
    private long product_id;
    @Column(length = 200)
    private String image_url;

    public ProductImage() {
    }

    public ProductImage(long product_id, String image_url) {
        this.product_id = product_id;
        this.image_url = image_url;
    }

    public ProductImage(String image_url) {
        this.image_url = image_url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return "ProductImage{" +
                "id=" + id +
                ", product_id=" + product_id +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}
