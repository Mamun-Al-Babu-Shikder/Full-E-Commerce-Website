package com.mcubes.entity;

import lombok.ToString;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 2/29/2020.
 */
@Entity
public class ProductVariant {

    @Id
    @SequenceGenerator(name = "product_variant_sequence", sequenceName = "product_variant_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_variant_sequence")
    private Long productVariantId;
    private long product_id;
    private long colorId;
    private long sizeId;
    private int stock;
    private double actualPrice;
    private double price;
    private double old_price;

    public ProductVariant() {
    }

    public ProductVariant(long colorId, long sizeId) {
        this.colorId = colorId;
        this.sizeId = sizeId;
    }

    public ProductVariant(double actualPrice, double price, double old_price, int stock) {
        this.actualPrice = actualPrice;
        this.price = price;
        this.old_price = old_price;
        this.stock = stock;
    }

    public Long getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(Long productVariantId) {
        this.productVariantId = productVariantId;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public long getColorId() {
        return colorId;
    }

    public void setColorId(long colorId) {
        this.colorId = colorId;
    }

    public long getSizeId() {
        return sizeId;
    }

    public void setSizeId(long sizeId) {
        this.sizeId = sizeId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = Math.abs(stock);
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = Math.abs(actualPrice);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = Math.abs(price);
    }


    public double getOld_price() {
        return old_price;
    }

    public void setOld_price(double old_price) {
        this.old_price = Math.abs(old_price);
    }

    @Override
    public String toString() {
        return "ProductVariant{" +
                "productVariantId=" + productVariantId +
                ", product_id=" + product_id +
                ", colorId=" + colorId +
                ", sizeId=" + sizeId +
                ", stock=" + stock +
                ", actualPrice=" + actualPrice +
                ", price=" + price +
                ", old_price=" + old_price +
                '}';
    }
}
