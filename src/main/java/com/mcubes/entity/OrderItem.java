package com.mcubes.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 2/23/2020.
 */
@Entity
public class OrderItem {

    @Id
    @SequenceGenerator(name = "OrderItemSequence", sequenceName = "OrderItemSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OrderItemSequence")
    private long orderItemId;
    private long productId;
    private String productImage;
    private String productName;
    private int quantity;
    private double actualPrice;
    private double price;
    private double old_price;
    private long productColorId;
    private String colorName;
    private String colorCode;
    private long productSizeId;
    private String sizeName;
    @Transient
    private double amount;

    public OrderItem() {
    }

    public long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
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

    public long getProductColorId() {
        return productColorId;
    }

    public void setProductColorId(long productColorId) {
        this.productColorId = productColorId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
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

    public double getAmount() {
        return this.price * this.quantity;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemId=" + orderItemId +
                ", productId=" + productId +
                ", productImage='" + productImage + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", actualPrice=" + actualPrice +
                ", price=" + price +
                ", old_price=" + old_price +
                ", productColorId=" + productColorId +
                ", colorName='" + colorName + '\'' +
                ", colorCode='" + colorCode + '\'' +
                ", productSizeId=" + productSizeId +
                ", sizeName='" + sizeName + '\'' +
                ", amount=" + amount +
                '}';
    }
}
