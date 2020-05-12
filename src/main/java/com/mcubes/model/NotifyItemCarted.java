package com.mcubes.model;

/**
 * Created by A.A.MAMUN on 4/25/2020.
 */
public class NotifyItemCarted {

    private String customerName;
    private long productId;
    private String productName;
    private String productImage;

    public NotifyItemCarted() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    @Override
    public String toString() {
        return "NotifyItemCarted{" +
                "customerName='" + customerName + '\'' +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productImage='" + productImage + '\'' +
                '}';
    }
}
