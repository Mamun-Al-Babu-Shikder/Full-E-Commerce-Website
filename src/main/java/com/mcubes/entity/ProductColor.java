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
public class ProductColor {

    @Id
    @SequenceGenerator(name = "product_color_sequence", sequenceName = "product_color_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_color_sequence")
    private long productColorId;
    private String color;
    private String colorCode;

    public ProductColor() {
    }

    public ProductColor(long productColorId,String colorName, String colorCode) {
        this.productColorId = productColorId;
        this.color = colorName;
        this.colorCode = colorCode;
    }

    public ProductColor(String colorName, String colorCode) {
        this.color = colorName;
        this.colorCode = colorCode;
    }

    public long getProductColorId() {
        return productColorId;
    }

    public void setProductColorId(long productColorId) {
        this.productColorId = productColorId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    @Override
    public String toString() {
        return "ProductColor{" +
                "productColorId=" + productColorId +
                ", color='" + color + '\'' +
                ", colorCode='" + colorCode + '\'' +
                '}';
    }
}
