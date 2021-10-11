package com.hatt.shoppingcartdemo.model;

import com.hatt.shoppingcartdemo.entity.Brand;

public class BrandInfo {
    private Integer brandId;
    private String name;
    private Integer priority;

    public BrandInfo() {
    }

    public BrandInfo(Brand brand) {
        this.brandId = brand.getBrandId();
        this.name = brand.getName();
        this.priority = brand.getPriority();
    }

    // Using in JPA/Hibernate query
    public BrandInfo(Integer brandId, String name, Integer priority) {
        this.brandId = brandId;
        this.name = name;
        this.priority = priority;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
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