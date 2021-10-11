package com.hatt.shoppingcartdemo.model;

import com.hatt.shoppingcartdemo.entity.Catagory;
import java.util.Set;

public class CatagoryInfo {
    private String catId;
    private String name;
    private Integer priority;

    public CatagoryInfo() {
    }

    public CatagoryInfo(Catagory catagory) {
        this.catId = catagory.getCatId();
        this.name = catagory.getName();
        this.priority = catagory.getPriority();
    }

    // Using in JPA/Hibernate query
    public CatagoryInfo(String catId, String name, Integer priority) {
        this.catId = catId;
        this.name = name;
        this.priority = priority;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}