package com.hatt.shoppingcartdemo.model;

import com.hatt.shoppingcartdemo.entity.Product;

public class ProductInfo {
    private int productId;
    private String name;
    private double price;
    private Integer colourId;
    private String colourName;

    public ProductInfo() {
    }

    public ProductInfo(Product product) {
        this.productId = product.getProductId();
        this.name = product.getName();
        this.price = product.getPrice();
    }

    // Using in JPA/Hibernate query
    public ProductInfo(int productId, String name, double price, Integer colourId, String colourName) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.colourId = colourId;
        this.colourName = colourName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public Integer getColourId() {
        return colourId;
    }

    public void setColourId(Integer colourId) {
        this.colourId = colourId;
    }

    public String getColourName() {
        return colourName;
    }

    public void setColourName(String colourName) {
        this.colourName = colourName;
    }
}