package com.mcubes.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

/**
 * Created by A.A.MAMUN on 2/3/2020.
 */
@Entity
//@NoArgsConstructor
//@Setter
//@Getter
@ToString
public class Product {

    @Id
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    private Long product_id;
    private String name;
    private String date;
    private boolean trending;
    private String model;
    @ManyToOne(targetEntity = Brand.class)
    @JoinColumn(name = "brand_id", referencedColumnName = "brand_id")
    private Brand brand;
    @Column(length = 25)
    private int category_id;
    @Column(length = 25)
    private int subcategory_id;
    private double actualPrice; // Only for Admin
    private double price;
    private double old_price;
    @Transient
    private int discount;
    private String weight;
    private long stock;
    @Column(length = 1000)
    private String description;
    @Transient
    private MultipartFile multipartImage;
    @Column(length = 500)
    private String image;
    private int rating;
    private long sell;
    private long viewed;

    @Transient
    private MultipartFile multipartImages[];

    @OneToMany(targetEntity = ProductImage.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private List<ProductImage> productImages;

    /*
    @OneToMany(targetEntity = ProductColor.class, cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private List<ProductColor> productColors;

    @OneToMany(targetEntity = ProductSize.class, cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private List<ProductSize> productSizes;
    */

    @OneToMany(targetEntity = ProductVariant.class, cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private List<ProductVariant> productVariants;


    public Product(){

    }

    public Product(String image, String name, double actualPrice, double price, double old_price, long stock) {
        this.image = image;
        this.name = name;
        this.actualPrice = actualPrice;
        this.price = price;
        this.old_price = old_price;
        this.stock = stock;
    }

    public Product(String name, double price, double old_price, String image, java.util.Collection<ProductColor> productColors) {
        this.name = name;
        this.price = price;
        this.old_price = old_price;
        this.image = image;
        //this.productColors = productColors;
       // this.productSizes = productSizes;
    }

    public Product(String name, double price, double old_price, String image){
        this.name = name;
        this.price = price;
        this.old_price = old_price;
        this.image = image;
    }

    public Product(String name, double price, String image){
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(int subcategory_id) {
        this.subcategory_id = subcategory_id;
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
        this.price =  Math.abs(price);
    }

    public double getOld_price() {
        return old_price;
    }

    public void setOld_price(double old_price) {
        this.old_price = Math.abs(old_price);
    }


    public int getDiscount() {
        if(old_price==0 || price>=old_price)
            return 0;
        this.discount = (int) (((price - old_price)/old_price)*100);
        return this.discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public long getStock() {

        if(productVariants!=null && productVariants.size()!=0){
            int totalStock = 0;
            for(ProductVariant p : productVariants){
                totalStock+=p.getStock();
            }
            this.stock = totalStock;
        }
        return stock;
    }

    public void setStock(long stock) {
        this.stock = Math.abs(stock);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    /*
    public void setProductImages(List<ProductImage> productImages) {
        this.image = productImages.get(0).getImage_url();
        this.productImages = productImages;
    }
    */

    public MultipartFile getMultipartImage() {
        return multipartImage;
    }

    public void setMultipartImage(MultipartFile multipartImage) {
        this.multipartImage = multipartImage;
    }

    public MultipartFile[] getMultipartImages() {
        return multipartImages;
    }

    public void setMultipartImages(MultipartFile[] multipartImages) {
        this.multipartImages = multipartImages;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /*
    public List<ProductColor> getProductColors() {
        return productColors;
    }

    public void setProductColors(List<ProductColor> productColors) {
        this.productColors = productColors;
    }

    public List<ProductSize> getProductSizes() {
        return productSizes;
    }

    public void setProductSizes(List<ProductSize> productSizes) {
        this.productSizes = productSizes;
    }
    */

    public List<ProductVariant> getProductVariants() {
        return productVariants;
    }

    public void setProductVariants(List<ProductVariant> productVariants) {
        this.productVariants = productVariants;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public long getSell() {
        return sell;
    }

    public void setSell(long sell) {
        this.sell = sell;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isTrending() {
        return trending;
    }

    public void setTrending(boolean trending) {
        this.trending = trending;
    }

    public long getViewed() {
        return viewed;
    }

    public void setViewed(long viewed) {
        this.viewed = viewed;
    }
}
