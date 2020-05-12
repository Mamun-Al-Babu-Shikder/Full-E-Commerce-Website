package com.mcubes.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by A.A.MAMUN on 2/5/2020.
 */
@Entity
public class ProductCategory {

    @Id
    @SequenceGenerator(name = "category_sequence", sequenceName = "category_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
    private Integer category_id;
    private String name;
    private String icon;
    @OneToMany(targetEntity = SubProductCategory.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private List<SubProductCategory> subProductCategories;

    public ProductCategory() {
    }

    public ProductCategory(int category_id, String name, String icon) {
        this.category_id = category_id;
        this.name = name;
        this.icon = icon;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public List<SubProductCategory> getSubProductCategories() {
        if(subProductCategories==null)
            subProductCategories = new ArrayList<>();
        return subProductCategories;
    }

    public void setSubProductCategories(List<SubProductCategory> subProductCategories) {
        this.subProductCategories = subProductCategories;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "category_id=" + category_id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", subProductCategories=" + subProductCategories +
                '}';
    }
}
