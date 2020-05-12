package com.mcubes.entity;

import javax.persistence.*;

/**
 * Created by A.A.MAMUN on 2/5/2020.
 */
@Entity
public class SubProductCategory {

    @Id
    @SequenceGenerator(name = "subcategory_sequence", sequenceName = "subcategory_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subcategory_sequence")
    private Integer subcategory_id;
    private int category_id;
    private String name;

    public SubProductCategory() {
    }

    public SubProductCategory(int subcategory_id, int category_id, String name) {
        this.subcategory_id = subcategory_id;
        this.category_id = category_id;
        this.name = name;
    }

    public Integer getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(Integer subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "SubProductCategory{" +
                "subcategory_id=" + subcategory_id +
                ", category_id=" + category_id +
                ", name='" + name + '\'' +
                '}';
    }
}
