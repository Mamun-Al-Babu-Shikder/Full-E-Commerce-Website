package com.mcubes.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by A.A.MAMUN on 2/23/2020.
 */
@Entity
@ToString
public class CustomerOrder {

    @Transient
    private static final DecimalFormat decimalFormat = new DecimalFormat("##.##");

    @Transient
    public static final String  PENDING  = "Pending"
            , SUCCESS = "Success"
            , FAILED = "Failed";


    @Id
    @SequenceGenerator(name = "CustomerOrderSequence", sequenceName = "CustomerOrderSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CustomerOrderSequence")
    private long orderId;
    private String orderNumber;
    private String userName;
    private String userEmail;
    @OneToOne(targetEntity = ShippingAddress.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "shippingAddressId")
    private ShippingAddress shippingAddress;
    @OneToMany(targetEntity = OrderItem.class, cascade = {CascadeType.ALL})
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private List<OrderItem> orderItems;
    @Column(length = 1500)
    private String note;
    @Column(length = 50)
    private String date;
    @Column(length = 15)
    private String status;
    private String method;
    private double serviceCharge;
    private double totalActualPrice;
    private double totalPrice;
    private double totalOldPrice;
    @Transient
    private double total;
    @Transient
    double profit;
    @Transient
    private double profitPercentage;
    @Transient
    private double discount;
    @Transient
    private double discountPercentage;

    public CustomerOrder() {
    }

    public CustomerOrder(long orderId, String orderNumber, String date, String status , double totalPrice
            , double totalOldPrice, double serviceCharge) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.date = date;
        this.status = status;
        this.serviceCharge = serviceCharge;
        this.totalPrice = totalPrice;
        this.totalOldPrice = totalOldPrice;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public double getTotalActualPrice() {
        return totalActualPrice;
    }

    public void setTotalActualPrice(double totalActualPrice) {
        this.totalActualPrice = totalActualPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalOldPrice() {
        return totalOldPrice;
    }

    public void setTotalOldPrice(double totalOldPrice) {
        this.totalOldPrice = totalOldPrice;
    }

    public double getTotal() {
        this.total = totalPrice + serviceCharge;
        return total;
    }

    private void setTotal(double total) {
        this.total = total;
    }

    public double getProfit() {
        this.profit = this.totalPrice - this.totalActualPrice;
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getProfitPercentage() {
        if(this.totalActualPrice>0) {
            this.profitPercentage = (this.getProfit() / this.totalActualPrice) * 100;
            return Double.parseDouble(decimalFormat.format(profitPercentage));
        }
        return 0;
    }

    public void setProfitPercentage(double profitPercentage) {
        this.profitPercentage = profitPercentage;
    }


    public double getDiscount() {
        this.discount = this.totalOldPrice - this.totalPrice;
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscountPercentage() {
        if(this.totalOldPrice>0){
            this.discountPercentage = (this.getDiscount() / this.totalOldPrice) * 100;
            return Double.parseDouble(decimalFormat.format(discountPercentage));
        }
        return 0;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
