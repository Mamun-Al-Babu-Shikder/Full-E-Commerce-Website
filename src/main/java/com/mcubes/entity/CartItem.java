package com.mcubes.entity;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 2/21/2020.
 */
@Entity
public class CartItem {


    @Id
    @SequenceGenerator(name = "cart_item_sequence", sequenceName = "cart_item_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_item_sequence")
    private Long cartItem_id;
    private String userEmail;
    private Long product_id;
    private int quantity;
    private long productColorId;
    private long productSizeId;


    @Transient
    private String name;
    @Transient
    private double price;
    @Transient
    private String image;
    @Transient
    private double costPerItem;
    @Transient
    private String colorName;
    @Transient
    private String sizeName;


    public CartItem() {
    }

    public Long getCartItem_id() {
        return cartItem_id;
    }

    public void setCartItem_id(Long cartItem_id) {
        this.cartItem_id = cartItem_id;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = Math.abs(quantity);
    }

    public long getProductColorId() {
        return productColorId;
    }

    public void setProductColorId(long productColorId) {
        this.productColorId = productColorId;
    }

    public long getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(long productSizeId) {
        this.productSizeId = productSizeId;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getCostPerItem() {
        return costPerItem;
    }

    public void setCostPerItem(double costPerItem) {
        this.costPerItem = costPerItem;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartItem_id=" + cartItem_id +
                ", userEmail='" + userEmail + '\'' +
                ", product_id=" + product_id +
                ", quantity=" + quantity +
                ", productColorId=" + productColorId +
                ", productSizeId=" + productSizeId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", costPerItem=" + costPerItem +
                ", colorName='" + colorName + '\'' +
                ", sizeName='" + sizeName + '\'' +
                '}';
    }
}

