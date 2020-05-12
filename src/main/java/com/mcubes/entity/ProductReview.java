package com.mcubes.entity;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 3/1/2020.
 */
@Entity
public class ProductReview {

    @Id
    @SequenceGenerator(name = "product_review_sequence", sequenceName = "product_review_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_review_sequence")
    private Long productReviewId;
    private String userEmail;
    private String userName;
    private String title;
    @Column(length = 1510)
    private String description;
    private int rating;
    private String date;
    private long productId;

    public ProductReview() {
    }

    public Long getProductReviewId() {
        return productReviewId;
    }

    public void setProductReviewId(Long productReviewId) {
        this.productReviewId = productReviewId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "ProductReview{" +
                "productReviewId=" + productReviewId +
                ", userEmail='" + userEmail + '\'' +
                ", userName='" + userName + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", date='" + date + '\'' +
                ", productId=" + productId +
                '}';
    }
}
