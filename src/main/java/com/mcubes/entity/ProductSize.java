package com.mcubes.entity;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 2/26/2020.
 */
@Entity
public class ProductSize {

    @Id
    @SequenceGenerator(name = "product_size_sequence", sequenceName = "product_size_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_size_sequence")
    private long productSizeId;
    private String sizeName;

    public ProductSize() {
    }

    public ProductSize(long productSizeId, String sizeName) {
        this.productSizeId = productSizeId;
        this.sizeName = sizeName;
    }

    public ProductSize(String sizeName) {
        this.sizeName = sizeName;
    }

    public long getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(long productSizeId) {
        this.productSizeId = productSizeId;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }


    @Override
    public String toString() {
        return "ProductSize{" +
                "productSizeId=" + productSizeId +
                ", sizeName='" + sizeName + '\'' +
                '}';
    }
}
